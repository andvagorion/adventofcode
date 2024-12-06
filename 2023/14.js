const { range, as_grid } = require("./util/util");

const move = (grid) => {
    const cols = grid[0].length;

    range(cols).forEach((col) => {
        let moves = 0;
        do {
            moves = 0;
            const rows = grid.length;

            for (let row = 1; row < rows; row++) {
                if (grid[row][col] == "O" && grid[row - 1][col] == ".") {
                    grid[row - 1][col] = "O";
                    grid[row][col] = ".";
                    moves += 1;
                }
            }
        } while (moves > 0);
    });
};

const total_load = (grid) => {
    let sum = 0;
    range(grid.length).forEach((row) => {
        const weight = grid.length - row;
        const cols = grid[row].length;
        range(cols).forEach((col) => {
            if (grid[row][col] == "O") {
                sum += weight;
            }
        });
    });
    return sum;
};

let grid = as_grid("14");
move(grid);
console.log(total_load(grid));
