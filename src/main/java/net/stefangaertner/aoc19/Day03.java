package net.stefangaertner.aoc19;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.stefangaertner.aoc18.pojo.Point;
import net.stefangaertner.util.FileUtils;

public class Day03 {

	public static void main(String[] strings) {

		List<String> lines = FileUtils.read("aoc19/003-data1");
		List<String[]> inputs = lines.stream().map(l -> l.split(",")).collect(Collectors.toList());

		part1(inputs);
		part2(inputs);
	}

	public static void part2(List<String[]> inputs) {
		
		List<List<Point>> wires = new ArrayList<>();

		for (String[] input : inputs) {
			List<Point> points = new ArrayList<>();

			Point current = new Point(0, 0);
			points.add(current);

			for (String s : input) {

				Point move = null;
				int times = Integer.parseInt(s.substring(1));

				switch (s.charAt(0)) {
				case 'R':
					move = new Point(1, 0);
					break;
				case 'L':
					move = new Point(-1, 0);
					break;
				case 'U':
					move = new Point(0, -1);
					break;
				case 'D':
					move = new Point(0, 1);
					break;
				}

				for (int i = 0; i < times; i++) {
					current = new Point(current.x + move.x, current.y + move.y);
					points.add(current);
				}

			}

			wires.add(points);
		}

		List<Point> first = wires.get(0);
		List<Point> second = wires.get(1);
		Set<Point> intersections = getIntersections(inputs);
		
		int total = Integer.MAX_VALUE;
		
		for (Point p : intersections) {
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
		
		System.out.println("Part2: " + total);
	}

	public static void part1(List<String[]> input) {
		
		Set<Point> intersections = getIntersections(input);

		int minDist = Integer.MAX_VALUE;
		
		for (Point p : intersections) {
			if (p.x == 0 && p.y == 0) {
				// ignore 0,0
				continue;
			}
			
			int dist = getDistance(0, 0, p.x, p.y);
			if (dist < minDist) {
				minDist = dist;
			}
		}
		
		System.out.println("Part 1: " + minDist);
	}

	public static Set<Point> getIntersections(List<String[]> inputs) {
		List<Set<Point>> wires = new ArrayList<>();

		for (String[] input : inputs) {
			Set<Point> points = new HashSet<>();

			Point current = new Point(0, 0);
			points.add(current);

			for (String s : input) {

				Point move = null;
				int times = Integer.parseInt(s.substring(1));

				switch (s.charAt(0)) {
				case 'R':
					move = new Point(1, 0);
					break;
				case 'L':
					move = new Point(-1, 0);
					break;
				case 'U':
					move = new Point(0, -1);
					break;
				case 'D':
					move = new Point(0, 1);
					break;
				}

				for (int i = 0; i < times; i++) {
					current = new Point(current.x + move.x, current.y + move.y);
					points.add(current);
				}

			}

			wires.add(points);
		}

		Set<Point> first = wires.get(0);
		Set<Point> second = wires.get(1);

		first.retainAll(second);

		return first;

	}

	private static int getDistance(int x, int y, int x2, int y2) {
		return Math.abs(x - x2) + Math.abs(y - y2);
	}

}
