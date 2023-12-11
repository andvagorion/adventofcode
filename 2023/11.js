const { range, as_grid, manhattan_distance } = require("./util/util");

const grid = as_grid("11");

const galaxies = [];
range(grid.length).forEach((y) => {
    range(grid[0].length).forEach((x) => {
        if (grid[y][x] == "#") galaxies.push([x, y]);
    });
});

const empty_rows = [];
range(grid.length).forEach((y) => {
    if (grid[y].every((c) => c == ".")) empty_rows.push(y);
});

const empty_cols = [];
range(grid[0].length).forEach((x) => {
    if (range(grid.length).every((y) => grid[y][x] == ".")) empty_cols.push(x);
});

const empty_between = (arr, c0, c1) => {
    if (c0 == c1) return 0;

    const min = c0 < c1 ? c0 : c1;
    const max = c0 > c1 ? c0 : c1;

    const x = arr.filter((c) => c > min && c < max);
    return x;
};

const unchecked = [...galaxies];
let sum1 = 0;
let sum2 = 0;

while (unchecked.length > 0) {
    const galaxy = unchecked.shift();

    unchecked.forEach((other) => {
        let dist = manhattan_distance(galaxy, other);
        sum1 += dist;
        sum2 += dist;

        const rows = empty_between(empty_rows, galaxy[1], other[1]);
        const cols = empty_between(empty_cols, galaxy[0], other[0]);

        if (rows.length > 0) {
            sum1 += rows.length;
            sum2 += rows.length * 999999;
        }
        if (cols.length > 0) {
            sum1 += cols.length;
            sum2 += cols.length * 999999;
        }
    });
}

console.log(sum1);
console.log(sum2);
