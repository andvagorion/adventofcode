from operator import add

def read_lines(file):
  with open(file) as f:
    return f.read().splitlines()

def write(file, content):
  with open(file, 'w') as f:
    f.write(content)

def reverse(items):
  return items[::-1]

def tuple_add(a, b):
  return tuple(map(add, a, b))
