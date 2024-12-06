import advent

with open('input/01') as f:
  content = f.read()

def shift(s):
  s += s[0]
  while len(s) > 1:
    a = s[0]
    b = s[1]
    s = s[1:]
    yield a,b

def count(l):
  ct = 0
  for (a, b) in l:
    if (a == b):
      ct += int(a)
  return ct


def shift2(s):
  s2 = s[len(s)>>1:] + s[0:len(s)>>1]
  while len(s) > 0:
    a = s[0]
    b = s2[0]
    s = s[1:]
    s2 = s2[1:]
    yield a,b

advent.part(1, count(shift(content)))
advent.part(2, count(shift2(content)))