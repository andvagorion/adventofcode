package net.stefangaertner.aoc19;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.Point2D;

public class Day10 {

	static Point2D chosenPart2 = Point2D.of(27, 19);

	public static void main(String[] strings) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = Advent.read("aoc19/010-data");

		List<Point2D> asteroids = getAsteroidsFromGrid(lines);

		int max = 0;

		for (int i = 0; i < asteroids.size(); i++) {

			Point2D curr = asteroids.get(i);

			List<Point2D> whatCurrentCanSee = new ArrayList<>();
			int canSee = 0;

			inner: for (int j = 0; j < asteroids.size(); j++) {

				if (i == j) {
					continue;
				}

				Point2D other = asteroids.get(j);

				double angle1 = Math.atan2(other.y - curr.y, other.x - curr.x) * 180 / Math.PI;
				double dist1 = Math
						.sqrt((other.x - curr.x) * (other.x - curr.x) + (other.y - curr.y) * (other.y - curr.y));

				for (int k = 0; k < asteroids.size(); k++) {
					if (i == k || k == j) {
						continue;
					}
					Point2D test = asteroids.get(k);

					double angle2 = Math.atan2(test.y - curr.y, test.x - curr.x) * 180 / Math.PI;
					double dist2 = Math
							.sqrt((test.x - curr.x) * (test.x - curr.x) + (test.y - curr.y) * (test.y - curr.y));

					if (Double.compare(angle1, angle2) == 0 && dist1 < dist2) {
						// blocked
						continue inner;
					}
				}

				whatCurrentCanSee.add(other);
				canSee++;

			}

			if (canSee > max) {
				max = canSee;
			}

		}

		return max;
	}

	static long part2() {
		List<String> lines = Advent.read("aoc19/010-data");

		List<Point2D> asteroids = getAsteroidsFromGrid(lines);

		Point2D chosen = chosenPart2;
		asteroids.remove(chosen);

		// sort by angle, sort by distance
		Comparator<Point2D> byAngle = Comparator.comparing(a -> getAngle(chosen, a));
		Comparator<Point2D> byDistance = Comparator.comparing(a -> getDist(chosen, a));
		asteroids.sort(byAngle.thenComparing(byDistance));

		List<Point2D> vaporized = new ArrayList<>();
		int i = 0;
		double minAngle = 0 - Math.PI / 2;

		// determine start
		while (getAngle(chosen, asteroids.get(i)) < minAngle) {
			i++;
		}

		double currentAngle = 0;
		while (!asteroids.isEmpty()) {

			// only asteroids with the same angle remaining
			if (onlySameAngle(asteroids, chosen, currentAngle)) {
				break;
			}

			if (i > asteroids.size() - 1) {
				i = 0;
			}

			Point2D a = asteroids.get(i);

			double angle = getAngle(chosen, a);
			if (angle == currentAngle) {
				i++;
				continue;
			}

			currentAngle = angle;

			vaporized.add(a);
			asteroids.remove(a);

		}

		// clear the rest
		for (Point2D a : asteroids) {
			vaporized.add(a);
		}

		Point2D p = vaporized.get(199);

		return (p.x * 100 + p.y);
	}

	private static boolean onlySameAngle(List<Point2D> asteroids, Point2D center, double currentAngle) {
		for (Point2D a : asteroids) {
			if (getAngle(center, a) != currentAngle) {
				return false;
			}
		}
		return true;
	}

	private static double getAngle(Point2D pair, Point2D other) {
		return Math.atan2(other.y - pair.y, other.x - pair.x);
	}

	private static double getDist(Point2D pair, Point2D other) {
		return Math.sqrt((other.x - pair.x) * (other.x - pair.x) + (other.y - pair.y) * (other.y - pair.y));
	}

	private static List<Point2D> getAsteroidsFromGrid(List<String> lines) {
		List<Point2D> asteroids = new ArrayList<>();

		for (int y = 0; y < lines.size(); y++) {

			String line = lines.get(y);

			for (int x = 0; x < line.length(); x++) {

				char c = line.charAt(x);
				if (c == '#') {
					asteroids.add(Point2D.of(x, y));
				}

			}

		}

		return asteroids;
	}

}
