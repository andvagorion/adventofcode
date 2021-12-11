package net.stefangaertner.aoc19;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.Point2D;

public class Day03 {

	public static void main(String[] strings) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	public static long part1() {
		List<String> lines = Advent.read("aoc19/003-data1");
		List<String[]> input = lines.stream().map(l -> l.split(",")).collect(Collectors.toList());

		Set<Point2D> intersections = getIntersections(input);

		int minDist = Integer.MAX_VALUE;

		for (Point2D p : intersections) {
			if (p.x == 0 && p.y == 0) {
				// ignore 0,0
				continue;
			}

			int dist = getDistance(0, 0, p.x, p.y);
			if (dist < minDist) {
				minDist = dist;
			}
		}

		return minDist;
	}

	public static long part2() {
		List<String> lines = Advent.read("aoc19/003-data1");
		List<String[]> inputs = lines.stream().map(l -> l.split(",")).collect(Collectors.toList());

		List<List<Point2D>> wires = new ArrayList<>();

		for (String[] input : inputs) {
			List<Point2D> points = new ArrayList<>();

			Point2D current = Point2D.of(0, 0);
			points.add(current);

			for (String s : input) {

				Point2D move = null;
				int times = Integer.parseInt(s.substring(1));

				switch (s.charAt(0)) {
				case 'R':
					move = Point2D.of(1, 0);
					break;
				case 'L':
					move = Point2D.of(-1, 0);
					break;
				case 'U':
					move = Point2D.of(0, -1);
					break;
				case 'D':
					move = Point2D.of(0, 1);
					break;
				}

				for (int i = 0; i < times; i++) {
					current = Point2D.of(current.x + move.x, current.y + move.y);
					points.add(current);
				}

			}

			wires.add(points);
		}

		List<Point2D> first = wires.get(0);
		List<Point2D> second = wires.get(1);
		Set<Point2D> intersections = getIntersections(inputs);

		int total = Integer.MAX_VALUE;

		for (Point2D p : intersections) {
			if (p.x == 0 && p.y == 0) {
				// ignore 0,0
				continue;
			}

			int i1 = first.indexOf(p);
			int i2 = second.indexOf(p);

			if (i1 + i2 < total) {
				total = i1 + i2;
			}
		}

		return total;
	}

	public static Set<Point2D> getIntersections(List<String[]> inputs) {
		List<Set<Point2D>> wires = new ArrayList<>();

		for (String[] input : inputs) {
			Set<Point2D> points = new HashSet<>();

			Point2D current = Point2D.of(0, 0);
			points.add(current);

			for (String s : input) {

				Point2D move = null;
				int times = Integer.parseInt(s.substring(1));

				switch (s.charAt(0)) {
				case 'R':
					move = Point2D.of(1, 0);
					break;
				case 'L':
					move = Point2D.of(-1, 0);
					break;
				case 'U':
					move = Point2D.of(0, -1);
					break;
				case 'D':
					move = Point2D.of(0, 1);
					break;
				}

				for (int i = 0; i < times; i++) {
					current = Point2D.of(current.x + move.x, current.y + move.y);
					points.add(current);
				}

			}

			wires.add(points);
		}

		Set<Point2D> first = wires.get(0);
		Set<Point2D> second = wires.get(1);

		first.retainAll(second);

		return first;

	}

	private static int getDistance(int x, int y, int x2, int y2) {
		return Math.abs(x - x2) + Math.abs(y - y2);
	}

}
