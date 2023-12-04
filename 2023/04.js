const { parse_int, as_lines, sum, range } = require("./util/util");

const lines = as_lines("04");

const parse = (lines) =>
    lines
        .map((line) => line.replace(/\s+/g, " "))
        .map((line) => {
            const id = parse_int(line.split(": ")[0].split(" ")[1]);
            const winning_numbers = line
                .split(": ")[1]
                .split(" | ")[0]
                .split(" ")
                .map(parse_int);
            const input_numbers = line
                .split(": ")[1]
                .split(" | ")[1]
                .split(" ")
                .map(parse_int);
            return { id: id, winning: winning_numbers, mine: input_numbers };
        });

const to_wins = (card) =>
    card.mine.filter((i) => card.winning.includes(i)).length;

const to_points = (card) => {
    const amount = to_wins(card);
    if (amount == 0) return 0;
    return 2 ** (amount - 1);
};

const input = parse(lines);

const sum1 = input.map(to_points).reduce(sum, 0);
console.log(sum1);

const amounts = range(input.length).reduce(
    (acc, val) => ({ ...acc, [val + 1]: 1 }),
    {}
);

input.forEach((card) => {
    const num = to_wins(card);
    const amount = amounts[card.id]
    range(num)
        .map((n) => card.id + n + 1)
        .forEach((id) => {
            amounts[id] += amount;
        });
});

const sum2 = Object.values(amounts).reduce(sum, 0)

console.log(sum2);
