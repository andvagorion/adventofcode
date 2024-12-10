const {
    find_all,
    get_value,
    N,
    S,
    W,
    E,
    move,
    pos_equals,
} = require("./util/grid");
const { range } = require("./util/arrays");
const { read_array_int } = require("./util/reader");

const next_options = (grid, position) => {
    const val = get_value(grid, position);

    return [N, S, W, E]
        .map((dir) => move(position, dir))
        .filter((other) => {
            const other_val = get_value(grid, other);
            return !isNaN(other_val) && val + 1 == other_val;
        });
};

const count_trails = (grid, start) => {
    let sum = 0;
    let options = [start];
    let peaks = find_all(grid, 9);

    while (options.length > 0) {
        const pos = options.pop();

        if (
            get_value(grid, pos) == 9 &&
            peaks.some((other) => pos_equals(pos, other))
        ) {
            sum += 1;
            peaks = peaks.filter((peak) => !pos_equals(peak, pos));
            continue;
        }

        const possible = next_options(grid, pos);
        possible.forEach((el) => options.push(el));
    }

    return sum;
};

const count_different_trails = (grid, start) => {
    let sum = 0;
    let options = [start];

    while (options.length > 0) {
        const pos = options.pop();

        if (get_value(grid, pos) == 9) {
            sum += 1;
            continue;
        }

        const possible = next_options(grid, pos);
        possible.forEach((el) => options.push(el));
    }

    return sum;
};

const part1 = () => {
    const grid = read_array_int("input/day10");
    const start_positions = find_all(grid, 0);

    const out = start_positions.reduce(
        (sum, pos) => sum + count_trails(grid, pos),
        0
    );

    console.log(out);
};

const part2 = () => {
    const grid = read_array_int("input/day10");
    const start_positions = find_all(grid, 0);

    const out = start_positions.reduce(
        (sum, pos) => sum + count_different_trails(grid, pos),
        0
    );

    console.log(out);
};

part1();
part2();
