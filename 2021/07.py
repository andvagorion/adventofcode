from aoc import aoc
from functools import reduce

lines = aoc.read_lines('input/07.txt')

positions = [int(i) for i in lines[0].split(',')]
max = reduce(lambda a, b: a if a > b else b, positions)

def diff(a, b):
    if a > b: return a - b
    else: return b - a

pos = -1
min = -1

for i in range(max):
    sum = reduce(lambda a, b: a + b, map(lambda a: diff(i, a), positions))
    if min == -1 or sum < min:
        pos = i
        min = sum

print(min)

def fuel(a, b):
    d = diff(a, b)
    f = 0
    for i in range(d + 1):
        f += i
    return f

pos = -1
min = -1

for i in range(max):
    sum = reduce(lambda a, b: a + b, map(lambda a: fuel(i, a), positions))
    if min == -1 or sum < min:
        pos = i
        min = sum

print(pos)
print(min)