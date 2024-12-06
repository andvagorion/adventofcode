from aoc import aoc

SAMPLE = 'sample/14.txt'
DATA = 'input/14.txt'

def get_rules(lines):
    rules = dict()
    for line in lines[2:]:
        parts = line.split(' -> ')
        rules[parts[0]] = parts[1]
    return rules

def step(formula, rules, counts):
    next_formula = dict()
    for key in formula:
        middle = rules[key]
        times = formula[key]
        first = key[0] + middle
        second = middle + key[1]
        next_formula[first] = next_formula.get(first, 0) + times
        next_formula[second] = next_formula.get(second, 0) + times
        
        counts[middle] = counts.get(middle, 0) + times

    assert 2 * sum(formula.values()) == sum(next_formula.values())
    assert sum(counts.values()) == sum(next_formula.values()) + 1
    return next_formula, counts

def calc(filename, times):
    lines = aoc.read_lines(filename)

    initial = [c for c in lines[0]]
    formula = dict()
    for i in range(len(initial) - 1):
        key = initial[i] + initial[i + 1]
        formula[key] = formula.get(key, 0) + 1
    
    counts = {key: initial.count(key) for key in set(initial)}

    rules = get_rules(lines)
    
    for _ in range(times):
        formula, counts = step(formula, rules, counts)
    
    mn = min(counts.values())
    mx = max(counts.values())

    return mx - mn

print(calc(DATA, 10))    
print(calc(DATA, 40))
