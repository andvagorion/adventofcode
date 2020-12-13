package net.stefangaertner.aoc20.util;

import net.stefangaertner.aoc18.pojo.Point;

public interface Ship {

	void move(Point move);

	default void turn(int degrees) {
		if (degrees > 0) {
			while (degrees > 0) {
				turnRight();
				degrees -= 90;
			}
		} else if (degrees < 0) {
			while (degrees < 0) {
				turnLeft();
				degrees += 90;
			}
		}
	}

	void forward(int value);

	void turnRight();

	void turnLeft();

	Point getPosition();

}