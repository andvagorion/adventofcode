const { as_grid, range } = require("./util/util");

const X = 0,
  Y = 1;
const N = 0,
  E = 1,
  S = 2,
  W = 3;

const update = (grid, [x, y], input) => {
  if (x < 0 || x >= grid[0].length || y < 0 || y >= grid.length) return;

  if (grid[y][x].includes(input)) return;

  return [x, y, input];
};

const handle = (point, energy, input) => {
  let next;
  if (input == W) next = [point[X] + 1, point[Y]];
  if (input == E) next = [point[X] - 1, point[Y]];
  if (input == N) next = [point[X], point[Y] + 1];
  if (input == S) next = [point[X], point[Y] - 1];

  return update(energy, next, input);
};

const to_output = (value, input) => {
  if (value == "/") {
    switch (input) {
      case N:
        return E;
      case E:
        return N;
      case S:
        return W;
      case W:
        return S;
    }
  }
  if (value == "\\") {
    switch (input) {
      case N:
        return W;
      case W:
        return N;
      case S:
        return E;
      case E:
        return S;
    }
  }
};

const energize = (grid, start_x, start_y, start_dir) => {
  const energy = range(grid.length).map((y) =>
    range(grid[0].length).map(() => [])
  );

  let points = [[start_x, start_y, start_dir]];

  while (points.length > 0) {
    const [x, y, input] = points.pop();
    const value = grid[y][x];

    next = [];

    if (value == ".") {
      next.push(handle([x, y], energy, input));
    }

    if (value == "-" && (input == W || input == E)) {
      next.push(handle([x, y], energy, input));
    }

    if (value == "|" && (input == N || input == S)) {
      next.push(handle([x, y], energy, input));
    }

    if (value == "|" && (input == W || input == E)) {
      next.push(handle([x, y], energy, S));
      next.push(handle([x, y], energy, N));
    }

    if (value == "-" && (input == N || input == S)) {
      next.push(handle([x, y], energy, W));
      next.push(handle([x, y], energy, E));
    }

    if (value == "/") {
      const output = to_output(value, input);
      next.push(handle([x, y], energy, output));
    }

    if (value == "\\") {
      const output = to_output(value, input);
      next.push(handle([x, y], energy, output));
    }

    energy[y][x].push(input);

    next.filter((e) => e != null).forEach((p) => points.push(p));
  }

  return energy.flat().reduce((a, e) => a + (e.length > 0 ? 1 : 0), 0);
};

const grid = as_grid("16");
const count1 = energize(grid, 0, 0, W);
console.log(count1);

let max = 0;

range(grid.length).forEach((y) => {
  let count = energize(grid, 0, y, W);
  if (count > max) max = count;

  count = energize(grid, grid[0].length - 1, y, E);
  if (count > max) max = count;
});

range(grid[0].length).forEach((x) => {
  let count = energize(grid, x, 0, N);
  if (count > max) max = count;

  count = energize(grid, x, grid.length - 1, S);
  if (count > max) max = count;
});

console.log(max);
