package net.stefangaertner.aoc19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import net.stefangaertner.aoc18.pojo.Pair;
import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.util.FileUtils;
import net.stefangaertner.util.PairUtils;
import net.stefangaertner.util.StringUtils;
import net.stefangaertner.util.TileType;

public class Day15 {

	public static void main(String[] strings) throws IOException {

		explore();

	}

	private static void part1() {

		List<String> lines = FileUtils.read("aoc19/015-data");
		String code = lines.get(0);

		Parser p = Parser.create(code).stopOnInput();

		Map<Pair, Character> map = new HashMap<>();

		Pair pos = Pair.of(0, 0);
		map.put(pos, 's');

		int c = 0;

		ThreadLocalRandom rnd = ThreadLocalRandom.current();

		boolean stopped = false;

		while (c < 1000000 && !stopped) {
			c++;

			int dir = rnd.nextInt(1, 5);

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

			p.input(String.valueOf(dir));
			p.run();

			String out = p.getLastOutput();

			Pair x = null;
			char x1 = ' ';

			if ("0".equals(out)) {
				// wall
				x = Pair.of(pos.x + mov.x, pos.y + mov.y);
				x1 = '#';
			} else if ("1".equals(out)) {
				// moved
				pos = Pair.of(pos.x + mov.x, pos.y + mov.y);

				x = pos;
				x1 = '.';

			} else if ("2".equals(out)) {
				// moved, found tank
				pos = Pair.of(pos.x + mov.x, pos.y + mov.y);

				x = pos;
				x1 = 'T';

				stopped = true;
			}

			if (!map.containsKey(x)) {
				map.put(x, x1);
			}
		}

		print(map);

	}

	static ThreadLocalRandom rnd = ThreadLocalRandom.current();

	private static void explore() {

		Map<Pair, Character> map = new HashMap<>();

		for (int t = 0; t < 10; t++) {

			String first = "U,L,U,L,D,R,U,D,L,U,L,D,L,D,R,U,R,D," + "R,D,L,D,R,D,L,D,R,U,R,U," + "R,D,R,"
					+ "U,R,D,R,D,L,D,R,D,L,D,R,D,L,D";

			List<String> lines = FileUtils.read("aoc19/015-data");
			String code = lines.get(0);

			Parser p = Parser.create(code).stopOnInput();

			Pair pos = Pair.of(0, 0);
			map.put(pos, 's');

			pos = preparedWalk(p, map, pos, first);

			int i = 0;
			int max = 100000;

			while (i < max) {
				i++;

				for (int j = 0; j < 10; j++) {
					if (i == max / 10 * j) {
						System.out.println((j * 10) + "%");
					}
				}

				int dir = rnd.nextInt(1, 5);

				pos = move(p, pos, dir, map);

			}

		}

		print(map);

	}

	private static Pair preparedWalk(Parser p, Map<Pair, Character> map, Pair pos, String first) {
		for (int j = 0; j < first.length(); j++) {

			int dir = -1;

			char in = first.charAt(j);
			switch (in) {
			case 'U':
				dir = 1;
				break;
			case 'D':
				dir = 2;
				break;
			case 'L':
				dir = 3;
				break;
			case 'R':
				dir = 4;
				break;
			case ',':
				continue;
			}

			for (int k = 0; k < 10; k++) {
				pos = move(p, pos, dir, map);
			}

		}

		return pos;
	}

	private static Pair move(Parser p, Pair pos, int dir, Map<Pair, Character> map) {

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

	private static void print(Map<Pair, Character> map) {
		Pair[] minMax = PairUtils.getMinAndMax(map.keySet());
		Pair min = minMax[0];
		Pair max = minMax[1];

		int offsetY = Math.abs(min.y);
		int offsetX = Math.abs(min.x);
		int sizeY = offsetY + max.y + 1;
		int sizeX = offsetX + max.x + 1;

		char[][] image = new char[sizeY][];
		for (int y = 0; y < sizeY; y++) {
			image[y] = new char[sizeX];
			for (int x = 0; x < sizeX; x++) {
				image[y][x] = ' ';
			}
		}

		for (Entry<Pair, Character> e : map.entrySet()) {
			Pair pos1 = e.getKey();
			image[pos1.y + offsetY][pos1.x + offsetX] = e.getValue();
		}

		StringUtils.print2Darray(image);
	}

	private static void part2(boolean manualInput, boolean debugPrint) throws IOException {

	}

}
