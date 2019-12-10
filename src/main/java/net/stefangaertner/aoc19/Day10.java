package net.stefangaertner.aoc19;

import java.util.ArrayList;
import java.util.List;

import net.stefangaertner.aoc18.pojo.Pair;
import net.stefangaertner.util.FileUtils;
import net.stefangaertner.util.StringUtils;

public class Day10 {

	static Pair chosenPart2 = new Pair(27, 19);

	public static void main(String[] strings) {

		List<String> lines = FileUtils.read("aoc19/010-data");

//		Pair center = new Pair(10, 10);
//		System.out.println(getAngle(center, new Pair(0, 9)));
//		System.out.println(getAngle(center, new Pair(5, 5)));
//		System.out.println(getAngle(center, new Pair(10, 5)));
//		System.out.println(getAngle(center, new Pair(15, 5)));
//		System.out.println(getAngle(center, new Pair(15, 10)));
//		System.out.println(getAngle(center, new Pair(15, 15)));
//		System.out.println(getAngle(center, new Pair(10, 15)));
//		System.out.println(getAngle(center, new Pair(5, 15)));

		part2(lines);
	}
	
	private static void part2b(List<String> lines) {
		
		List<Pair> asteroids = getAsteroidsFromGrid(lines);
		
		// sort by angle, sort by distance ???
		
	}

	private static void part2(List<String> lines) {

		List<Pair> asteroids = getAsteroidsFromGrid(lines);

		Pair chosen = new Pair(11, 13);

		asteroids.remove(chosen);

		List<Pair> vaporized = new ArrayList<>();

		int boomCount = 0;
		int count = 0;

		double currentAngle = -90.001;
		while (!asteroids.isEmpty() && count < 999) {
			count++;
			
			Pair next = getNextTarget(asteroids, chosen, currentAngle);

			if (next == null) {
				currentAngle = -90.001;
				continue;
			}

			boomCount++;
			asteroids.remove(next);
			vaporized.add(next);

			currentAngle = getAngle(chosen, next);

			System.out.println("#" + boomCount + " vaporized " + next + " at " + currentAngle);
			
		}
		
		// not 10,21 or 

		System.out.println(vaporized.get(199));

	}

	private static int c = 0;

	private static Pair getNextTarget(List<Pair> asteroids, Pair chosen, double currentAngle) {

		Pair target = null;
		double minAngle = Double.MAX_VALUE;
		double minDist = Double.MAX_VALUE;

		for (int i = 0; i < asteroids.size(); i++) {
			Pair other = asteroids.get(i);
			double otherAngle = getAngle(chosen, other);

			if (otherAngle <= currentAngle) {
				continue;
			}

			double angleDist = otherAngle - currentAngle;
			double otherDist = getDist(chosen, other);

			// #64 vaporized 19, 12 at -7.125016348901798
			// currentAngle is -8.130102354155978

			// suppose: next is 0.0

			if (asteroids.size() == 111107) {
				System.out.println(other);
				System.out.println(otherAngle + ", " + currentAngle);
				System.out.println(angleDist);
				System.out.println(otherDist);
				if (angleDist <= minAngle && otherDist < minDist) {
					System.out.println("setting " + other);
				}
			}

			if (angleDist < minAngle || (angleDist == minAngle && otherDist < minDist)) {
				target = other;
				minAngle = angleDist;
				minDist = otherDist;
			}
		}

		c++;

		return target;
	}

	private static double getAngle(Pair pair, Pair other) {
		double angle = Math.toDegrees(Math.atan2(other.y - pair.y, other.x - pair.x));
		if (angle < -90) {
			angle = 360 + angle;
		}
		
		return angle;
	}

	private static double getDist(Pair pair, Pair other) {
		return Math.sqrt((other.x - pair.x) * (other.x - pair.x) + (other.y - pair.y) * (other.y - pair.y));
	}

	private static void part1(List<String> lines) {

		List<Pair> asteroids = getAsteroidsFromGrid(lines);

		Pair best = null;
		int max = 0;

		for (int i = 0; i < asteroids.size(); i++) {

			Pair curr = asteroids.get(i);

			List<Pair> whatCurrentCanSee = new ArrayList<>();
			int canSee = 0;

			inner: for (int j = 0; j < asteroids.size(); j++) {

				if (i == j) {
					continue;
				}

				Pair other = asteroids.get(j);

				double angle1 = Math.atan2(other.y - curr.y, other.x - curr.x) * 180 / Math.PI;
				double dist1 = Math
						.sqrt((other.x - curr.x) * (other.x - curr.x) + (other.y - curr.y) * (other.y - curr.y));

				for (int k = 0; k < asteroids.size(); k++) {
					if (i == k || k == j) {
						continue;
					}
					Pair test = asteroids.get(k);

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

		System.out.println(best);
		System.out.println(max);

	}

	private static List<Pair> getAsteroidsFromGrid(List<String> lines) {
		List<Pair> asteroids = new ArrayList<>();

		for (int y = 0; y < lines.size(); y++) {

			String line = lines.get(y);

			for (int x = 0; x < line.length(); x++) {

				char c = line.charAt(x);
				if (c == '#') {
					asteroids.add(new Pair(x, y));
				}

			}

		}

		return asteroids;
	}

	private static void debugPrint(List<String> lines, Pair curr, List<Pair> whatCurrentCanSee) {
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
		for (Pair p : whatCurrentCanSee) {
			grid[p.y][p.x] = 'O';
		}

		StringUtils.print2Darray(grid);
	}

	public static void part2(String code) {

		System.out.println("Part 2: ");
	}

}
