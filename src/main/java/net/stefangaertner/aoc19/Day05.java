package net.stefangaertner.aoc19;

import java.util.List;

import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.util.FileUtils;

public class Day05 {

	static String example1 = "1,9,10,3,2,3,11,0,99,30,40,50";
	static String example2 = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";

	public static void main(String[] strings) {

		List<String> lines = FileUtils.read("aoc19/005-data1");
		String line = lines.get(0);

		System.out.println("Part 1:");
		Parser.create(line).input("1").run();
		
		// not 10915667
		
		System.out.println("");
		System.out.println("Part 2:");
		Parser.create(line).input("5").run();
		
	}

}
