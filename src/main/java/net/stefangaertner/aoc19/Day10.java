package net.stefangaertner.aoc19;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import net.stefangaertner.aoc18.pojo.Point;
import net.stefangaertner.util.FileUtils;
import net.stefangaertner.util.StringUtils;

public class Day10 {

	static Point chosenPart2 = new Point(27, 19);

	public static void main(String[] strings) {
		List<String> lines = FileUtils.read("aoc19/010-data");
		part1(lines);
		part2(lines);
	}
	
	private static void part1(List<String> lines) {

		List<Point> asteroids = getAsteroidsFromGrid(lines);

		Point best = null;
		int max = 0;

		for (int i = 0; i < asteroids.size(); i++) {

			Point curr = asteroids.get(i);

			List<Point> whatCurrentCanSee = new ArrayList<>();
			int canSee = 0;

			inner: for (int j = 0; j < asteroids.size(); j++) {

				if (i == j) {
					continue;
				}

				Point other = asteroids.get(j);

				double angle1 = Math.atan2(other.y - curr.y, other.x - curr.x) * 180 / Math.PI;
				double dist1 = Math
						.sqrt((other.x - curr.x) * (other.x - curr.x) + (other.y - curr.y) * (other.y - curr.y));

				for (int k = 0; k < asteroids.size(); k++) {
					if (i == k || k == j) {
						continue;
					}
					Point test = asteroids.get(k);

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
				best = curr;
				max = canSee;
			}

		}

		System.out.println("Part 1: " + max + " (" + best + ")");

	}
	
	private static void part2(List<String> lines) {
		
		List<Point> asteroids = getAsteroidsFromGrid(lines);
		
		Point chosen = chosenPart2;
		asteroids.remove(chosen);
		
		// sort by angle, sort by distance
		Comparator<Point> byAngle = Comparator.comparing(a -> getAngle(chosen, a));
		Comparator<Point> byDistance = Comparator.comparing(a -> getDist(chosen, a));
		asteroids.sort(byAngle.thenComparing(byDistance));
		
		List<Point> vaporized = new ArrayList<>();
		int i = 0;
		double minAngle = 0 - Math.PI / 2;
		
		// determine start
		while(getAngle(chosen, asteroids.get(i)) < minAngle) {
			i++;
		}
		
		int c = 1;
		double currentAngle = 0;
		while (!asteroids.isEmpty()) {
			
			// only asteroids with the same angle remaining
			if (onlySameAngle(asteroids, chosen, currentAngle)) {
				// System.out.println("now");
				break;
			}
			
			if (i > asteroids.size() - 1) {
				i = 0;
			}
			
			Point a = asteroids.get(i);
			
			double angle = getAngle(chosen, a);
			if (angle == currentAngle) {
				i++;
				continue;
			}
			
			currentAngle = angle;
			
			vaporized.add(a);
			// System.out.println("#" + c + " " + a);
			asteroids.remove(a);
			
			c++;
		}
		
		// clear the rest
		for (Point a : asteroids) {
			vaporized.add(a);
			// System.out.println("#" + c + " " + a);
			c++;
		}
		
		Point p = vaporized.get(199);
		
		System.out.println("Part 2: " + (p.x * 100 + p.y));
	}
	
	private static boolean onlySameAngle(List<Point> asteroids, Point center, double currentAngle) {
		for (Point a : asteroids) {
			if (getAngle(center, a) != currentAngle) {
				return false;
			}
		}
		return true;
	}

	private static double getAngle(Point pair, Point other) {
		return Math.atan2(other.y - pair.y, other.x - pair.x);
	}

	private static double getDist(Point pair, Point other) {
		return Math.sqrt((other.x - pair.x) * (other.x - pair.x) + (other.y - pair.y) * (other.y - pair.y));
	}

	private static List<Point> getAsteroidsFromGrid(List<String> lines) {
		List<Point> asteroids = new ArrayList<>();

		for (int y = 0; y < lines.size(); y++) {

			String line = lines.get(y);

			for (int x = 0; x < line.length(); x++) {

				char c = line.charAt(x);
				if (c == '#') {
					asteroids.add(new Point(x, y));
				}

			}

		}

		return asteroids;
	}

	private static void debugPrint(List<String> lines, Point curr, List<Point> whatCurrentCanSee) {
		int ym = lines.size();
		int xm = lines.get(0).length();

		char[][] grid = new char[ym][];
		for (int y = 0; y < ym; y++) {
			grid[y] = new char[xm];
			for (int x = 0; x < xm; x++) {
				grid[y][x] = '.';
			}
		}

		grid[curr.y][curr.x] = 'X';
		for (Point p : whatCurrentCanSee) {
			grid[p.y][p.x] = 'O';
		}

		StringUtils.print2Darray(grid);
	}
	
}
