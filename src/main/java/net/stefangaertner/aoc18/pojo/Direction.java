package net.stefangaertner.aoc18.pojo;

public enum Direction {

	UP, DOWN, LEFT, RIGHT;

	public boolean isReverseOf(Direction other) {
		switch (this) {
		case DOWN:
			return other.equals(Direction.UP);
		case LEFT:
			return other.equals(Direction.RIGHT);
		case RIGHT:
			return other.equals(Direction.LEFT);
		case UP:
			return other.equals(Direction.DOWN);
		default:
			return false;
		}
	}

}
