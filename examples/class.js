class Person {
	constructor(name, age) {
		this.name = name;
		this.age = age;		
	}

	printName() {
		console.log("The name is " + this.name);
	}

	isOlderThan(age) {
		return this.age > age;
	}
}

let p = new Person("Daniel", 10);
p.printName();

if (p.isOlderThan(90)) {
	console.log("Wow, that's old");
}


