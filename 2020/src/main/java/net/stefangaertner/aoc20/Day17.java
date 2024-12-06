package net.stefangaertner.aoc20;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.Point;
import net.stefangaertner.aoc20.util.Point3D;
import net.stefangaertner.aoc20.util.Point4D;

public class Day17 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/017");
		Set<Point> active = new HashSet<>();

		for (int y = 0; y < lines.size(); y++) {
			String line = lines.get(y);
			for (int x = 0; x < line.length(); x++) {
				if (line.charAt(x) == '#') {
					active.add(Point3D.of(x, y, 0));
				}
			}
		}

		for (int i = 0; i < 6; i++) {
			active = simulate(active);
		}

		return active.size();
	}

	static long part2() {
		List<String> lines = AOC.read("aoc20/017");
		Set<Point> active = new HashSet<>();

		for (int y = 0; y < lines.size(); y++) {
			String line = lines.get(y);
			for (int x = 0; x < line.length(); x++) {
				if (line.charAt(x) == '#') {
					active.add(Point4D.of(x, y, 0, 0));
				}
			}
		}

		for (int i = 0; i < 6; i++) {
			active = simulate(active);
		}

		return active.size();
	}

	private static Set<Point> simulate(Set<Point> active) {
		Set<Point> out = new HashSet<>();

		for (Point point : active) {

			// check point itself
			int activeNeighbors = countActiveNeighbors(point, active);
			if (activeNeighbors == 2 || activeNeighbors == 3) {
				out.add(point);
			}

			for (Point neighbor : point.getNeighbors()) {
				// don't test active neighbors
				if (active.contains(neighbor)) {
					continue;
				}

				// test inactive neighbors only
				activeNeighbors = countActiveNeighbors(neighbor, active);
				// System.out.println(String.format("%s has %s
				// neighbors.", other, activeNeighbors));
				if (activeNeighbors == 3) {
					out.add(neighbor);
				}
			}

		}

		return out;
	}

	private static int countActiveNeighbors(Point point, Set<Point> active) {
		return (int) point.getNeighbors().stream().filter(p -> active.contains(p)).count();
	}

}
