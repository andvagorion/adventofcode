from aoc import aoc
from functools import reduce

lines = aoc.read_lines('input/10.txt')
add = lambda a, b: a + b

opening = ['(', '[', '{', '<']
closing = [')', ']', '}', '>']

points1 = { ')': 3, ']': 57, '}': 1197, '>': 25137 }

def matches(o, c):
    return opening.index(o) == closing.index(c)

def calc_points(line):
    opened = []
    unprocessed = [c for c in line]
    while unprocessed:
        current = unprocessed.pop(0)
        if current in opening:
            opened.append(current)
        else: 
            previous = opened.pop()
            if not matches(previous, current): return points1[current]
    return 0

points = [calc_points(line) for line in lines]
print(reduce(add, points))

points2 = { ')': 1, ']': 2, '}': 3, '>': 4 }

def calc_points2(line):
    opened = []
    unprocessed = [c for c in line]
    while unprocessed:
        current = unprocessed.pop(0)
        if current in opening:
            opened.append(current)
        else: 
            previous = opened.pop()
            if not matches(previous, current): return 0
    
    score = 0
    while opened:
        score *= 5
        o = opened.pop()
        c = closing[opening.index(o)]
        score += points2[c]
    
    return score

sum = [calc_points2(line) for line in lines]
sum = [i for i in sum if i > 0]
sum.sort()

print(sum[(len(sum) - 1) >> 1])
