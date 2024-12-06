package net.stefangaertner.aoc20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.PointHex;
import net.stefangaertner.aoc20.util.PointHex.HexDirection;
import net.stefangaertner.aoc20.util.StringUtils;

public class Day24 {

	static boolean DEBUG = false;

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/024");

		Set<PointHex> flipped = parse(lines);

		return flipped.size();
	}

	static long part2() {
		List<String> lines = AOC.read("aoc20/024");

		Set<PointHex> currentState = parse(lines);

		if (DEBUG) {
			printWhole(currentState);
		}

		for (int i = 0; i < 100; i++) {

			Set<PointHex> next = new HashSet<>();

			for (PointHex point : currentState) {
				boolean active = isActive(currentState, point);
				if (active) {
					next.add(point);
				}

				for (PointHex other : point.getNeighbors()) {
					boolean active2 = isActive(currentState, other);
					if (active2) {
						next.add(other);
					}
				}
			}

			currentState = next;

			if (DEBUG) {
				System.out.println("day " + (i + 1) + " => " + currentState.size() + " active tiles.");
				printWhole(currentState);
			}
		}

		return currentState.size();

	}

	private static boolean isActive(Set<PointHex> state, PointHex point) {

		boolean currentlyActive = state.contains(point);

		Set<PointHex> neighbors = point.getNeighbors();

		long activeNeighbors = neighbors.stream().filter(n -> state.contains(n)).count();

		if (currentlyActive) {
			// Any black tile with zero or more than 2 black tiles immediately
			// adjacent to
			// it is flipped to white.

			return activeNeighbors == 1 || activeNeighbors == 2;

		} else {
			// Any white tile with exactly 2 black tiles immediately adjacent to
			// it is
			// flipped to black.

			return activeNeighbors == 2;

		}

	}

	private static Set<PointHex> parse(List<String> lines) {
		Set<PointHex> flipped = new HashSet<>();

		for (String line : lines) {

			PointHex pos = PointHex.of(0, 0);

			List<PointHex> trail = new ArrayList<>();
			trail.add(pos);

			LinkedList<HexDirection> symbols = StringUtils.parseSymbols(line,
					Arrays.asList(PointHex.HexDirection.values()));

			while (!symbols.isEmpty()) {

				HexDirection dir = symbols.pop();
				pos = pos.add(dir.getVector(pos));

				if (DEBUG) {
					trail.add(pos);
				}
			}

			if (DEBUG) {
				printTrail(trail);
			}

			if (flipped.contains(pos)) {
				flipped.remove(pos);
			} else {
				flipped.add(pos);
			}

			if (DEBUG) {
				printWhole(flipped);
			}
		}

		return flipped;
	}

	private static void printWhole(Set<PointHex> flipped) {
		int minY = flipped.stream().min(Comparator.comparing(p -> p.y)).get().y;
		int maxY = flipped.stream().max(Comparator.comparing(p -> p.y)).get().y;

		int minX = flipped.stream().min(Comparator.comparing(p -> p.x)).get().x;
		int maxX = flipped.stream().max(Comparator.comparing(p -> p.x)).get().x;

		StringBuilder sb = new StringBuilder();
		for (int y = minY; y <= maxY; y++) {
			if (y % 2 != 0) {
				sb.append("  ");
			}
			for (int x = minX; x <= maxX; x++) {
				String s = "  ";
				if (x == 0 && y == 0) {
					s = "00";
				}
				if (flipped.contains(PointHex.of(x, y))) {
					s = "##";
				}
				sb.append("[" + s + "]");
			}
			sb.append("\n");
		}

		System.out.println(sb.toString());
	}

	private static void printTrail(List<PointHex> trail) {
		int minY = trail.stream().min(Comparator.comparing(p -> p.y)).get().y;
		int maxY = trail.stream().max(Comparator.comparing(p -> p.y)).get().y;

		int minX = trail.stream().min(Comparator.comparing(p -> p.x)).get().x;
		int maxX = trail.stream().max(Comparator.comparing(p -> p.x)).get().x;

		StringBuilder sb = new StringBuilder();
		for (int y = minY; y <= maxY; y++) {
			if (y % 2 != 0) {
				sb.append("  ");
			}
			for (int x = minX; x <= maxX; x++) {
				String s = "  ";
				if (trail.contains(PointHex.of(x, y))) {
					int i = trail.indexOf(PointHex.of(x, y));
					if (i < 10) {
						s = "0" + i;
					} else {
						s = "" + i;
					}
				}
				sb.append("[" + s + "]");
			}
			sb.append("\n");
		}

		System.out.println(sb.toString());
	}

}
