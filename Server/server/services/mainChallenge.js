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
    mainchallenge:
        // get all replies to latest challenge
        function (req, res) {

            // Options for pagination. Don't know what "delta" means!!!
            // var options = {
            //         perPage: pageSize,
            //         delta  : 3,
            //         page   : page
            // };

            Challenge.findOne().sort('-date').exec(
                function(err, challenge) {
                    var host = app.get('host');
                    challenge.thumb_url = host + challenge.thumb_url;
                	res.json(challenge);
                     // Find the replies to the most recent challenge 
                    // var query = Reply.find({challenge_id : challenge._id}, '-likes -comments');
                    
                    // Paginate the results based on the options built above
                    // query.paginate(options, function(error, paginatedResults) {
                    //     if(error) {
                    //         res.send(error, 400);
                    //     } else {
                    //         // Add host prefix to static resources
                    //         var host = app.get('host');
                    //         for (i = 0; i < paginatedResults.results.length; i++) {
                    //             paginatedResults.results[i].thumb_url = host + paginatedResults.results[i].thumb_url;
                    //         }
                    //         res.json(paginatedResults);
                    //     }
                    // });
            });
        },
    getMainChallenge : function(cb) {
        Challenge.findOne().sort('-date').exec(
            function(err, challenge) {
                if (err) return cb(err, null);
                cb(null, challenge);
            }
        )}
}