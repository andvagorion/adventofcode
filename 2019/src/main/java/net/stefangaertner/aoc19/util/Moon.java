package net.stefangaertner.aoc19.util;

public class Moon {

	public int x;
	public int y;
	public int z;

	public int vx = 0;
	public int vy = 0;
	public int vz = 0;

	public boolean rotated = false;
	public int rot = 0;

	public Moon (int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "pos = [x=" + x + ", y=" + y + ", z=" + z + "], vel = [x=" + vx + ", vy=" + vy + ", vz =" + vz + "]";
	}

	public boolean samePosition(Moon moon) {
		return moon.x == this.x && moon.y == this.y && moon.z == this.z;
	}

}
