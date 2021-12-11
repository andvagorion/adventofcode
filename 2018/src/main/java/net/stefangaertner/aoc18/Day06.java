package net.stefangaertner.aoc18;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.stefangaertner.aoc18.pojo.Coordinate;
import net.stefangaertner.aoc18.util.Advent;

public class Day06 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = Advent.read("aoc18/006-data");
		List<Coordinate> coords = getCoordinates(lines);
		int[][] grid = getGrid(coords);

		for (Coordinate c : coords) {
			grid[c.y][c.x] = c.id;
		}

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if (grid[y][x] > 0)
					continue;
				int id = checkMinDistance(x, y, coords);
				grid[y][x] = id;
			}
		}

		// printGrid(grid);

		// discard all IDs that are at least once on one edge
		Set<Integer> discarded = new HashSet<>();
		for (int y = 0; y < grid.length; y++) {
			int idl = grid[y][0];
			discarded.add(idl);
			int idr = grid[y][grid[y].length - 1];
			discarded.add(idr);

			if (y == 0 || y == grid.length - 1) {
				for (int x = 0; x < grid[y].length; x++) {
					discarded.add(grid[y][x]);
				}

			}
		}

		Map<Integer, Integer> sum = new HashMap<>();

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				int id = grid[y][x];
				if (!sum.containsKey(id)) {
					sum.put(id, 0);
				}
				int newSum = sum.get(id) + 1;
				sum.put(id, newSum);
			}
		}

		int maxSize = 0;
		for (Entry<Integer, Integer> e : sum.entrySet()) {
			if (!discarded.contains(e.getKey())) {
				int v = e.getValue();
				if (v > maxSize) {
					maxSize = v;
				}
			}
		}

		return maxSize;
	}

	static long part2() {
		List<String> lines = Advent.read("aoc18/006-data");
		List<Coordinate> coords = getCoordinates(lines);
		int[][] grid = getGrid(coords);

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				int sum = 0;
				for (Coordinate c : coords) {
					int dist = checkDistance(x, y, c);
					sum += dist;
				}
				grid[y][x] = sum;
			}
		}

		int sum = 0;

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if (grid[y][x] < 10000) {
					sum++;
				}
			}
		}

		return sum;
	}

	private static List<Coordinate> getCoordinates(List<String> lines) {
		List<Coordinate> coords = new ArrayList<>();

		int i = 1;
		for (String line : lines) {

			String[] s = line.split(", ");
			int x = Integer.parseInt(s[0]);
			int y = Integer.parseInt(s[1]);

			Coordinate c = new Coordinate(i, x, y);
			coords.add(c);
			i++;
		}

		return coords;
	}

	private static int[][] getGrid(List<Coordinate> coords) {

		int maxX = 0;
		int maxY = 0;
		for (Coordinate c : coords) {
			if (c.x > maxX) {
				maxX = c.x;
			}
			if (c.y > maxY) {
				maxY = c.y;
			}
		}

		return new int[maxY + 1][maxX + 1];
	}

	private static int checkDistance(int x, int y, Coordinate c) {
		int dist = Math.abs(c.x - x) + Math.abs(c.y - y);
		return dist;
	}

	private static int checkMinDistance(int x, int y, List<Coordinate> coords) {
		int chosen = -1;
		int minDist = 100000;
		for (Coordinate c : coords) {
			int dist = Math.abs(c.x - x) + Math.abs(c.y - y);
			if (dist < minDist) {
				minDist = dist;
				chosen = c.id;
			}
		}

		int same = 0;
		for (Coordinate c : coords) {
			int dist = Math.abs(c.x - x) + Math.abs(c.y - y);
			if (dist == minDist) {
				same++;
			}
		}

		if (same > 1) {
			return -1;
		}
		return chosen;
	}

	@SuppressWarnings("unused")
	private static void printGrid(int[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			String s = "[";
			for (int x = 0; x < grid[0].length; x++) {
				s += append(grid[y][x], 3);
			}
			s += "]";
			System.out.println(s);
		}
	}

	private static String append(int id, int len) {
		String idStr = String.valueOf(id);
		while (idStr.length() < len) {
			idStr = " " + idStr;
		}
		return idStr;
	}

}
