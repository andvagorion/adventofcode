import advent
from util import toChars

with open('input/09') as f:
  lines = [toChars(line) for line in f.read().splitlines()]

totalScore = 0
garbageCount = 0

def parse(line):
  global totalScore
  global garbageCount
  
  depth = 0
  garbage = False
  ignore = False
  for c in line:
    if ignore:
      ignore = False
      continue;
    
    if garbage:
      if c == '!':
        ignore = True
      elif c == '>':
        garbage = False
      else:
        garbageCount += 1
      continue
    
    if c == '<':
      garbage = True
      continue
    
    if c == '}':
      totalScore += depth
      depth -= 1
      continue
    
    if c == '{':
      depth += 1
      continue
    
    if c == ',':
      continue
    
    raise Exception('ouch. encountered ' + c)

for line in lines:
  parse(line)

advent.part(1, totalScore)
advent.part(2, garbageCount)