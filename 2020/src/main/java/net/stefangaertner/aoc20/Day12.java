package net.stefangaertner.aoc20;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.Ship;
import net.stefangaertner.aoc20.util.Ship1;
import net.stefangaertner.aoc20.util.Ship2;
import net.stefangaertner.aoc20.util.Vector2D;

public class Day12 {

	private static final Pattern linePattern = Pattern.compile("(\\w)(\\d+)");

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/012");
		return parse(new Ship1(), lines);
	}

	static long part2() {
		List<String> lines = AOC.read("aoc20/012");
		return parse(new Ship2(), lines);
	}

	static long parse(Ship ship, List<String> lines) {
		for (String line : lines) {
			parse(ship, line);
		}

		return Math.abs(ship.getPosition().x) + Math.abs(ship.getPosition().y);
	}

	private static void parse(Ship ship, String line) {
		Matcher matcher = linePattern.matcher(line);
		matcher.matches();
		String action = matcher.group(1);
		int value = Integer.parseInt(matcher.group(2));

		switch (action) {
		case "N":
			ship.move(Vector2D.of(0, value));
			return;
		case "S":
			ship.move(Vector2D.of(0, -value));
			return;
		case "W":
			ship.move(Vector2D.of(-value, 0));
			return;
		case "E":
			ship.move(Vector2D.of(value, 0));
			return;
		case "L":
			ship.turn(-value);
			return;
		case "R":
			ship.turn(value);
			return;
		case "F":
			ship.forward(value);
			return;
		}
	}

}
