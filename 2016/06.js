const { as_lines } = require("./util/load");
const { range } = require("./util/range");
const { alphabet } = require("./util/text");

const lines = as_lines("06");

const min_max = range(lines[0].length).map((i) => {
    const counts = {};
    alphabet.forEach((a) => (counts[a] = 0));
    lines.map((line) => line.charAt(i)).forEach((c) => (counts[c] += 1));
    return [
        alphabet.reduce((a, b) => (counts[a] < counts[b] ? a : b), 99999),
        alphabet.reduce((a, b) => (counts[a] > counts[b] ? a : b), 0),
    ];
});

console.log(min_max.map((a) => a[1]).join(""));
console.log(min_max.map((a) => a[0]).join(""));
