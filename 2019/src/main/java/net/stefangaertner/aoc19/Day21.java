package net.stefangaertner.aoc19;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.Parser;

public class Day21 {

	public static void main(String[] strings) throws IOException {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
//		execute("021-part1");
//		execute("021-part1b");
		return Long.valueOf(execute("021-part1c"));
	}

	static long part2() {
		return Long.valueOf(execute("021-part2"));
	}

	private static String execute(String part) {
		String code = Advent.readLine("aoc19/021-data");
		Parser p = Parser.create(code);

		List<String> lines = Advent.read("aoc19/" + part);
		lines = lines.stream()
				.filter(s -> !s.trim()
						.isEmpty() && !s.startsWith("#"))
				.collect(Collectors.toList());

		lines.forEach(p::asciiInput);

		p.run();
		String out = parseOutput(p.getUnhandledOutput());

		// System.out.println(out);

		String[] parts = out.split("\n");
		return parts[parts.length - 1];
	}

	private static String parseOutput(List<String> list) {
		return list.stream()
				.map(s -> {
					long i = Long.parseLong(s);
					if (i < 128) {
						return String.valueOf((char) i);
					} else {
						return String.valueOf(i);
					}
				})
				.collect(Collectors.joining());
	}

}
