package net.stefangaertner.aoc18;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.stefangaertner.aoc18.pojo.Rectangle;
import net.stefangaertner.aoc18.pojo.WaterNode;
import net.stefangaertner.aoc18.util.Advent;
import net.stefangaertner.aoc18.util.StringUtils;

public class Day17 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	private static Pattern pattern = Pattern.compile("^(\\w)=(\\d+), (\\w)=(\\d+)\\.\\.(\\d+)$");

	static int countStill = 0;
	static int countFlowing = 0;

	static long part1() {
		List<String> lines = Advent.read("aoc18/017-data");

		List<Rectangle> rects = parse(lines);

		char[][] area = asArea(rects);

		int previousCount = 0;

		while (true) {
			fill(area);
			makeStill(area);

			int count = countChars(area, '~');
			if (count == previousCount) {
				break;
			}
			previousCount = count;
		}

		fill(area);

		countStill = countChars(area, '~');
		countFlowing = countChars(area, '|');

		// debugExport(area);

		return countStill + countFlowing;
	}

	static long part2() {
		return countStill;
	}

	private static int countChars(char[][] area, char d) {
		int sum = 0;

		int minY = 0;
		while (true) {
			String s = String.valueOf(area[minY]);
			if (!s.contains("#")) {
				minY++;
			} else {
				break;
			}
		}

		for (int y = minY; y < area.length; y++) {
			char[] row = area[y];
			for (int x = 0; x < row.length; x++) {
				char c = row[x];
				if (c == d) {
					sum++;
				}
			}
		}

		return sum;
	}

	private static void fill(char[][] area) {
		WaterNode source = findSource(area);

		// flow straight down first
		flow(area, new WaterNode(source.x, source.y + 1));
	}

	private static void flow(char[][] area, WaterNode node) {

		// can't fill, return
		if (area[node.y][node.x] != '.')
			return;

		// fill with flowing water if not filled
		if (area[node.y][node.x] == '.') {
			area[node.y][node.x] = '|';
		}

		// end of grid
		if (node.y >= area.length - 1)
			return;

		// check beneath
		char beneath = area[node.y + 1][node.x];
		if (beneath == '.') {
			flow(area, new WaterNode(node.x, node.y + 1));
		}

		if (beneath == '#' || beneath == '~') {
			// flow left and right
			flow(area, new WaterNode(node.x + 1, node.y));
			flow(area, new WaterNode(node.x - 1, node.y));
		}
	}

	private static void makeStill(char[][] area) {

		for (int y = 0; y < area.length; y++) {
			char[] row = area[y];
			String rowStr = String.valueOf(row);
			if (!rowStr.contains("#|"))
				continue;

			String[] parts = rowStr.split("#");

			for (int i = 0; i < parts.length; i++) {
				if (parts[i].isEmpty())
					continue;
				parts[i] = replace(parts[i]);
			}

			String replaced = String.join("#", parts);

			area[y] = replaced.toCharArray();
		}

		for (int y = 0; y < area.length; y++) {
			char[] row = area[y];
			for (int x = 0; x < row.length; x++) {
				char c = row[x];
				if (c == '|') {
					area[y][x] = '.';
				}
			}
		}
	}

	private static String replace(String s) {
		String needle = StringUtils.repeat("|", s.length());
		if (s.equals(needle))
			return StringUtils.repeat("~", s.length());
		return s;
	}

	private static WaterNode findSource(char[][] area) {
		for (int y = 0; y < area.length; y++) {
			char[] row = area[y];
			for (int x = 0; x < row.length; x++) {

				if (row[x] == '+')
					return new WaterNode(x, y);

			}
		}

		return null;
	}

	private static char[][] asArea(List<Rectangle> rects) {

		int offset = rects.stream()
				.map(r -> r.x)
				.min(Comparator.comparing(Integer::valueOf))
				.get();

		int maxX = rects.stream()
				.map(r -> r.x + r.w)
				.max(Comparator.comparing(Integer::valueOf))
				.get();
		int maxY = rects.stream()
				.map(r -> r.y + r.h)
				.max(Comparator.comparing(Integer::valueOf))
				.get();

		int width = maxX - offset + 4;

		offset = offset - 2;

		char[][] c = new char[maxY][];

		for (int y = 0; y < c.length; y++) {
			c[y] = new char[width];
			for (int x = 0; x < width; x++) {
				c[y][x] = '.';
			}
		}

		for (Rectangle r : rects) {
			for (int y = r.y; y < r.y + r.h; y++) {
				for (int x = r.x; x < r.x + r.w; x++) {
					c[y][x - offset] = '#';
				}
			}
		}

		c[0][500 - offset] = '+';

		return c;
	}

	private static List<Rectangle> parse(List<String> lines) {

		List<Rectangle> coords = new ArrayList<>();

		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				String w = matcher.group(1);
				int n = Integer.parseInt(matcher.group(2));
				String x = matcher.group(3);
				int m0 = Integer.parseInt(matcher.group(4));
				int m1 = Integer.parseInt(matcher.group(5));

				coords.add(parseRect(w, n, x, m0, m1));

			}
		}

		return coords;
	}

	private static Rectangle parseRect(String w, int n, String x, int m0, int m1) {

		Rectangle rect = new Rectangle();

		if (w.equals("x")) {
			// n is x
			rect.x = n;
			rect.w = 1;
			rect.y = m0;
			rect.h = m1 - m0 + 1;
		} else {
			rect.x = m0;
			rect.w = m1 - m0 + 1;
			rect.y = n;
			rect.h = 1;
		}

		return rect;
	}

	@SuppressWarnings("unused")
	private static void debugExport(char[][] area) throws FileNotFoundException {
		// Creating a File object that represents the disk file.
		PrintStream o = new PrintStream(new File("src/main/resources/017-debug-output.txt"));
		// Store current System.out before assigning a new value
		PrintStream console = System.out;
		// Assign o to output stream
		System.setOut(o);

		StringUtils.print2Darray(area);

		System.setOut(console);
	}

}
