from aoc import aoc
from aoc.point import point
from aoc.grid import grid
import re

pattern = '^(\d+),(\d+)$'
pattern_fold = '^fold along (\w)=(\d+)'

lines = aoc.read_lines('input/13.txt')

dots = []
folds = []

for line in lines:
    digits = re.match(pattern, line)
    if digits:
        x, y = digits.groups()
        dots.append(point(int(x),int(y)))
        continue
    
    fold_instr = re.match(pattern_fold, line)
    if fold_instr:
        axis, num = fold_instr.groups()
        folds.append((axis, int(num)))

def print_dots(dots):
    x_max = max([p.x for p in dots])
    y_max = max([p.y for p in dots])
    g = grid(x_max + 1, y_max + 1, '.')
    for dot in dots:
        g.set(dot, '#')
    g.print_condensed()

def fold_hor(dots, y):
    copy = [p for p in dots if p.y < y]
    for p in [p for p in dots if p.y > y]:
        copy.append(point(p.x, y - (p.y - y)))
    return copy

def fold_vert(dots, x):
    copy = [p for p in dots if p.x < x]
    for p in [p for p in dots if p.x > x]:
        copy.append(point(x - (p.x - x), p.y))
    return copy

def fold(dots: list[point], fold_instr: tuple):
    if fold_instr[0] == 'x':
        return fold_vert(dots, fold_instr[1])
    else:
        return fold_hor(dots, fold_instr[1])

dots = fold(dots, folds[0])
print(len(set(dots)))

for i in range(1, len(folds)):
    dots = fold(dots, folds[i])

print_dots(dots)