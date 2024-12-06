package net.stefangaertner.aoc18;

import net.stefangaertner.aoc18.util.Advent;

public class Day11 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static String part1() {
		int serial = 7139;

		int[][] grid = computeGrid(300, 300, serial);

		int chosenX = -1;
		int chosenY = -1;
		int max = Integer.MIN_VALUE;

		for (int x = 0; x < 300 - 3; x++) {
			for (int y = 0; y < 300 - 3; y++) {

				int local = computeLocal(grid, x, y, 3);
				if (local > max) {
					chosenX = x;
					chosenY = y;
					max = local;
				}

			}
		}

		return String.format("%s,%s", chosenX, chosenY);
	}

	static String part2() {
		int serial = 7139;
		int maxCycles = 10;

		int[][] grid = computeGrid(300, 300, serial);

		int chosenS = -1;
		int chosenX = -1;
		int chosenY = -1;

		int prevMax = Integer.MIN_VALUE;
		int max = Integer.MIN_VALUE;

		int notChanged = 0;

		for (int s = 1; s < 300; s++) {

			if (prevMax == max) {
				notChanged++;
			} else {
				notChanged = 0;
				prevMax = max;
			}

			for (int x = 0; x < 300 - s; x++) {
				for (int y = 0; y < 300 - s; y++) {

					int local = computeLocal(grid, x, y, s);

					if (local > max) {
						chosenS = s;
						chosenX = x;
						chosenY = y;
						max = local;
					}

				}
			}

			if (notChanged > maxCycles) {
				break;
			}

			// System.out.println(
			// "Checked " + s + ", current max size is " + max + " => " + chosenX + "," +
			// chosenY + "," + chosenS);
		}

		// this will eventually terminate, but takes forever.
		// luckily the correct solution has a width of 16x16
		// so this can just be aborted after a while...

		// System.out.println("#2 max size is " + max + " => " + chosenX + "," + chosenY
		// + "," + chosenS);

		return String.format("%s,%s,%s", chosenX, chosenY, chosenS);
	}

	private static int[][] computeGrid(int width, int height, int serial) {

		int[][] grid = new int[300][];

		for (int y = 0; y < 300; y++) {
			grid[y] = new int[300];
			for (int x = 0; x < 300; x++) {
				int level = computeLevel(x, y, serial);
				grid[y][x] = level;
			}
		}

		return grid;
	}

	private static int computeLevel(int x, int y, int serial) {

		int rackId = x + 10;
		int level = rackId * y;
		level += serial;
		level *= rackId;

		if (level > 100) {

			level %= 1000;
			level -= level % 100;
			level /= 100;

		} else {
			level = 0;
		}

		return level - 5;
	}

	private static int computeLocal(int[][] grid, int x, int y, int size) {
		int sum = 0;

		for (int x0 = x; x0 < x + size; x0++) {
			for (int y0 = y; y0 < y + size; y0++) {
				sum += grid[y0][x0];
			}
		}

		return sum;
	}

}
