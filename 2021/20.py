from aoc import aoc

SAMPLE = 'sample/20.txt'
SAMPLE_SIZE = 5

DATA = 'input/20.txt'
DATA_SIZE = 100

X = 0
Y = 1

algorithm = None
pixels = None

to_bool = lambda c: True if c == '#' else False

def parse_algorith(file):
    lines = aoc.read_lines(file)
    return [to_bool(c) for c in lines[0]]

def parse_pixels(file):
    lines = aoc.read_lines(file)[2:]
    pixels = {}
    for row in range(len(lines)):
        line = lines[row]
        for col in range(len(line)):
            pos = (col, row)
            val = to_bool(line[col])
            pixels[pos] = val
    return pixels

def get_min_and_max(pixels):    
    x_vals = [p[X] for p in pixels.keys()]
    y_vals = [p[Y] for p in pixels.keys()]
    p0 = (min(x_vals), min(y_vals))
    p1 = (max(x_vals), max(x_vals))
    return p0, p1

def eval_pixel(pixels, x, y):
    if (x, y) not in pixels: return False
    return pixels[(x, y)]

def get_index(pixels, xn, yn):
    num = ''
    for y in range(yn - 1, yn + 2):
        for x in range(xn - 1, xn + 2):
            val = eval_pixel(pixels, x, y)
            num += '1' if val else '0'
    return int(num, 2)

def step(pixels, algorithm):
    p0, p1 = get_min_and_max(pixels)

    # outside edges
    extra = 6
    
    p0 = (p0[X] - 1 - extra, p0[Y] - 1 - extra)
    p1 = (p1[X] + 2 + extra, p1[Y] + 2 + extra)
    
    next_pixels = {}
    for y in range(p0[Y], p1[X]):
        for x in range(p0[X], p1[X]):
            idx = get_index(pixels, x, y)
            next_pixels[(x,y)] = algorithm[idx]
    return next_pixels

def debug_print(pixels):
    p0, p1 = get_min_and_max(pixels)
    for y in range(p0[Y] - 1, p1[X] + 2):
        row = ''
        for x in range(p0[X] - 1, p1[X] + 2):
            row += '#' if eval_pixel(pixels, x, y) else '.'
        print(row)

def clean(pixels, size, steps):
    mn = -3 * steps
    mx = size + 3 * steps
    return {k: v for k, v in pixels.items() if k[X] > mn and k[X] < mx and k[Y] > mn and k[Y] < mx}

def part1(pixels, algorithm, size):
    pixels = step(pixels, algorithm)
    pixels = step(pixels, algorithm)
    pixels = clean(pixels, size, 2)
    lit = list(pixels.values()).count(True)
    print(lit)

def part2(pixels, algorithm, size):
    steps = 50

    for i in range(steps):
        pixels = step(pixels, algorithm)
        if i % 2 == 1:
            pixels = clean(pixels, size, i)
    
    lit = list(pixels.values()).count(True)
    print(lit)

algorithm = parse_algorith(DATA)
pixels = parse_pixels(DATA)
size = DATA_SIZE

part1(pixels, algorithm, size)
part2(pixels, algorithm, size)