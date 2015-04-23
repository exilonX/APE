/**
 * Created by simona on 3/24/15.
 */
var express          = require('express'),
    restful          = require('node-restful'),
    mongoose         = restful.mongoose,
    mongoosePaginate = require('mongoose-query-paginate'),
    lockFile         = require('lockfile'),
    fs               = require('fs');

var mainChallenge = require('./mainChallenge');

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

    saveReply:
    // Add a comment to a reply
        function (req, res) {
            var data = req.body;

            // save the path to the file and the additional info to mongo
            Reply.create({}, function(err, reply) {
                // Evaluate possible errors
                if (evaluateReplyError(res, err, reply))
                    return;
                mainChallenge.getMainChallenge(function(err, data) {
                    if (evaluateReplyError(res, err, data))
                        return;

                    reply.challenge_id = data._id;
                    reply.username = req.body.username;
                    reply.date = new Date();
                    reply.title = req.body.title;
                    reply.thumb_url = req.files.image.path.substring(7);
                    reply.content_url = req.files.image.path.substring(7);

                    reply.save();

                    res.json({result: 'success'});

                })

            });
        },

    hasReplied : function(req, res) {
        Challenge.findOne().sort('-date').exec(
            function(err, challenge) {
                if (evaluateReplyError(res, err, challenge))
                    return;
                Reply.findOne({username: req.body.username, challenge_id : challenge._id}, function (err, reply) {
                    if (evaluateReplyError(res, err, reply))
                        return;
                    if (reply.length > 0) return res.json({hasReplied: true});
                    res.json({hasReplied: false});
                })
            }
        );
    },

    bestReply : function(cb) {
        Reply.find({}).sort({number_likes : -1}).exec(function(err, replies) {
            if (err) return cb(err, null);
            if (replies.length == 0) return cb(new Error("no reply found"), null);
            //console.log(replies);
            var maxNumberOfLikes = replies[0].number_likes;

            Reply.find({'number_likes' : maxNumberOfLikes}, function(err, data) {
                if (err) return cb(err, null);

                if (data.length == 0) return cb(null, {bestReply : 'none'});
                if (data.length == 1) {
                    return cb(null, {bestReply : data[0]});
                }

                var randomIndex = Math.floor(Math.random() * data.length);
                cb(null, {bestReply : data[randomIndex]});
            })
        })
    },

    createChallenge : function(bestReplyUser, cb) {
        Challenge.create({},
            function(err, challenge) {
                // Evaluate possible errors
                if (err) return cb(err, null);
                challenge.username = bestReplyUser;
                challenge.date = new Date();
                challenge.title = 'This is the newest challenge from' + bestReplyUser;
                challenge.thumb_url = 'images/test/sparta.jpg';
                challenge.content_url = 'images/test/sparta.jpg';

                challenge.save();
                cb(null, {response: 'ok'});
            });
    },

    updateNrLikes : function() {
        Reply.find().exec(function(err, data) {
            console.log("Intra aici");
            console.log(data.length);
            for(var i in data) {
                data[i].number_likes = data[i].likes.length;
                console.log(data[i].likes.length);
                data[i].save();
            }
        })
    }


}