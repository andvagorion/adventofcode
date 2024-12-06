import advent
import util
from functools import reduce
from operator import xor

with open('input/10') as f:
  data = f.readline()[0:-1]

def switch(numbers, pos, length):
  start = pos
  end = (start + length - 1) % len(numbers)
  
  while start != end:
    temp = numbers[start]
    numbers[start] = numbers[end]
    numbers[end] = temp
    
    start = (start + 1) % len(numbers)
    if (start == end): break
    end = (end - 1) if end > 0 else len(numbers) - 1
  
  return numbers

def knot(lengths, cycles):
  numbers = [i for i in range(256)]
  currentPosition = 0
  skipSize = 0

  for _ in range(cycles):
    for length in lengths:
    
      if (length > 1):
        numbers = switch(numbers, currentPosition, length)
      
      currentPosition = (currentPosition + length + skipSize) % len(numbers)
      skipSize += 1
  
  return numbers;

def hash(ints):
  hexData = [hex(reduce(xor, part))[2:] for part in util.splitInLength(ints, 16)]
  return "".join(hexData)

def part1():
  lengths = [int(num) for num in data.split(',')]
  numbers = knot(lengths, 1)
  return numbers[0] * numbers[1]

def part2():
  byteData = [int(ord(num)) for num in data]
  byteData.extend([17, 31, 73, 47, 23])
  numbers = knot(byteData, 64)
  return hash(numbers)

advent.part(1, part1())
advent.part(2, part2())