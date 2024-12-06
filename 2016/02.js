const { load } = require("./util/load.js");
const { transpose } = require("./util/objects.js");
const { range } = require("./util/range.js");

const data = load("02");

let idx = 4;
let code = [];

const moves = new Map();
moves.set("U", () => {
    if (idx > 2) idx -= 3;
});
moves.set("D", () => {
    if (idx < 6) idx += 3;
});
moves.set("L", () => {
    if (idx % 3 > 0) idx -= 1;
});
moves.set("R", () => {
    if (idx % 3 < 2) idx += 1;
});

data.split("\n").forEach((line) => {
    if (line == "") return;
    line.split("").forEach((c) => {
        moves.get(c).call();
    });
    code.push(idx + 1);
});

console.log(code.join(""));

///

const down = { 1: "3", 2: "6", 3: "7", 4: "8", 6: "A", 7: "B", 8: "C", B: "D" };
const up = transpose(down);
const right = {
    2: "3",
    3: "4",
    5: "6",
    6: "7",
    7: "8",
    8: "9",
    A: "B",
    B: "C",
};
const left = transpose(right);

moves.set("U", () => (idx = idx in up ? up[idx] : idx));
moves.set("D", () => (idx = idx in down ? down[idx] : idx));
moves.set("L", () => (idx = idx in left ? left[idx] : idx));
moves.set("R", () => (idx = idx in right ? right[idx] : idx));

idx = "5";
code = [];

data.split("\n").forEach((line) => {
    if (line == "") return;
    line.split("").forEach((c) => {
        moves.get(c).call();
    });
    code.push(idx);
});

console.log(code.join(""));
