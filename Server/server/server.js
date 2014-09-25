var express         = require('express'),
    jwt             = require('jwt-simple'),
    bodyParser      = require('body-parser'),
    restful         = require('node-restful'),
    mongoose        = restful.mongoose,
    methodOverride  = require('method-override'),
    path            = require('path'),
    router          = express.Router(),
    validator       = require('validator'),
    models          = require('./models'),
    auth            = require('./authentication');

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
var User            = mongoose.model('User');
var Challenge       = mongoose.model('Challenge');
var Reply           = mongoose.model('Reply');

// routes


// middleware for member resources
// it will do the required authentication
router.get('/member/*', function(req, res, next) {
    auth.loggedOn(req, res);
    next();
});

// main feed containg replies to latest challenge
router.route('feed')
    .get(function(req, res) {
        // find latest challenge
        Challenge.findOne().sort('-date').exec(
            function(err, challenge) {
                // find replies to it and return them
                Reply.find({challenge_id : challenge._id},
                    function(err, replies) {
                        if (err)
                            res.send(err);
                        res.json(replies);
                });
        });
    });

// sign up new user
router.route('/signup')
    .post(function(req, res) {
        // check required fields are filled
        if (!req.body.name || !req.body.email || !req.body.password) {
            res.json({result : 'failure', errors : ['Please complete all fields.']});
            return;
        }
        // check username uniqueness
        User.findOne({ name : req.body.name}, function(err, user_found) {
            var errors = [];
            if (user_found)
                errors.push('Username already exists.');
            // check email is valid
            if (!validator.isEmail(req.body.email)) {
                errors.push('Invalid email address.');
            }

            // check email uniqueness
            User.findOne({ email : req.body.email }, function(err, user_found) {
                if (user_found)
                    errors.push('Email address is already in use.');

                // return error if validation failed
                if (errors.length != 0) {
                    res.json({result : 'failure', errors : errors});
                    return;
                }
                // save the object
                var user = new User();
                user.name = req.body.name;
                user.password = req.body.password;
                user.email = req.body.email;

                user.save(function(err) {
                    if (err)
                        res.send(err);
                    res.json({result : 'success'});
                });
            });
        });
    });


// log in user (used only when a token expires or does not exists)
router.route('/login')
    .post(function(req, res) {
        auth.authenticate(req, res);
    });

// get user info
router.route('/user/:name')

    .get(function(req, res) {
        // do not expose password and email
        User.findOne({name : req.params.name}, '-password -email', function(err, user) {
            if (err)
                res.send(err);
            res.json(user);
        });
    });

// register routes -------------------------------
// all of our routes will be prefixed with /api
app.use('/api', router);


app.listen(port);
console.log('Server started on port ' + port);
