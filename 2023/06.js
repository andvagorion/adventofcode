const { range, parse_int, as_lines } = require("./util/util");

const lines = as_lines("06");

const parse = (str) => str.split(/\s+/g).slice(1).map(parse_int);

const times = parse(lines[0]);
const dists = parse(lines[1]);

const to_dist = (time, max_speed) => (time - max_speed) * max_speed;

const part1 = range(times.length)
    .map((race) => {
        const time = times[race];
        const dist = dists[race];

        const wins = range(time)
            .map((max_speed) => to_dist(time, max_speed))
            .filter((t) => t > dist).length;

        return wins;
    })
    .reduce((a, b) => a * b, 1);

console.log(part1);

const time = parse_int(lines[0].split(/\s+/g).slice(1).join(""));
const dist = parse_int(lines[1].split(/\s+/g).slice(1).join(""));

const wins = range(time)
    .map((max_speed) => to_dist(time, max_speed))
    .filter((t) => t > dist).length;

console.log(wins);
