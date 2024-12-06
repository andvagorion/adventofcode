package net.stefangaertner.aoc19;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.Parser;

public class Day05 {

	static String example1 = "1,9,10,3,2,3,11,0,99,30,40,50";
	static String example2 = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";

	public static void main(String[] strings) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		String line = Advent.readLine("aoc19/005-data1");

		Parser p1 = Parser.create(line)
				.input("1")
				.run();
		return Long.valueOf(p1.getLastOutput());
	}

	static long part2() {
		String line = Advent.readLine("aoc19/005-data1");

		Parser p2 = Parser.create(line)
				.input("5")
				.run();
		return Long.valueOf(p2.getLastOutput());

	}

}
