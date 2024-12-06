import advent

with open('input/04') as f:
  content = f.readlines()

def valid(words):
  return len(words) == len(set(words))

def sort(word):
  return str(sorted(list(word)))

def countValid(phrases):
  return sum([1 if valid(phrase) else 0 for phrase in phrases])

def part1():
  phrases = [phrase.split() for phrase in content]
  return countValid(phrases)

def sortEach(words):
  return [sort(word) for word in words]

def part2():
  phrases = [sortEach(phrase.split()) for phrase in content]
  return countValid(phrases)

advent.part(1, part1())
advent.part(2, part2())
