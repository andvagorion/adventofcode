import advent
import util

# 1 (center) = layer 0
def getLayer(num):
  return next(i-1 for i in util.naturalNumbers() if firstNumber(i) > num)

# first number in layer: (n * 2 - 1)^2 + 1
def firstNumber(layer):
  return (layer * 2 - 1) * (layer * 2 - 1) + 1

def sideLength(layer):
  return layer * 2

def midPoint(sideLength):
  return int(sideLength / 2 - 1)

def dist(n):
  layer = getLayer(n)
  positionInSide = (n - firstNumber(layer)) % sideLength(layer)
  distInLayer = abs(positionInSide - midPoint(sideLength(layer)))
  return layer + distInLayer

num = 368078

advent.part(1, dist(num))
advent.part(2, None)
