const  fs = require("fs");

function padWithLeadingZeros(string) {
    return new Array(5 - string.length).join("0") + string;
}

function unicodeCharEscape(charCode) {
    return "\\u" + padWithLeadingZeros(charCode.toString(16));
}

function unicodeEscape(string) {
    return string.split("")
                 .map(function (char) {
                     var charCode = char.charCodeAt(0);
                     return charCode > 127 ? unicodeCharEscape(charCode) : char;
                 })
                 .join("");
}



var filename = process.argv[2];
var file = fs.readFileSync(filename);
var obj = JSON.parse(file);

var filename_out = filename.replace(".decoded.json", "");
fs.writeFileSync(filename_out, unicodeEscape(JSON.stringify(obj, null, '\t')));