package net.stefangaertner.aoc19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import net.stefangaertner.aoc18.pojo.Point;
import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.util.FileUtils;
import net.stefangaertner.util.PointUtils;
import net.stefangaertner.util.StringUtils;

public class Day15 {

	public static void main(String[] strings) throws IOException {

		Map<Point, Character> map = explore();

		// PART 1: manually counted

		// Map<Pair, Character> map = readExample();
		// print(map);

		int i = 0;

		while (true) {
			i++;

			List<Point> empty = map.entrySet().stream().filter(m -> m.getValue() == 'T').map(Entry::getKey)
					.collect(Collectors.toList());

			for (Point e : empty) {
				fill(map, e, 'T');
			}

			boolean anyEmpty = map.values().stream().anyMatch(c -> c == '.');
			if (!anyEmpty) {
				break;
			}

			// print(map);
		}

		// print(map);

		System.out.println("Part 2: " + i);
	}

	private static void fill(Map<Point, Character> map, Point pos, char c) {

		for (int i = 1; i < 5; i++) {
			Point delta = toPair(i);
			Point target = Point.of(pos.x + delta.x, pos.y + delta.y);

			if (map.containsKey(target) && map.get(target) == '.') {
				map.put(target, c);
			}
		}

	}

	private static Map<Point, Character> explore() {

		List<String> lines = FileUtils.read("aoc19/015-data");
		String code = lines.get(0);

		Map<Point, Character> map = new HashMap<>();
		Parser p = Parser.create(code).stopOnInput();

		Point pos = Point.of(0, 0);
		map.put(pos, 's');

		Set<Point> visited = new HashSet<>();
		visited.add(pos);

		Stack<Parser> states = new Stack<>();
		Stack<Point> positions = new Stack<>();

		Point prevPos = Point.of(-1, -1);

		int steps = 0;
		while (true) {
			steps++;
			prevPos = pos.copy();

			// reveal the surrounding map
			for (int i = 1; i < 5; i++) {
				peek(p, map, pos, i);
			}

			// find possible moves
			List<Point> moves = findMoves(map, visited, pos);

			// no more moves at current position
			if (moves.isEmpty()) {

				// check if we're done
				if (positions.isEmpty()) {
					break;
				}

				// backtrack to last possible move
				p = states.pop();
				pos = positions.pop();

				continue;
			}

			// printState(map, pos, moves, positions);

			if (moves.size() > 1) {

				states.push(p.copy());
				positions.push(pos.copy());

			}

			// move to next tile
			Point target = moves.get(0);
			pos = move(p, map, pos, target);
			visited.add(pos);

			// break condition for errors
			if (prevPos != null && prevPos.equals(pos)) {
				break;
			}

		}

		return map;

	}

	private static Point move(Parser p, Map<Point, Character> map, Point pos, Point target) {
		int dir = toDir(Point.of(target.x - pos.x, target.y - pos.y));
		return move(p, map, pos, dir);
	}

	private static List<Point> findMoves(Map<Point, Character> map, Set<Point> visited, Point pos) {
		List<Point> moves = new ArrayList<>();
		for (int i = 1; i < 5; i++) {
			Point delta = toPair(i);
			Point target = Point.of(pos.x + delta.x, pos.y + delta.y);
			if (map.containsKey(target) && map.get(target) == '.') {
				moves.add(target);
			}
		}
		moves = moves.stream().filter(m -> !visited.contains(m)).collect(Collectors.toList());
		return moves;
	}

	private static void peek(Parser p, Map<Point, Character> map, Point pos, int dir) {

		Point prev = Point.of(pos.x, pos.y);
		Point target = move(p, map, pos, dir);

		if (!prev.equals(target)) {
			// go back, we're only peeking

			Point delta = Point.of(target.x - prev.x, target.y - prev.y);
			int dir2 = reverse(toDir(delta));
			move(p, map, target, dir2);
		}
	}

	private static int reverse(int dir) {
		if (dir == 1) {
			return 2;
		} else if (dir == 2) {
			return 1;
		} else if (dir == 3) {
			return 4;
		} else if (dir == 4) {
			return 3;
		}

		return -1;
	}

	private static Point move(Parser p, Map<Point, Character> map, Point pos, int dir) {

		Point mov = toPair(dir);
		Point target = Point.of(pos.x + mov.x, pos.y + mov.y);

		p.input(String.valueOf(dir));
		p.run();

		String out = p.getLastOutput();

		char c = ' ';

		if ("0".equals(out)) {
			// wall, don't move
			c = '#';
		} else if ("1".equals(out)) {
			// open, move
			c = '.';
			pos = target;
		} else if ("2".equals(out)) {
			// tank, don't move
			c = 'T';
		}

		if (!map.containsKey(target)) {
			map.put(target, c);
		}

		return pos;
	}

	private static Point toPair(int dir) {

		Point mov = null;

		if (dir == 1) {
			// north
			mov = Point.of(0, -1);
		} else if (dir == 2) {
			// south
			mov = Point.of(0, 1);
		} else if (dir == 3) {
			// west
			mov = Point.of(-1, 0);
		} else if (dir == 4) {
			// east
			mov = Point.of(1, 0);
		}

		return mov;
	}

	private static int toDir(Point pair) {

		if (Point.of(0, -1).equals(pair)) {
			// north
			return 1;
		} else if (Point.of(0, 1).equals(pair)) {
			// south
			return 2;
		} else if (Point.of(-1, 0).equals(pair)) {
			// west
			return 3;
		} else if (Point.of(1, 0).equals(pair)) {
			// east
			return 4;
		}

		return -1;

	}

	private static char[][] getGrid(Map<Point, Character> map) {
		Point[] minMax = PointUtils.getMinAndMax(map.keySet());
		Point min = minMax[0];
		Point max = minMax[1];

		int offsetY = Math.abs(min.y);
		int offsetX = Math.abs(min.x);
		int sizeY = offsetY + max.y + 1;
		int sizeX = offsetX + max.x + 1;

		char[][] grid = new char[sizeY][];
		for (int y = 0; y < sizeY; y++) {
			grid[y] = new char[sizeX];
			for (int x = 0; x < sizeX; x++) {
				grid[y][x] = ' ';
			}
		}

		for (Entry<Point, Character> e : map.entrySet()) {
			Point pos1 = e.getKey();
			grid[pos1.y + offsetY][pos1.x + offsetX] = e.getValue();
		}

		return grid;
	}

	private static void print(Map<Point, Character> map) {
		char[][] grid = getGrid(map);
		StringUtils.print2Darray(grid);
	}

	private static void printState(Map<Point, Character> map, Point pos, List<Point> moves, Stack<Point> positions) {
		if (!moves.isEmpty()) {
			Map<Point, Character> printMap = new HashMap<>(map);
			printMap.put(pos, 'x');
			for (Point m : moves) {
				printMap.put(m, '!');
			}

			if (!positions.isEmpty()) {
				printMap.put(positions.peek(), '?');
			}

			StringUtils.print2Darray(getGrid(printMap));
			System.out.println(" ");

		}
	}

	private static Map<Point, Character> readExample() {

		Map<Point, Character> map = new HashMap<>();

		List<String> lines = FileUtils.read("aoc19/015-example");

		for (int y = 0; y < lines.size(); y++) {

			String line = lines.get(y);

			for (int x = 0; x < line.length(); x++) {

				char c = line.charAt(x);

				if (c == '#' || c == 'O' || c == '.') {
					if (c == 'O') {
						c = 'T';
					}
					map.put(Point.of(x, y), c);
				}

			}

		}

		return map;
	}

}
