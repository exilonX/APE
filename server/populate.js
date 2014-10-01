// to repopulate the databse use the following command:
// mongo populate.js

var conn    = new Mongo();
var db      = conn.getDB('ape');

// clear database
db.dropDatabase();

// insert some users ---------------------------------------------
// clear text password for following users is '123456'
db.users.insert({name : 'andreea', email : 'andreea.badoiu@gmail.com',
    password : '$2a$11$rWftGcr3HduY8OZwlmZ8G.LEkmCvOQGQ9bxmZmRSBm1jCV9rtnfIm'});
db.users.insert({name : 'ionel', email: 'ionel.ionel@gmail.com',
    password: '$2a$11$rWftGcr3HduY8OZwlmZ8G.LEkmCvOQGQ9bxmZmRSBm1jCV9rtnfIm'});
db.users.insert({name : 'leona', email: 'leona.cilibeanu@gmail.com',
    password: '$2a$11$rWftGcr3HduY8OZwlmZ8G.LEkmCvOQGQ9bxmZmRSBm1jCV9rtnfIm'});
db.users.insert({name : 'marius', email: 'marius.avram@gmail.com',
    password: '$2a$11$rWftGcr3HduY8OZwlmZ8G.LEkmCvOQGQ9bxmZmRSBm1jCV9rtnfIm'});
db.users.insert({name : 'razvan', email: 'razvan.florea@gmail.com',
    password: '$2a$11$rWftGcr3HduY8OZwlmZ8G.LEkmCvOQGQ9bxmZmRSBm1jCV9rtnfIm'});

// print them out
db.users.find().forEach(printjson);

// create two challenges -----------------------------------------
var user = db.users.findOne({name: 'marius'});
print('_id = ' + user._id);

// one yesterday
var yesterday = new Date();
yesterday.setDate(yesterday.getDate() - 1);
db.challenges.insert({
    username : 'marius', date : yesterday, title : 'This is nay Sparta',
    thumb_url: 'http://i58.tinypic.com/15e7ern.jpg',
    likes : [
        { _id : new ObjectId(), username : 'marius', date : new Date() },
        { _id : new ObjectId(), username : 'leona', date : new Date() },
        { _id : new ObjectId(), username : 'razvan', date : new Date() }
    ], 
    comments : [
        { _id : new ObjectId(), username : 'ionel'._id, date : new Date(), comment : 'I want to be like you when I grow up!' },
        { _id : new ObjectId(), username : 'leona'._id, date : new Date(), comment : 'I\'m addicted to this.' },
        { _id : new ObjectId(), username : 'razvan'._id, date : new Date(), comment : 'Booooo! Go away.' }
    ]});

// one today
db.challenges.insert({
    username : 'marius', date : new Date(), title : 'This is Sparta', 
    thumb_url: 'http://i58.tinypic.com/15e7ern.jpg', likes : [], comments : []});

// print them out
db.challenges.find().forEach(printjson);

// create replies for last challenge
var last_challenge = db.challenges.findOne({title : 'This is Sparta'});

db.replies.insert({challenge_id : last_challenge._id, username : 'andreea', title : 'Very professional',
        date : new Date(), thumb_url : 'http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg', 
        likes : [
            { _id : new ObjectId(), username : 'ionel', date : new Date() },
            { _id : new ObjectId(), username : 'razvan', date : new Date() }
        ],
        comments : [
            { _id : new ObjectId(), username : 'ionel', date : new Date(), comment : 'Cool!' },
            { _id : new ObjectId(), username : 'razvan', date : new Date(), comment : 'You\'re my idol' }
        ]});
db.replies.insert({challenge_id : last_challenge._id, username : 'ionel', title: 'Stupid pic',
        date : new Date(), thumb_url : 'http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg',
        likes : [
            { _id : new ObjectId(), username : 'andreea', date : new Date() },
            { _id : new ObjectId(), username : 'leona', date : new Date() }
        ], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'marius', title : 'Kill me now',
        date : new Date(), thumb_url : 'http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'leona', title: 'Truly beatifull',
        date : new Date(), thumb_url : 'http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'razvan', title : 'This is all you got?',
        date : new Date(), thumb_url : 'http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'ionel', title: 'I can do better than that',
        date : new Date(), thumb_url : 'http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg',
        likes : [
            { _id : new ObjectId(), username : 'marius', date : new Date() },
        ], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'marius', title : 'Life is nice',
        date : new Date(), thumb_url : 'http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'andreea', title: 'Got lucky',
        date : new Date(), thumb_url : 'http://img-9gag-lol.9cache.com/photo/aAVp75L_700b.jpg', likes : [], comments : []});

// print them out
db.replies.find().forEach(printjson);
