const { range } = require("./util/arrays");
const { read_array } = require("./util/reader");

const grid = read_array("input/day4");

const part1 = () => {
    let sum = 0;

    range(grid.length).forEach((y) => {
        range(grid[y].length).forEach((x) => {
            const hor = range(4).map((n) => [x + n, y]);
            const vert = range(4).map((n) => [x, y + n]);
            const diag1 = range(4).map((n) => [x + n, y + n]);
            const diag2 = range(4).map((n) => [x + n, y + 3 - n]);

            [hor, vert, diag1, diag2].forEach((tuples) => {
                const word = tuples.map(([x, y]) => grid[y]?.[x]).join("");
                if (word == "XMAS" || word == "SAMX") {
                    sum += 1;
                }
            });
        });
    });

    console.log(sum);
};

const part2 = () => {
    let sum = 0;

    range(grid.length).forEach((y) => {
        range(grid[y].length).forEach((x) => {
            const diag1 = range(3).map((n) => [x + n, y + n]);
            const diag2 = range(3).map((n) => [x + n, y + 2 - n]);

            if (
                [diag1, diag2].every((tuples) => {
                    const word = tuples.map(([x, y]) => grid[y]?.[x]).join("");
                    return word == "MAS" || word == "SAM";
                })
            ) {
                sum += 1;
            }
        });
    });

    console.log(sum);
};

part1();
part2();
