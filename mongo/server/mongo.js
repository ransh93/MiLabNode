"use strict";

const async = require('async');
const MongoClient = require('mongodb').MongoClient;
const MONGO_URL = "mongodb://localhost:27017/mongotest";

MongoClient.connect(MONGO_URL, (err, db) => {
    if (err) {
        console.error(err);
        return;
    }

    let collection = db.collection('docs');
    async.series([
        
        cb => collection.insertMany([
           {username: "foo"}, {username: "bar"}, {username: "something"}
        ], cb),

        cb => collection.find().toArray((err, docs) => {
            if (err) return cb(err);

            console.log(`Found ${docs.length} documents`);
            docs.forEach(doc => console.log(doc.username));

            return cb(null);
        })
    ], (err, result) => {
        db.close();

        if (err) {
            console.error(err);
            return;
        }

        console.log("Completed successfully");
    });
});