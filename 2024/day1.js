const { read_lines } = require("./util/reader");

const load = () => {
    const left = [];
    const right = [];

    const lines = read_lines("input/day1");

    lines.forEach((line) => {
        const [l, r] = line.split(/\s+/);
        left.push(parseInt(l));
        right.push(parseInt(r));
    });

    return { left: left, right: right };
};

const part1 = () => {
    const { left, right } = load();

    left.sort();
    right.sort();

    let sum = 0;

    for (let i = 0; i < left.length; i++) {
        const diff = right[i] - left[i];
        if (diff < 0) sum -= diff;
        else sum += diff;
    }

    console.log(sum);
};

const part2 = () => {
    const { left, right } = load();

    let sum = 0;

    left.forEach((num) => {
        const count = right.reduce(
            (prev, curr) => (curr == num ? prev + 1 : prev),
            0
        );
        sum += count * num;
    });

    console.log(sum);
};

part1();
part2();
