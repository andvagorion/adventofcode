const fs = require("fs");

const load = (day) => {
    return fs.readFileSync(__dirname + `/../input/${day}`, "utf8");
};

exports.load = load;

const as_lines = (day, options = { keep_empty_lines: false }) => {
    return load(day)
        .split(/\r?\n/)
        .filter((line) => (!options.keep_empty_lines ? line != "" : true));
};

exports.as_lines = as_lines;

const as_grid = (day) => {
    return as_lines(day).map((line) => line.split(""));
};

exports.as_grid = as_grid;

const transpose_grid = (grid) =>
    range(grid[0].length).map((col) =>
        range(grid.length).map((row) => grid[grid.length - 1 - row][col])
    );

exports.transpose_grid = transpose_grid;

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

const chunked_by_empty_lines = (arr) => {
    const chunks = [];
    let chunk = [];
    arr.forEach((line) => {
        if (line != "") {
            chunk.push(line);
        } else {
            chunks.push(chunk);
            chunk = [];
        }
    });
    return chunks;
};

exports.chunked_by_empty_lines = chunked_by_empty_lines;

const grid_find = (grid, el) => {
    for (let y = 0; y < grid.length; y++) {
        const row = grid[y];
        for (let x = 0; x < row.length; x++) {
            if (row[x] == el) return [x, y];
        }
    }
    return null;
};

exports.grid_find = grid_find;

const grid_count = (grid, char) => grid.flat().filter((c) => c == char).length;

exports.grid_count = grid_count;

exports.grid_debug_print = (grid) => {
    console.log(grid.map((row) => row.join("")).join("\n"));
};

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

const manhattan_distance = ([x0, y0], [x1, y1]) => {
    return Math.abs(x1 - x0) + Math.abs(y1 - y0);
};

exports.manhattan_distance = manhattan_distance;

const floodfill_grid = (arr, start, condition, val) => {
    const ff = [start];

    while (ff.length > 0) {
        const next = ff.shift();

        const x = next[0];
        const y = next[1];
        arr[y][x] = val;

        const neighbors = grid_neighbors(arr, next);

        neighbors.forEach((neighbor) => {
            const nx = neighbor[0];
            const ny = neighbor[1];
            if (
                condition(arr[ny][nx]) &&
                !ff.some((other) => other[0] == nx && other[1] == ny)
            )
                ff.push([nx, ny]);
        });
    }
};

exports.floodfill_grid = floodfill_grid;
