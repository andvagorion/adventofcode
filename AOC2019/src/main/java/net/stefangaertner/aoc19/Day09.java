package net.stefangaertner.aoc19;

import java.util.List;

import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.util.Advent;
import net.stefangaertner.util.FileUtils;

public class Day09 {

	static String example1 = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99";
	static String example2 = "1102,34915192,34915192,7,4,7,99,0";
	static String example3 = "104,1125899906842624,99";

	public static void main(String[] strings) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = FileUtils.read("aoc19/009-data");
		String code = lines.get(0);

		Parser p = Parser.create(code)
				.input("1")
				.run();

		return Long.parseLong(p.getLastOutput());
	}

	static long part2() {
		List<String> lines = FileUtils.read("aoc19/009-data");
		String code = lines.get(0);

		Parser p = Parser.create(code)
				.input("2")
				.run();

		return Long.parseLong(p.getLastOutput());
	}

}
