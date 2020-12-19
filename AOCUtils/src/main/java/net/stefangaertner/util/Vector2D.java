package net.stefangaertner.util;

public class Vector2D {

	public int x;
	public int y;

	public Vector2D (int x, int y) {
		this.x = x;
		this.y = y;
	}

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

		Vector2D other = (Vector2D) obj;
		return this.x == other.x && this.y == other.y;
	}

	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

	public Vector2D turnRight() {
		if (Vector2D.of(0, -1)
				.equals(this)) {
			return Vector2D.of(1, 0);
		} else if (Vector2D.of(1, 0)
				.equals(this)) {
			return Vector2D.of(0, 1);
		} else if (Vector2D.of(0, 1)
				.equals(this)) {
			return Vector2D.of(-1, 0);
		} else if (Vector2D.of(-1, 0)
				.equals(this)) {
			return Vector2D.of(0, -1);
		}
		return null;
	}

	public Vector2D turnLeft() {
		if (Vector2D.of(0, -1)
				.equals(this)) {
			return Vector2D.of(-1, 0);
		} else if (Vector2D.of(-1, 0)
				.equals(this)) {
			return Vector2D.of(0, 1);
		} else if (Vector2D.of(0, 1)
				.equals(this)) {
			return Vector2D.of(1, 0);
		} else if (Vector2D.of(1, 0)
				.equals(this)) {
			return Vector2D.of(0, -1);
		}
		return null;
	}

	public Vector2D add(Vector2D delta) {
		return Vector2D.of(this.x + delta.x, this.y + delta.y);
	}

}
