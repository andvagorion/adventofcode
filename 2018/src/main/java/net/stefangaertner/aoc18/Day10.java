package net.stefangaertner.aoc18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.stefangaertner.aoc18.pojo.BigRectangle;
import net.stefangaertner.aoc18.pojo.Coordinate;
import net.stefangaertner.aoc18.util.Advent;

public class Day10 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	private static Pattern pattern = Pattern
			.compile("^position\\=\\<\\s*(-*\\d+),\\s*(-*\\d+)\\>\\s*velocity\\=\\<\\s*(-*\\d+),\\s*(-*\\d+)\\>$");

	static String part1() {
		List<String> lines = Advent.read("aoc18/010-data");

		List<Coordinate> coords = parse(lines);

		int count = 0;
		long prev = getSize(coords);
		boolean shrinking = true;

		while (shrinking) {
			for (Coordinate c : coords)
				c.move();
			long next = getSize(coords);

			shrinking = next < prev;
			if (shrinking) {
				prev = next;
				count++;
			}
		}

		coords = parse(lines);
		for (int i = 0; i < count; i++) {
			for (Coordinate c : coords)
				c.move();
		}

		print(coords);

		return "LKPHZHHJ";
	}

	static long part2() {
		List<String> lines = Advent.read("aoc18/010-data");

		List<Coordinate> coords = parse(lines);

		int count = 0;
		long prev = getSize(coords);
		boolean shrinking = true;

		while (shrinking) {
			for (Coordinate c : coords)
				c.move();
			long next = getSize(coords);

			shrinking = next < prev;
			if (shrinking) {
				prev = next;
				count++;
			}
		}

		return count;
	}

	private static long getSize(List<Coordinate> coords) {
		BigRectangle bounds = getBounds(coords);
		return bounds.w * bounds.h;

	}

	private static BigRectangle getBounds(List<Coordinate> coords) {

		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;

		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;

		for (Coordinate c : coords) {
			if (c.x < minX)
				minX = c.x;
			if (c.y < minY)
				minY = c.y;

			if (c.x > maxX)
				maxX = c.x;
			if (c.y > maxY)
				maxY = c.y;
		}

		return new BigRectangle(minX, minY, maxX - minX, maxY - minY);
	}

	private static void print(List<Coordinate> coords) {

		BigRectangle bounds = getBounds(coords);

		char[][] output = new char[(int) (bounds.h + 1)][(int) (bounds.w + 1)];

		for (Coordinate c : coords) {
			output[(int) (c.y - bounds.y)][(int) (c.x - bounds.x)] = 'x';
		}

		for (int i = 0; i < output.length; i++) {
			System.out.println(Arrays.toString(output[i]));
		}
	}

	private static List<Coordinate> parse(List<String> lines) {

		List<Coordinate> coords = new ArrayList<>();

		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				int x = Integer.parseInt(matcher.group(1));
				int y = Integer.parseInt(matcher.group(2));
				int dx = Integer.parseInt(matcher.group(3));
				int dy = Integer.parseInt(matcher.group(4));

				Coordinate c = new Coordinate(-1, x, y);
				c.setVelocity(dx, dy);

				coords.add(c);
			}
		}

		return coords;

	}

}
