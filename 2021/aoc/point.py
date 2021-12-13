class point(object):
    
    def __init__(self, x, y):
        self.x = x
        self.y = y
    
    def __str__(self):
        return '(%d,%d)' % (self.x, self.y)
    __repr__ = __str__

    def __hash__(self):
        return hash(self.x) ^ hash(self.y)

    def __eq__(self, other):
        return type(other) == type(self) and self.x == other.x and self.y == other.y
