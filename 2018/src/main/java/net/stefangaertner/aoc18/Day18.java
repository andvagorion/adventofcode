package net.stefangaertner.aoc18;

import java.util.List;

import net.stefangaertner.aoc18.util.Advent;

public class Day18 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = Advent.read("aoc18/018-data");
		char[][] area = new char[lines.size()][];

		for (int i = 0; i < lines.size(); i++) {
			area[i] = lines.get(i)
					.toCharArray();
		}

		// StringUtils.print2Darray(area);

		for (long i = 0; i < 10; i++) {
			area = magic(area);
		}

		// StringUtils.print2Darray(area);

		return val(area);
	}

	static long part2() {
		List<String> lines = Advent.read("aoc18/018-data");
		// goal: 1000000000

		char[][] area = new char[lines.size()][];

		for (int i = 0; i < lines.size(); i++) {
			area[i] = lines.get(i)
					.toCharArray();
		}

		// cycle will repeat every 28 steps
		// 10000 => 196350

		int[] vals = new int[28];
		long midpoint = 10000;

		for (long i = 0; i < midpoint + 28; i++) {
			area = magic(area);
			if (i >= midpoint) {
				vals[(int) (i % midpoint)] = val(area);
			}
		}

		long goal = 1000000000;
		long offset = goal - midpoint - 1;
		int idx = (int) (offset %= 28);

		return vals[idx];
	}

	private static int val(char[][] area) {
		int trees = 0;
		int yards = 0;

		for (int y = 0; y < area.length; y++) {
			char[] row = area[y];
			for (int x = 0; x < row.length; x++) {
				char c = row[x];
				if (c == '|')
					trees++;
				else if (c == '#')
					yards++;
			}
		}

		return trees * yards;
	}

	private static char[][] magic(char[][] arr) {

		char[][] ret = new char[arr.length][arr[0].length];

		for (int y = 0; y < arr.length; y++) {
			char[] row = arr[y];
			for (int x = 0; x < row.length; x++) {

				char c = row[x];

				// . = open
				// | = trees
				// # = lumberyard

				if (c == '.') {

					// three or more adjacent are trees => becomes tree
					int sum = checkAdjacent(arr, x, y, '|');
					if (sum >= 3) {
						ret[y][x] = '|';
					} else {
						ret[y][x] = '.';
					}

				} else if (c == '|') {

					// three or more adjacent lumberyards => becomes lumberyard
					int sum = checkAdjacent(arr, x, y, '#');
					if (sum >= 3) {
						ret[y][x] = '#';
					} else {
						ret[y][x] = '|';
					}

				} else if (c == '#') {

					int yards = checkAdjacent(arr, x, y, '#');
					int trees = checkAdjacent(arr, x, y, '|');

					if (yards >= 1 && trees >= 1) {
						ret[y][x] = '#';
					} else {
						ret[y][x] = '.';
					}

				}

			}
		}

		return ret;
	}

	private static int checkAdjacent(char[][] arr, int x, int y, char c) {
		int sum = 0;

		// x..
		// ...
		// ...
		if (y > 0 && x > 0 && arr[y - 1][x - 1] == c) {
			sum++;
		}
		// .x.
		// ...
		// ...
		if (y > 0 && arr[y - 1][x] == c) {
			sum++;
		}
		// ..x
		// ...
		// ...
		if (y > 0 && x < arr[y].length - 1 && arr[y - 1][x + 1] == c) {
			sum++;
		}

		// ...
		// x..
		// ...
		if (x > 0 && arr[y][x - 1] == c) {
			sum++;
		}
		// ...
		// ..x
		// ...
		if (x < arr[y].length - 1 && arr[y][x + 1] == c) {
			sum++;
		}

		// ...
		// ...
		// x..
		if (y < arr.length - 1 && x > 0 && arr[y + 1][x - 1] == c) {
			sum++;
		}
		// ...
		// ...
		// .x.
		if (y < arr.length - 1 && arr[y + 1][x] == c) {
			sum++;
		}
		// ...
		// ...
		// ..x
		if (y < arr.length - 1 && x < arr[y].length - 1 && arr[y + 1][x + 1] == c) {
			sum++;
		}

		return sum;
	}
}
