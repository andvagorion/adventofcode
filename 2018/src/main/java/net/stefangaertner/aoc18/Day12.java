package net.stefangaertner.aoc18;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.stefangaertner.aoc18.util.Advent;

public class Day12 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = Advent.read("aoc18/012-data");
		String input = lines.get(0)
				.split(" ")[2];

		Map<String, String> rules = parseLines(lines);

		long num = 20;

		long firstIndex = 0;

		for (long i = 0; i < num; i++) {

			// will always be 2 bigger on each side;
			input = applyRules(input, rules);

			// cut all dots
			int first = input.indexOf('#');
			int last = input.lastIndexOf('#');
			input = input.substring(first, last + 1);

			firstIndex += first;
		}

		long sum = 0;
		long index = 0 - num * 2 + firstIndex;
		for (char c : input.toCharArray()) {
			if (c == '#')
				sum += index;
			index++;
		}

		return sum;
	}

	static long part2() {

		String input = "##....##...##.....##....##....##...##....##....##...##.....##....##....##....##....##....##...##....##....##....##....##....##...##....##....##...##...##.....##....##...##....##";

		long num = 50000000000L;

		long iterations = 9000;
		long startIdx = 29922 - 3000;

		for (long i = iterations; i < num; i += 1000) {
			startIdx += 3000;
		}

		long sum = 0;
		long index = 0 - num * 2 + startIdx;
		for (char c : input.toCharArray()) {
			if (c == '#') {
				sum += index;
			}
			index++;
		}

		return sum;
	}

	private static String applyRules(String input, Map<String, String> rules) {

		String out = "";

		input = "...." + input + "....";

		for (int i = 0; i < input.length() - 4; i++) {
			String part = input.substring(i, i + 5);
			String next = rules.get(part);
			if (next == null)
				next = ".";
			out += next;
		}

		return out;
	}

	private static Map<String, String> parseLines(List<String> lines) {
		Map<String, String> rules = new HashMap<>();
		for (String line : lines) {
			if (line.contains("=>")) {

				String[] rule = line.split(" ");
				rules.put(rule[0], rule[2]);

			}
		}
		return rules;
	}

}
