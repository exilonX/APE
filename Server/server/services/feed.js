var express          = require('express'),
    restful          = require('node-restful'),
    mongoose         = restful.mongoose,
    mongoosePaginate = require('mongoose-query-paginate');

GLOBAL.app = express();

var Challenge       = mongoose.model('Challenge');
var Reply           = mongoose.model('Reply');

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
                            res.send(error);
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

    replyComments:
        // get reply comment (+ number of likes)
        function (req, res) {
            Reply.findOne({ _id : req.params._reply_id }, function(err, reply) {
                if (err)
                    res.send(err);

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

    replyLike:
        // like a reply
        function (req, res) {
        },

    replyCommentLike:
        // like a reply comment
        function (req, res)
        {
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
