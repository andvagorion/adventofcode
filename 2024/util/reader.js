const fs = require("node:fs");

const as_lines = (data) => {
    return data.split(/\r?\n/).filter((str) => str != "");
};

const read_lines = (path) => {
    const data = fs.readFileSync(path, "utf8");
    return as_lines(data);
};

const read_array = (path) => {
    const lines = read_lines(path);
    const grid = [];
    for (let i = 0; i < lines.length; i++) {
        const a = lines[i].split("");
        grid[i] = a;
    }
    return grid;
};

module.exports = {
    read_lines,
    read_array,
};
