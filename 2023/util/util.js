const fs = require("fs");

const load = (day) => {
    return fs.readFileSync(__dirname + `/../input/${day}`, "utf8");
};

exports.load = load;

const as_lines = (day) => {
    return load(day)
        .split(/\r?\n/)
        .filter((line) => line != "");
};

exports.as_lines = as_lines;

const as_grid = (day) => {
    return as_lines(day).map((line) => line.split(""));
};

exports.as_grid = as_grid;

const range = (len) => [...Array(len).keys()];

exports.range = range;

const reverse_str = (str) => str.split("").reverse().join("");

exports.reverse_str = reverse_str;

const parse_int = (str) => parseInt(str);

exports.parse_int = parse_int;

const only_digits = (line) => line.match(/\d+/g).map((s) => parseInt(s));

exports.only_digits = only_digits;

const sum = (a, b) => a + b;

exports.sum = sum;

const chunked = (arr, num) =>
    range(arr.length)
        .filter((i) => i % num == 0)
        .map((i) => arr.slice(i, i + num));

exports.chunked = chunked;

const grid_find = (grid, el) => {
    for (let y = 0; y < grid.length; y++) {
        const row = grid[y];
        for (let x = 0; x < row.length; x++) {
            if (row[x] == el) return [x, y];
        }
    }
};

exports.grid_debug_print = (grid) => {
    console.log(grid.map((row) => row.join("")).join("\n"));
};

exports.grid_find = grid_find;

const in_grid = (grid, [x, y]) => {
    return y >= 0 && y < grid.length && x >= 0 && x < grid[0].length;
};

exports.in_grid = in_grid;

const grid_neighbors_diag = (grid, pos) => {
    const neighbors = [];

    const x0 = pos[1];
    const y0 = pos[0];
    range(3)
        .map((i) => y0 - 1 + i)
        .forEach((y) => {
            range(3)
                .map((i) => x0 - 1 + i)
                .forEach((x) => {
                    const neighbor = [x, y];
                    if ((y != pos[1] || x != pos[0]) && in_grid(grid, neighbor))
                        neighbors.push(neighbor);
                });
        });

    return neighbors;
};

exports.grid_neighbors_diag = grid_neighbors_diag;

const grid_neighbors = (grid, pos) => {
    const neighbors = [
        [pos[0] - 1, pos[1]],
        [pos[0] + 1, pos[1]],
        [pos[0], pos[1] - 1],
        [pos[0], pos[1] + 1],
    ];
    return neighbors.filter((n) => in_grid(grid, n));
};

exports.grid_neighbors = grid_neighbors;

const greatest_common_divisor = (a, b) => {
    return !b ? a : greatest_common_divisor(b, a % b);
};

exports.greatest_common_divisor = greatest_common_divisor;

const least_common_multiple = (a, b) => {
    return (a * b) / greatest_common_divisor(a, b);
};

exports.least_common_multiple = least_common_multiple;

const least_common_multiple_many = (numbers) => {
    numbers = numbers.map((n) => BigInt(n));

    const num0 = numbers[0];
    if (num0 == null) throw Error("num0 is null");
    numbers.shift();

    if (numbers.length == 2) {
        return least_common_multiple(num0, numbers.shift());
    }

    return numbers.reduce((a, b) => least_common_multiple(a, b), num0);
};

exports.least_common_multiple_many = least_common_multiple_many;
