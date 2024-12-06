from aoc import aoc
from functools import reduce

lines = aoc.read_lines('input/08.txt')

def unique_patterns(line):
    first = line.split(' | ')[0]
    return [''.join(sorted(p)) for p in first.split()]

def find_mapping(line):
    patterns = unique_patterns(line)
    
    one = next(p for p in patterns if len(p) == 2)
    seven = next(p for p in patterns if len(p) == 3)
    four = next(p for p in patterns if len(p) == 4)
    eight = next(p for p in patterns if len(p) == 7)
    
    nine = next(p for p in patterns if len(p) == 6 and set(four).issubset(set(p)))
    six = next(p for p in patterns if len(p) == 6 and not set(one).issubset(set(p)))
    zero = next(p for p in patterns if len(p) == 6 and p != nine and p != six)

    two = next(p for p in patterns if len(p) == 5 and not set(p).issubset(set(nine)))
    five = next(p for p in patterns if len(p) == 5 and set(p).issubset(set(six)))
    three = next(p for p in patterns if len(p) == 5 and p != two and p != five)

    return [zero, one, two, three, four, five, six, seven, eight, nine]

def coded_numbers(line):
    second = line.split(' | ')[1]
    return [''.join(sorted(p)) for p in second.split()]

def get_number(line, mapping):
    numbers = coded_numbers(line)
    return int(''.join([str(mapping.index(num)) for num in numbers]))

def unique_coded_numbers(line):
    numbers = coded_numbers(line) 
    return len([num for num in numbers if len(num) in [2, 3, 4, 7]])

sum = reduce(lambda a, b: a + b, [unique_coded_numbers(line) for line in lines])
print(sum)

sum = reduce(lambda a, b: a + b, [get_number(line, find_mapping(line)) for line in lines])
print(sum)
