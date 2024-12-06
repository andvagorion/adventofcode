import re
from aoc import aoc
from aoc.math3 import math3
from aoc.cuboid import cuboid

SAMPLE = 'sample/22.txt'
DATA = 'input/22.txt'

PATTERN = r'^(on|off) x=(-?\d+)\.\.(-?\d+),y=(-?\d+)\.\.(-?\d+),z=(-?\d+)\.\.(-?\d+)'

def process(reactor, op, cube):
    activate = op == 'on'
    
    for x in range(cube.x0, cube.x1 + 1):
        for y in range(cube.y0, cube.y1 + 1):
            for z in range(cube.z0, cube.z1 + 1):
                if reactor == None:
                    print(x, y, z, reactor, op, cube)
                assert (x, y, z) in reactor
                reactor[(x, y, z)] = activate

def part1(filename):
    lines = aoc.read_lines(filename)
    reactor = cuboid(-50, 50, -50, 50, -50, 50)
    reactor_cubes = {(x, y, z): False for x in range(-50, 51) for y in range(-50, 51) for z in range(-50, 51)}
    
    for line in lines:
        parts = re.match(PATTERN, line)
        if parts:
            op, x0, x1, y0, y1, z0, z1 = parts.groups()
            cube = cuboid(x0, x1, y0, y1, z0, z1)
            
            if not math3.intersect_cuboid(reactor, cube):
                continue
        
            process(reactor_cubes, op, cube)
    
    print(sum(1 for val in reactor_cubes.values() if val))

part1(DATA)