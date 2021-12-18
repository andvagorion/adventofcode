from aoc import aoc
from aoc.point import point
from aoc.grid import grid
from itertools import count, permutations

lines = aoc.read_lines('data/14.txt')

rules = {}
for line in lines[2:]:
    parts = line.split(' -> ')
    rules[parts[0]] = parts[1]

def step(formula):
    next_formula = []
    for i in range(len(formula) - 1):
        first = formula[i] 
        second = formula[i+1]
        produces = rules[first + second]
        if i == 0: next_formula.append(first)
        next_formula.append(produces)
        next_formula.append(second)
    return next_formula

def steps(formula, n):
    for i in range(n):
        formula = step(formula)
    return formula

def counts(formula) -> dict:
    el_count = {}

    elements = set(formula)
    for element in elements:
        el_count[element] = formula.count(element)
    
    return el_count

def part1():
    formula = [c for c in lines[0]]
    formula = steps(formula, 10)
    total = sorted([val for val in counts(formula).values()])
    print(total[len(total) - 1] - total[0])

part1()