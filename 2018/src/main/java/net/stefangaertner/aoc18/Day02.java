package net.stefangaertner.aoc18;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.stefangaertner.aoc18.util.Advent;

public class Day02 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = Advent.read("aoc18/002-data");

		Function<String, Map<Integer, Integer>> freq = line -> line.chars()
				.boxed()
				.collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
		Function<Map<Integer, Integer>, int[]> ints = m -> new int[] { m.containsValue(2) ? 1 : 0,
				m.containsValue(3) ? 1 : 0 };
		int[] res = lines.stream()
				.map(freq)
				.map(ints)
				.reduce((a, b) -> new int[] { a[0] + b[0], a[1] + b[1] })
				.get();
		int mult = res[0] * res[1];

		return mult;
	}

	static String part2() {
		List<String> lines = Advent.read("aoc18/002-data");
		String s1 = null;
		String s2 = null;

		int max = 0;

		for (String current : lines) {
			for (String other : lines) {
				if (current.equals(other)) {
					continue;
				}

				int same = getSharedCharCount(current, other);

				if (same > max) {
					max = same;
					s1 = current;
					s2 = other;
				}
			}
		}

		System.out.println("#2 Match with " + max + " same characters");
		System.out.println(s1 + " / " + s2 + " => " + getSharedChars(s1, s2));

		return getSharedChars(s1, s2);
	}

	private static String getSharedChars(String s1, String s2) {
		char[] c1 = s1.toCharArray();
		char[] c2 = s2.toCharArray();

		String s = "";

		for (int i = 0; i < c1.length; i++) {
			if (c1[i] == c2[i]) {
				s += c1[i];
			}
		}

		return s;
	}

	private static int getSharedCharCount(String s1, String s2) {
		char[] c1 = s1.toCharArray();
		char[] c2 = s2.toCharArray();

		int sum = 0;

		for (int i = 0; i < c1.length; i++) {
			if (c1[i] == c2[i]) {
				sum++;
			}
		}

		return sum;
	}
}
