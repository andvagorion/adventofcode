const fs = require("fs");

const load = (day) => {
    return fs.readFileSync(__dirname + `/../input/${day}`, "utf8");
};

exports.load = load;

const as_lines = (day) => {
    return load(day)
        .split("\n")
        .filter((line) => line != "");
};

exports.as_lines = as_lines;

const range = (len) => [...Array(len).keys()];

exports.range = range;

const reverse_str = (str) => str.split("").reverse().join("");

exports.reverse_str = reverse_str;

const parse_int = (str) => parseInt(str);

exports.parse_int = parse_int;

const only_digits = (line) => line.match(/\d+/g).map((s) => parseInt(s));

exports.only_digits = only_digits;
