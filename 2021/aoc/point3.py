from __future__ import annotations
from functools import total_ordering

@total_ordering
class point3(object):
    
    def __init__(self, x, y, z):
        self.x = x
        self.y = y
        self.z = z
    
    def subtract(self, other:point3) -> point3:
        return point3(
            self.x - other.x,
            self.y - other.y,
            self.z - other.z
        )
    
    def add(self, other:point3) -> point3:
        return point3(
            self.x + other.x,
            self.y + other.y,
            self.z + other.z
        )
    
    def __str__(self) -> str:
        return '(%d,%d,%d)' % (self.x, self.y, self.z)
    __repr__ = __str__

    def __hash__(self) -> int:
        return hash(self.x) ^ hash(self.y) ^ hash(self.z)

    def __eq__(self, other) -> bool:
        return type(other) == type(self) and self.x == other.x and self.y == other.y and self.z == other.z
    
    def __lt__(self, other) -> bool:
        if type(other) != type(self): return NotImplemented
        return (self.x, self.y, self.z) < (other.x, other.y, other.z)
