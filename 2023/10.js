const {
    range,
    as_grid,
    grid_find,
    grid_neighbors,
    grid_debug_print,
} = require("./util/util");

const N = 0,
    E = 1,
    S = 2,
    W = 3;

const grid = as_grid("10");

const dists = range(grid.length).map(() => range(grid[0].length).map(() => -1));

const get_dir = (el, other) => {
    if (other[1] < el[1]) return N;
    if (other[1] > el[1]) return S;
    if (other[0] < el[0]) return W;
    if (other[0] > el[0]) return E;
};

const connects = (grid, el, other) => {
    const dir = get_dir(el, other);
    const val = grid[el[1]][el[0]];

    const cn = dir == N && (val == "|" || val == "L" || val == "J");
    const cs = dir == S && (val == "|" || val == "7" || val == "F");
    const cw = dir == W && (val == "-" || val == "7" || val == "J");
    const ce = dir == E && (val == "-" || val == "L" || val == "F");

    return cn || cs || cw || ce;
};

const start = grid_find(grid, "S");
dists[start[1]][start[0]] = 0;
grid[start[1]][start[0]] = "-";

let max = 0;

const checked = [];
const to_check = [start];

while (to_check.length > 0) {
    const curr = to_check.shift();
    const curr_val = dists[curr[1]][curr[0]];
    const neighbors = grid_neighbors(grid, curr);

    neighbors
        .filter((n) => !checked.some((c) => c[0] == n[0] && c[1] == n[1]))
        .forEach((neighbor) => {
            if (
                connects(grid, neighbor, curr) &&
                connects(grid, curr, neighbor)
            ) {
                const val = curr_val + 1;
                dists[neighbor[1]][neighbor[0]] = val;
                if (val > max) max = val;
                to_check.push(neighbor);
                checked.push(neighbor);
            }
        });
}

console.log(max);

const debug_print_pipes = (grid) => {
    const pipes = range(grid.length).map((y) =>
        range(grid[0].length).map((x) => {
            const dist = dists[y][x];

            if (dist < 0) return ".";

            const val = grid[y][x];

            if (val == "F") return "┌";
            if (val == "7") return "┐";
            if (val == "J") return "┘";
            if (val == "L") return "└";
            if (val == "-") return "─";
            if (val == "|") return "│";
            return ".";
        })
    );

    grid_debug_print(pipes);
};

const paint = (g, x0, y0, arr) => {
    const y_big = y0 * 3;
    const x_big = x0 * 3;
    range(3).forEach((y) => {
        const row = arr[y].split("");
        range(3).forEach((x) => {
            g[y_big + y][x_big + x] = row[x];
        });
    });
};

const big = range(grid.length * 3).map(() =>
    range(grid[0].length * 3).map(() => ".")
);

range(grid.length).forEach((y) => {
    range(grid[y].length).forEach((x) => {
        if (dists[y][x] < 0) {
            paint(big, x, y, ["   ", " e ", "   "]);
            return;
        }

        if (grid[y][x] == "F") paint(big, x, y, ["   ", " xx", " x "]);
        if (grid[y][x] == "7") paint(big, x, y, ["   ", "xx ", " x "]);
        if (grid[y][x] == "L") paint(big, x, y, [" x ", " xx", "   "]);
        if (grid[y][x] == "J") paint(big, x, y, [" x ", "xx ", "   "]);
        if (grid[y][x] == "-") paint(big, x, y, ["   ", "xxx", "   "]);
        if (grid[y][x] == "|") paint(big, x, y, [" x ", " x ", " x "]);
    });
});

// flood fill

const ff = [[0, 0]];

while (ff.length > 0) {
    const next = ff.shift();

    const x = next[0];
    const y = next[1];
    big[y][x] = "x";

    const neighbors = grid_neighbors(big, next);

    neighbors.forEach((neighbor) => {
        const nx = neighbor[0];
        const ny = neighbor[1];
        if (
            big[ny][nx] == " " &&
            !ff.some((other) => other[0] == nx && other[1] == ny)
        )
            ff.push([nx, ny]);
    });
}

const big_str = big.map(row => row.join('')).join('')

const count = (big_str.match(/ e /g) || []).length;

console.log(count);
