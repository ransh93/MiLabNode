const EventEmitter = require('events').EventEmitter;

class EachSecond extends EventEmitter {
	start() {
		const self = this;
		this._id = setInterval(function() {
			self.emit('second');
		}, 1000);		
	}
}

let e = new EachSecond();
e.on('second', function() {
	console.log(new Date());
});

e.start();