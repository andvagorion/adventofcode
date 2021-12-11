def naturalNumbers():
  index = 1
  while True:
    yield index
    index += 1

def toChars(string):
  return [char for char in string]

def splitInLength(lst, length):
  remaining = lst
  while len(remaining):
    part = remaining[0:length]
    remaining = remaining[length:]
    yield part