const { find, N, turn_right, offgrid, find_first } = require("./util/grid");
const { range } = require("./util/arrays");
const { read_array } = require("./util/reader");

const grid = read_array("input/day6");

const wall = (grid, pos) => {
    return grid[pos[1]][pos[0]] == "#";
};

const run = (original_grid, obstacle_pos, start) => {
    let grid = original_grid.map((row) => [...row]);
    grid[obstacle_pos[1]][obstacle_pos[0]] = "#";

    const positions = [];
    positions.push([[...start], N]);

    let curr = start;
    let done = false;
    let dir = N;

    let i = 0;

    while (!done) {
        const next = [curr[0] + dir[0], curr[1] + dir[1]];

        if (offgrid(grid, next)) {
            return false;
        }

        if (wall(grid, next)) {
            dir = turn_right(dir);
            continue;
        }

        if (
            positions.some((pos) => {
                const prev_pos = pos[0];
                const prev_dir = pos[1];
                return (
                    prev_dir == dir &&
                    prev_pos[0] == next[0] &&
                    prev_pos[1] == next[1]
                );
            })
        ) {
            console.log("FOUND: ", i, next, dir);
            return true;
        }

        positions.push([next, dir]);

        curr = next;

        if (i++ > 10_000) done = true;
    }
};

let guard = find_first(grid, "^");
grid[guard[1]][guard[0]] = ".";

const possible_obstacles = [];

// just brute-force it...
for (let y of range(grid.length)) {
    for (let x of range(grid[y].length)) {
        if (wall(grid, [x, y]) || (guard[0] == x && guard[1] == y)) continue;

        const obstacle_pos = [x, y];

        const yeah = run(grid, obstacle_pos, guard);
        if (yeah) {
            possible_obstacles.push(obstacle_pos);
        }
    }
}

console.log(possible_obstacles.length);
