package net.stefangaertner.aoc19;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.aoc19.util.Point2D;
import net.stefangaertner.aoc19.util.StringUtils;

public class Day11 {

	public static void main(String[] strings) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		String code = Advent.readLine("aoc19/011-data");

		Map<Point2D, String> visited = runRobot(code, "0", false);

		return visited.size();
	}

	static String part2() {
		String code = Advent.readLine("aoc19/011-data");

		boolean debugPrint = false;

		Map<Point2D, String> visited = runRobot(code, "1", debugPrint);

		if (debugPrint) {
			System.out.println("Visited: " + visited.size());
		}

		Point2D min = Point2D.of(0, 0);
		Point2D max = Point2D.of(0, 0);

		for (Point2D p : visited.keySet()) {
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

		int offsetY = Math.abs(min.y);
		int offsetX = Math.abs(min.x);
		int sizeY = offsetY + max.y + 1;
		int sizeX = offsetX + max.x + 1;

		char[][] image = new char[sizeY][];
		for (int y = 0; y < sizeY; y++) {
			image[y] = new char[sizeX];
			for (int x = 0; x < sizeX; x++) {
				image[y][x] = '.';
			}
		}

		image[offsetY][offsetX] = '-';

		if (debugPrint) {
			StringUtils.print2Darray(image);
		}

		for (Entry<Point2D, String> e : visited.entrySet()) {
			Point2D p = e.getKey();
			if (e.getValue().equals("1")) {
				image[p.y + offsetY][p.x + offsetX] = 'X';
			}
		}

		StringUtils.print2Darray(image);

		return "FKEKCFRK";
	}

	private static Map<Point2D, String> runRobot(String code, String initialPanelColor, boolean debugPrint) {

		Point2D dir = Point2D.of(0, -1);
		Point2D pos = Point2D.of(0, 0);

		Map<Point2D, String> visited = new HashMap<>();
		visited.put(pos, initialPanelColor);

		Parser p = Parser.create(code).stopOnOutput();

		int c = 0;

		while (!p.isFinished()) {
			c++;

			if (debugPrint && c % 1000 == 0) {
				System.out.println(c + " moves.");
			}

			String tileCol = !visited.containsKey(pos) ? "0" : visited.get(pos);
			p.input(tileCol);

			p.run();
			String color = p.getLastOutput();
			visited.put(pos, color);

			p.run();
			String move = p.getLastOutput();

			if ("0".equals(move)) {
				// turn left

				if (Point2D.of(0, -1).equals(dir)) {
					dir = Point2D.of(-1, 0);
				} else if (Point2D.of(-1, 0).equals(dir)) {
					dir = Point2D.of(0, 1);
				} else if (Point2D.of(0, 1).equals(dir)) {
					dir = Point2D.of(1, 0);
				} else if (Point2D.of(1, 0).equals(dir)) {
					dir = Point2D.of(0, -1);
				}

			} else {
				// turn right

				if (Point2D.of(0, -1).equals(dir)) {
					dir = Point2D.of(1, 0);
				} else if (Point2D.of(1, 0).equals(dir)) {
					dir = Point2D.of(0, 1);
				} else if (Point2D.of(0, 1).equals(dir)) {
					dir = Point2D.of(-1, 0);
				} else if (Point2D.of(-1, 0).equals(dir)) {
					dir = Point2D.of(0, -1);
				}

			}

			// move robot
			pos = Point2D.of(pos.x + dir.x, pos.y + dir.y);

		}

		if (debugPrint) {
			String out = visited.keySet().stream().map(t -> "[" + t.x + ", " + t.y + "]")
					.collect(Collectors.joining(", "));
			System.out.println(out);
		}

		return visited;
	}

}
