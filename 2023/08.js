const {
    as_lines,
    least_common_multiple_many,
} = require("./util/util");

const lines = as_lines("08");

const ops = lines.shift().split("");

const nodes = new Map();

lines.forEach((line) => {
    const key = line.substring(0, 3);
    const conns = [line.substring(7, 10), line.substring(12, 15)];
    nodes.set(key, conns);
});

let i = 0;
let curr = "AAA";

while (curr != "ZZZ") {
    const op = ops[i % ops.length] == "L" ? 0 : 1;
    curr = nodes.get(curr)[op];
    i += 1;
}

console.log(i);

let keys = Array.from(nodes.keys()).filter((key) => key.endsWith("A"));

i = 0;

const curr_nodes = new Map();
keys.forEach((key) => curr_nodes.set(key, [key]));

const end_nums = new Map();

while (keys.length > 0) {
    const op = ops[i % ops.length] == "L" ? 0 : 1;

    const remaining_keys = [];

    keys.forEach((key) => {
        const curr = curr_nodes.get(key);
        const next = nodes.get(curr[curr.length - 1])[op];

        if (next.endsWith("Z")) {
            end_nums.set(key, [next, i + 1]);
        }

        if (
            !end_nums.has(key) ||
            !curr_nodes.get(key).includes(next)
        ) {
            remaining_keys.push(key);
        }

        curr_nodes.get(key).push(next);
    });

    keys = remaining_keys;

    i += 1;
}

const nums = Array.from(end_nums.values()).map(([key, num]) => num)
console.log(least_common_multiple_many(nums).toString());
