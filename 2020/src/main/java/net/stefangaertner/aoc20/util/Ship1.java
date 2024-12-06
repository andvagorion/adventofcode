package net.stefangaertner.aoc20.util;

public class Ship1 implements Ship {

	private Point2D pos = Point2D.of(0, 0);
	private Vector2D dir = Vector2D.of(1, 0);

	@Override
	public void move(Vector2D move) {
		this.pos = pos.add(move);
	}

	public void turnRight() {
		this.dir = Vector2D.of(dir.y, -dir.x);
	}

	public void turnLeft() {
		this.dir = Vector2D.of(-dir.y, dir.x);
	}

	@Override
	public void forward(int value) {
		this.move(Vector2D.of(dir.x * value, dir.y * value));
	}

	@Override
	public Point2D getPosition() {
		return this.pos;
	}

}
