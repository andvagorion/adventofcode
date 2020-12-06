package net.stefangaertner.aoc18.pojo;

public class Point {

	public int x;
	public int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return this.x + ", " + this.y;
	}

	public static Point of(int x, int y) {
		return new Point(x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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

		Point other = (Point) obj;
		return this.x == other.x && this.y == other.y;
	}

	public Point copy() {
		return new Point(this.x, this.y);
	}

	public Point turnRight() {
		if (Point.of(0, -1).equals(this)) {
			return Point.of(1, 0);
		} else if (Point.of(1, 0).equals(this)) {
			return Point.of(0, 1);
		} else if (Point.of(0, 1).equals(this)) {
			return Point.of(-1, 0);
		} else if (Point.of(-1, 0).equals(this)) {
			return Point.of(0, -1);
		}
		return null;
	}

	public Point turnLeft() {
		if (Point.of(0, -1).equals(this)) {
			return Point.of(-1, 0);
		} else if (Point.of(-1, 0).equals(this)) {
			return Point.of(0, 1);
		} else if (Point.of(0, 1).equals(this)) {
			return Point.of(1, 0);
		} else if (Point.of(1, 0).equals(this)) {
			return Point.of(0, -1);
		}
		return null;
	}

	public Point add(Point delta) {
		return Point.of(this.x + delta.x, this.y + delta.y);
	}

}
