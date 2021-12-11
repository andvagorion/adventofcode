package net.stefangaertner.aoc20.util;

import java.util.HashSet;
import java.util.Set;

public class Point4D implements Point {

	public int x;
	public int y;
	public int z;
	public int w;

	public static Point4D of(int x, int y, int z, int w) {
		return new Point4D(x, y, z, w);
	}

	private Point4D (int x, int y, int z, int w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.x;
		result = prime * result + this.y;
		result = prime * result + this.z;
		result = prime * result + this.w;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Point4D other = (Point4D) obj;
		if (this.x != other.x) {
			return false;
		}
		if (this.y != other.y) {
			return false;
		}
		if (this.z != other.z) {
			return false;
		}
		if (this.w != other.w) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("Point4D [%s, %s, %s, %s]", this.x, this.y, this.z, this.w);
	}

	@Override
	public Set<Point4D> getNeighbors() {
		Set<Point4D> neighbors = new HashSet<>();
		for (int x = this.x - 1; x <= this.x + 1; x++) {
			for (int y = this.y - 1; y <= this.y + 1; y++) {
				for (int z = this.z - 1; z <= this.z + 1; z++) {
					for (int w = this.w - 1; w <= this.w + 1; w++) {
						Point4D other = Point4D.of(x, y, z, w);
						if (other.equals(this)) {
							continue;
						}
						neighbors.add(other);
					}
				}
			}
		}
		return neighbors;
	}

	public long manhattanDistanceTo(Point4D other) {
		return Math.abs(this.x - other.x) + Math.abs(this.y - other.y) + Math.abs(this.z - other.z)
				+ Math.abs(this.w - other.w);
	}

}
