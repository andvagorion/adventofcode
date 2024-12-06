package net.stefangaertner.aoc19.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

	public static boolean matchesCross(char[][] grid, Point2D center, char c) {

		if (grid[center.y][center.x] != c) {
			return false;
		}

		List<Point2D> pairs = new ArrayList<>();
		pairs.add(Point2D.of(-1, 0));
		pairs.add(Point2D.of(1, 0));
		pairs.add(Point2D.of(0, -1));
		pairs.add(Point2D.of(0, 1));

		for (Point2D pair : pairs) {
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

	public static List<Point2D> findNeighbors(char[][] grid, Point2D center, char c) {
		return findNeighbors(grid, center, false, c);
	}

	public static List<Point2D> findNeighborsDiagonal(char[][] grid, Point2D center, char c) {
		return findNeighbors(grid, center, true, c);
	}

	private static List<Point2D> findNeighbors(char[][] grid, Point2D center, boolean diagonal, char c) {

		List<Point2D> pairs = getNeighboringPairs(center, diagonal);

		List<Point2D> neighbors = new ArrayList<>();

		for (Point2D pair : pairs) {
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

	public static int countNeighbors(boolean[][] grid, Point2D center, Function<Boolean, Boolean> mapper) {
		return countNeighbors(grid, center, false, mapper);
	}

	public static int countNeighborsDiagonal(boolean[][] grid, Point2D center, Function<Boolean, Boolean> mapper) {
		return countNeighbors(grid, center, true, mapper);
	}

	private static int countNeighbors(boolean[][] grid, Point2D center, boolean diagonal,
			Function<Boolean, Boolean> mapper) {
		List<Point2D> pairs = getNeighboringPairs(center, diagonal);

		return (int) pairs.stream().filter(p -> {
			try {
				return mapper.apply(grid[p.y][p.x]);
			} catch (IndexOutOfBoundsException e) {
				return false;
			}
		}).count();
	}

	public static List<Point2D> getDirections(Point2D center, boolean diagonal) {
		List<Point2D> pairs = new ArrayList<>();

		pairs.add(Point2D.of(-1, 0));
		pairs.add(Point2D.of(1, 0));
		pairs.add(Point2D.of(0, -1));
		pairs.add(Point2D.of(0, 1));

		if (diagonal) {
			pairs.add(Point2D.of(-1, -1));
			pairs.add(Point2D.of(-1, 1));
			pairs.add(Point2D.of(1, -1));
			pairs.add(Point2D.of(1, 1));
		}

		return pairs;
	}

	private static List<Point2D> getNeighboringPairs(Point2D center, boolean diagonal) {
		List<Point2D> pairs = getDirections(center, diagonal);
		return pairs.stream().map(p -> Point2D.of(p.x + center.x, p.y + center.y)).collect(Collectors.toList());
	}

	public static <T> void print2Darray(T[] arr) {
		if (arr == null) {
			return;
		}

		System.out.println(Arrays.stream(arr).map(String::valueOf).collect(Collectors.joining(", ")));
	}

	public static void print(int[] arr) {
		if (arr == null) {
			return;
		}

		System.out.println(Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining(", ")));
	}

	public static int countLineOfSight(char[][] grid, Point2D center, boolean diagonal, char c, char not) {
		List<Point2D> directions = getDirections(center, diagonal);

		int sum = 0;

		for (Point2D dir : directions) {
			Point2D curr = center.add(dir);

			while (curr.y < grid.length && curr.y >= 0 && curr.x < grid[0].length && curr.x >= 0) {
				if (grid[curr.y][curr.x] == not) {
					break;
				}

				if (grid[curr.y][curr.x] == c) {
					sum++;
					break;
				}

				curr = curr.add(dir);
			}
		}

		return sum;
	}

}
