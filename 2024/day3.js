const { read_lines } = require("./util/reader");

const corrupted = read_lines("input/day3").join("");

const mul = /mul\(\d+,\d+\)/g;
const mul_cond = /(mul\(\d+,\d+\)|do\(\)|don't\(\))/g;

const evaluate = (s) => {
    const [a, b] = s.match(/\d+/g);
    return a * b;
};

const part1 = () => {
    let sum = 0;

    corrupted.match(mul).forEach((el) => {
        sum += evaluate(el);
    });

    console.log(sum);
};

const part2 = () => {
    let active = true;
    let sum = 0;

    corrupted.match(mul_cond).forEach((el) => {
        if (el == "don't()") {
            active = false;
        } else if (el == "do()") {
            active = true;
        } else if (active) {
            sum += evaluate(el);
        }
    });

    console.log(sum);
};

part1();
part2();
