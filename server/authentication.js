var express         = require('express');
var jwt             = require('jwt-simple');
var moment          = require('moment');
var app             = express();
var restful         = require('node-restful');
var mongoose        = restful.mongoose;

var User            = mongoose.model('User');

app.set('jwt_token_secret', 'd783ada0fl9b');

module.exports = {
    loggedOn:
        // verifies if token is valid and sets proper user in request
        function(req, res) {
            // allow multiple types of request (e.g. GET, POST, PUT)
            var token = (req.body && req.body.access_token) ||
                (req.query && req.query.access_token) ||
                req.headers['x-access-token'];

            if (token) {
                try {
                    var decoded = jwt.decode(token, app.get('jwt_token_secret'));
                }
                catch (err) {
                    res.send(401);
                    return;
                }
            }
            else {
                res.send(401);
                return;
            }

            if (decoded.exp <= Date.now()) {
                res.end('Access token has expired.', 400);
            }
            User.findOne({ name : decoded.iss }, '-password -email', function(err, user) {
                req.user = user;
            });
        },

    authenticate:
        // does first time authentication
        function authenticate(req, res) {
            var name = req.body.name;
            var password = req.body.password;
            if (!name || !password) {
                return res.send('Both fields are required.', 400);
            }

            User.findOne({ name: name }, function(err, user) {
                if (err) {
                    // user not found
                    return res.send(401);
                }

                if (!user) {
                    // incorrect username
                    return res.send(401);
                }

                if (!user.isValidPassword(password)) {
                    // incorrect password
                    return res.send(401);
                }

                var expires     = moment().add(1, 'years');
                var token       = jwt.encode({
                    iss : name,
                    exp : expires
                }, app.get('jwt_token_secret'));

                res.json({
                    token   : token,
                    expires : expires,
                    user    : name
                });

                // User has authenticated OK
                res.send(200);
            });
        }
}
