const express = require('express');
const request = require('request');

const PORT = 8080;
let app = express();

app.get('/weather', (req, res, next) => {

    // You can create your own api key and insert it here
    let apiKey = '9c2dfbf76859454eb94f183f3d8c6abd';
    let city = 'London';
    let url = `http://api.openweathermap.org/data/2.5/weather?q=${city}&units=metric&appid=${apiKey}`

    request(url, function (err, response, body) {
        if(err){
            console.log('error:', error);
            return res.error(err);
          } else {
            let weather = JSON.parse(body)
            let message = `It's ${weather.main.temp} degrees in ${weather.name}!`;
            console.log(message);
            return res.json(weather);
          }
    });
    
});

app.listen(PORT, () => {
    console.log(`Listening on port ${PORT}`);
});
