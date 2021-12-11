package net.stefangaertner.aoc20.util;

public class Ship2 implements Ship {

	public Point2D pos = Point2D.of(0, 0);
	public Point2D waypoint = Point2D.of(10, 1);

	public void move(Vector2D move) {
		this.waypoint = waypoint.add(move);
	}

	public void turnRight() {
		this.waypoint = Point2D.of(waypoint.y, -waypoint.x);
	}

	public void turnLeft() {
		this.waypoint = Point2D.of(-waypoint.y, waypoint.x);
	}

	public void forward(int value) {
		this.pos = Point2D.of(pos.x + waypoint.x * value, pos.y + waypoint.y * value);
	}

	@Override
	public Point2D getPosition() {
		return this.pos;
	}

}
