const { range, as_grid, grid_find, grid_neighbors } = require("./util/util");

const debug_print = (grid, tiles) => {
    const out = range(grid.length)
        .map((y) =>
            range(grid[y].length)
                .map((x) => {
                    if (tiles.some((nt) => nt[0] == x && nt[1] == y))
                        return "O";
                    else return grid[y][x];
                })
                .join("")
        )
        .join("\n");

    console.log(out);
    console.log("=====");
};

const grid = as_grid("21");

const start = grid_find(grid, "S");

grid[start[1]][start[0]] = ".";

const tiles = [[start]];
let steps = 0;
let amount = -1;

while (steps < 64) {
    const current_tiles = tiles.shift();
    const next_tiles = [];

    current_tiles.forEach((tile) => {
        const neighbors = grid_neighbors(grid, tile);

        neighbors.forEach((n) => {
            if (grid[n[1]][n[0]] != ".") return;

            if (!next_tiles.some((t) => t[0] == n[0] && t[1] == n[1]))
                next_tiles.push(n);
        });
    });

    // debug_print(grid, next_tiles)

    tiles.push(next_tiles);
    steps += 1;

    amount = next_tiles.length;
}

console.log(amount);
