class cuboid(object):

    def __init__(self, x0, x1, y0, y1, z0, z1):
        self.x0 = int(x0)
        self.x1 = int(x1)
        self.y0 = int(y0)
        self.y1 = int(y1)
        self.z0 = int(z0)
        self.z1 = int(z1)
    
    def __str__(self):
        return '(%d,%d,%d,%d,%d,%d)' % (self.x0, self.x1, self.y0, self.y1, self.z0, self.z1)