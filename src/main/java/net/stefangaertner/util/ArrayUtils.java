package net.stefangaertner.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.stefangaertner.aoc18.pojo.Point;

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

	public static boolean matchesCross(char[][] grid, Point center, char c) {

		if (grid[center.y][center.x] != c) {
			return false;
		}

		List<Point> pairs = new ArrayList<>();
		pairs.add(Point.of(-1, 0));
		pairs.add(Point.of(1, 0));
		pairs.add(Point.of(0, -1));
		pairs.add(Point.of(0, 1));

		for (Point pair : pairs) {
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

	public static List<Point> findNeighbors(char[][] grid, Point center, char c) {

		List<Point> pairs = getNeighboringPairs(center);

		List<Point> neighbors = new ArrayList<>();

		for (Point pair : pairs) {
			try {
				if (grid[pair.y][pair.x] == c) {
					neighbors.add(pair);
				}
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}

		return neighbors;
	}

	public static int countNeighbors(boolean[][] grid, Point center, Function<Boolean, Boolean> mapper) {
		List<Point> pairs = getNeighboringPairs(center);

		return (int) pairs.stream().filter(p -> {
			try {
				return mapper.apply(grid[p.y][p.x]);
			} catch (IndexOutOfBoundsException e) {
				return false;
			}
		}).count();

	}

	private static List<Point> getNeighboringPairs(Point center) {

		List<Point> pairs = new ArrayList<>();
		pairs.add(Point.of(-1, 0));
		pairs.add(Point.of(1, 0));
		pairs.add(Point.of(0, -1));
		pairs.add(Point.of(0, 1));

		return pairs.stream().map(p -> Point.of(p.x + center.x, p.y + center.y)).collect(Collectors.toList());
	}

	public static <T> void print2Darray(T[] arr) {
		if (arr == null)
			return;

		System.out.println(Arrays.stream(arr).map(String::valueOf).collect(Collectors.joining(", ")));
	}

	public static void print(int[] arr) {
		if (arr == null)
			return;

		System.out.println(Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining(", ")));
	}

}
