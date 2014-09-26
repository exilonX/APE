var express         = require('express'),
    app             = express(),
    restful         = require('node-restful'),
    mongoose        = restful.mongoose;

var Challenge       = mongoose.model('Challenge');
var Reply           = mongoose.model('Reply');

module.exports = {
    feed:
        // get all replies to latest challenge
        function (req, res) {
            Challenge.findOne().sort('-date').exec(
                function(err, challenge) {
                    // find replies to it and return them
                    Reply.find({challenge_id : challenge._id},
                        function(err, replies) {
                            if (err) {
                                res.send(err);
                            }
                            res.json(replies);
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
