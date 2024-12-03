const fs = require("node:fs");

const as_lines = (data) => {
    return data.split(/\r?\n/).filter((str) => str != "");
};

const read_lines = (path) => {
    const data = fs.readFileSync(path, "utf8");
    return as_lines(data);
};

module.exports = {
    read_lines,
};
