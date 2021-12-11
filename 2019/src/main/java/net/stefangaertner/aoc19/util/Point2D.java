package net.stefangaertner.aoc19.util;

import java.util.HashSet;
import java.util.Set;

public class Point2D implements Point {

	public int x;
	public int y;

	public static Point2D of(int x, int y) {
		return new Point2D(x, y);
	}

	private Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.x;
		result = prime * result + this.y;
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
		Point2D other = (Point2D) obj;
		if (this.x != other.x) {
			return false;
		}
		if (this.y != other.y) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("Point2D [%s, %s]", this.x, this.y);
	}

	@Override
	public Set<Point2D> getNeighbors() {
		Set<Point2D> neighbors = new HashSet<>();
		for (int x = this.x - 1; x <= this.x + 1; x++) {
			for (int y = this.y - 1; y <= this.y + 1; y++) {
				Point2D other = Point2D.of(x, y);
				if (other.equals(this)) {
					continue;
				}
				neighbors.add(other);
			}
		}
		return neighbors;
	}

	public Point2D add(Point2D other) {
		return Point2D.of(this.x + other.x, this.y + other.y);
	}

	public Point2D add(Vector2D other) {
		return Point2D.of(this.x + other.x, this.y + other.y);
	}

	public Point2D copy() {
		return Point2D.of(this.x, this.y);
	}

}
