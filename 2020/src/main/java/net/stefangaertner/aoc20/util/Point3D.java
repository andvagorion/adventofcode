package net.stefangaertner.aoc20.util;

import java.util.HashSet;
import java.util.Set;

public class Point3D implements Point {

	public int x;
	public int y;
	public int z;

	public static Point3D of(int x, int y, int z) {
		return new Point3D(x, y, z);
	}

	private Point3D (int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.x;
		result = prime * result + this.y;
		result = prime * result + this.z;
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
		Point3D other = (Point3D) obj;
		if (this.x != other.x) {
			return false;
		}
		if (this.y != other.y) {
			return false;
		}
		if (this.z != other.z) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("Point3D [%s, %s, %s]", this.x, this.y, this.z);
	}

	@Override
	public Set<Point3D> getNeighbors() {
		Set<Point3D> neighbors = new HashSet<>();
		for (int x = this.x - 1; x <= this.x + 1; x++) {
			for (int y = this.y - 1; y <= this.y + 1; y++) {
				for (int z = this.z - 1; z <= this.z + 1; z++) {
					Point3D other = Point3D.of(x, y, z);
					if (other.equals(this)) {
						continue;
					}
					neighbors.add(other);
				}
			}
		}
		return neighbors;
	}

}
