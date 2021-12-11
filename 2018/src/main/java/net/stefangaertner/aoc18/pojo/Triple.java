package net.stefangaertner.aoc18.pojo;

public class Triple {
	
	public int x;
	public int y;
	public int z;

	public Triple(int x2, int y2, int z2) {
		this.x = x2;
		this.y = y2;
		this.z = z2;
	}

	public Triple() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triple other = (Triple) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Triple [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	public int dist(Triple triple) {
		int dx = Math.abs(triple.x - this.x);
		int dy = Math.abs(triple.y - this.y);
		int dz = Math.abs(triple.z - this.z);
		
		return dx + dy + dz;
	}
}
