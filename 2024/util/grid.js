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

const find_first = (g, needle) => {
    for (let y of range(g.length)) {
        for (let x of range(g[y].length)) {
            if (g[y][x] == needle) return [x, y];
        }
    }
};

const debug_print = (grid, positions = [], c = "#") => {
    const out = range(grid.length)
        .map((y) => {
            return range(grid[y].length)
                .map((x) => {
                    if (positions.some((pos) => pos[0] == x && pos[1] == y)) {
                        return "#";
                    }
                    return grid[y][x];
                })
                .join("");
        })
        .join("\n");

    console.log(out);
};

const offgrid = (grid, pos) => {
    return (
        pos[1] < 0 ||
        pos[1] >= grid.length ||
        pos[0] < 0 ||
        pos[0] >= grid[1].length
    );
};

const iterate = (grid, fn) =>
    range(grid.length).forEach((y) =>
        range(grid[y].length).forEach((x) => {
            const val = grid[y][x];
            fn(val, x, y);
        })
    );

const find_all = (grid, symbol) => {
    const locs = [];
    iterate(grid, (val, x, y) => {
        if (val == symbol) locs.push([x, y]);
    });
    return locs;
};

module.exports = {
    N,
    S,
    W,
    E,
    turn_right,
    find_first,
    debug_print,
    offgrid,
    iterate,
    find_all,
};
