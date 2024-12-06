import advent

with open('input/02') as f:
  content = f.readlines()

def maxMinusMin(line):
  numbers = [int(n) for n in line.split()]
  return max(numbers) - min(numbers)

def divideEven(line):
  numbers = [int(n) for n in line.split()]
  numbers = [num for num in numbers if len([other for other in numbers if other % num == 0 or num % other == 0]) == 2]
  return int(max(numbers) / min(numbers))

advent.part(1, sum(map(maxMinusMin, content)))
advent.part(2, sum(map(divideEven, content)))