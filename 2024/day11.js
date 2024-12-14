const { range } = require("./util/arrays");
const { read_lines } = require("./util/reader");

const numbers = read_lines("input/day11")[0]
  .split(" ")
  .map((n) => parseInt(n));

console.log(numbers);

const split_even = (num) => {
  const str = "" + num;
  const len = str.length / 2;
  return [parseInt(str.substring(0, len)), parseInt(str.substring(len))];
};

const step = (arr) =>
  arr
    .map((num) => {
      if (num == 0) return 1;
      if (("" + num).length % 2 == 0) return split_even(num);
      return num * 2024;
    })
    .flat();

const sum = 0;

let out = [...numbers];
range(25).forEach((_) => {
  out = step(out);
});

console.log(out.length);
