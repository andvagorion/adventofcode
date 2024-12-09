const { range } = require("./util/arrays");
const { timed } = require("./util/timed");
const { read_lines } = require("./util/reader");

const lines = read_lines("input/day9");

const debugPrint = (disk) =>
  console.log(
    disk
      .map((el) => {
        return range(el[NUM])
          .map((_) => {
            if (el[ID] == -1) return ".";
            else return el[ID];
          })
          .join("");
      })
      .join("")
  );

const read = (disk) => {
  const numbers = disk.split("");

  let id = 0;
  let file = true;
  let out = [];

  while (numbers.length > 0) {
    const next = parseInt(numbers.shift());

    if (file) {
      range(next).forEach((_) => out.push(id));
      id += 1;
    } else {
      range(next).forEach((_) => out.push(-1));
    }

    file = !file;
  }

  return out;
};


const defrag = (disk) => {
  let i = 0;

  while (i++ < 500_000_000) {
    const next = disk.pop();
    const free = disk.indexOf(-1);

    if (free < 0) {
      if (next != ".") disk.push(next);
      return disk.map((el) => parseInt(el));
    }

    disk[free] = next;
  }

  return disk.map((el) => parseInt(el));
};

const read2 = (str) => {
  const disk = str.split("");

  let id = -1;
  let file = false;

  return disk.map((el) => {
    file = !file;
    if (file) {
      id += 1;
      return [id, parseInt(el)];
    } else {
      return [-1, parseInt(el)];
    }
  });
};

const ID = 0;
const NUM = 1;

const findByID = (disk, id) => {
  for (let idx = 0; idx < disk.length; idx++) {
    if (disk[idx][ID] == id) return idx;
  }
  return -1;
};

const findEmpty = (disk, size) => {
  for (let idx = 0; idx < disk.length; idx++) {
    if (disk[idx][ID] == -1 && disk[idx][NUM] >= size) return idx;
  }
  return -1;
};

const defrag2 = (disk) => {
  let largestId = disk.reduce((a, b) => (a > b ? a : b), -1)[ID];

  for (let id = largestId; id >= 0; id--) {
    const blockIdx = findByID(disk, id);
    const block = disk[blockIdx];

    const emptyIdx = findEmpty(disk, block[NUM]);

    if (emptyIdx == -1 || emptyIdx >= blockIdx) {
      continue;
    }

    const remainder = disk[emptyIdx][NUM] - block[NUM];

    disk[emptyIdx] = block;
    disk[blockIdx] = [-1, block[NUM]];

    if (remainder > 0) {
      disk.splice(emptyIdx + 1, 0, [-1, remainder]);
    }
  }

  return disk;
};

const checksum = (disk) =>
  disk.map((el, idx) => (el == -1 ? 0 : el * idx)).reduce((a, b) => a + b, 0);

const checksum2 = (disk) => {
  const converted = disk
    .map((el) => range(el[NUM]).map((_) => el[ID]))
    .flat()
    .map((el) => (el < 0 ? 0 : el));
  return checksum(converted);
};

const part1 = () => {
  let disk = read(lines[0]);
  disk = defrag(disk);
  console.log(checksum(disk));
};

const part2 = () => {
  disk = read2(lines[0]);
  disk = defrag2(disk);
  console.log(checksum2(disk));
};

part1();
part2();
