package net.stefangaertner.aoc18.pojo;

public class Nanobot {

	public int x;
	public int y;
	public int z;

	@Override
	public String toString() {
		return "Nanobot [x=" + x + ", y=" + y + ", z=" + z + ", r=" + r + "]";
	}

	public int r;

	public Nanobot(int x, int y, int z, int r) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
	}

	public boolean isInRange(Nanobot bot) {
		int dx = Math.abs(bot.x - this.x);
		int dy = Math.abs(bot.y - this.y);
		int dz = Math.abs(bot.z - this.z);

		return (dx + dy + dz) <= this.r;
	}

	public boolean isInRange(Triple point) {
		int dx = Math.abs(point.x - this.x);
		int dy = Math.abs(point.y - this.y);
		int dz = Math.abs(point.z - this.z);

		return (dx + dy + dz) <= this.r;
	}

	public int dist(Triple triple) {
		int dx = Math.abs(triple.x - this.x);
		int dy = Math.abs(triple.y - this.y);
		int dz = Math.abs(triple.z - this.z);

		return dx + dy + dz;
	}

	public int dist(Nanobot other) {
		return dist(new Triple(other.x, other.y, other.z));
	}
}
