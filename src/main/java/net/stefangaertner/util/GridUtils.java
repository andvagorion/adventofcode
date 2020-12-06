package net.stefangaertner.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.stefangaertner.aoc18.pojo.Pair;

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

	public static char[][] clone(char[][] grid) {
		char[][] copy = new char[grid.length][];
		for (int y = 0; y < grid.length; y++) {
			copy[y] = new char[grid[y].length];
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

	public static Pair find(char[][] grid, char toFind) {
		for (int y = 0; y < grid.length; y++) {
			char[] row = grid[y];
			for (int x = 0; x < row.length; x++) {
				char c = grid[y][x];
				if (c == toFind) {
					return Pair.of(x, y);
				}
			}
		}
		return null;
	}

	public static List<Pair> getNeighboringPairs(Pair center) {

		List<Pair> pairs = new ArrayList<>();
		pairs.add(Pair.of(-1, 0));
		pairs.add(Pair.of(1, 0));
		pairs.add(Pair.of(0, -1));
		pairs.add(Pair.of(0, 1));

		return pairs.stream().map(p -> Pair.of(p.x + center.x, p.y + center.y)).collect(Collectors.toList());
	}

	public static List<Pair> findNeighbors(char[][] grid, Pair center, Predicate<Character> predicate) {

		List<Pair> pairs = getNeighboringPairs(center);

		List<Pair> neighbors = new ArrayList<>();

		for (Pair pair : pairs) {
			try {
				char c = grid[pair.y][pair.x];
				if (predicate.test(c)) {
					neighbors.add(pair);
				}
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}

		return neighbors;
	}

	public static Map<Pair, Integer> findReachable(char[][] grid, Pair coord, Predicate<Character> walkable,
			Predicate<Character> unwalkable, Predicate<Character> target) {

		Set<Pair> visited = new HashSet<>();
		Map<Pair, Integer> reachable = new HashMap<>();

		findReachable(grid, coord, walkable, unwalkable, target, visited, reachable, 0);

		return reachable;
	}

	private static void findReachable(char[][] grid, Pair coord, Predicate<Character> walkable,
			Predicate<Character> unwalkable, Predicate<Character> target, Set<Pair> visited, Map<Pair, Integer> found,
			int tiles) {

		visited.add(coord);

		List<Pair> neighbors = findNeighbors(grid, coord, unwalkable.negate());

		for (Pair neighbor : neighbors) {

			if (visited.contains(neighbor)) {
				continue;
			}

			char c = grid[neighbor.y][neighbor.x];

			if (target.test(c)) {
				found.put(neighbor, tiles + 1);
			}

			if (walkable.test(c)) {
				findReachable(grid, neighbor, walkable, unwalkable, target, visited, found, tiles + 1);
			}
		}

	}

	public static void change(char[][] newGrid, char c, char d) {
		Pair p = GridUtils.find(newGrid, c);
		newGrid[p.y][p.x] = d;
	}
}
