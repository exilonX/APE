/**
 * Created by ionel.merca on 4/24/2015.
 */

var GCM = require('gcm').GCM;
var CONST = require('../const');
var restful          = require('node-restful'),
    mongoose         = restful.mongoose;

var User       = mongoose.model('User');


function GCMService() {
    this.apiKey = CONST.GCM_API_KEY;
    this.registration_ids = {};
    this.gcm = new GCM(this.apiKey);
}

GCMService.prototype.addRegistrationId = function(username, registration_id, cb) {
    this.registration_ids[username] = registration_id;
    if (!registration_id || registration_id == undefined) return cb(err, null);
    // Add the registration id to the database replace it if it already exists
    User.find({username : username}, function(err, users) {
        if (err) return cb(err, null);
        users[0].registration_id = registration_id;
        users[0].save(function(err) {
            if (err) return cb(err, null);
            cb(null, {addRegistrationId : 'ok'});
        });
    });
}

GCMService.prototype.getRegistrationID = function(username, cb) {
    if (this.registration_ids[username] != undefined) return cb(null, this.registration_ids[username]);
    User.find({'username' : username}, function(err, users) {
        if (err) return cb(err, null);
        cb(null, users[0].registration_id);
    })
}

GCMService.prototype.sendNotification = function(message, username) {

    this.getRegistrationID(username, function(err, registration_id) {

        var notification = {
            'registration_id' : registration_id,
            'data' : message
        }

        gcm.send(notification, function(err, messageId) {
            if (err) {
                console.log("Something has gone wrong!");
            } else {
                console.log("Sent with message ID: ", messageId);
            }
        })

    })

}



