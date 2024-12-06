from aoc import aoc

lines = aoc.read_lines('input/05.txt')

def as_tuple(s):
    t = s.split(',')
    return (int(t[0]), int(t[1]))

def vert_line(p1, p2):
    x = p1[0]
    y0 = p1[1] if p1[1] < p2[1] else p2[1]
    y1 = p1[1] if p1[1] >= p2[1] else p2[1]
    return [(x, y) for y in range(y0, y1 + 1)]

def hor_line(p1, p2):
    y = p1[1]
    x0 = p1[0] if p1[0] < p2[0] else p2[0]
    x1 = p1[0] if p1[0] >= p2[0] else p2[0]
    return [(x, y) for x in range(x0, x1 + 1)]

def diag_line(p1, p2):
    step_y = 1 if p2[1] > p1[1] else - 1
    step_x = 1 if p2[0] > p1[0] else - 1

    points = []

    start_x = p1[0]
    start_y = p1[1]

    end_x = p2[0]
    end_y = p2[1]

    while start_x != end_x and start_y != end_y:
        points.append((start_x, start_y))
        start_x += step_x
        start_y += step_y
    points.append((start_x, start_y))

    return points

def create_grid(w, h):
    grid = []
    for y in range(h + 1):
        grid.append([0 for x in range(w + 1)])
    return grid

def get_max(positions):
    max_x = 0
    max_y = 0
    for pos in positions:
        if pos[0] > max_x: max_x = pos[0]
        if pos[1] > max_y: max_y = pos[1]
    return (max_x, max_y)

def fill_grid(grid, positions):
    for pos in positions:
        x = pos[0]
        y = pos[1]
        grid[y][x] += 1
    return grid

def count_min(grid, num):
    count = 0
    for row in grid:
       for x in row:
            if x >= num: count += 1
    return count

positions = []

for line in lines:
    from_to = line.split()
    p1 = as_tuple(from_to[0])
    p2 = as_tuple(from_to[2])

    if p1[0] == p2[0]:
        positions.extend(vert_line(p1, p2))
    if p1[1] == p2[1]:
        positions.extend(hor_line(p1, p2))

max = get_max(positions)
grid = create_grid(max[0], max[1])
grid = fill_grid(grid, positions)

# for row in grid: print(row)

overlap = count_min(grid, 2)

print(overlap)

for line in lines:
    from_to = line.split()
    p1 = as_tuple(from_to[0])
    p2 = as_tuple(from_to[2])

    if p1[0] != p2[0] and p1[1] != p2[1]:
        positions.extend(diag_line(p1, p2))

max = get_max(positions)
grid = create_grid(max[0], max[1])
grid = fill_grid(grid, positions)

# for row in grid: print(row)

overlap = count_min(grid, 2)

print(overlap)