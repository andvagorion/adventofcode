import advent

with open('input/08') as f:
  code = [line.split() for line in f.readlines()]

def inc(memory, register, value):
  memory[register] += value

def dec(memory, register, value):
  memory[register] -= value

def initRegisters(memory, *registers):
  for register in registers:
    if register not in memory:
      memory[register] = 0

def shouldBeExecuted(memory, line):
  register = line[4]
  operator = line[5]
  value = line[6]
  condition = 'memory[\'' + register + '\']' + operator + value
  return eval(condition)

highest = float('-inf')
ath = float('-inf')

def run():
  global highest
  global ath
  
  memory = {}
  for line in code:
    
    initRegisters(memory, line[0], line[4])
    
    if shouldBeExecuted(memory, line):
      register = line[0]
      func =  line[1]
      value = line[2]
      operation = func + '(memory,\'' + register + '\',' + value + ')'
      eval(operation)
    
    currentMax = max(memory.values())
    if (currentMax > ath): ath = currentMax
    
  highest = max(memory.values())

run()
advent.part(1, highest)
advent.part(2, ath)