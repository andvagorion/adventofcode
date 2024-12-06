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

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.aoc19.util.Point2D;
import net.stefangaertner.aoc19.util.Point2DUtils;
import net.stefangaertner.aoc19.util.StringUtils;

public class Day15 {

	public static void main(String[] strings) throws IOException {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		// manually counted

		return 214L;
	}

	static long part2() {
		Map<Point2D, Character> map = explore();

		// print(map);

		int i = 0;

		while (true) {
			i++;

			List<Point2D> empty = map.entrySet().stream().filter(m -> m.getValue() == 'T').map(Entry::getKey)
					.collect(Collectors.toList());

			for (Point2D e : empty) {
				fill(map, e, 'T');
			}

			boolean anyEmpty = map.values().stream().anyMatch(c -> c == '.');
			if (!anyEmpty) {
				break;
			}

			// print(map);
		}

		// print(map);

		return i;
	}

	private static void fill(Map<Point2D, Character> map, Point2D pos, char c) {

		for (int i = 1; i < 5; i++) {
			Point2D delta = toPair(i);
			Point2D target = Point2D.of(pos.x + delta.x, pos.y + delta.y);

			if (map.containsKey(target) && map.get(target) == '.') {
				map.put(target, c);
			}
		}

	}

	private static Map<Point2D, Character> explore() {
		String code = Advent.readLine("aoc19/015-data");

		Map<Point2D, Character> map = new HashMap<>();
		Parser p = Parser.create(code).stopOnInput();

		Point2D pos = Point2D.of(0, 0);
		map.put(pos, 's');

		Set<Point2D> visited = new HashSet<>();
		visited.add(pos);

		Stack<Parser> states = new Stack<>();
		Stack<Point2D> positions = new Stack<>();

		Point2D prevPos = Point2D.of(-1, -1);

		while (true) {
			prevPos = pos.copy();

			// reveal the surrounding map
			for (int i = 1; i < 5; i++) {
				peek(p, map, pos, i);
			}

			// find possible moves
			List<Point2D> moves = findMoves(map, visited, pos);

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
			Point2D target = moves.get(0);
			pos = move(p, map, pos, target);
			visited.add(pos);

			// break condition for errors
			if (prevPos != null && prevPos.equals(pos)) {
				break;
			}

		}

		return map;

	}

	private static Point2D move(Parser p, Map<Point2D, Character> map, Point2D pos, Point2D target) {
		int dir = toDir(Point2D.of(target.x - pos.x, target.y - pos.y));
		return move(p, map, pos, dir);
	}

	private static List<Point2D> findMoves(Map<Point2D, Character> map, Set<Point2D> visited, Point2D pos) {
		List<Point2D> moves = new ArrayList<>();
		for (int i = 1; i < 5; i++) {
			Point2D delta = toPair(i);
			Point2D target = Point2D.of(pos.x + delta.x, pos.y + delta.y);
			if (map.containsKey(target) && map.get(target) == '.') {
				moves.add(target);
			}
		}
		moves = moves.stream().filter(m -> !visited.contains(m)).collect(Collectors.toList());
		return moves;
	}

	private static void peek(Parser p, Map<Point2D, Character> map, Point2D pos, int dir) {

		Point2D prev = Point2D.of(pos.x, pos.y);
		Point2D target = move(p, map, pos, dir);

		if (!prev.equals(target)) {
			// go back, we're only peeking

			Point2D delta = Point2D.of(target.x - prev.x, target.y - prev.y);
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

	private static Point2D move(Parser p, Map<Point2D, Character> map, Point2D pos, int dir) {

		Point2D mov = toPair(dir);
		Point2D target = Point2D.of(pos.x + mov.x, pos.y + mov.y);

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

	private static Point2D toPair(int dir) {

		Point2D mov = null;

		if (dir == 1) {
			// north
			mov = Point2D.of(0, -1);
		} else if (dir == 2) {
			// south
			mov = Point2D.of(0, 1);
		} else if (dir == 3) {
			// west
			mov = Point2D.of(-1, 0);
		} else if (dir == 4) {
			// east
			mov = Point2D.of(1, 0);
		}

		return mov;
	}

	private static int toDir(Point2D pair) {

		if (Point2D.of(0, -1).equals(pair)) {
			// north
			return 1;
		} else if (Point2D.of(0, 1).equals(pair)) {
			// south
			return 2;
		} else if (Point2D.of(-1, 0).equals(pair)) {
			// west
			return 3;
		} else if (Point2D.of(1, 0).equals(pair)) {
			// east
			return 4;
		}

		return -1;

	}

	private static char[][] getGrid(Map<Point2D, Character> map) {
		Point2D[] minMax = Point2DUtils.getMinAndMax(map.keySet());
		Point2D min = minMax[0];
		Point2D max = minMax[1];

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

		for (Entry<Point2D, Character> e : map.entrySet()) {
			Point2D pos1 = e.getKey();
			grid[pos1.y + offsetY][pos1.x + offsetX] = e.getValue();
		}

		return grid;
	}

	@SuppressWarnings("unused")
	private static void printState(Map<Point2D, Character> map, Point2D pos, List<Point2D> moves,
			Stack<Point2D> positions) {
		if (!moves.isEmpty()) {
			Map<Point2D, Character> printMap = new HashMap<>(map);
			printMap.put(pos, 'x');
			for (Point2D m : moves) {
				printMap.put(m, '!');
			}

			if (!positions.isEmpty()) {
				printMap.put(positions.peek(), '?');
			}

			StringUtils.print2Darray(getGrid(printMap));
			System.out.println(" ");

		}
	}

	@SuppressWarnings("unused")
	private static Map<Point2D, Character> readExample() {

		Map<Point2D, Character> map = new HashMap<>();

		List<String> lines = Advent.read("aoc19/015-example");

		for (int y = 0; y < lines.size(); y++) {

			String line = lines.get(y);

			for (int x = 0; x < line.length(); x++) {

				char c = line.charAt(x);

				if (c == '#' || c == 'O' || c == '.') {
					if (c == 'O') {
						c = 'T';
					}
					map.put(Point2D.of(x, y), c);
				}

			}

		}

		return map;
	}

}
