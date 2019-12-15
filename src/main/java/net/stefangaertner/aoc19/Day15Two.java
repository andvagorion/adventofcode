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

import net.stefangaertner.aoc18.pojo.Pair;
import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.util.FileUtils;
import net.stefangaertner.util.PairUtils;
import net.stefangaertner.util.StringUtils;

public class Day15Two {

	public static void main(String[] strings) throws IOException {

		Map<Pair, Character> map = explore();

		// PART 1: manually counted

		// Map<Pair, Character> map = readExample();
		// print(map);

		int i = 0;

		while (true) {
			i++;

			List<Pair> empty = map.entrySet().stream().filter(m -> m.getValue() == 'T').map(Entry::getKey)
					.collect(Collectors.toList());

			for (Pair e : empty) {
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

	private static void fill(Map<Pair, Character> map, Pair pos, char c) {

		for (int i = 1; i < 5; i++) {
			Pair delta = toPair(i);
			Pair target = Pair.of(pos.x + delta.x, pos.y + delta.y);

			if (map.containsKey(target) && map.get(target) == '.') {
				map.put(target, c);
			}
		}

	}

	private static Map<Pair, Character> explore() {

		List<String> lines = FileUtils.read("aoc19/015-data");
		String code = lines.get(0);

		Map<Pair, Character> map = new HashMap<>();
		Parser p = Parser.create(code).stopOnInput();

		Pair pos = Pair.of(0, 0);
		map.put(pos, 's');

		Set<Pair> visited = new HashSet<>();
		visited.add(pos);

		Stack<Parser> states = new Stack<>();
		Stack<Pair> positions = new Stack<>();

		Pair prevPos = Pair.of(-1, -1);

		int steps = 0;
		while (true) {
			steps++;
			prevPos = pos.copy();

			// reveal the surrounding map
			for (int i = 1; i < 5; i++) {
				peek(p, map, pos, i);
			}

			// find possible moves
			List<Pair> moves = findMoves(map, visited, pos);

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
			Pair target = moves.get(0);
			pos = move(p, map, pos, target);
			visited.add(pos);

			// break condition for errors
			if (prevPos != null && prevPos.equals(pos)) {
				break;
			}

		}

		return map;

	}

	private static Pair move(Parser p, Map<Pair, Character> map, Pair pos, Pair target) {
		int dir = toDir(Pair.of(target.x - pos.x, target.y - pos.y));
		return move(p, map, pos, dir);
	}

	private static List<Pair> findMoves(Map<Pair, Character> map, Set<Pair> visited, Pair pos) {
		List<Pair> moves = new ArrayList<>();
		for (int i = 1; i < 5; i++) {
			Pair delta = toPair(i);
			Pair target = Pair.of(pos.x + delta.x, pos.y + delta.y);
			if (map.containsKey(target) && map.get(target) == '.') {
				moves.add(target);
			}
		}
		moves = moves.stream().filter(m -> !visited.contains(m)).collect(Collectors.toList());
		return moves;
	}

	private static void peek(Parser p, Map<Pair, Character> map, Pair pos, int dir) {

		Pair prev = Pair.of(pos.x, pos.y);
		Pair target = move(p, map, pos, dir);

		if (!prev.equals(target)) {
			// go back, we're only peeking

			Pair delta = Pair.of(target.x - prev.x, target.y - prev.y);
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

	private static Pair move(Parser p, Map<Pair, Character> map, Pair pos, int dir) {

		Pair mov = toPair(dir);
		Pair target = Pair.of(pos.x + mov.x, pos.y + mov.y);

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

	private static Pair toPair(int dir) {

		Pair mov = null;

		if (dir == 1) {
			// north
			mov = Pair.of(0, -1);
		} else if (dir == 2) {
			// south
			mov = Pair.of(0, 1);
		} else if (dir == 3) {
			// west
			mov = Pair.of(-1, 0);
		} else if (dir == 4) {
			// east
			mov = Pair.of(1, 0);
		}

		return mov;
	}

	private static int toDir(Pair pair) {

		if (Pair.of(0, -1).equals(pair)) {
			// north
			return 1;
		} else if (Pair.of(0, 1).equals(pair)) {
			// south
			return 2;
		} else if (Pair.of(-1, 0).equals(pair)) {
			// west
			return 3;
		} else if (Pair.of(1, 0).equals(pair)) {
			// east
			return 4;
		}

		return -1;

	}

	private static char[][] getGrid(Map<Pair, Character> map) {
		Pair[] minMax = PairUtils.getMinAndMax(map.keySet());
		Pair min = minMax[0];
		Pair max = minMax[1];

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

		for (Entry<Pair, Character> e : map.entrySet()) {
			Pair pos1 = e.getKey();
			grid[pos1.y + offsetY][pos1.x + offsetX] = e.getValue();
		}

		return grid;
	}

	private static void print(Map<Pair, Character> map) {
		char[][] grid = getGrid(map);
		StringUtils.print2Darray(grid);
	}

	private static void printState(Map<Pair, Character> map, Pair pos, List<Pair> moves, Stack<Pair> positions) {
		if (!moves.isEmpty()) {
			Map<Pair, Character> printMap = new HashMap<>(map);
			printMap.put(pos, 'x');
			for (Pair m : moves) {
				printMap.put(m, '!');
			}

			if (!positions.isEmpty()) {
				printMap.put(positions.peek(), '?');
			}

			StringUtils.print2Darray(getGrid(printMap));
			System.out.println(" ");

		}
	}

	private static Map<Pair, Character> readExample() {

		Map<Pair, Character> map = new HashMap<>();

		List<String> lines = FileUtils.read("aoc19/015-example");

		for (int y = 0; y < lines.size(); y++) {

			String line = lines.get(y);

			for (int x = 0; x < line.length(); x++) {

				char c = line.charAt(x);

				if (c == '#' || c == 'O' || c == '.') {
					if (c == 'O') {
						c = 'T';
					}
					map.put(Pair.of(x, y), c);
				}

			}

		}

		return map;
	}

}
