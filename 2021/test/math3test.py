import unittest
from aoc.math3 import math3
from aoc.cuboid import cuboid

class Math3Test(unittest.TestCase):

    def test_intersect_simple(self):
        c1:cuboid = cuboid(0, 10, 0, 10, 0, 10)
        c2:cuboid = cuboid(5, 6, 5, 6, 5, 6)
        self.assertTrue(math3.intersect_cuboid(c1, c2))
    
    def test_intersect_none(self):
        c1:cuboid = cuboid(0, 0, 0, 10, 10, 10)
        c2:cuboid = cuboid(5, -5, -5, -2, 6, 6)
        self.assertFalse(math3.intersect_cuboid(c1, c2))
    
    def test_intersect_large(self):
        c1:cuboid = cuboid(0, 10, 0, 10, 0, 10)
        c2:cuboid = cuboid(5, 19, 5, 29, 5, 370)
        self.assertTrue(math3.intersect_cuboid(c1, c2))
        
    def test_intersect_tiny(self):
        c1:cuboid = cuboid(-90, 10, -40, 10, -30, 10)
        c2:cuboid = cuboid(1, 1, 1, 1, 1, 1)
        self.assertTrue(math3.intersect_cuboid(c1, c2))
        
    def test_intersect_none_large_outside(self):
        c1:cuboid = cuboid(-50, 50, -50, 50, -50, 50)
        c2:cuboid = cuboid(-54112, -39298, -85059, -49293, -27449, 7877)
        self.assertFalse(math3.intersect_cuboid(c1, c2))
