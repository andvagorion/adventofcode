import advent

with open('input/11') as f:
  content = [dir for dir in f.readline()[0:-1].split(',')]

N = (1, 1)
S = (-1, -1)
NW = (0, 1)
NE = (1, 0)
SW = (-1, 0)
SE = (0, -1)

def evenRow(pos):
  return (pos[1] % 2) == 0

def move(pos, vec):
  return (pos[0] + vec[0], pos[1] + vec[1])

def toOrigin(pos):
  (x, y) = pos
  if x < 0 and y < 0:
    return N
  elif x > 0 and y > 0:
    return S
  elif x < 0:
    return NE
  elif x > 0:
    return SW
  elif y < 0:
    return NW
  elif y > 0:
    return NE

def dist(_pos):
  steps = 0
  pos = (_pos[0], _pos[1])
  i = 0
  
  while pos != (0, 0):
    vec = toOrigin(pos)
    pos = move(pos, vec)
    steps += 1

  return steps

totalSteps = 0
maxSteps = 0

def parse():
  global totalSteps
  global maxSteps
  
  pos = (0, 0)
  for dir in content:
    vec = eval(dir.upper())
    pos = move(pos, vec)
    
    steps = dist(pos)
    if steps > maxSteps:
      maxSteps = steps
  
  totalSteps = dist(pos)

parse()

advent.part(1, totalSteps)
advent.part(2, maxSteps)