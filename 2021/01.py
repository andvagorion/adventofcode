from aoc import aoc

lines = [int(i) for i in aoc.read_lines('input/01.txt')]

def one():
  count = 0
  for i in range(len(lines) - 1):
    win = lines[i: i + 2]
    if win[1] > win[0]: count += 1
  return count

def two():
  prev = -1
  count = 0
  for i in range(len(lines) - 2):
    win = lines[i: i + 3]
    sum = win[0] + win[1] + win[2]
    if prev > 0 and sum > prev: count += 1
    prev = sum
  return count

print(one())
print(two())

