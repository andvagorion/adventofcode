from aoc import aoc
from aoc.point import point
from aoc.grid import grid
from itertools import permutations

lines = aoc.read_lines('input/12.txt')

caves = set()
for line in lines:
    c0 = line.split('-')[0]
    caves.add(c0)
    c1 = line.split('-')[1]
    caves.add(c1)
caves = list(caves)

connections = {}
for cave in caves: connections[cave] = []

for line in lines:
    c0 = line.split('-')[0]
    c1 = line.split('-')[1]
    if c1 != 'start':
        connections[c0].append(c1)
    if c1 != 'end' and c0 != 'start':
        connections[c1].append(c0)

def visit1(cave, node_count, total_count):
    pos = caves.index(cave)

    if cave.islower() and node_count[pos] == 1:
        return

    _node_count = [i for i in node_count]
    _node_count[pos] += 1
    
    if cave == 'end':
        total_count[0] += 1
        return
    
    for other in connections[cave]:
        visit1(other, _node_count, total_count)

def is_valid(node_count):
    occurences = [node_count[i] for i in range(len(caves)) if caves[i].islower()]
    return occurences.count(2) <= 1

def visit2(cave, node_count, total_count):
    pos = caves.index(cave)

    if cave.islower() and node_count[pos] == 2:
        return

    _node_count = [i for i in node_count]
    _node_count[pos] += 1
    
    if cave == 'end':
        if is_valid(_node_count):
            total_count[0] += 1
        return
    
    for other in connections[cave]:
        visit2(other, _node_count, total_count)

def part1():
    node_count = [0 for _ in caves]
    total_count = [0]
    visit1('start', node_count, total_count)
    return total_count[0]

def part2():
    node_count = [0 for _ in caves]
    total_count = [0]
    visit2('start', node_count, total_count)
    return total_count[0]

print(part1())

# beware, this is very inefficient and takes forever...
print(part2())