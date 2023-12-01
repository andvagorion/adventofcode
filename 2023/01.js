const { as_lines, range, parse_int } = require("./util/util");

const lines = as_lines("01");

const example = [
    "two1nine",
    "eightwothree",
    "abcone2threexyz",
    "xtwone3four",
    "4nineeightseven2",
    "zoneight234",
    "7pqrstsixteen",
];

const sum = lines
    .map((s) => s.split(""))
    .map((arr) => arr.map(parse_int).filter(Number.isInteger))
    .map((arr) => arr[0] * 10 + arr[arr.length - 1])
    .reduce((a, b) => a + b, 0);

console.log(sum);

const numbers = [
    "one",
    "two",
    "three",
    "four",
    "five",
    "six",
    "seven",
    "eight",
    "nine",
];

const is_digit = (str, num, pos) => {
    if (num > str.length) return false;
    if (num + pos.length > str.length) return false;
    return str.indexOf(num) == pos;
};

const change_numbers = (str) => {
    let out = str + "";

    numbers.forEach((num) => {
        const idx = numbers.indexOf(num) + 1;
        out = out.replaceAll(num, num + idx + num);
    });

    return out;
};

const sum2 = lines
    .map(change_numbers)
    .map((s) => s.split(""))
    .map((arr) => arr.map(parse_int).filter(Number.isInteger))
    .map((arr) => arr[0] * 10 + arr[arr.length - 1])
    .reduce((a, b) => {
        const s = a + b;
        return s;
    }, 0);

console.log(sum2);
