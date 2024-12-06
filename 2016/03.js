const { chunked, transpose } = require("./util/array");
const { as_lines } = require("./util/load");
const { to_numbers } = require("./util/text");

const lines = as_lines("03");

const is_triangle = (nums) =>
    nums[0] + nums[1] > nums[2] &&
    nums[0] + nums[2] > nums[1] &&
    nums[1] + nums[2] > nums[0];

const number_lines = lines.map((line) => to_numbers(line));

const part1 = number_lines.filter((nums) => is_triangle(nums)).length;

console.log(part1);

const part2 = chunked(number_lines, 3)
    .map((lines) => transpose(lines))
    .flat()
    .filter((nums) => is_triangle(nums)).length;

console.log(part2);
