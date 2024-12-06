const {
    range,
    as_lines,
    chunked_by_empty_lines,
} = require("./util/util");

const transpose = (lines) => {
    const len = lines[0].length;

    return range(len).map((x) =>
        range(lines.length)
            .map((y) => lines[y][x])
            .join("")
    );
};

const full_reflects = (lines, idx) => {
    const first = lines.slice(0, idx).reverse();
    const second = lines.slice(idx);

    const len = Math.min(first.length, second.length);

    return range(len).every((y) => {
        return first[y] == second[y];
    });
};

const find_reflection_line = (lines) => {
    for (let curr = 1; curr < lines.length; curr++) {
        let prev = curr - 1;

        const reflects = lines[prev] == lines[curr];

        if (reflects && full_reflects(lines, curr)) return curr;
    }

    return -1;
};

const input = as_lines("13", { keep_empty_lines: true });

const chunks = chunked_by_empty_lines(input);

let sum = 0;

chunks.forEach((chunk) => {
    const hor = find_reflection_line(chunk);
    if (hor > 0) sum += hor * 100;

    if (hor > 0) return;

    const vert = find_reflection_line(transpose(chunk));
    if (vert > 0) sum += vert;
});

console.log(sum);
