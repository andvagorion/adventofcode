const { contains } = require("./util/array.js");
const { load } = require("./util/load.js");
const { range } = require("./util/range.js");

const data = load("01");

let idx = 0;
const steps = [0, 0, 0, 0];

data.split(", ").forEach((t) => {
    const dir = t.charAt(0);
    if (dir == "L") idx = idx > 0 ? idx - 1 : (idx = 3);
    if (dir == "R") idx = idx < 3 ? idx + 1 : (idx = 0);
    steps[idx] += parseInt(t.substring(1));
});

const total_distance = (arr) =>
    Math.abs(arr[0] - arr[2]) + Math.abs(arr[1] - arr[3]);

const part1 = total_distance(steps);
console.log(part1);

///

const rot_r = v => [v[1], -v[0]]
const rot_l = v => [-v[1], v[0]]
const add = (a,b) => [a[0] + b[0], a[1] + b[1]]

const visited = []
let pos = [0, 0]
let vec = [0, 1]

data.split(', ').some(t => {
  vec = t.charAt(0) == 'R' ? rot_r(vec) : rot_l(vec)
  const len = parseInt(t.substring(1))
  
  return range(len).some(i => {
    const next = add(pos, vec)
    if (contains(visited, next)) {
      console.log(Math.abs(next[0]) + Math.abs(next[1]))
      return true
    }
    pos = next
    visited.push(next)
  })
})
