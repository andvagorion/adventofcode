from aoc import aoc

lines = aoc.read_lines('001.txt')

def one():
  count = 0
  for i in range(len(lines) - 1):
    win = lines[i: i + 2]
    n1 = int(win[0])
    n2 = int(win[1])
    if n2 > n1: count += 1
  return count

def two():
  prev = -1
  count = 0
  for i in range(len(lines) - 2):
    win = lines[i: i + 3]
    n1 = int(win[0])
    n2 = int(win[1])
    n3 = int(win[2])
    sum = n1 + n2 + n3
    if prev > 0 and sum > prev: count += 1
    prev = sum
  return count

print(one())
print(two())

