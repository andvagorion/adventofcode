package net.stefangaertner.aoc20;

import java.util.List;
import java.util.function.Predicate;

import net.stefangaertner.util.FileUtils;
import net.stefangaertner.util.StringUtils;

public class Day02 {

	private static final String pattern = "^(\\d+)\\-(\\d+) (\\w)\\: (\\w+)";

	private static final Predicate<String> oldPolicy = str -> {
		String[] parsed = StringUtils.parse(str, pattern);

		int min = Integer.parseInt(parsed[0]);
		int max = Integer.parseInt(parsed[1]);
		char needle = parsed[2].charAt(0);
		String pw = parsed[3];

		char[] chars = pw.toCharArray();

		int count = 0;

		for (char c : chars) {
			if (c == needle) {
				count++;
			}
		}

		return count >= min && count <= max;
	};

	private static final Predicate<String> newPolicy = str -> {
		String[] parsed = StringUtils.parse(str, pattern);

		int pos1 = Integer.parseInt(parsed[0]);
		int pos2 = Integer.parseInt(parsed[1]);
		char needle = parsed[2].charAt(0);
		String pw = parsed[3];

		// non-0-based
		boolean pos1Char = pw.charAt(pos1 - 1) == needle;
		boolean pos2Char = pw.charAt(pos2 - 1) == needle;

		return (pos1Char || pos2Char) && !(pos1Char && pos2Char);
	};

	static long part1(List<String> lines) {
		return lines.stream().filter(oldPolicy).count();
	}

	static long part2(List<String> lines) {
		return lines.stream().filter(newPolicy).count();
	}

	public static void main(String[] args) {

		List<String> lines = FileUtils.read("aoc20/002");

		System.out.println(String.format("Part 1: %d", part1(lines)));
		System.out.println(String.format("Part 2: %d", part2(lines)));

	}

}
