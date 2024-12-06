from aoc import aoc
from aoc.point import point

lines = aoc.read_lines('input/09.txt')

grid = [[int(c) for c in line] for line in lines]

def get_neighbors(grid, x, y):
    neighbors = []
    if y > 0: neighbors.append(point(x, y-1))
    if y < len(grid) - 1: neighbors.append(point(x, y+1))
    if x > 0: neighbors.append(point(x-1, y))
    if x < len(grid[0]) - 1: neighbors.append(point(x+1, y))
    return neighbors

def find_basin(grid, x, y):
    visited = [point(x, y)]
    basin = [point(x, y)]
    tovisit = get_neighbors(grid, x, y)
    
    while tovisit:
        p = tovisit.pop()
        if p in visited: continue

        visited.append(p)

        if grid[p.y][p.x] == 9: continue
        
        basin.append(p)
        neighbors = get_neighbors(grid, p.x, p.y)
        tovisit.extend([n for n in neighbors if n not in visited])
        
    return basin

def low_point(grid, x, y):
    neighbors = get_neighbors(grid, x, y)
    val = grid[y][x]
    return not any(grid[p.y][p.x] <= val for p in neighbors)

risk = 0
for y in range(len(grid)):
    row = grid[y]
    for x in range(len(row)):
        if low_point(grid, x, y):
            risk += 1 + grid[y][x]

print(risk)

basin_sizes = []
for y in range(len(grid)):
    row = grid[y]
    for x in range(len(row)):
        if low_point(grid, x, y):
            basin = find_basin(grid, x, y)
            basin_sizes.append(len(basin))

basin_sizes.sort()
sum = basin_sizes[-1] * basin_sizes[-2] * basin_sizes[-3]
print(sum)