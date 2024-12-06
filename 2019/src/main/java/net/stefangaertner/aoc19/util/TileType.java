package net.stefangaertner.aoc19.util;

public enum TileType {

	EMPTY(0), WALL(1), BLOCK(2), PADDLE(3), BALL(4);

	private int id;

	private TileType (int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static TileType forId(int id) {

		switch (id) {
		case 0:
			return EMPTY;
		case 1:
			return WALL;
		case 2:
			return BLOCK;
		case 3:
			return PADDLE;
		case 4:
			return BALL;
		default:
			return null;
		}

	}

	public char getChar() {

		switch (this.id) {
		case 0:
			return '.';
		case 1:
			return 'x';
		case 2:
			return '#';
		case 3:
			return '=';
		case 4:
			return 'o';
		default:
			return ' ';
		}
	}

	@Override
	public String toString() {
		return String.valueOf(this.getChar());
	}
}
