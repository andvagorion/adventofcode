from __future__ import annotations
from aoc.point import point
from typing import Callable, TypeVar

T = TypeVar('T')

class grid(object):

    def __init__(self, w: int, h: int, initial: T = 0):
        self.width = w
        self.height = h
        self.matrix: list[list[T]] = [[initial for _ in range(w)] for _ in range(h)]
    
    @staticmethod
    def with_values(ls: list, fn: Callable[[T], T] = lambda val: val):
        w: int = len(ls[0])
        h: int = len(ls)
        g: grid = grid(w, h)
        for y in range(h):
            for x in range(w):
                g.matrix[y][x] = fn(ls[y][x])
        return g

    def print(self):
        for row in self.matrix:
            print(row)
    
    def print_condensed(self):
        for row in self.matrix:
            print(''.join([str(c) for c in row]))
    
    def get(self, p: point) -> T:
        return self.matrix[p.y][p.x]
    
    def set(self, p: point, val: T) -> None:
        self.matrix[p.y][p.x] = val

    def neighbors(self, p: point, diag: bool = False) -> list[point]:
        other: list[point] = [
            point(p.x, p.y-1),
            point(p.x, p.y+1),
            point(p.x-1, p.y),
            point(p.x+1, p.y)
        ]

        if diag:
            other.extend([
                point(p.x-1, p.y-1),
                point(p.x+1, p.y-1),
                point(p.x-1, p.y+1),
                point(p.x+1, p.y+1)
            ])

        return [p for p in other if self.in_grid(p)]

    def in_grid(self, p: point) -> bool:
        return (
            p.y >= 0 and p.y < self.height and
            p.x >= 0 and p.x < self.width
        )
    
    def update(self, p: point, func: Callable[[T], None]) -> None:
        self.set(p, func(self.get(p)))
    
    def update_if(self, p: point, func: Callable[[T], None], predicate: Callable[[T], bool] = lambda val: True) -> bool:
        if predicate(self.get(p)):
            self.set(p, func(self.get(p)))
            return True
        return False

    def update_all(self, func: Callable[[T], None]) -> None:
        self.update_all_if(func)
    
    def update_all_if(self, func: Callable[[T], None], predicate: Callable[[T], bool] = lambda val: True) -> int:
        points = self.find_all(predicate)
        for p in points:
            self.matrix[p.y][p.x] = func(self.matrix[p.y][p.x])
        return len(points)
    
    def find_all(self, predicate: Callable[[T], bool] = lambda _: True) -> list[point]:
        return [
            point(x,y)
            for x in range(self.width) 
            for y in range(self.height) 
            if predicate(self.matrix[y][x])
        ]
    
    def any(self, predicate:  Callable[[T], bool]) -> bool:
        for x in range(self.width):
            for y in range(self.height):
                if predicate(self.matrix[y][x]): return True
        return False
    
    def clone(self) -> grid:
        cl = grid(self.width, self.height, self.get(point(0,0)))

        for y in range(self.height):
            for x in range (self.width):
                cl.set(point(x, y), self.get(point(x, y)))
        
        return cl
    
    def __eq__(self:grid, other:grid) -> bool:
        if type(self) != type(other): return False
        if self.width != other.width or self.height != other.height: return False
        for y in range(self.height):
            if self.matrix[y] != other.matrix[y]: return False
        return True
