from aoc.rect import rect

class point(object):
    
    def __init__(self, x, y):
        self.x = x
        self.y = y
    
    def in_rect(self, r:rect) -> bool:
        return (
            self.x - r.x >= 0        and
            self.x - r.x <= r.w   and
            self.y - r.y >= 0        and
            self.y - r.y <= r.h
        )
    
    def __str__(self) -> str:
        return '(%d,%d)' % (self.x, self.y)
    __repr__ = __str__

    def __hash__(self) -> int:
        return hash(self.x) ^ hash(self.y)

    def __eq__(self, other) -> bool:
        return type(other) == type(self) and self.x == other.x and self.y == other.y

    def __lt__(self, other) -> bool:
        if type(other) != type(self): return NotImplemented
        return (self.x, self.y) < (other.x, other.y)
