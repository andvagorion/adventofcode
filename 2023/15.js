const { as_lines, range } = require("./util/util");

const lines = as_lines("15");

const parts = lines[0].split(",");

const hash = (str) => {
    let curr = 0;

    for (const char of str) {
        const num = char.charCodeAt(0);

        curr += num;
        curr *= 17;

        curr %= 256;
    }

    return curr;
};

let sum = 0;

for (const part of parts) {
    const hashed = hash(part);
    sum += hashed;
}

console.log(sum);

const find = (arr, fn) => {
    for (let i = 0; i < arr.length; i++) {
        if (fn(arr[i])) return i;
    }
    return -1;
};

const boxes = range(256).map(() => []);

for (const part of parts) {
    if (part.includes("-")) {
        const str = part.split("-")[0];
        const box_idx = hash(str);

        const str_idx = find(boxes[box_idx], (el) => el[0] == str);
        if (str_idx >= 0) boxes[box_idx].splice(str_idx, 1);
    }

    if (part.includes("=")) {
        const str = part.split("=")[0];
        const foc = part.split("=")[1];
        const box_idx = hash(str);

        // label already present?
        const str_idx = find(boxes[box_idx], (el) => el[0] == str);
        if (str_idx >= 0) {
            boxes[box_idx][str_idx][1] = foc;
        } else {
            boxes[box_idx].push([str, foc]);
        }
    }
}

let sum2 = 0;

for (let box_num = 0; box_num < boxes.length; box_num++) {
    const box = boxes[box_num];
    for (let slot_num = 0; slot_num < box.length; slot_num++) {
        const focal_len = box[slot_num][1]
        const value = (1 + box_num) * (slot_num + 1) * focal_len;
        sum2 += value;
    }
}

console.log(sum2);
