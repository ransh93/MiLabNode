"use strict";

const express = require('express');
const bodyParser = require('body-parser');
const FCM = require('fcm-push');

const FCM_SERVER_KEY = 'AIzaSyC2mIl6xB0CJ2m1Hb1hgbbfWt2M0QB5Qoo';
const PORT = 8080;

let app = express();
app.use(bodyParser.json());

let fcm = new FCM(FCM_SERVER_KEY);

let tokens = {}; // this should probably be in a database

app.post('/:user/token', (req, res, next) => {
    let token = req.body.token;
    console.log(`Received save token request from ${req.params.user} for token=${token}`);

    if (!token) return res.status(400).json({err: "missing token"});

    tokens[req.params.user] = token;
    res.status(200).json({msg: "saved ok"});
});

app.post('/:user/message', (req, res, next) => {
    let message = req.body.message;
    console.log(`Going to send message to ${req.params.user} message=${message}`);

    if (!message) return res.status(400).json({err: "missing message"});

    let targetToken = tokens[req.params.user];
    if (!targetToken) return res.status(404).json({err: `no token for user ${req.params.user}`});

    fcm.send({
        to: targetToken,
        data: {
            someKey: "some value"
        },
        notification: {
            title: "message title",
            body: message
        }
    }, (err, response) => {
        if (err) return res.status(500).json({err: `message sending failed - ${err}`});
        return res.status(200).json({msg: "sent ok"});
    });
});

app.get('/tokens', (req, res, next) => {
    res.status(200).json(tokens);
});

app.listen(PORT, () => {
    console.log(`Server is now online on port ${PORT}`);
});