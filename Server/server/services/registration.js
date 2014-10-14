var express         = require('express'),
    jwt             = require('jwt-simple'),
    moment          = require('moment'),
    app             = express(),
    restful         = require('node-restful'),
    mongoose        = restful.mongoose,
    validator       = require('validator');

var User            = mongoose.model('User');

app.set('jwt_token_secret', 'd783ada0fl9b');

module.exports = {
    register:
        // used to register new user
        function (req, res) {
            // check required fields are filled
            if (!req.body.name || !req.body.email || !req.body.password) {
                res.send({result: 'failure', errors : ['Please complete all fields.']}, 400);
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
                        res.send({result : 'failure', errors : errors}, 400);
                        return;
                    }
                    // save the object
                    var user = new User();

                    user.name = req.body.name;
                    user.email = req.body.email;
                    // hash password
                    user.hashPassword(req.body.password, function(err, password) {
                        if (err)
                            res.send(err, 400);
                        user.password = password;
                        user.save(function(err) {
                            if (err)
                                res.send(err, 400);
                            res.json({result : 'success'});
                        });
                    });
                });
            });
        },

    loggedOn:
        // verifies if token is valid and sets proper user in request
        function (req, res) {
            // allow multiple types of request (e.g. GET, POST, PUT)
            var token = (req.body && req.body.access_token) ||
                (req.query && req.query.access_token) ||
                req.headers['x-access-token'];

            // Debug output
            if (req.body && req.body.access_token)
                console.log("Auth token POST parameter");
            else if (req.query && req.query.access_token)
                console.log("Auth token GET ? parameter");
            console.log("token=" + token);

            if (token) {
                try {
                    var decoded = jwt.decode(token, app.get('jwt_token_secret'));
                }
                catch (err) {
                    res.send({result: 'failure', erros: ['Error on authentication.']}, 401);
                    return;
                }
            }
            else {
                res.send(401);
                return;
            }

            if (decoded.exp <= Date.now()) {
                res.end({result: 'failure', errors: ['Access token has expired.']}, 400);
            }
            // add username in request. parameter will be passed on to other routes
            User.findOne({ name : decoded.iss }, '-password -email', function(err, user) {
                req.user = user;
            });
        },

    authenticate:
        // does first time authentication
        function (req, res) {
            var name = req.body.name;
            var password = req.body.password;
            if (!name || !password) {
                return res.send({result: 'failure', errors: ['Both fields are required.']}, 400);
            }

            User.findOne({ name: name }, function(err, user) {
                if (err)
                    return res.send(err, 400);

                if (!user) {
                    // incorrect username
                    return res.send({result: 'failure', errors: ['Username does not exist.']}, 401);
                }

                // verify password
                user.isValidPassword(password, function(err, matches) {
                    if (err || !matches) {
                        // incorrect password
                        return res.send({result: 'failure', errors: ['Wrong password.']}, 401);
                    }
                    else { 
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
                        res.send({result: 'success'}, 200);
                    }
                });
            });
        },

    userInfo:
        // get user info which could be used for a profile page
        function(req, res) {
            // do not expose password and email
            User.findOne({name : req.params.name}, '-password -email', function(err, user) {
                if (err)
                    res.send(err, 400);
                res.json(user);
            });
        }
}
