package net.stefangaertner.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class GridUtils {

	public static char[][] createGrid(int width, int height, char c) {
		char[][] grid = new char[height][];
		for (int y = 0; y < height; y++) {
			grid[y] = new char[width];
			for (int x = 0; x < width; x++) {
				grid[y][x] = c;
			}
		}
		return grid;
	}

	public static boolean[][] createGrid(int width, int height, boolean b) {
		boolean[][] grid = new boolean[height][];
		for (int y = 0; y < height; y++) {
			grid[y] = new boolean[width];
			for (int x = 0; x < width; x++) {
				grid[y][x] = false;
			}
		}
		return grid;
	}

	public static char[][] toGrid(List<String> lines) {

		char[][] grid = new char[lines.size()][];

		for (int y = 0; y < lines.size(); y++) {

			String line = lines.get(y);

			grid[y] = new char[line.length()];

			for (int x = 0; x < line.length(); x++) {

				grid[y][x] = line.charAt(x);

			}
		}

		return grid;
	}

	public static boolean[][] toGrid(List<String> lines, Function<Character, Boolean> mapper) {

		boolean[][] grid = new boolean[lines.size()][];

		for (int y = 0; y < lines.size(); y++) {

			String line = lines.get(y);

			grid[y] = new boolean[line.length()];

			for (int x = 0; x < line.length(); x++) {

				grid[y][x] = mapper.apply(line.charAt(x));

			}
		}

		return grid;

	}

	public static boolean[][] clone(boolean[][] grid) {
		boolean[][] copy = new boolean[grid.length][];
		for (int y = 0; y < grid.length; y++) {
			copy[y] = new boolean[grid[y].length];			
			System.arraycopy(grid[y], 0, copy[y], 0, grid[y].length);
		}
		return copy;
	}

	public static int countValues(boolean[][] grid, Predicate<Boolean> predicate) {
		int sum = 0;
		
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				if (predicate.test(grid[y][x])) {
					sum++;
				}
			}
		}
		
		return sum;
	}

}
