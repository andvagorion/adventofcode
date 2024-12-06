const { range } = require("./util/arrays");
const { read_lines } = require("./util/reader");

const lines = read_lines("input/day5");

const pattern_rule = /(\d+)\|(\d+)/;
const pattern_update = /(\d+)(,\d+)+/;

const rules = [];
const updates = [];

for (const line of lines) {
  if (pattern_rule.test(line)) {
    let matches = line.match(pattern_rule);
    rules.push([matches[1], matches[2]].map((i) => parseInt(i)));
  } else if (pattern_update.test(line)) {
    let matches = line.match(/\d+/g);
    updates.push([...matches].map((i) => parseInt(i)));
  }
}

const valid_order = (update) => {
  for (let idx = 0; idx < update.length; idx++) {
    const page = update[idx];
    const rules_to_apply = rules.filter((rule) => rule[0] == page);

    const valid = rules_to_apply.every((rule) => {
      const other_idx = update.indexOf(rule[1]);
      return other_idx == -1 || other_idx > idx;
    });

    if (!valid) return false;
  }

  return true;
};

const middle = (arr) => {
  const idx = (arr.length - 1) / 2;
  return arr[idx];
};

const sort = (pages) => {
  const sorted = [];
  let remaining = [...pages];

  const after_page = new Map(
    pages.map((page) => {
      return [
        page,
        rules.filter((rule) => rule[0] == page).map((rule) => rule[1]),
      ];
    })
  );

  while (remaining.length > 0) {
    // find page with no "after" pages present in update
    const next = remaining.find((page) => {
      return after_page.get(page).every((other) => !remaining.includes(other));
    });

    // remove from remaining
    remaining = remaining.filter((i) => i != next);

    sorted.push(next);
  }

  return sorted;
};

let sum1 = 0;
let sum2 = 0;

for (const update of updates) {
  if (valid_order(update)) {
    sum1 += middle(update);
  } else {
    const sorted = sort(update);
    sum2 += middle(sorted);
  }
}

console.log(sum1);
console.log(sum2);
