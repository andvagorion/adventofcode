const { range } = require("./range");

const contains = (arr, other) =>
    arr.some((el) => [0, 1].every((i) => el[i] == other[i]));

exports.contains = contains;

const chunked = (arr, num) =>
    range(arr.length)
        .filter((i) => i % num == 0)
        .map((i) => arr.slice(i, i + num));

exports.chunked = chunked;

const transpose = (arr) =>
    range(arr[0].length).map((col) =>
        range(arr.length).map((row) => arr[row][col])
    );

exports.transpose = transpose;

const sliding_window = (arr, size) => {
    if (size > arr.length)
        throw new Error(`${size} is larger than array size.`);

    if (size == arr.length) {
        return arr;
    }

    return range(arr.length - size + 1).map((i) => arr.slice(i, i + size));
};

exports.sliding_window = sliding_window;
