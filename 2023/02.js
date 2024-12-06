const { as_lines, range, parse_int, only_digits } = require("./util/util");

const lines = as_lines("02");

const example = [
    "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
    "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
    "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
    "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
    "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green",
];

const to_rounds = (str) => {
    return str.split("; ").map(
        (round) =>
            round
                .split(", ")
                .map((r) => r.split(" "))
                .reduce((obj, r) => ({ ...obj, [r[1]]: parse_int(r[0]) }), {}),
        {}
    );
};

const to_game = (line) => {
    const info = line.split(": ");
    const id = parse_int(info[0].split(" ")[1]);
    const rounds = to_rounds(info[1]);
    return { id: id, rounds: rounds };
};

const max = { red: 12, green: 13, blue: 14 };

const is_possible = (game) =>
    game.rounds.every((round) =>
        Object.entries(round).every(([k, v]) => v <= max[k])
    );

const sum = lines
    .map(to_game)
    .filter(is_possible)
    .map((game) => game.id)
    .reduce((a, b) => a + b, 0);

console.log(sum);

const fewest = (game) =>
    game.rounds.reduce(
        (acc, round) => {
            const min = { ...acc };
            Object.keys(round).forEach((k) => {
                if (round[k] > min[k]) min[k] = round[k];
            });
            return min;
        },
        { red: 0, green: 0, blue: 0 }
    );

const power = (round) => Object.values(round).reduce((a, b) => a * b, 1);

const sum2 = lines
    .map(to_game)
    .map(fewest)
    .map(power)
    .reduce((a, b) => a + b, 0);

console.log(sum2);
