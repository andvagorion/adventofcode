package net.stefangaertner.aoc20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.IntPair;

public class Day13 {

	public static void main(String[] args) {
		AOC.print(1, part1());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/013");
		int earliestTime = Integer.parseInt(lines.get(0));

		int[] busTimes = Arrays.stream(lines.get(1).split(",")).filter(str -> !"x".equals(str))
				.mapToInt(Integer::parseInt).toArray();

		int val = -1;
		int min = Integer.MAX_VALUE;
		for (int i : busTimes) {
			int times = earliestTime / i;
			int d2 = (times + 1) * i - earliestTime;
			if (d2 < min) {
				val = i;
				min = d2;
			}
		}

		return val * min;
	}

	static long part2() {
		List<String> lines = AOC.read("aoc20/013");
		String[] nums = lines.get(1).split(",");

		List<IntPair> pairs = new ArrayList<>();
		for (int i = 0; i < nums.length; i++) {
			String num = nums[i];
			if ("x".equals(num)) {
				continue;
			}
			int n = Integer.parseInt(num);
			pairs.add(IntPair.of(n, i));
		}

		long largest = pairs.stream().map(p -> p.a).max(Comparator.naturalOrder()).get();
		long val = largest * 30000;

		while (val < Long.MAX_VALUE) {

			long t = val - val % pairs.get(0).a;

			boolean found = true;

			for (IntPair pair : pairs) {
				long num = pair.a;
				long diff = pair.b;

				boolean match = (t + diff) % num == 0;

				if (!match) {
					found = false;
					break;
				}
			}

			if (found) {
				return t;
			}

			val += largest;
		}

		return -1;
	}

}
