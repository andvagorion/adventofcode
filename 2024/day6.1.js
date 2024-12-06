const { find, N, turn_right } = require("./util/grid");
const { range } = require("./util/arrays");
const { read_array } = require("./util/reader");

const grid = read_array("input/day6");

const debug_print = (grid, positions) => {
  const out = range(grid.length)
    .map((y) => {
      return range(grid[y].length)
        .map((x) => {
          if (positions.some((pos) => pos[0] == x && pos[1] == y)) {
            return "X";
          }
          return grid[y][x];
        })
        .join("");
    })
    .join("\n");

  console.log(out);
};

const offscreen = (grid, pos) => {
  return (
    pos[1] < 0 ||
    pos[1] >= grid.length ||
    pos[0] < 0 ||
    pos[0] >= grid[1].length
  );
};

const wall = (grid, pos) => {
  return grid[pos[1]][pos[0]] == "#";
};

let guard = find(grid, "^");
grid[guard[1]][guard[0]] = ".";

const positions = [];
positions.push([...guard]);

let done = false;
let dir = N;

let i = 0;

while (!done) {
  const next = [guard[0] + dir[0], guard[1] + dir[1]];

  if (offscreen(grid, next)) {
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
