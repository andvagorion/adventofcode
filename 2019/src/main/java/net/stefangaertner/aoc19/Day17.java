package net.stefangaertner.aoc19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.ArrayUtils;
import net.stefangaertner.aoc19.util.GridUtils;
import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.aoc19.util.Point2D;
import net.stefangaertner.aoc19.util.StringUtils;
import net.stefangaertner.aoc19.util.Vector2D;

public class Day17 {

	public static void main(String[] strings) throws IOException {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		String code = Advent.readLine("aoc19/017-data");
		boolean debugPrint = false;

		char[][] grid = parseGrid(code);

		List<Point2D> crosses = new ArrayList<>();

		int sum = 0;

		for (int y = 0; y < grid.length; y++) {
			char[] row = grid[y];
			for (int x = 0; x < row.length; x++) {
				if (ArrayUtils.matchesCross(grid, Point2D.of(x, y), '#')) {
					crosses.add(Point2D.of(x, y));
					int mult = x * y;
					sum += mult;
				}
			}
		}

		if (debugPrint) {
			for (Point2D pair : crosses) {
				grid[pair.y][pair.x] = 'O';
			}

			StringUtils.print2Darray(grid);
		}

		return sum;
	}

	static long part2() {
		String code = Advent.readLine("aoc19/017-data");
		boolean debugPrint = false;

		// List<String> commands = getMovementCommands(code);
		// System.out.println(commands.stream().collect(Collectors.joining(",")));

		/*
		 * (A) L,12,L,8,R,10,R,10,
		 * 
		 * (B) L,6,L,4,L,12,
		 * 
		 * (A) L,12,L,8,R,10,R,10,
		 * 
		 * (B) L,6,L,4,L,12,
		 * 
		 * (C) R,10,L,8,L,4,R,10,
		 * 
		 * (B) L,6,L,4,L,12,
		 * 
		 * (A) L,12,L,8,R,10,R,10,
		 * 
		 * (C) R,10,L,8,L,4,R,10,
		 * 
		 * (B) L,6,L,4,L,12,
		 * 
		 * (C) R,10,L,8,L,4,R,10
		 */

		String order = "A,B,A,B,C,B,A,C,B,C";
		String a = "L,12,L,8,R,10,R,10";
		String b = "L,6,L,4,L,12";
		String c = "R,10,L,8,L,4,R,10";

		String dust = move(code, order, a, b, c, debugPrint);

		return Long.valueOf(dust);
	}

	private static String move(String code, String order, String a, String b, String c, boolean debugPrint) {

		Parser p = Parser.create(code).stopOnInput().stopOnOutput();

		// wake up robot by changing address 0 from 1 to 2
		p.setMemoryAddress(0, 2);

		p.asciiInput(order).asciiInput(a).asciiInput(b).asciiInput(c).asciiInput("n");

		while (!p.isFinished() && !p.needsInput()) {

			p.run();

			if (debugPrint) {

				String out = p.getLastOutput();
				int num = Integer.parseInt(out);
				if (num < 128) {
					System.out.print((char) num);
				} else {
					System.out.println(num);
				}

			}

		}

		return p.getLastOutput();
	}

	@SuppressWarnings("unused")
	private static List<String> getMovementCommands(String code) {

		char[][] grid = parseGrid(code);

		List<Point2D> visited = new ArrayList<>();
		List<String> commands = new ArrayList<>();

		Point2D pos = GridUtils.find(grid, '^');
		visited.add(pos);

		// robot starts looking north
		Vector2D dir = Vector2D.of(0, -1);

		int max = 0;

		while (true && max < 40) {
			max++;

			// find next position to go
			List<Point2D> possiblePositions = findPossiblePositions(grid, pos);

			possiblePositions = possiblePositions.stream().filter(p -> !visited.contains(p))
					.collect(Collectors.toList());

			if (possiblePositions.isEmpty()) {
				// no more possible tiles
				break;
			} else if (possiblePositions.size() > 1) {
				System.out.println("ERROR. MORE THAN ONE POSSIBLE MOVE.");
				debugPrint(grid, visited, pos, possiblePositions);

				break;
			}

			Point2D nextPos = possiblePositions.get(0);

			// find turn direction
			Vector2D left = dir.turnLeft();
			Vector2D right = dir.turnRight();

			if (Point2D.of(pos.x + left.x, pos.y + left.y).equals(nextPos)) {
				commands.add("L");
				dir = left;
			} else if (Point2D.of(pos.x + right.x, pos.y + right.y).equals(nextPos)) {
				commands.add("R");
				dir = right;
			}

			// go forward as much as possible
			int moves = 0;
			while (isPlatform(grid, pos.add(dir))) {
				pos = pos.add(dir);
				visited.add(pos);
				moves++;
			}

			commands.add(String.valueOf(moves));

		}

		return commands;
	}

	private static void debugPrint(char[][] grid, List<Point2D> visited, Point2D pos, List<Point2D> highlight) {
		char[][] out = GridUtils.clone(grid);
		out[pos.y][pos.x] = 'X';
		visited.forEach(p -> out[p.y][p.x] = 'O');
		highlight.forEach(p -> out[p.y][p.x] = '?');
		StringUtils.print2Darray(out);
	}

	private static boolean isPlatform(char[][] grid, Point2D pos) {
		try {

			char c = grid[pos.y][pos.x];
			return c == '#';

		} catch (ArrayIndexOutOfBoundsException e) {
			// out of grid
			return false;

		}
	}

	private static List<Point2D> findPossiblePositions(char[][] grid, Point2D pos) {

		List<Point2D> neighbors = ArrayUtils.findNeighbors(grid, pos, '#');

		// assumption: paths never overlap direct in a corner

		return neighbors;
	}

	private static char[][] parseGrid(String code) {
		Parser p = Parser.create(code).stopOnOutput();

		StringBuilder sb = new StringBuilder();

		while (!p.isFinished()) {

			// 35 means #, 46 means ., 10 starts a new line

			p.run();

			String out = p.getLastOutput();
			int num = Integer.parseInt(out);

			sb.append((char) num);

		}

		String[] lines = sb.toString().split((char) 10 + "");

		char[][] grid = ArrayUtils.fromStrings(lines);

		return grid;
	}

}
