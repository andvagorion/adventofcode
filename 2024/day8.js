const { range } = require("./util/arrays");
const { offgrid, find_all, iterate } = require("./util/grid");
const { read_array } = require("./util/reader");

const grid = read_array("input/day8");

const get_symbols = (grid) => {
    const symbols = new Set();
    iterate(grid, (el) => symbols.add(el));
    return [...symbols].filter((el) => el != ".");
};

const dist = (p1, p2) => {
    return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
};

const subtract = (p1, p2) => {
    return [p1[0] - p2[0], p1[1] - p2[1]];
};

const add = (p1, p2) => {
    return [p1[0] + p2[0], p1[1] + p2[1]];
};

const calc = (location, diff, continuous = false) => {
    const nodes = [];

    let done = false;
    let current = location;

    while (!done) {
        const antinode = add(current, diff);

        if (offgrid(grid, antinode)) {
            done = true;
            continue;
        }

        nodes.push(antinode);

        current = antinode;

        if (!continuous) done = true;
    }

    return nodes;
};

const add_all = (antinodes, nodes) => {
    nodes.forEach((node) => {
        if (!antinodes.some((el) => el[0] == node[0] && el[1] == node[1])) {
            antinodes.push(node);
        }
    });
};

const solve = (continuous = false) => {
    const symbols = get_symbols(grid);
    const antinodes = [];

    for (const symbol of symbols) {
        const locations = find_all(grid, symbol);

        for (const location of locations) {
            for (const other of locations) {
                if (dist(location, other) == 0) continue;
                const diff = subtract(location, other);

                const nodes = calc(location, diff, continuous);
                add_all(antinodes, nodes);

                if (continuous) {
                    const diff_opposite = subtract([0, 0], diff);
                    const start = add(location, diff);
                    const nodes = calc(start, diff_opposite, continuous);
                    add_all(antinodes, nodes);
                }
            }
        }
    }

    return antinodes;
};

console.log(solve().length);
console.log(solve(true).length);
