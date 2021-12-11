package net.stefangaertner.aoc18;

import net.stefangaertner.aoc18.pojo.Cave;
import net.stefangaertner.aoc18.util.Advent;
import net.stefangaertner.aoc18.util.Vector2D;

public class Day22 {

	public static void main(String[] args) {
		Advent.print(1, part1());
	}

	static long part1() {
		int depth = 4848;
		Vector2D target = new Vector2D(15, 700);

		Cave cave = new Cave(depth, target);

		// cave.print();

		int risk = cave.getTotalRisk();
		return risk;
	}

	@SuppressWarnings("unused")
	private static void example() {
		int depth = 510;
		Vector2D target = new Vector2D(10, 10);
		Cave cave = new Cave(depth, target);

		cave.print();

		int risk = cave.getTotalRisk();
		System.out.println(risk);
	}

}
