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
