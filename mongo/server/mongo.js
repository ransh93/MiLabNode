"use strict";

const MongoClient = require('mongodb').MongoClient;
const MONGO_URL = "mongodb://localhost:27017";

const client = new MongoClient(MONGO_URL, {
    useNewUrlParser: true
});

client.connect()
.then(() => {
    let db = client.db('mongotest');
    let collection = db.collection('docs');
    return collection.insertMany([
        {username: "foo"}, {username: "bar"}, {username: "something"}
    ]);
})
.then(() => {
    let db = client.db('mongotest');
    let collection = db.collection('docs');
    return collection.find().toArray();
})
.then(docs => {
    console.log(`Found ${docs.length} documents`);
    docs.forEach(doc => console.log(doc.username));
    return;
})
.catch(err => {
    console.error(err);
});