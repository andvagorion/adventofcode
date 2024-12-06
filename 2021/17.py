from aoc.point import point
from aoc.rect import rect

def sum_ap_x(x0, n, d):
    n = x0 if x0 - n < 0 else n
    xn = x0 + (n - 1) * d
    return int(n * (x0 + xn) / 2)

def sum_ap_y(y0, n, d):
    yn = y0 + (n - 1) * d
    return int(n * (y0 + yn) / 2)

def step(velocity:point, n:int):
    return point(
        sum_ap_x(velocity.x, n, -1),
        sum_ap_y(velocity.y, n, -1)
    )

def process(velocity:point, target:rect) -> point:
    max_x = target.x + target.w
    min_y = target.y
    pos = point(0,0)
    i = 0
    while pos.x >= 0 and pos.x <= max_x and pos.y >= min_y:
        i += 1
        pos = step(velocity, i)
        yield pos

def part1(target):
    total_max_y = 0
    for x in range(target.x):
        for y in range(target.x):
            velocity = point(x,y)
            steps:list[point] = [step for step in process(velocity, target)]
            
            if any(step.in_rect(target) for step in steps):
                highest = max(steps, key = lambda p: p.y)
                total_max_y = max(total_max_y, highest.y)

    print(total_max_y)

# SAMPLE: target area: x=20..30, y=-10..-5
SAMPLE = rect(20, -10, 10, 5)

# DATA: target area: x=155..182, y=-117..-67
DATA = rect(155, -117, (182-155), (117-67))

part1(DATA)