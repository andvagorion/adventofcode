package net.stefangaertner.aoc20;

import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.aoc20.util.BootCode;
import net.stefangaertner.util.FileUtils;

public class Day08 {

	public static void main(String[] args) {
		List<String> lines = FileUtils.read("aoc20/008");

		System.out.println(String.format("Part 1: %d", part1(lines)));
		System.out.println(String.format("Part 2: %d", part2(lines)));
	}

	static long part1(List<String> lines) {
		BootCode bc = new BootCode(lines);
		bc.execute();

		return bc.acc;
	}

	static long part2(List<String> lines) {
		
		for (int i = 0; i < lines.size(); i++) {

			List<String> copy = lines.stream().collect(Collectors.toList());

			String op = lines.get(i).split(" ")[0];
			String arg = lines.get(i).split(" ")[1];

			if ("jmp".equals(op)) {
				op = "nop";
			} else if ("nop".equals(op)) {
				op = "jmp";
			}

			copy.set(i, String.format("%s %s", op, arg));
			BootCode bc = new BootCode(copy);
			bc.execute();
			
			if (!bc.loop) {
				return bc.acc;
			}
		}
		
		return -1;
	}

}
