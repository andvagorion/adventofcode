const { as_lines } = require("./util/load");
const { alphabet } = require("./util/text");

const lines = as_lines("04");

const count = (word) =>
    alphabet.map((letter) => [letter, word.split(letter).length - 1]);
const frequency = (word) => count(word).sort((a, b) => b[1] - a[1]);

const get_hash = (room) =>
    room.substring(0, room.length - 10).replace(/-/g, "");
const sector_id = (room) =>
    parseInt(room.substring(room.length - 10, room.length - 7));
const checksum = (room) => room.substring(room.length - 6, room.length - 1);

const is_real = (room) => {
    const computed = frequency(get_hash(room))
        .slice(0, 5)
        .map((a) => a[0])
        .join("");
    return checksum(room) == computed;
};

const real_rooms = lines.filter((line) => is_real(line));

const part1 = real_rooms
    .map((line) => sector_id(line))
    .reduce((a, b) => a + b, 0);
console.log(part1);

////////////

const rot = (c, num) => alphabet[(alphabet.indexOf(c) + (num % 26)) % 26];
const unscramble = (room) => {
    const id = sector_id(room);
    return get_hash(room)
        .split("")
        .map((c) => rot(c, id))
        .join("");
};

real_rooms
    .map((room) => [unscramble(room), sector_id(room)])
    .filter((s) => s[0].includes("north"))
    .forEach((s) => console.log(s[1]));
