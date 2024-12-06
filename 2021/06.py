from aoc import aoc
from functools import reduce

lines = aoc.read_lines('input/06.txt')

fish = [0 for i in range(9)]
for i in lines[0].split(','): fish[int(i)] += 1

def update(fish):
    next = [fish[i + 1] for i in range(len(fish) - 1)]
    next[6] += fish[0]
    next.append(fish[0])
    return next

for i in range(80): fish = update(fish)
sum = reduce(lambda a, b: a + b, fish)
print(sum)

fish = [0 for i in range(9)]
for i in lines[0].split(','): fish[int(i)] += 1

for i in range(256): fish = update(fish)
sum = reduce(lambda a, b: a + b, fish)
print(sum)
