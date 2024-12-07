const { range } = require("./util/arrays");
const { read_lines } = require("./util/reader");

const lines = read_lines("input/day7");

const operations = [(a, b) => a + b, (a, b) => a * b];

const combine = (arr, num) => {
  if (num == 0) return arr;

  const all = [];
  for (const i of range(operations.length)) {
    const next = [...arr, i];
    const all_next = combine(next, num - 1);

    if (num > 1) {
      for (const a of all_next) all.push(a);
    } else {
      all.push(all_next);
    }
  }
  return all;
};

const solvable = (wanted, numbers) => {
  const combinations = combine([], numbers.length - 1);

  for (const combination of combinations) {
    let result = numbers[0];

    for (let i = 0; i < numbers.length - 1; i++) {
      const op = operations[combination[i]];
      result = op(result, numbers[i + 1]);
    }

    if (result == wanted) return true;
  }
  return false;
};

const solve = () => {
  let sum = 0;

  for (const line of lines) {
    let matches = line.match(/\d+/g).map((i) => parseInt(i));
    const result = matches.shift();

    if (solvable(result, matches)) {
      sum += result;
    }
  }

  console.log(sum);
};

solve();
operations.push((a, b) => parseInt("" + a + "" + b));
solve();
