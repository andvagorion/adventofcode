import sys
from aoc import aoc
from aoc.grid import grid
from aoc.point import point

lines = aoc.read_lines('data/15.txt')

def debug_print_path(g, prev_nodes):
    path = []
    prev_node = end
    while prev_node:
        path.append(prev_node)
        prev_node = prev_nodes[prev_node]

    print('PATH', list(reversed(path)))

    for p in path: g.set(p, 0)
    g.print_condensed()

def part1(g:grid, start:point, end:point) -> int:
    all_points = g.find_all()
    distances = {node: sys.maxsize for node in all_points}
    distances[start] = 0

    prev = {node : None for node in all_points}

    unvisited = [start]
    visited = []

    while unvisited:
        min_p = min(unvisited, key=lambda p: distances[p])
        current = unvisited.pop(unvisited.index(min_p))
        if current in visited: continue

        visited.append(current)

        assert len(visited) <= len(all_points)
        #print(len(visited), '/', len(all_points))
        
        neighbors = g.neighbors(current)
        for neighbor in neighbors:
            risk_value = g.get(neighbor)
            tentative = distances[current] + risk_value
            if tentative < distances[neighbor]:
                distances[neighbor] = tentative
                prev[neighbor] = current
            
            if neighbor not in visited:
                unvisited.append(neighbor)
        
    return distances[end]

g = grid.with_values(lines, lambda val: int(val))
start = point(0,0)
end = point(g.width - 1, g.height - 1)

minimum_risk = part1(g, start, end)
print(minimum_risk)