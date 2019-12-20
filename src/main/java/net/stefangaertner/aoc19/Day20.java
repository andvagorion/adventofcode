package net.stefangaertner.aoc19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.stefangaertner.aoc18.pojo.Pair;
import net.stefangaertner.util.FileUtils;
import net.stefangaertner.util.StringUtils;

public class Day20 {

	public static void main(String[] strings) throws IOException {

		List<String> lines = FileUtils.read("aoc19/020-data");

		char[][] grid = toGrid(lines);

		Map<String, Pair> entries = parse(grid);

		entries.entrySet().stream().forEach(e -> System.out.println(e.getKey() + " => " + e.getValue()));
		System.out.println(" ");

		// find connected entries by flood fill
		Map<String, Map<String, Integer>> connections = findConnections(lines, entries);

		connections.entrySet().stream().forEach(e -> {
			System.out.println(e.getKey());
			System.out.println("=======");
			e.getValue().entrySet().stream().forEach(e2 -> {
				System.out.println(e2.getKey() + " => " + e2.getValue());
			});
		});
		System.out.println(" ");

		// find shortest path

		List<List<String>> paths = new ArrayList<>();
		findPaths(connections, 0, "AA", "", 0);

		paths.stream().forEach(p -> {
			System.out.println(p.stream().collect(Collectors.joining(" => ")));
		});

	}

	private static void findPaths(Map<String, Map<String, Integer>> connections, int count, String node, String s,
			int currentTiles) {

		if (s.contains(node)) {
			// loop
			return;
		}

		s += " => " + node + "(" + currentTiles + ", " + count + ")";

		if (node.equals("ZZ")) {
			System.out.println(s);
			System.out.println(count);
			return;
		}

		if (!node.equals("AA")) {

			// jump through portal
			String portal = getPortal(node);
			count++;
			node = portal;

			s += " => " + node + "(" + 1 + ")";
		}

		Map<String, Integer> links = connections.get(node);

		for (Map.Entry<String, Integer> link : links.entrySet()) {

			String other = link.getKey();
			Integer tiles = link.getValue();

			findPaths(connections, count + tiles, other, s, tiles);
		}
	}

	private static String getPortal(String str) {
		if (str.endsWith("2")) {
			return str.substring(0, str.length() - 1);
		} else {
			return str + "2";
		}
	}

	private static Map<String, Map<String, Integer>> findConnections(List<String> lines, Map<String, Pair> entries) {

		Map<String, Map<String, Integer>> connections = new HashMap<>();

		char c = '=';

		for (Map.Entry<String, Pair> entry : entries.entrySet()) {

			Pair val = entry.getValue();

			char[][] grid = toGrid(lines);
			grid[val.y][val.x] = c;

			Map<String, Integer> connected = new HashMap<>();

			// StringUtils.print2Darray(grid);

			int i = 0;
			while (true) {

				List<Pair> toBeFilled = new ArrayList<>();

				for (int y = 0; y < grid.length; y++) {
					char[] row = grid[y];
					for (int x = 0; x < row.length; x++) {

						if (grid[y][x] == c) {

							if (shouldFill(grid, y - 1, x, c)) {
								toBeFilled.add(Pair.of(x, y - 1));
							}

							if (shouldFill(grid, y, x - 1, c)) {
								toBeFilled.add(Pair.of(x - 1, y));
							}
							if (shouldFill(grid, y, x + 1, c)) {
								toBeFilled.add(Pair.of(x + 1, y));
							}
							if (shouldFill(grid, y + 1, x, c)) {
								toBeFilled.add(Pair.of(x, y + 1));
							}

						}

					}
				}

				// StringUtils.print2Darray(grid);

				if (!toBeFilled.isEmpty()) {
					i++;

					for (Pair p : toBeFilled) {
						grid[p.y][p.x] = c;
					}

					for (Map.Entry<String, Pair> e2 : entries.entrySet()) {
						if (toBeFilled.contains(e2.getValue())) {
							connected.put(e2.getKey(), i);
						}
					}

				}

				if (toBeFilled.isEmpty()) {
					break;
				}

				toBeFilled.clear();
			}

			connections.put(entry.getKey(), connected);

		}

		return connections;
	}

	private static boolean shouldFill(char[][] grid, int y, int x, char c) {
		if (grid[y][x] == '.') {
			return true;
		}
		return false;
	}

	private static void part1() {

	}

	private static char[][] toGrid(List<String> lines) {

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

	private static Map<String, Pair> parse(char[][] grid) {

		Map<String, Pair> entries = new HashMap<>();

		for (int y = 0; y < grid.length; y++) {

			char[] row = grid[y];

			for (int x = 0; x < row.length; x++) {

				if (isEntry(grid, x, y)) {

					String name = getEntryName(grid, x, y);
					if (entries.containsKey(name)) {
						name += "2";
					}
					entries.put(name, new Pair(x, y));
				}

			}

		}

		return entries;

	}

	private static String getEntryName(char[][] grid, int x, int y) {

		char name1 = '-';
		char name2 = '-';

		if (isTopExit(grid, x, y)) {
			name1 = grid[y - 2][x];
			name2 = grid[y - 1][x];
		} else if (isLeftExit(grid, x, y)) {
			name1 = grid[y][x - 2];
			name2 = grid[y][x - 1];
		} else if (isRightExit(grid, x, y)) {
			name1 = grid[y][x + 1];
			name2 = grid[y][x + 2];
		} else if (isBottomExit(grid, x, y)) {
			name1 = grid[y + 1][x];
			name2 = grid[y + 2][x];
		}

		return name1 + "" + name2;
	}

	private static boolean isEntry(char[][] grid, int x, int y) {
		boolean isPath = grid[y][x] == '.';
		return isPath && (isTopExit(grid, x, y) || isLeftExit(grid, x, y) || isRightExit(grid, x, y)
				|| isBottomExit(grid, x, y));
	}

	private static boolean isBottomExit(char[][] grid, int x, int y) {
		return grid[y + 1][x] != '#' && grid[y + 1][x] != '.';
	}

	private static boolean isRightExit(char[][] grid, int x, int y) {
		return grid[y][x + 1] != '#' && grid[y][x + 1] != '.';
	}

	private static boolean isLeftExit(char[][] grid, int x, int y) {
		return grid[y][x - 1] != '#' && grid[y][x - 1] != '.';
	}

	private static boolean isTopExit(char[][] grid, int x, int y) {
		return grid[y - 1][x] != '#' && grid[y - 1][x] != '.';
	}

	private int countThickness(List<String> lines) {
		int middle = lines.size() / 2;
		String line = lines.get(middle);

		int thickness = 0;

		for (int x = 0; x < line.length(); x++) {
			char c = line.charAt(x);
			if (c == '#' || c == '.') {
				thickness++;
			}
		}

		return thickness / 2;
	}

}
