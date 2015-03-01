var express          = require('express'),
    restful          = require('node-restful'),
    mongoose         = restful.mongoose,
    mongoosePaginate = require('mongoose-query-paginate'),
    lockFile         = require('lockfile');

GLOBAL.app = express();

var Challenge       = mongoose.model('Challenge');
var Comment         = mongoose.model('Comment');
var Like            = mongoose.model('Like');
var Reply           = mongoose.model('Reply');


// Helper functions used below
function evaluateReplyError(res, err, reply) {
    if (err) {
        res.send(err, 400);
        return true;
    }
    if (!reply) {
        res.send({result : 'failure', errors: ['Invalid reply.']}, 400);
        return true;
    }
}

module.exports = {
    feed:
        // get all replies to latest challenge
        function (req, res) {
            // If the user specified the page size then use it, else use a default value
            var pageSize;
            if(req.query.pageSize) {
                pageSize = req.query.pageSize;
            } else {
                pageSize = 3;
            }

            // If the user specified a certain page then return that page, else return the first page
            var page;
            if(req.query.page) {
                page = req.query.page;
            } else {
                page = 1;
            }

            // Options for pagination. Don't know what "delta" means!!!
            var options = {
                    perPage: pageSize,
                    delta  : 3,
                    page   : page
            };

            Challenge.findOne().sort('-date').exec(
                function(err, challenge) {
                     // Find the replies to the most recent challenge 
                    var query = Reply.find({challenge_id : challenge._id}, '-likes -comments');
                    
                    // Paginate the results based on the options built above
                    query.paginate(options, function(error, paginatedResults) {
                        if(error) {
                            res.send(error, 400);
                        } else {
                            // Add host prefix to static resources
                            var host = app.get('host');
                            for (i = 0; i < paginatedResults.results.length; i++) {
                                paginatedResults.results[i].thumb_url = host + paginatedResults.results[i].thumb_url;
                            }
                            res.json(paginatedResults);
                        }
                    });
            });
        },

    createReply:
        // create a new reply to latest challenge
        function (req, res) {

        },


    replyGetComments:
        // get reply comment (+ number of likes for each comment)
        function (req, res) {
            Reply.findOne({ _id : req.params._reply_id }, function(err, reply) {
                // Evaluate possible errors
                if (evaluateReplyError(res, err, reply))
                    return;

                // Also add number of likes to each comment
                comments = [];
                for (i = 0; i < reply.comments.length; i++) {
                    comment = JSON.parse(JSON.stringify(reply.comments[i]));
                    comment['num_likes'] = reply.comments[i].likes.length;
                    comments.push(comment);
                }
                res.json(comments);

            });
        },

    replyAddComment:
        // Add a comment to a reply
        function (req, res) {
            Reply.findOne({ _id : req.body._reply_id }, function(err, reply) {
                // Evaluate possible errors
                if (evaluateReplyError(res, err, reply))
                    return;

                var comment = new Comment();
                // TODO: replace with username from registration
                comment.username = 'gigel';
                comment.comment = req.body.comment;
                comment.date = new Date();
                reply.comments.push(comment);
                reply.save();

                res.json({result: 'success'});
            });
        },

    replyGetLikes:
        // Get likes for a reply
        function (req, res) {
            console.log(new require('mongoose').Types.ObjectId(req.params._reply_id));
            Reply.findOne({ _id : require('mongoose').Types.ObjectId(req.params._reply_id) }, function(err, reply) {
                // Evaluate possible errors
                if (evaluateReplyError(res, err, reply))
                    return;

                res.json({ likes : reply.likes, num_likes: reply.likes.length });
            });
        },

    replyAddLike:
        // Add a like to a reply
        function (req, res) {
            Reply.findOne({ _id : req.body._reply_id }, function(err, reply) {
                // Evaluate possible errors
                if (evaluateReplyError(res, err, reply))
                    return;

                var lockFileName = reply._id + '.lock';
                var errors = [];

                // Do not allow multiple likes from the same user.
                // Acquire a lock for this reply until like is saved.
                lockFile.lock(lockFileName, {}, function(err) {
                    for (i = 0; i < reply.likes.length; i++) {
                        if (reply.likes[i].username == 'gigel') {
                            errors.push('Cannot like multiple times.');
                            break;
                        }
                    }

                    if (errors.length == 0) {
                        var like = new Like();
                        // TODO: replace with username from registration
                        like.username = 'gigel';
                        like.date = new Date();
                        reply.likes.push(like);
                        reply.save();
                        res.json({result: 'success'});
                    }
                    else {
                        res.send({result: 'failure', errors: errors}, 400);
                    }

                    // Release lock
                    lockFile.unlock(lockFileName, function (err) {
                        if (err)
                            console.log(lockFileName + ' unlocking error.');
                    });
                });
                
            });
        },

    replyCommentAddLike:
        // like a reply comment
        function (req, res) {
            Reply.findOne({ _id : req.body._reply_id }, function(err, reply) {
                // Evaluate possible errors
                if (evaluateReplyError(res, err, reply))
                    return;

                var errors = [];

                // Search for comment
                var found = false;
                for (i = 0; i < reply.comments.length; i++) {
                    if (reply.comments[i]._id == req.body._comment_id) {
                        found = true;
                        var lockFileName = reply.comments[i]._id + '.lock';
                        // Do not allow multiple likes from the same user.
                        // Acquire a lock for this comment, until like is saved.
                        var index = i;
                        lockFile.lock(lockFileName, function(err) {
                            for (var j = 0; j < reply.comments[index].likes.length; j++) {
                                if (reply.comments[index].likes[j].username == 'gigel') {
                                    errors.push('Cannot like multiple times.');
                                    break;
                                }
                            }

                            if (errors.length == 0) {
                                var like = new Like();

                                // TODO: replace with username from registration
                                like.username = 'gigel';

                                like.date = new Date();
                                reply.comments[index].likes.push(like);
                                reply.save();
                                res.json({result: 'success'});
                            }
                            else {
                                res.send({result: 'failure', errors: errors }, 400);
                            }

                            // Release lock
                            lockFile.unlock(lockFileName, function (err) {
                                if (err)
                                    console.log(lockFileName + ' unlocking error.');
                            });
                        });
                    }
                }

                if (!found) {
                    errors.push('Comment does not exist.');
                    res.send({result: 'failure', errors: errors }, 400);
                }
            });
        },

    canCreateChallenge:
        // determines if the authenticated user won last challenge
        function (req, res) {
        },

    challenge:
        // get latest challenge (+ number of likes and comments)
        function (req, res) {
        },

    createChallenge:
        // create a challenge
        function (req, res) {
        },

    challengeComments:
        // get challenge comments (+ number of likes)
        function (req, res) { 
        },

    challengeLike:
        // like a challenge
        function (req, res) {
        },

    challengeCommentLike:
        // like a challenge comment
        function (req, res) {
        }
}
