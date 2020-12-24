import advent

weights = {}
children = {}
parents = {}

def parse(lines):
  global weights
  global children
  global parents
  
  for line in lines:
    parts = line.split()
    
    name = parts[0]
    weight = parts[1][1:-1]
    
    weights[name] = int(weight)
    children[name] = []
    
    if name not in parents.keys():
      parents[name] = None
    
    if len(parts) > 2:
      for part in parts[3:]:
        child = part[0:-1] if ',' in part else part
        children[name].append(child)
        parents[child] = name

with open('07') as f:
  parse(f.readlines())

def root():
  return next(parent[0] for parent in parents.items() if not parent[1])

def weight(node):
  sub = sum([weight(child) for child in children[node]])
  return weights[node] + sub

def imbalanced():
  node = root()
  shouldBe = 0
  
  while node:
    
    subWeights = [weight(child) for child in children[node]]
    wrongWeight = next((w for w in subWeights if subWeights.count(w) == 1), None)
    
    if not wrongWeight:
      # current node has wrong weight
      return shouldBe
    
    node = children[node][subWeights.index(wrongWeight)]
    
    other = next((w for w in subWeights if subWeights.count(w) > 1), None)
    diff = other - wrongWeight
    shouldBe = weights[node] + diff

advent.part(1, root())
advent.part(2, imbalanced())