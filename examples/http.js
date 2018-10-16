const http = require('http');

let server = http.createServer(function(request, response) {
	console.log("Got request");
	response.end("Hello world");
});

server.listen(8080);

