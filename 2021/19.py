import re
from aoc import aoc
from aoc.point3 import point3
from aoc.math3 import math3

SAMPLE = 'sample/19.txt'
DATA = 'data/19.txt'

pattern_scanner = re.compile(r'--- scanner \d+ ---')
pattern_point = re.compile(r'-?\d+,-?\d+,-?\d+')

def parse(file):
    lines = aoc.read_lines(file)
    scanners = {}

    scanner_num = -1
    for line in lines:
        if pattern_scanner.match(line):
            scanner_num = int(line.split()[2])
            scanners[scanner_num] = {
                'id': scanner_num,
                'pos': point3(0, 0, 0),
                'rot': [ 0,0,0, 0,0,0, 0,0,0 ],
                'ref': 0,
                'beacons': []
            }
        elif pattern_point.match(line):
            assert scanner_num >= 0
            x, y, z = [int(n) for n in line.split(',')]
            scanners[scanner_num]['beacons'].append(point3(x, y, z))
    
    return scanners

def find_relative_pos(fixed_scanner, scanners):
    relative_hits = []
    
    for other in scanners:
        found = eval_relative_pos(fixed_scanner, other)
        if found: relative_hits.append(other['id'])
    
    return relative_hits

def eval_relative_pos(scanner_fixed, scanner_relative):
    print('evaluating', scanner_relative['id'], 'relative to', scanner_fixed['id'])
    fixed_beacons = scanner_fixed['beacons']
    relative_beacons = scanner_relative['beacons']

    for matrix in math3.all():

        rotated = [math3.rotate(beacon, matrix) for beacon in relative_beacons]

        for beacon in rotated:
            
            for orig in fixed_beacons:
                diff = orig.subtract(beacon)

                assert beacon.add(diff) in fixed_beacons
                
                moved = [other.add(diff) for other in rotated]
                count = len(list(filter(lambda a: a in fixed_beacons, moved)))

                assert count >= 1
                
                if count >= 12:
                    scanner_relative['ref'] = scanner_fixed['id']
                    scanner_relative['pos'] = diff
                    scanner_relative['rot'] = matrix
                    print('hit', scanner_relative['ref'], scanner_relative['pos'], scanner_relative['rot'])
                    return True
    
    return False

def calc_real_position(scanners, id):
    scanner = scanners[id]
    
    if scanner['ref'] == scanner['id']:
        return scanner['beacons']
    
    fixed_beacons = scanner['beacons']
    
    # apply own rotation / transposition first
    ref = id
    while ref != 0:
        diff = scanners[ref]['pos']
        rot = scanners[ref]['rot']
        fixed_beacons = [math3.rotate(beacon, rot).add(diff) for beacon in fixed_beacons]
        ref = scanners[ref]['ref']
    
    return fixed_beacons

def calculate_relative_positions(scanners):
    handled = [0]
    should_be_checked = [0]

    while should_be_checked:
        next_id_to_check = should_be_checked.pop()
        fixed = next(scanners[key] for key in scanners if scanners[key]['id'] == next_id_to_check)
        
        scanners_to_find_pos = [scanners[key] for key in scanners if key not in handled and key != next_id_to_check]
        if not scanners_to_find_pos:
            break

        relative_to_n = find_relative_pos(fixed, scanners_to_find_pos)

        if not relative_to_n:
            continue
        
        for i in relative_to_n:
            handled.append(i)
            should_be_checked.append(i)

def count_all_beacons(scanners):
    all_beacons = set()
    for key in scanners:
        fixed_beacons = calc_real_position(scanners, key)
        all_beacons.update(fixed_beacons)
    return len(all_beacons)

scanners = parse(DATA)
calculate_relative_positions(scanners)
print(count_all_beacons(scanners))
