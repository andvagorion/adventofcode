const {
  as_lines,
  range,
  chunked_by_empty_lines,
  parse_int,
} = require("./util/util");

const to_workflow = (line) => {
  const name = line.substring(0, line.indexOf("{"));
  const rules_str = line.substring(line.indexOf("{") + 1, line.length - 1);
  const rules = rules_str.split(",").map((rule) => {
    if (rule.includes(":")) {
      const [condition, next] = rule.split(":");
      const [field, val] = condition.split(/<|>/g);

      if (condition.includes("<")) {
        return (obj) => (obj[field] < parse_int(val) ? next : null);
      } else {
        return (obj) => (obj[field] > parse_int(val) ? next : null);
      }
    } else {
      return () => rule;
    }
  });
  return [name, rules];
};

const to_workflows = (lines) => {
  const map = new Map();
  lines.forEach((line) => {
    const [name, rules] = to_workflow(line);
    map.set(name, rules);
  });
  return map;
};

const to_objects = (lines) => {
  return lines.map((line) => {
    const [x, m, a, s] = line
      .substring(1, line.length - 1)
      .split(",")
      .map((s) => s.split("=")[1])
      .map(parse_int);
    return { x: x, m: m, a: a, s: s };
  });
};

const input = as_lines("19", { keep_empty_lines: true });

const chunks = chunked_by_empty_lines(input);

const workflows = to_workflows(chunks[0]);
const objects = to_objects(chunks[1]);

let accepted_sum = 0;

objects.forEach((obj) => {
  let out = "in";
  while (out != "A" && out != "R") {
    const rules = workflows.get(out);
    for (const rule of rules) {
      const next = rule(obj);

      if (next == null) continue;

      out = next;
      break;
    }
  }

  if (out == "A") accepted_sum += Object.values(obj).reduce((a, b) => a + b, 0);
});

console.log(accepted_sum);
