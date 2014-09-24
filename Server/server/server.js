var express         = require('express');
var jwt             = require('jwt-simple');
var bodyParser      = require('body-parser');
var restful         = require('node-restful');
var mongoose        = restful.mongoose;
var methodOverride  = require('method-override');
var path            = require('path');
var router          = express.Router();
var models          = require('./models');

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
var Challenge       = mongoose.model('Challenge');
var Reply           = mongoose.model('Reply');

// routes

// main feed containg replies to latest challenge
router.route('/feed')
    .get(function(req, res) {
        // find lastest challenge
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

// register routes -------------------------------
// all of our routes will be prefixed with /api
app.use('/api', router);


app.listen(port);
console.log('Server started on port ' + port);
