// to repopulate the databse use the following command:
// mongo populate.js

var conn    = new Mongo('ds043350.mongolab.com:43350');
var db      = conn.getDB('ape');

// authenticate
db.auth('ape', 'm0nk3y');

// clear database
// remove collecitons individualy
db.comments.drop();
db.likes.drop();
db.users.drop();
db.replies.drop();
db.challenges.drop();

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
    thumb_url: 'images/test/sparta.jpg',
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
    thumb_url: 'images/test/sparta.jpg', likes : [], comments : []});

// print them out
db.challenges.find().forEach(printjson);

// create replies for last challenge
var last_challenge = db.challenges.findOne({title : 'This is Sparta'});

db.replies.insert({challenge_id : last_challenge._id, username : 'andreea', title : 'Very professional',
        date : new Date(), thumb_url : 'images/test/lions.jpg', 
        likes : [
            { _id : new ObjectId(), username : 'ionel', date : new Date() },
            { _id : new ObjectId(), username : 'razvan', date : new Date() },
            { _id : new ObjectId(), username : 'leona', date : new Date() },
            { _id : new ObjectId(), username : 'andreea', date : new Date() }

        ],
        comments : [
            { _id : new ObjectId(), username : 'ionel', date : new Date(), comment : 'Cool!' },
            { _id : new ObjectId(), username : 'razvan', date : new Date(), comment : 'You\'re my idol' },
            { _id : new ObjectId(), username : 'marius', date: new Date(), comment : 'I wanna be like me when I grow up!',
              likes : [
                { _id : new ObjectId(), username : 'razvan', date : new Date() },
                { _id : new ObjectId(), username : 'leona', date : new Date() }
              ]
            },
            { _id : new ObjectId(), username : 'marius', date: new Date(), comment : 'But I\'m going to start the change from tomorrow' },
            { _id : new ObjectId(), username : 'andreea', date : new Date(), comment : 'Good for you!' },
            { _id : new ObjectId(), username : 'marius', date: new Date(), comment : 'Thank you for the encouragement!',
              likes : [
                { _id : new ObjectId(), username : 'andreea', date : new Date() }
              ]
            },
            { _id : new ObjectId(), username : 'marius', date: new Date(), comment : 'I\'m not lying!' }
        ]});
db.replies.insert({challenge_id : last_challenge._id, username : 'ionel', title: 'Stupid pic',
        date : new Date(), thumb_url : 'images/test/lions.jpg',
        likes : [
            { _id : new ObjectId(), username : 'andreea', date : new Date() },
            { _id : new ObjectId(), username : 'leona', date : new Date() }
        ], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'marius', title : 'Kill me now',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'leona', title: 'Truly beatifull',
        date : new Date(), thumb_url : 'images/test/lions.jpg',
        likes : [
            { _id : new ObjectId(), username : 'ionel', date : new Date() },
            { _id : new ObjectId(), username : 'andreea', date : new Date() }
        ],
         comments : [
            { _id : new ObjectId(), username : 'ionel', date : new Date(), comment : 'Banana!' },
            { _id : new ObjectId(), username : 'razvan', date : new Date(), comment : 'Baybe I love you!' },
            { _id : new ObjectId(), username : 'marius', date: new Date(), comment : 'E.T. go home.',
              likes : [
                { _id : new ObjectId(), username : 'razvan', date : new Date() },
                { _id : new ObjectId(), username : 'leona', date : new Date() }
              ]
            }]
        });
db.replies.insert({challenge_id : last_challenge._id, username : 'razvan', title : 'This is all you got?',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'ionel', title: 'I can do better than that',
        date : new Date(), thumb_url : 'images/test/lions.jpg',
        likes : [
            { _id : new ObjectId(), username : 'marius', date : new Date() },
        ], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'marius', title : 'Life is nice',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'andreea', title: 'Got lucky',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'razvan', title: 'Panic at the club',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'marius', title: 'Workaholic',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'leona', title: 'I want to work for this too',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'ionel', title: 'I\'m bored',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'marius', title: 'Born tired',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'razvan', title: 'Underground the movie',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'andreea', title: 'Its been some time since',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'leona', title: 'God give me courage',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'razvan', title: 'Boiled space',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'marius', title: 'Have I been here before ?',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'ionel', title: 'Sparta is Sparta',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'leona', title: 'Visiting the moon',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'marius', title: 'I don\'t have a clue...',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'ionel', title: 'What I\'m doing here',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'marius', title : 'Life is ugly',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'andreea', title: 'Got unlucky',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'razvan', title: 'Smoothness at the club',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'marius', title: 'Not a slave anymore',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'leona', title: 'I want to stay and do nothing',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'ionel', title: 'I never been bored in my life',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'marius', title: 'Full of life',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'razvan', title: 'Black cat, white cat',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'andreea', title: 'It was yesterday',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'leona', title: 'This is Satan',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'razvan', title: 'I had my share',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'marius', title: 'Someone like you ?',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'ionel', title: 'We could stick around',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'leona', title: 'This night throut',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'marius', title: 'I don\'t care about the young folks',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});
db.replies.insert({challenge_id : last_challenge._id, username : 'ionel', title: 'I care about talking',
        date : new Date(), thumb_url : 'images/test/lions.jpg', likes : [], comments : []});



// print them out
db.replies.find().forEach(printjson);
