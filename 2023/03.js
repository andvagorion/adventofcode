const { range, parse_int, as_lines } = require("./util/util");

const lines = as_lines("03");

const matches = (x, y, fn) => {
    if (y < 0 || y >= lines.length) return false;
    if (x < 0 || x >= lines[y].length) return false;

    const val = lines[y][x];

    if (/\d/.test(val)) return false;

    return fn(val);
};

const get_neighbors = (x0, y0, fn) =>
    range(3)
        .map((y) => y - 1)
        .map((y) => y0 + y)
        .map((y) =>
            range(3)
                .map((x) => x - 1)
                .map((x) => x0 + x)
                .map((x) => (matches(x, y, fn) ? x + "-" + y : null))
                .filter((x) => x != null)
        )
        .flat()
        .filter((x) => x != null);

const test_neighbor = (x0, y0, fn) =>
    range(3)
        .map((y) => y - 1)
        .map((y) => y0 + y)
        .some((y) =>
            range(3)
                .map((x) => x - 1)
                .map((x) => x0 + x)
                .some((x) => matches(x, y, fn))
        );

const neighbor_is_symbol = (x0, y0) => test_neighbor(x0, y0, (c) => c != ".");

const pattern = /(\d+)/g;

const sum1 = range(lines.length)
    .map((y) => {
        const line = lines[y];

        let matches = line.matchAll(pattern);
        if (matches == null) return;
        matches = Array.from(matches, (m) => [m[0], m.index]);

        const nums = matches.filter(([num, idx]) =>
            range(num.length)
                .map((x) => idx + x)
                .some((x) => neighbor_is_symbol(x, y))
        );

        return nums;
    })
    .flat()
    .map(parse_int)
    .reduce((a, b) => a + b, 0);

console.log(sum1);

const gear_nums = new Map();

range(lines.length).forEach((y) => {
    const line = lines[y];

    let matches = line.matchAll(pattern);
    if (matches == null) return;
    matches = Array.from(matches, (m) => [m[0], m.index]);

    matches.map(([num, idx]) =>
        range(num.length)
            .map((x) => idx + x)
            .forEach((x) => {
                const gears = get_neighbors(x, y, (c) => c == "*");
                if (gears.length > 0) {
                    gears.forEach((gear) => {
                        if (!gear_nums.has(gear))
                            gear_nums.set(gear, new Set());

                        gear_nums.get(gear).add(num);
                    });
                }
            })
    );
});

const sum2 = Array.from(gear_nums.values())
    .filter((set) => set.size == 2)
    .map((set) => [...set].reduce((a, b) => a * b, 1))
    .reduce((a, b) => a + b, 0);

console.log(sum2);
