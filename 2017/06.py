import advent

with open('input/06') as f:
  content = [int(num) for num in f.read().split()]

def maxIndex(memory):
  maxVal = 0
  index = 0
  for i, val in enumerate(memory, start=0):
    if (val > maxVal):
      index = i
      maxVal = val
  return index

def redistribute(memory):
  index = maxIndex(memory)
  toRedistribute = memory[index]
  
  copy = memory.copy()
  copy[index] = 0
  
  while (toRedistribute > 0):
    index = (index + 1) % len(copy)
    copy[index] += 1
    toRedistribute -= 1
  return copy

runCount = 0
firstOcc = 0

def loop(config):
  global runCount
  global firstOcc
  
  memory = config.copy()
  seen = [memory]
  
  while (True):
    runCount += 1
    nextMem = redistribute(memory)
    
    if nextMem in seen:
      firstOcc = seen.index(nextMem)
      return
    
    seen.append(nextMem)
    memory = nextMem

loop(content)
advent.part(1, runCount)
advent.part(2, runCount - firstOcc)