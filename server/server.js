var express = require('express');
var jwt = require('jwt-simple');
var bodyParser = require('body-parser');
var restful = require('node-restful');
var mongoose = restful.mongoose;
var methodOverride = require('method-override');
var path = require('path');
var router = express.Router();

GLOBAL.app = express();

// configure app to use bodyParser()
// this will let us get the data from a POST
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(express.query());
app.use(methodOverride());

var port = 8080;

router.route('/apeFeed')
    // get all the bears (accessed at GET http://localhost:8080/api/bears)
    .get(function(req, res) {
        var jsonRes = [
            {
                "username": "exilonX",
                "title": "Stupid pic",
                "timestamp": "04:47:60",
                "thumb_url": "http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg"
            },
            {
                "username": "andreea",
                "title": "Cute pic",
                "timestamp": "05:47:60",
                "thumb_url": "http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg"
            },
            {
                "username": "razvan",
                "title": "My pic",
                "timestamp": "06:54:60",
                "thumb_url": "http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg"
            },
            {
                "username": "leona",
                "title": "My pic",
                "timestamp": "06:33:60",
                "thumb_url": "http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg"
            },
            {
                "username": "marius",
                "title": "My pic",
                "timestamp": "06:12:60",
                "thumb_url": "http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg"
            },
            {
                "username": "exilonX",
                "title": "Stupid pic",
                "timestamp": "04:47:60",
                "thumb_url": "http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg"
            },
            {
                "username": "andreea",
                "title": "Cute pic",
                "timestamp": "05:47:60",
                "thumb_url": "http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg"
            },
            {
                "username": "razvan",
                "title": "My pic",
                "timestamp": "06:54:60",
                "thumb_url": "http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg"
            },
            {
                "username": "leona",
                "title": "My pic",
                "timestamp": "06:33:60",
                "thumb_url": "http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg"
            },
            {
                "username": "marius",
                "title": "My pic",
                "timestamp": "06:12:60",
                "thumb_url": "http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg"
            }
        ];

        res.json(jsonRes);

    });

// REGISTER OUR ROUTES -------------------------------
// all of our routes will be prefixed with /api
app.use('/api', router);


app.listen(port);
console.log('Server started on port ' + port);