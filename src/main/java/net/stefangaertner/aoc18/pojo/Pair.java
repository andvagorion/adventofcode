package net.stefangaertner.aoc18.pojo;

public class Pair {

	public int x;
	public int y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return this.x + ", " + this.y;
	}

	public static Pair of(int x, int y) {
		return new Pair(x, y);
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

		Pair other = (Pair) obj;
		return this.x == other.x && this.y == other.y;
	}

	public Pair copy() {
		return new Pair(this.x, this.y);
	}

	public Pair turnRight() {
		if (Pair.of(0, -1).equals(this)) {
			return Pair.of(1, 0);
		} else if (Pair.of(1, 0).equals(this)) {
			return Pair.of(0, 1);
		} else if (Pair.of(0, 1).equals(this)) {
			return Pair.of(-1, 0);
		} else if (Pair.of(-1, 0).equals(this)) {
			return Pair.of(0, -1);
		}
		return null;
	}

	public Pair turnLeft() {
		if (Pair.of(0, -1).equals(this)) {
			return Pair.of(-1, 0);
		} else if (Pair.of(-1, 0).equals(this)) {
			return Pair.of(0, 1);
		} else if (Pair.of(0, 1).equals(this)) {
			return Pair.of(1, 0);
		} else if (Pair.of(1, 0).equals(this)) {
			return Pair.of(0, -1);
		}
		return null;
	}

	public Pair add(Pair delta) {
		return Pair.of(this.x + delta.x, this.y + delta.y);
	}

}
