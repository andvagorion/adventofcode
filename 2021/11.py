from aoc import aoc
from aoc.point import point
from aoc.grid import grid

lines = aoc.read_lines('input/11.txt')

octopi = grid.with_values(lines, lambda val: int(val))

increment = lambda val: val + 1
set_zero = lambda val: 0

should_flash = lambda val: val > 9
is_not_zero = lambda val: val != 0

def step():
    octopi.update_all(increment)

    have_flashed = 0
    while octopi.any(should_flash):
        flashy_bois = octopi.find_all(should_flash)
        have_flashed += len(flashy_bois)

        for flashy_boi in flashy_bois:
            octopi.update(flashy_boi, set_zero)

            for neighbor in octopi.neighbors(flashy_boi, True):
                octopi.update_if(neighbor, increment, is_not_zero)

    return have_flashed

sum = 0
for i in range(100):
    sum += step()

print(sum)

count = 100
while step() != 100:
    count += 1

print(count + 1)