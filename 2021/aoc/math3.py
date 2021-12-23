from aoc.point3 import point3
from aoc.cuboid import cuboid

class math3(object):

    IDENTITY = (
        1, 0, 0,
        0, 1, 0,
        0, 0, 1
    )

    MATRIX_X = (
        1,  0,  0,
        0,  0, -1,
        0,  1,  0
    )

    MATRIX_Y = (
        0,  0,  1,
        0,  1,  0,
        -1,  0,  0
    )

    MATRIX_Z = (
        0, -1,  0,
        1,  0,  0,
        0,  0,  1
    )

    @staticmethod
    def rotate(p:point3, m:tuple) -> point3:
        return point3(
            p.x * m[0] + p.y * m[1] + p.z * m[2],
            p.x * m[3] + p.y * m[4] + p.z * m[5],
            p.x * m[6] + p.y * m[7] + p.z * m[8]
        )
    
    @staticmethod
    def dot_product(m:tuple, n:tuple) -> tuple:
        return (
            m[0] * n[0] + m[1] * n[3] + m[2] * n[6],
            m[0] * n[1] + m[1] * n[4] + m[2] * n[7],
            m[0] * n[2] + m[1] * n[5] + m[2] * n[8],

            m[3] * n[0] + m[4] * n[3] + m[5] * n[6],
            m[3] * n[1] + m[4] * n[4] + m[5] * n[7],
            m[3] * n[2] + m[4] * n[5] + m[5] * n[8],

            m[6] * n[0] + m[7] * n[3] + m[8] * n[6],
            m[6] * n[1] + m[7] * n[4] + m[8] * n[7],
            m[6] * n[2] + m[7] * n[5] + m[8] * n[8]
        )
    
    @staticmethod
    def transpose(m:tuple) -> tuple:
        return (
            m[0], m[3], m[6],
            m[1], m[4], m[7],
            m[2], m[5], m[8]
        )
    
    @staticmethod
    def rotx(p:point3):
        return math3.rotate(p, math3.MATRIX_X)

    @staticmethod
    def roty(p:point3):
        return math3.rotate(p, math3.MATRIX_Y)

    @staticmethod
    def rotz(p:point3):
        return math3.rotate(p, math3.MATRIX_Z)

    @staticmethod
    def all():
        matrices = set()
        
        px = math3.IDENTITY
        for x in range(4):
            px = math3.dot_product(px, math3.MATRIX_X)
            matrices.add(px)
            
            py = px
            for y in range(4):
                py = math3.dot_product(py, math3.MATRIX_Y)
                matrices.add(py)
                
                pz = py
                for z in range(4):
                    pz = math3.dot_product(pz, math3.MATRIX_Z)
                    matrices.add(pz)
        
        return matrices
    
    @staticmethod
    def intersect_cuboid(src:cuboid, trg:cuboid):
        if (
            ((src.x0 <= trg.x0 <= src.x1) or (trg.x0 <= src.x0 <= trg.x1)) and
            ((src.y0 <= trg.y0 <= src.y1) or (trg.y0 <= src.y0 <= trg.y1)) and
            ((src.z0 <= trg.z0 <= src.z1) or (trg.z0 <= src.z0 <= trg.z1))
        ): return True

        return False
