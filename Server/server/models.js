// database models

var mongoose    = require('mongoose');
var Schema      = mongoose.Schema;

var User  = new Schema({
    name                : String,
    email               : String,
    password            : String,  // clear password
    description         : String,
    profile_pic_url     : String,
    registration_date   : Date,
    birth_date          : Date
});


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
module.exports = mongoose.model('Reply', Reply);
