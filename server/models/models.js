// database models

var mongoose    = require('mongoose');
var Schema      = mongoose.Schema;
var bcrypt      = require('bcrypt');
var SALT_FACTOR = 11;

var User  = new Schema({
    name                : String,
    email               : String,
    password            : String,  // clear password
    description         : String,
    profile_pic_url     : String,
    registration_date   : Date,
    birth_date          : Date
});

// check if password is valid
User.methods.isValidPassword = function isValidPassword(password, next) {
    bcrypt.compare(password, this.password, function(err, matches){
        if (err) {
            return next(err);
        }
        next(null, matches);
    });
};

// return hashed password
User.methods.hashPassword = function hashPassword(password, next) {
    // generate a salt
    bcrypt.genSalt(SALT_FACTOR, function(err, salt) {
        if (err) {
            return next(err);
        }

        // hash the password using our new salt
        bcrypt.hash(password, salt, function(err, hash) {
            if (err) {
                return next(err);
            }
            next(null, hash);
        });
    });
};

var Like = new Schema({
    username            : String,
    date                : Date
});

var Comment = new Schema({
    username            : String,
    date                : Date,
    comment             : String,
    likes               : [Like]
});

var Challenge = new Schema({
    username            : String,
    date                : Date,
    title               : String,
    thumb_url           : String,
    content_url         : String,
    likes               : [Like],
    comments            : [Comment]
});

var OneChallenge = new Schema({
    username            : String,
    date                : Date,
    title               : String,
    thumb_url           : String,
    content_url         : String,
    likes               : [Like],
    comments            : [Comment]
});

var Reply = new Schema({
    challenge_id        : Schema.Types.ObjectId,
    username            : String,
    date                : Date,
    title               : String,
    thumb_url           : String,
    content_url         : String,
    likes               : [Like],
    comments            : [Comment]
});

// register schemas
module.exports = mongoose.model('User', User);
module.exports = mongoose.model('Comment', Comment);
module.exports = mongoose.model('Like', Like);
module.exports = mongoose.model('Challenge', Challenge);
module.exports = mongoose.model('OneChallenge', OneChallenge);
module.exports = mongoose.model('Reply', Reply);
