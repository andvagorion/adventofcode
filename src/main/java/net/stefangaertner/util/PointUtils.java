package net.stefangaertner.util;

import java.util.Collection;

import net.stefangaertner.aoc18.pojo.Point;

public class PointUtils {

	public static Point[] getMinAndMax(Collection<Point> pairs) {

		Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Point max = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

		for (Point p : pairs) {
			if (p.x < min.x) {
				min.x = p.x;
			}
			if (p.x > max.x) {
				max.x = p.x;
			}
			if (p.y < min.y) {
				min.y = p.y;
			}
			if (p.y > max.y) {
				max.y = p.y;
			}
		}

		return new Point[] { min, max };
	}

}
