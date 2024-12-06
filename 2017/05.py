import advent

with open('input/05') as f:
  content = [int(num) for num in f.readlines()]

def run():
  code = content.copy()
  steps = 0
  idx = 0
  while idx >= 0 and idx < len(code):
    offset = code[idx]
    code[idx] = code[idx] + 1
    idx = idx + offset
    steps += 1
  return steps

def run2():
  code = content.copy()
  steps = 0
  idx = 0
  while idx >= 0 and idx < len(code):
    offset = code[idx]
    if offset >= 3:
      code[idx] = code[idx] - 1
    else:
      code[idx] = code[idx] + 1
    idx = idx + offset
    steps += 1
  return steps

advent.part(1, run())
advent.part(2, run2())
