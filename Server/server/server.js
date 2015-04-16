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
