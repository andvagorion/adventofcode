package net.stefangaertner.aoc20;

import java.util.Arrays;
import java.util.List;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.Point2D;

public class Day03 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/003");
		char[][] grid = fromLines(lines);

		return ride(grid, Point2D.of(3, 1));
	}

	static long part2() {
		List<String> lines = AOC.read("aoc20/003");
		char[][] grid = fromLines(lines);

		List<Point2D> slopes = Arrays.asList(Point2D.of(1, 1), Point2D.of(3, 1), Point2D.of(5, 1), Point2D.of(7, 1),
				Point2D.of(1, 2));

		return slopes.stream().map(slope -> ride(grid, slope)).reduce(1L, (a, b) -> a * b);
	}

	private static long ride(char[][] grid, Point2D slope) {
		Point2D pos = Point2D.of(0, 0);
		long trees = 0;

		while (pos.y < grid.length) {
			if (grid[pos.y][pos.x] == '#') {
				trees++;
			}

			pos.x = (pos.x + slope.x) % grid[pos.y].length;
			pos.y = pos.y + slope.y;
		}

		return trees;
	}

	private static char[][] fromLines(List<String> lines) {
		int w = lines.get(0).length();
		int h = lines.size();

		char[][] grid = new char[h][];
		for (int y = 0; y < h; y++) {
			grid[y] = new char[w];
			for (int x = 0; x < w; x++) {
				grid[y][x] = lines.get(y).charAt(x);
			}
		}

		return grid;
	}

}
