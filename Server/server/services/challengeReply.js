/**
 * Created by simona on 3/24/15.
 */
var express          = require('express'),
    restful          = require('node-restful'),
    mongoose         = restful.mongoose,
    mongoosePaginate = require('mongoose-query-paginate'),
    lockFile         = require('lockfile'),
    fs               = require('fs');

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
            console.log(data);

            //TODO save the image to a file - in the static directory
            //var imageData = data.image;
            //var base64Data = imageData.replace(/^data:image\/gif;base64,/, "");
            //
            ////fs.writeFile("server/static/images/challengeReply/out.gif", base64Data, 'base64', function(err) {
            ////    console.log(err);
            ////});
            //fs.writeFile("server/static/images/challengeReply/out.gif", new Buffer(base64Data, "base64"), function(err) {
            //    console.log(err);
            //});

            console.log(req.files);
            console.log("+======================+");
            console.log(req.body);
            //res.send(200);

            // save the path to the file and the additional info to mongo
            Reply.create({}, function(err, reply) {
                // Evaluate possible errors
                if (evaluateReplyError(res, err, reply))
                    return;

                reply.challenge_id = mongoose.Types.ObjectId('55146985d0aa52e3f70219e3')
                reply.username = 'dummy';
                reply.date = new Date();
                reply.title = 'dummyTitle';
                reply.thumb_url = "static/images/challengeReply/out.gif";
                reply.content_url = "static/images/challengeReply/out.gif";

                reply.save();

                res.json({result: 'success'});
            });



        }
}
