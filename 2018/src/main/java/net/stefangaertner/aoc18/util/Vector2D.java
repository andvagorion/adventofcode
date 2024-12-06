package net.stefangaertner.aoc18.util;

public class Vector2D {

	public int x;
	public int y;

	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return this.x + ", " + this.y;
	}

	public static Vector2D of(int x, int y) {
		return new Vector2D(x, y);
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

		Vector2D other = (Vector2D) obj;
		return this.x == other.x && this.y == other.y;
	}

	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

	public Vector2D turnRight() {
		return Vector2D.of(this.y, -this.x);
	}

	public Vector2D turnLeft() {
		return Vector2D.of(-this.y, this.x);
	}

	public Vector2D add(Vector2D delta) {
		return Vector2D.of(this.x + delta.x, this.y + delta.y);
	}

}
