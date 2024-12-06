import re
from aoc import aoc
from aoc.grid import grid
from aoc.point import point

SAMPLE = 'sample/25.txt'
DATA = 'input/25.txt'

def move_east(cucumbers:grid) -> grid:
    step:grid = cucumbers.clone()
    eastie_bois = cucumbers.find_all(lambda cell: cell == '>')
    for boi in eastie_bois:
        x = (boi.x + 1) % cucumbers.width
        if cucumbers.get(point(x, boi.y)) == '.':
            step.set(point(boi.x, boi.y), '.')
            step.set(point(x, boi.y), '>')
    return step

def move_south(cucumbers:grid) -> grid:
    step:grid = cucumbers.clone()
    eastie_bois = cucumbers.find_all(lambda cell: cell == 'v')
    for boi in eastie_bois:
        y = (boi.y + 1) % cucumbers.height
        if cucumbers.get(point(boi.x, y)) == '.':
            step.set(point(boi.x, boi.y), '.')
            step.set(point(boi.x, y), 'v')
    return step

lines = aoc.read_lines(DATA)

cucumbers = grid.with_values(lines)

i = 0
prev = None
while prev != cucumbers:
    prev = cucumbers.clone()
    cucumbers = move_east(cucumbers)
    cucumbers = move_south(cucumbers)
    i += 1
    
print(i)