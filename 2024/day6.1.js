const { find, N, turn_right, offgrid, find_first } = require("./util/grid");
const { range } = require("./util/arrays");
const { read_array } = require("./util/reader");

const grid = read_array("input/day6");

const wall = (grid, pos) => {
    return grid[pos[1]][pos[0]] == "#";
};

let guard = find_first(grid, "^");
grid[guard[1]][guard[0]] = ".";

const positions = [];
positions.push([...guard]);

let done = false;
let dir = N;

let i = 0;

while (!done) {
    const next = [guard[0] + dir[0], guard[1] + dir[1]];

    if (offgrid(grid, next)) {
        done = true;
        break;
    }

    if (wall(grid, next)) {
        dir = turn_right(dir);
        continue;
    }

    if (!positions.some((pos) => pos[0] == next[0] && pos[1] == next[1])) {
        positions.push(next);
    }

    guard = next;

    if (i++ > 10_000) done = true;
}

console.log(positions.length);
