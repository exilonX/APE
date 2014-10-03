var express         = require('express'),
    jwt             = require('jwt-simple'),
    bodyParser      = require('body-parser'),
    restful         = require('node-restful'),
    mongoose        = restful.mongoose,
    methodOverride  = require('method-override'),
    path            = require('path'),
    router          = express.Router(),
    models          = require('./models/models'),
    registration    = require('./services/registration');
    feed            = require('./services/feed');

GLOBAL.app = express();

// configure app to use bodyParser()
// this will let us get the data from a POST
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(express.query());
app.use(methodOverride());

var port = 8080;

// connect to local database
mongoose.connect('mongodb://localhost/ape');

// routes

// middleware for member resources
// it will do the required authentication
router.get('/member/*', function(req, res, next) {
    registration.loggedOn(req, res);
    next();
});

// main feed containing replies to latest challenge
// parameters: none
router.route('/feed').get(feed.feed);
// sign up new user
// parameters: name, password, email
router.route('/signup').post(registration.register);
// log in user (used only when a token expires or does not exists)
// parameters: name, password
router.route('/login').post(registration.authenticate);
// get user info
// parameters: name
router.route('/user/:name').get(registration.userInfo);

// register routes -------------------------------
// all of our routes will be prefixed with /api
app.use('/api', router);


app.listen(port);
console.log('Server started on port ' + port);