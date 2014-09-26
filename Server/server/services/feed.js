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
        }

}
