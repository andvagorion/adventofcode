package net.stefangaertner.aoc20.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PointHex {

	public int x;
	public int y;

	public enum HexDirection {
		NW, NE, W, E, SW, SE;

		public static HexDirection parse(String str) {
			if (str == null || str.trim().isEmpty()) {
				return null;
			}

			return Arrays.stream(HexDirection.values()).filter(dir -> dir.name().equals(str.toUpperCase())).findFirst()
					.orElse(null);
		}

		public Vector2D getVector(PointHex currentPosition) {
			switch (this) {
			case E:
				return Vector2D.of(1, 0);
			case W:
				return Vector2D.of(-1, 0);
			case NW:
				if (currentPosition.y % 2 == 0) {
					return Vector2D.of(-1, -1);
				} else {
					return Vector2D.of(0, -1);
				}
			case NE:
				if (currentPosition.y % 2 == 0) {
					return Vector2D.of(0, -1);
				} else {
					return Vector2D.of(1, -1);
				}
			case SW:
				if (currentPosition.y % 2 == 0) {
					return Vector2D.of(-1, 1);
				} else {
					return Vector2D.of(0, 1);
				}
			case SE:
				if (currentPosition.y % 2 == 0) {
					return Vector2D.of(0, 1);
				} else {
					return Vector2D.of(1, 1);
				}
			default:
				return Vector2D.of(0, 0);
			}
		}
	}

	private PointHex(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static PointHex of(int x, int y) {
		return new PointHex(x, y);
	}

	public PointHex getNeighbor(HexDirection dir) {
		Vector2D vec = dir.getVector(this);
		return this.add(vec);
	}

	public Set<PointHex> getNeighbors() {
		Set<PointHex> neighbors = new HashSet<>();
		// e + w
		neighbors.add(PointHex.of(this.x - 1, this.y));
		neighbors.add(PointHex.of(this.x + 1, this.y));
		// nw + sw
		if (this.y % 2 == 0) {
			neighbors.add(PointHex.of(this.x - 1, this.y - 1));
			neighbors.add(PointHex.of(this.x - 1, this.y + 1));
		} else {
			neighbors.add(PointHex.of(this.x, this.y - 1));
			neighbors.add(PointHex.of(this.x, this.y + 1));
		}
		// ne + se
		if (this.y % 2 == 0) {
			neighbors.add(PointHex.of(this.x, this.y - 1));
			neighbors.add(PointHex.of(this.x, this.y + 1));
		} else {
			neighbors.add(PointHex.of(this.x + 1, this.y - 1));
			neighbors.add(PointHex.of(this.x + 1, this.y + 1));
		}
		return neighbors;
	}

	public PointHex add(Vector2D vector) {
		return PointHex.of(this.x + vector.x, this.y + vector.y);
	}

	@Override
	public String toString() {
		return String.format("PointHex [%s, %s]", this.x, this.y);
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
		PointHex other = (PointHex) obj;
		if (this.x != other.x) {
			return false;
		}
		if (this.y != other.y) {
			return false;
		}
		return true;
	}

}
