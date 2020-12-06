package net.stefangaertner.aoc19;

import java.io.IOException;
import java.util.List;

import net.stefangaertner.aoc18.pojo.Point;
import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.util.FileUtils;

public class Day19 {

	public static void main(String[] strings) throws IOException {

		List<String> lines = FileUtils.read("aoc19/019-data");
		String code = lines.get(0);

		part1(code);
		part2(code);
	}

	private static void part1(String code) {

		int sum = 0;

		for (int x = 0; x < 50; x++) {
			for (int y = 0; y < 50; y++) {
				Parser p = Parser.create(code);
				p.input(x, y);
				p.run();
				if ("1".equals(p.getLastOutput())) {
					sum++;
				}
			}
		}
		System.out.println("Part 1: " + sum);

	}

	private static void part2(String code) {

		int y = 100;
		int offset = 0;
		Point found = null;

		outer: while (y < 1000) {
			y++;

			int x = offset;
			boolean first = false;

			while (true) {

				boolean pulled = isPulled(code, x, y);

				if (!first && pulled) {
					first = true;

					// first point that is pulled in current row
					// set offset for next row
					offset = x;
				}

				if (pulled) {

					boolean checkRight = isPulled(code, x + 99, y);
					boolean checkDown = isPulled(code, x, y + 99);

					if (!checkRight) {
						// ship doesn't fit in current row (from current x value)
						break;
					}

					if (checkDown && checkRight) {
						found = Point.of(x, y);
						break outer;
					}

				}

				x++;
			}
		}

		int val = found.x * 10000 + y;

		System.out.println("Part 2: " + val);
		System.out.println("        (" + found + ")");

	}

	private static boolean isPulled(String code, int x, int y) {
		Parser p = Parser.create(code);
		p.input(x, y);
		p.run();
		return "1".equals(p.getLastOutput());
	}

}
