const fs = require("node:fs");

const as_lines = (data) => {
    return data.split(/\r?\n/).filter((str) => str != "");
};

const read_lines = (path) => {
    const data = fs.readFileSync(path, "utf8");
    return as_lines(data);
};

const IDENTITY = (el) => el;

const read_array = (path, fn = IDENTITY) => {
    const lines = read_lines(path);
    const grid = [];
    for (let i = 0; i < lines.length; i++) {
        const a = lines[i].split("");
        grid[i] = a.map(fn);
    }
    return grid;
};

const read_array_int = (path) => {
    return read_array(path, (el) => parseInt(el));
};

module.exports = {
    read_lines,
    read_array,
    read_array_int,
};
