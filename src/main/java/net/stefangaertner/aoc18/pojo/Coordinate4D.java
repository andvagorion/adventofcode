package net.stefangaertner.aoc18.pojo;

public class Coordinate4D {

	public int x;
	public int y;
	public int z;
	public int w;

	public Coordinate4D(int x, int y, int z, int w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}

	public int distanceTo(Coordinate4D other) {
		return Math.abs(this.x - other.x) + Math.abs(this.y - other.y) + Math.abs(this.z - other.z)
				+ Math.abs(this.w - other.w);
	}

}
