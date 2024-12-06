package net.stefangaertner.aoc18.pojo;

public class Cart {

	public int turn;
	public Direction dir;

	public boolean moved = false;
	public boolean crashed = false;

	public Cart(Direction direction) {
		this.dir = direction;
	}

	public void turn() {
		if (crashed)
			return;

		if (turn == 0) {
			this.turnLeft();
		} else if (turn == 1) {
			// continue straight
		} else if (turn == 2) {
			this.turnRight();
		}

		turn++;
		if (turn > 2)
			turn = 0;
	}

	public void turnRight() {
		if (dir == Direction.UP)
			dir = Direction.RIGHT;
		else if (dir == Direction.DOWN)
			dir = Direction.LEFT;
		else if (dir == Direction.RIGHT)
			dir = Direction.DOWN;
		else if (dir == Direction.LEFT)
			dir = Direction.UP;
	}

	public void turnLeft() {
		if (dir == Direction.UP)
			dir = Direction.LEFT;
		else if (dir == Direction.DOWN)
			dir = Direction.RIGHT;
		else if (dir == Direction.RIGHT)
			dir = Direction.UP;
		else if (dir == Direction.LEFT)
			dir = Direction.DOWN;
	}

	@Override
	public String toString() {
		if (crashed)
			return "X";
		if (dir == Direction.UP)
			return "^";
		if (dir == Direction.DOWN)
			return "v";
		if (dir == Direction.RIGHT)
			return ">";
		if (dir == Direction.LEFT)
			return "<";
		return null;
	}

}
