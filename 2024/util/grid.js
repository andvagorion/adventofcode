const { range } = require("./arrays");

const N = [0, -1];
const S = [0, 1];
const W = [-1, 0];
const E = [1, 0];

const turn_right = (dir) => {
    if (dir == N) return E;
    if (dir == E) return S;
    if (dir == S) return W;
    if (dir == W) return N;
};

const find = (g, needle) => {
  for (let y of range(g.length)) {
    for (let x of range(g[y].length)) {
      if (g[y][x] == needle) return [x, y];
    }
  }
};

module.exports = {
  N,
  S,
  W,
  E,
  turn_right,
  find,
};
