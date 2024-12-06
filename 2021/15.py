from aoc import aoc
from queue import PriorityQueue

X = 0
Y = 1

def get_neighbors(x, y):
    return [n for n in [
        (x + 1, y),
        (x, y + 1),
        (x - 1, y),
        (x, y - 1)
    ] if n[X] >= 0 and n[X] < COLS and n[Y] >= 0 and n[Y] < ROWS]

def solve(cave:list[int], start:tuple, end:tuple) -> int:
    reasonably_high = int(1e6)
    distances = {(x,y): reasonably_high for x in range(COLS) for y in range(ROWS)}
    distances[start] = 0

    prev = {(x,y) : None for x in range(COLS) for y in range(ROWS)}

    unvisited = PriorityQueue()
    unvisited.put((0, start))
    visited = set()

    while not unvisited.empty():
        current = unvisited.get()[1]
        
        if current in visited: continue
        visited.add(current)

        # if len(visited) % 100 == 0: print(len(visited), '/', ROWS * COLS)

        neighbors = get_neighbors(current[X], current[Y])
        for neighbor in neighbors:
            risk_value = cave[neighbor[Y]][neighbor[X]]
            tentative = distances[current] + risk_value
            if tentative < distances[neighbor]:
                distances[neighbor] = tentative
                prev[neighbor] = current
            
            if neighbor not in visited:
                unvisited.put((distances[neighbor], neighbor))
    
    minimum_risk = distances[end]
    print(minimum_risk)

PART1 = 'input/15.txt'
PART2 = 'input/15-2.txt'

lines = aoc.read_lines(PART2)

ROWS = len(lines)
COLS = len(lines[0])

start = (0,0)
end = (ROWS - 1, COLS - 1)

cave = [[int(i) for i in line] for line in lines]

solve(cave, start, end)
