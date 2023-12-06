const { parse_int, load, chunked } = require("./util/util");

const input = load("05");

const read_ranges = (block) => {
    const lines = block.split(/\r?\n/g).slice(1);

    const mapping = [];

    lines.forEach((line) => {
        const parts = line.split(/\s+/g).map(parse_int);

        const dest = parts[0];
        const src = parts[1];
        const num = parts[2];

        const start = src;
        const end = src + num - 1;
        const amount_to_add = dest - src;

        mapping.push([start, end, amount_to_add]);
    });

    return mapping;
};

const calc_seed = (seed_input, steps) => {
    let seed = seed_input;
    steps.forEach((ranges) => {
        const to_add = ranges
            .filter((r) => r[0] <= seed && r[1] >= seed)
            .flat();

        if (to_add.length == 0) return seed;

        seed = seed + to_add[2];
    });
    return seed;
};

const calc = (input_seeds) => {
    let seeds = [...input_seeds];

    input_parts.slice(1).forEach((block) => {
        const ranges = read_ranges(block);
        seeds = seeds.map((seed) => calc_seed(seed, ranges));
    });

    return seeds;
};

const input_parts = input.split(/\r?\n\r?\n/g);

const ranges = input_parts.slice(1).map(read_ranges);

let seeds = input_parts[0].split(": ")[1].split(" ").map(parse_int);
seeds = calc(seeds);
const lowest1 = seeds.reduce(
    (a, b) => (a < b ? a : b),
    Number.MAX_SAFE_INTEGER
);
console.log(lowest1);

// BEWARE: part 2 runs for a long time ...

let min = Number.MAX_SAFE_INTEGER;

seeds = input_parts[0].split(": ")[1].split(" ").map(parse_int);
chunked(seeds, 2).forEach(([start, num]) => {
    console.log("calculating", start, num);
    for (let i = start; i < start + num; i++) {
        const out = calc_seed(i, ranges);
        if (out < min) min = out;
    }
});

console.log(min);
