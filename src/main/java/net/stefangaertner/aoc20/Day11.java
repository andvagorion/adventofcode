package net.stefangaertner.aoc20;

import java.util.List;

import net.stefangaertner.aoc18.pojo.Point;
import net.stefangaertner.util.Advent;
import net.stefangaertner.util.ArrayUtils;
import net.stefangaertner.util.FileUtils;
import net.stefangaertner.util.GridUtils;

public class Day11 {

	private static final char EMPTY = 'L';
	private static final char OCCUPIED = '#';

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = FileUtils.read("aoc20/011");
		char[][] grid = GridUtils.toGrid(lines);

		int cycles = 0;
		while (cycles < 1000) {
			char[][] copy = update(grid);

			if (GridUtils.same(grid, copy)) {
				break;
			}

			grid = copy;
			cycles++;
		}

		return GridUtils.countValues(grid, OCCUPIED);
	}

	static long part2() {
		List<String> lines = FileUtils.read("aoc20/011");
		char[][] grid = GridUtils.toGrid(lines);

		int cycles = 0;
		while (cycles < 1000) {
			char[][] copy = update2(grid);

			if (GridUtils.same(grid, copy)) {
				break;
			}

			grid = copy;
			cycles++;
		}

		return GridUtils.countValues(grid, OCCUPIED);
	}

	private static char[][] update(char[][] grid) {
		char[][] copy = GridUtils.clone(grid);

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				Point pos = Point.of(x, y);
				List<Point> occupied = ArrayUtils.findNeighborsDiagonal(grid, pos, OCCUPIED);

				if (grid[y][x] == EMPTY && occupied.isEmpty()) {
					copy[y][x] = OCCUPIED;
				} else if (grid[y][x] == OCCUPIED && occupied.size() >= 4) {
					copy[y][x] = EMPTY;
				}
			}
		}

		return copy;
	}

	private static char[][] update2(char[][] grid) {
		char[][] copy = GridUtils.clone(grid);

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				Point pos = Point.of(x, y);
				int occupied = ArrayUtils.countLineOfSight(grid, pos, true, OCCUPIED, EMPTY);

				if (grid[y][x] == EMPTY && occupied == 0) {
					copy[y][x] = OCCUPIED;
				} else if (grid[y][x] == OCCUPIED && occupied >= 5) {
					copy[y][x] = EMPTY;
				}
			}
		}

		return copy;
	}

}
