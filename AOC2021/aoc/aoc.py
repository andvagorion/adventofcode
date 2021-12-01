import os

def read_lines(file):
  with open(file) as f:
    return f.read().splitlines()

def reverse(items):
  return items[::-1]
