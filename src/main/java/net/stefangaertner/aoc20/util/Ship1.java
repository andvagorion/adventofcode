package net.stefangaertner.aoc20.util;

import net.stefangaertner.aoc18.pojo.Point;

public class Ship1 implements Ship {
	
	private Point pos = Point.of(0, 0);
	private Point dir = Point.of(1, 0);
	
	@Override
	public void move(Point move) {
		this.pos = pos.add(move);
	}

	public void turnRight() {
		this.dir = Point.of(dir.y, -dir.x);
	}
	
	public void turnLeft() {
		this.dir = Point.of(-dir.y, dir.x);
	}

	@Override
	public void forward(int value) {
		this.move(Point.of(dir.x * value, dir.y * value));
	}
	
	@Override
	public Point getPosition() {
		return this.pos;
	}

}
