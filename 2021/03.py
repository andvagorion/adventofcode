from aoc import aoc
from functools import reduce

add_as_int = lambda a, b: int(a) + int(b)

lines = aoc.read_lines('input/03.txt')

def part1():
    line_len = len(lines[0])
    min = len(lines) / 2
    most_common = [0 for i in range(line_len)]

    total = [0 for i in range(line_len)]

    for i in range(line_len):
        vals = [line[i] for line in lines]
        total[i] = reduce(add_as_int, vals)

    most_common = [1 if i > min else 0 for i in total]
    gamma = int(''.join([str(int) for int in most_common]), 2)

    least_common = [1 if i == 0 else 0 for i in most_common]
    epsilon = int(''.join([str(int) for int in least_common]), 2)

    return gamma * epsilon

def o2():
    vals = [line for line in lines]

    i = 0
    while len(vals) > 1:
        count = reduce(add_as_int, [line[i] for line in vals])
        x = '1' if count >= len(vals) / 2 else '0'
        vals = [line for line in vals if line[i] == x]
        i += 1

    return int(vals[0], 2)

def co2():
    vals = [line for line in lines]

    i = 0
    while len(vals) > 1:
        count = reduce(add_as_int, [line[i] for line in vals])
        x = '1' if count < len(vals) / 2 else '0'
        vals = [line for line in vals if line[i] == x]
        i += 1

    return int(vals[0], 2)

def part2():
    return o2() * co2()

print(part1())
print(part2())
