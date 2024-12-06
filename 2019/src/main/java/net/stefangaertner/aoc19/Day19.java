package net.stefangaertner.aoc19;

import java.io.IOException;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.aoc19.util.Point2D;

public class Day19 {

	public static void main(String[] strings) throws IOException {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		String code = Advent.readLine("aoc19/019-data");

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

		return sum;
	}

	static long part2() {
		String code = Advent.readLine("aoc19/019-data");

		int y = 100;
		int offset = 0;
		Point2D found = null;

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
						// ship doesn't fit in current row (from current x
						// value)
						break;
					}

					if (checkDown && checkRight) {
						found = Point2D.of(x, y);
						break outer;
					}

				}

				x++;
			}
		}

		int val = found.x * 10000 + y;

		return val;
	}

	private static boolean isPulled(String code, int x, int y) {
		Parser p = Parser.create(code);
		p.input(x, y);
		p.run();
		return "1".equals(p.getLastOutput());
	}

}
