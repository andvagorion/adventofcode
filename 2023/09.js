const { range, parse_int, sum, as_lines } = require("./util/util");

const lines = as_lines("09");

const next_row = (numbers) =>
    range(numbers.length - 1).map((i) => {
        const num = numbers[i];
        const next = numbers[i + 1];
        return next - num;
    });

const calc = (line) => {
    const numbers = line.split(" ").map(parse_int);

    const rows = [numbers];
    let row = 0;

    while (!rows[row].every((i) => i == 0)) {
        rows.push(next_row(rows[row]));
        row += 1;
    }

    return rows;
};

const extrapolate_right = (rows) => {
    let row = rows.length - 2;

    while (row >= 0) {
        const diff_numbers = rows[row + 1];
        const numbers = rows[row];

        const diff = diff_numbers[diff_numbers.length - 1];

        const num = numbers[numbers.length - 1];

        const next = num + diff;
        rows[row].push(next);

        row -= 1;
    }

    return rows[0][rows[0].length - 1];
};

const part1 = lines.map(calc).map(extrapolate_right).reduce(sum, 0);

console.log(part1);

const extrapolate_left = (rows) => {
    let row = rows.length - 2;

    while (row >= 0) {
        const diff_numbers = rows[row + 1];
        const numbers = rows[row];

        const diff = diff_numbers[0];

        const num = numbers[0];

        const prev = num - diff;
        rows[row].unshift(prev);

        row -= 1;
    }

    return rows[0][0];
};

const part2 = lines.map(calc).map(extrapolate_left).reduce(sum, 0);

console.log(part2);
