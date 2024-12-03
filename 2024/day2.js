const { read_lines } = require("./util/reader");

const lines = read_lines("input/day2").map((line) =>
    line.split(/\s+/).map((str) => parseInt(str))
);

const get_diffs = (data) => {
    const diffs = [];
    for (let i = 0; i < data.length - 1; i++) {
        const diff = data[i] - data[i + 1];
        diffs.push(diff);
    }
    return diffs;
};

const is_safe = (line) => {
    const diffs = get_diffs(line);
    return (
        diffs.every((el) => el > 0 && el <= 3) ||
        diffs.every((el) => el < 0 && el >= -3)
    );
};

const part1 = () => {
    let sum = 0;

    lines.forEach((line) => {
        if (is_safe(line)) sum += 1;
    });

    console.log(sum);
};

const is_safe_dampened = (line) => {
    for (let i = 0; i < line.length; i++) {
        const copy = [...line];
        copy.splice(i, 1);

        if (is_safe(copy)) return true;
    }

    return false;
};

const part2 = () => {
    let sum = 0;

    lines.forEach((line) => {
        if (is_safe_dampened(line)) sum += 1;
    });

    console.log(sum);
};

part1();
part2();
