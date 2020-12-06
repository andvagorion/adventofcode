package net.stefangaertner.aoc18;

import net.stefangaertner.aoc18.pojo.Cave;
import net.stefangaertner.aoc18.pojo.Point;

public class Day22 {

	public static void main(String[] args) throws Exception {

		part1();
	}

	private static void part1() {
		int depth = 4848;
		Point target = new Point(15, 700);

		Cave cave = new Cave(depth, target);

		cave.print();

		int risk = cave.getTotalRisk();
		System.out.println(risk);
	}

	@SuppressWarnings("unused")
	private static void example() {
		int depth = 510;
		Point target = new Point(10, 10);
		Cave cave = new Cave(depth, target);
	
		cave.print();
	
		int risk = cave.getTotalRisk();
		System.out.println(risk);
	}

}
