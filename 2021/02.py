from aoc import aoc
from functools import reduce

lines = aoc.read_lines('input/02.txt')

def part1():
    pos = (0, 0)

    for line in lines:
        move = (0, 0)
        parts = line.split(' ')
        dir = parts[0]
        n = int(parts[1])
        if dir == 'forward':
            move = (n, 0)
        elif dir == 'down':
            move = (0, n)
        elif dir == 'up':
            move = (0, -n)
        pos = aoc.tuple_add(pos, move)
    
    return reduce(lambda x, y: x * y, pos)

def part2():
    pos = (0, 0)
    aim = 0

    for line in lines:
        parts = line.split(' ')
        dir = parts[0]
        n = int(parts[1])
        if dir == 'forward':
            pos = aoc.tuple_add(pos, (n, 0))
            pos = aoc.tuple_add(pos, (0, aim * n))
        elif dir == 'down':
            aim += n
        elif dir == 'up':
            aim -= n
    
    return reduce(lambda x, y: x * y, pos)

print(part1())
print(part2())
