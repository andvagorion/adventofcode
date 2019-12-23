package net.stefangaertner.aoc19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.stefangaertner.aoc18.pojo.Direction;
import net.stefangaertner.aoc18.pojo.Pair;
import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.util.ArrayUtils;
import net.stefangaertner.util.FileUtils;
import net.stefangaertner.util.StringUtils;

public class Day17 {

	public static void main(String[] strings) throws IOException {

		String code = FileUtils.read("aoc19/017-data").get(0);

		part1(code, false);

		// part2(code);

	}

	private static List<String> getMovementCommands(String code) {

		char[][] grid = parseGrid(code);

		List<String> commands = new ArrayList<>();

		Pair pos = findPosition(grid);

		// robot starts looking north
		Pair dir = Pair.of(0, -1);

		int max = 0;

		while (true && max < 40) {
			max++;

			grid[pos.y][pos.x] = 'x';

			// find next position to go
			Pair nextPos = findNextPosition(grid, pos);

			if (nextPos == null) {
				break;
			}

			// find turn direction
			Pair left = dir.turnLeft();
			Pair right = dir.turnRight();

			if (Pair.of(pos.x + left.x, pos.y + left.y).equals(nextPos)) {
				commands.add("L");
				dir = left;
			} else if (Pair.of(pos.x + right.x, pos.y + right.y).equals(nextPos)) {
				commands.add("R");
				dir = right;
			}

			// go forward as much as possible
			int moves = 0;
			while (isPlatform(grid, pos.add(dir))) {
				pos = pos.add(dir);
				grid[pos.y][pos.x] = 'x';
				moves++;
			}

			commands.add(String.valueOf(moves));

			StringUtils.print2Darray(grid);
		}

		return commands;
	}

	private static boolean isPlatform(char[][] grid, Pair pos) {
		char c = grid[pos.y][pos.x];
		return c == '#' || c == 'o';
	}

	private static Pair findNextPosition(char[][] grid, Pair pos) {

		List<Pair> neighbors = ArrayUtils.findNeighbors(grid, pos, '#');

		// assumption: paths never overlap direct in a corner

		return !neighbors.isEmpty() ? neighbors.get(0) : null;
	}

	private static Pair findPosition(char[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			char[] row = grid[y];
			for (int x = 0; x < row.length; x++) {
				char c = grid[y][x];
				if (c == '<' || c == '^' || c == 'v' || c == '>') {
					return Pair.of(x, y);
				}
			}
		}
		return null;
	}

	private static Pair getDirection(char c) {
		switch (c) {
		case '^':
			return Pair.of(0, -1);
		case '>':
			return Pair.of(1, 0);
		case 'v':
			return Pair.of(0, 1);
		case '<':
			return Pair.of(-1, 0);
		default:
			return null;
		}
	}

	private static void part2(String code) {
		
		List<String> commands = getMovementCommands(code);

		System.out.println(commands.stream().collect(Collectors.joining(",")));

		Parser p = Parser.create(code).stopOnInput().stopOnOutput();

		// wake up robot by changing address 0 from 1 to 2
		p.setMemoryAddress(0, 2);

		p.asciiInput("A,B,C\n");
		p.asciiInput("L\n");
		p.asciiInput("R\n");
		p.asciiInput("L\n");
		p.asciiInput("y\n");

		int i = 0;

		while (!p.isFinished() && !p.needsInput()) {

			p.run();

			String out = p.getLastOutput();
			int num = Integer.parseInt(out);

			System.out.print((char) num);

		}
	}

	private static void part1(String code, boolean debugPrint) {

		char[][] grid = parseGrid(code);

		List<Pair> crosses = new ArrayList<>();

		int sum = 0;

		for (int y = 0; y < grid.length; y++) {
			char[] row = grid[y];
			for (int x = 0; x < row.length; x++) {
				if (ArrayUtils.matchesCross(grid, Pair.of(x, y), '#')) {
					crosses.add(Pair.of(x, y));
					int mult = x * y;
					sum += mult;
				}
			}
		}

		if (debugPrint) {
			for (Pair pair : crosses) {
				grid[pair.y][pair.x] = 'O';
			}

			StringUtils.print2Darray(grid);
		}

		System.out.println("Part 1: " + sum);
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
