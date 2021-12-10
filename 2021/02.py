from aoc import aoc
from functools import reduce

pos = (0, 0)

lines = aoc.read_lines('02.txt')

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

print(reduce(lambda x, y: x * y, pos))

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

print(reduce(lambda x, y: x * y, pos))