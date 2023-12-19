const { as_lines, range, parse_int, floodfill_grid } = require("./util/util");

const get_dir = (str) => {
  switch (str) {
    case "U":
      return [0, -1];
    case "D":
      return [0, 1];
    case "R":
      return [1, 0];
    case "L":
      return [-1, 0];
    default:
      return null;
  }
};

const add = (point, other) => {
  return [point[0] + other[0], point[1] + other[1]];
};

const parse = (lines) => {
  let curr = [0, 0];
  const points = [[0, 0]];

  for (const line of lines) {
    const [dir_str, num_str, col] = line.split(" ");

    const dir = get_dir(dir_str);
    const num = parse_int(num_str);

    range(num).forEach(() => {
      curr = add(curr, dir);
      points.push(curr);
    });
  }

  return points;
};

const to_grid = (points) => {
  let min_y = Number.MAX_SAFE_INTEGER;
  let min_x = Number.MAX_SAFE_INTEGER;
  let max_y = Number.MIN_SAFE_INTEGER;
  let max_x = Number.MIN_SAFE_INTEGER;

  points.forEach((p) => {
    if (p[0] < min_x) min_x = p[0];
    if (p[0] > max_x) max_x = p[0];

    if (p[1] < min_y) min_y = p[1];
    if (p[1] > max_y) max_y = p[1];
  });

  const rows = Math.abs(min_y) + Math.abs(max_y) + 1 + 2;
  const cols = Math.abs(min_x) + Math.abs(max_x) + 1 + 2;
  const grid = range(rows).map(() => range(cols).map(() => "."));

  points.forEach((p) => {
    const x = p[0] - min_x + 1;
    const y = p[1] - min_y + 1;
    grid[y][x] = "x";
  });

  return grid;
};

const sum = (grid) =>
  grid.reduce(
    (a, row) =>
      a +
      row.reduce((b, col) => {
        const val = col == "x" || col == "." ? 1 : 0;
        return b + val;
      }, 0),
    0
  );

const lines = as_lines("18");

let points = parse(lines);

let grid = to_grid(points);

floodfill_grid(grid, [0, 0], (val) => val == ".", "o");

// console.log(grid.map((row) => row.join("")).join("\n"));

console.log(sum(grid));
