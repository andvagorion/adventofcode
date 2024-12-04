const range = (len) => [...Array(len).keys()];

const transpose = (grid) =>
    range(grid[0].length).map((col) =>
        range(grid.length).map((row) => grid[grid.length - 1 - row][col])
    );

module.exports = {
    range,
    transpose,
};
