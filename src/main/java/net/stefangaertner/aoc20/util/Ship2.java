package net.stefangaertner.aoc20.util;

import net.stefangaertner.aoc18.pojo.Point;

public class Ship2 implements Ship {

	public Point pos = Point.of(0, 0);
	public Point waypoint = Point.of(10, 1);

	public void move(Point move) {
		this.waypoint = waypoint.add(move);
	}

	public void turnRight() {
		this.waypoint = Point.of(waypoint.y, -waypoint.x);
	}

	public void turnLeft() {
		this.waypoint = Point.of(-waypoint.y, waypoint.x);
	}

	public void forward(int value) {
		this.pos = Point.of(pos.x + waypoint.x * value, pos.y + waypoint.y * value);
	}

	@Override
	public Point getPosition() {
		return this.pos;
	}

}
