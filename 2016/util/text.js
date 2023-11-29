const { range } = require("./range");

const to_numbers = (line) => line.match(/\d+/g).map((s) => parseInt(s));

exports.to_numbers = to_numbers;

const alphabet = "abcdefghijklmnopqrstuvwxyz".split("");

exports.alphabet = alphabet;

const sliding_window_str = (str, size) => {
    if (size > str.length)
        throw new Error(`${size} is larger than string size.`);

    if (size == str.length) {
        return str;
    }

    return range(str.length - size + 1).map(i => str.substring(i, i + size));
};

exports.sliding_window_str = sliding_window_str;


const is_palindrome = (word) => word[0] != word[1] && word == reverse(word);

exports.is_palindrome = is_palindrome;

const contains_palindrome = (word, len) => {
    if (len > word.length) return false;

    const palindrome = sliding_window_str(word, len).some(is_palindrome);

    return palindrome;
};

exports.contains_palindrome = contains_palindrome;

const reverse = (str) => str.split("").reverse().join("");

exports.reverse = reverse;
