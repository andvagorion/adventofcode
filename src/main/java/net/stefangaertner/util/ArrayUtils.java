package net.stefangaertner.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import net.stefangaertner.aoc18.pojo.Pair;

public class ArrayUtils {

	public static int[] combine(int[] a, int[] b) {
		int length = a.length + b.length;
		int[] result = new int[length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	public static long[] combine(long[] a, long[] b) {
		int length = a.length + b.length;
		long[] result = new long[length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	public static char[][] fromStrings(String[] lines) {
		int width = Arrays.stream(lines).max(Comparator.comparing(String::length)).get().length();

		int height = lines.length;

		char[][] grid = new char[height][];

		for (int y = 0; y < height; y++) {
			String line = lines[y];
			grid[y] = new char[width];
			for (int x = 0; x < width; x++) {
				char c = x < line.length() ? line.charAt(x) : ' ';
				grid[y][x] = c;
			}
		}

		return grid;
	}

	public static boolean matchesCross(char[][] grid, Pair center, char c) {

		if (grid[center.y][center.x] != c) {
			return false;
		}

		List<Pair> pairs = new ArrayList<>();
		pairs.add(Pair.of(-1, 0));
		pairs.add(Pair.of(1, 0));
		pairs.add(Pair.of(0, -1));
		pairs.add(Pair.of(0, 1));

		for (Pair pair : pairs) {
			try {
				if (grid[center.y + pair.y][center.x + pair.x] != c) {
					return false;
				}
			} catch (IndexOutOfBoundsException e) {
				return false;
			}
		}

		return true;

	}
	
	public static List<Pair> findNeighbors(char[][] grid, Pair center, char c) {
		
		List<Pair> pairs = new ArrayList<>();
		pairs.add(Pair.of(-1, 0));
		pairs.add(Pair.of(1, 0));
		pairs.add(Pair.of(0, -1));
		pairs.add(Pair.of(0, 1));

		List<Pair> neighbors = new ArrayList<>();
		
		for (Pair pair : pairs) {
			Pair test = Pair.of(center.x + pair.x, center.y + pair.y);
			try {
				if (grid[test.y][test.x] == c) {
					neighbors.add(test);
				}
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}

		return neighbors;
	}

}
