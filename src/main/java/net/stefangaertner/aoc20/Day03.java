package net.stefangaertner.aoc20;

import java.util.Arrays;
import java.util.List;

import net.stefangaertner.aoc18.pojo.Pair;
import net.stefangaertner.util.FileUtils;

public class Day03 {

	public static void main(String[] args) {
		System.out.println("Part 1: " + part1());
		System.out.println("Part 2: " + part2());
	}

	private static long part1() {
		List<String> lines = FileUtils.read("aoc20/003");
		char[][] grid = fromLines(lines);

		return ride(grid, Pair.of(3, 1));
	}

	private static long part2() {
		List<String> lines = FileUtils.read("aoc20/003");
		char[][] grid = fromLines(lines);

		List<Pair> slopes = Arrays.asList(Pair.of(1, 1), Pair.of(3, 1), Pair.of(5, 1), Pair.of(7, 1), Pair.of(1, 2));

		return slopes.stream().map(slope -> ride(grid, slope)).reduce(1L, (a, b) -> a * b);
	}

	private static long ride(char[][] grid, Pair slope) {
		Pair pos = Pair.of(0, 0);
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
