var express         = require('express'),
    jwt             = require('jwt-simple'),
    bodyParser      = require('body-parser'),
    restful         = require('node-restful'),
    mongoose        = restful.mongoose,
    methodOverride  = require('method-override'),
    path            = require('path'),
    router          = express.Router(),
    models          = require('./models/models'),
    registration    = require('./services/registration'),
    feed            = require('./services/feed');
    challengeReply  = require('./services/challengeReply'),
    onechallenge    = require('./services/mainChallenge'),
    multer          = require('multer');

var GCM = require('./services/gcm');

var CronJob = require('cron').CronJob;

var bestReply;
var allBestReplies = [];


var waitChallengeJob = new CronJob('0 */10 * * * *', function() {
    // I should notify the other user that the challenge expired
    if (data.bestReply != null)
        GCM.sendNotification("Your chance expired, you should reply in 10 minutes", data.bestReply.username);


    // get the best reply (if more have the same number of likes) get one random
    challengeReply.bestReply(allBestReplies, function(err, data) {
        if (err) return console.log("Error while executing job");
        console.log("The data from best reply");
        console.log(data);
        bestReply = data.bestReply;
        allBestReplies.push(bestReply.username);
        // se notifica cel care a castigat iar atunci cand acesta raspunde
        // trebuie sa se notifice ceilalti ca s-a schimbat challenge ul si
        // pot da reply
        GCM.sendNotification("Ai castigat ai 10 minute sa raspunzi", data.bestReply.username);

        // after sending the notification to the client that won a new cron job
        // should start and a new winner should be selected if the first one
        // didn't respond in a timely manner



        //challengeReply.createChallenge(data.bestReply.username, function(err, data) {
        //    if (err) return console.log("Error in creating the challenge");
        //    // notify via GCM all the registered users that the challenge was changed
        //
        //
        //
        //    GCM.notifyAll("Challenge-ul s-a schimbat", function(err, data){
        //        if (err) return console.log("Eroare " + err.message);
        //        console.log(data);
        //    })
        //    return data;
        //})
    });
});

// Sec Min Hour DayOfMonth Month DayOfWeek
var job = new CronJob('0 30 16 * * *',
    function() {
        waitChallengeJob.start();
        // add a new challenge with the username of the one that had the best reply
    },
    function() {
        console.log("Job ended");
    },
    true, "Europe/Bucharest");


GLOBAL.app = express();

// configure app to use bodyParser()
// this will let us get the data from a POST
app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));
app.use(express.query());
app.use(methodOverride());
app.use(require('morgan')('combined'));
app.use(multer({ dest: './static/images/challengeReply/'}));


// connect to local database
//mongoose.connect('mongodb://ape:m0nk3y@ds043350.mongolab.com:43350/ape');
mongoose.connect('mongodb://localhost:27017/ape');


// set debug mode
app.set('debug', true);
// set host prefix
app.set('host', 'http://52.11.44.10:8080/');

// routes

// middleware for member resources
// it will do the required authentication
router.get('/member/*', function(req, res, next) {
    registration.loggedOn(req, res);
    next();
});

// save a challenge reply
router.route('/challenge_reply').post(challengeReply.saveReply);

// main feed containing replies to latest challenge
// parameters: none
router.route('/feed').get(feed.feed);
// get comments for a given reply
router.route('/reply/comments/:_reply_id').get(feed.replyGetComments);
// add comment for a given reply
// parameters: _reply_id, comment
router.route('/reply/comment/add').put(feed.replyAddComment);
// get likes for a given reply
router.route('/reply/likes/:_reply_id').get(feed.replyGetLikes);
// add like for a given reply
// parameters: _reply_id
router.route('/reply/like/add').put(feed.replyAddLike);
// add like for a reply comment
// parameters: _reply_id, _comment_id
router.route('/reply/comment/like/add').put(feed.replyCommentAddLike);

// check if the user has already replied to the current challenge
router.route('/reply/hasReplied').post(challengeReply.hasReplied)

router.route('/reply/bestReply').get(challengeReply.bestReply);

// register the user in the GCM service
router.route('/register_gcm').post(function(req, res) {
    var username = req.body.username;
    var registration_id = req.body.registration_id;

    console.log("Se inregistreaza la GCM" + username + "  " + registration_id);

    GCM.addRegistrationId(username, registration_id, function(err, data) {
        if (err) return res.json({error : true, message : 'Error ' + err.message}, 404);
        return res.json({error : false, message : "Success register"});
    });

});

// sign up new user
// parameters: name, password, email
router.route('/signup').post(registration.register);
// log in user (used only when a token expires or does not exists)
// parameters: name, password
router.route('/login').post(registration.authenticate);
// get user info
// parameters: name
router.route('/user/:name').get(registration.userInfo);

router.route('/challenge').get(onechallenge.mainchallenge);
router.route('/challenge').post(function(req, res) {
    // stop the best reply cron job
    waitChallengeJob.stop();
    allBestReplies = [];
    bestReply = null;
    // create the challenge and notify the users
    onechallenge.createChallenge(req, res);
});

router.route('/iswinner').get(function(req, res) {
    if (req.body.username == bestReply.username) {
        return res.json({winner : true});
    }
    return res.json({winner : false});
})

app.use(express.static(path.join(__dirname , '/static')));

// register routes -------------------------------
// all of our routes will be prefixed with /api
app.use('/api', router);

if (app.get('debug')) {
    var port = (process.env.PORT || 8080);
    app.listen(port);
    console.log('Server started on port ' + port)
}
else {
    app.listen('/tmp/ape.sock');
    console.log('Server started with nginx proxy.');
}
