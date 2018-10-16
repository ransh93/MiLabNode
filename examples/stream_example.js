const zlib = require('zlib');
const fs = require('fs');

const gzip = zlib.createGzip();
const readStream = fs.createReadStream('file.txt');
const writeStream = fs.createWriteStream('file.txt.gz');

readStream.pipe(gzip).pipe(writeStream);

