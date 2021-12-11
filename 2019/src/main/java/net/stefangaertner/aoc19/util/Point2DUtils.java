package net.stefangaertner.aoc19.util;

import java.util.Collection;

public class Point2DUtils {

	public static Point2D[] getMinAndMax(Collection<Point2D> pairs) {

		Point2D min = Point2D.of(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Point2D max = Point2D.of(Integer.MIN_VALUE, Integer.MIN_VALUE);

		for (Point2D p : pairs) {
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

		return new Point2D[] { min, max };
	}

}
