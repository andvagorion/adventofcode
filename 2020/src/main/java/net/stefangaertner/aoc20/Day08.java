package net.stefangaertner.aoc20;

import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.BootCode;

public class Day08 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/008");

		BootCode bc = new BootCode(lines);
		bc.execute();

		return bc.acc;
	}

	static long part2() {
		List<String> lines = AOC.read("aoc20/008");

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
