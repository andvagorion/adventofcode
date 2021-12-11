package net.stefangaertner.aoc18;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.stefangaertner.aoc18.util.Advent;

public class Day01 {

	private static boolean DEBUG = false;

	public static void main(String[] strings) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = Advent.read("aoc18/001-data");
		return lines.stream()
				.collect(Collectors.summingInt(Integer::parseInt));
	}

	static long part2() {
		List<String> lines = Advent.read("aoc18/001-data");

		Set<Integer> freqs = new HashSet<>();
		int currentFreq = 0;

		int count = 0;
		boolean found = false;

		while (!found) {

			int i = count % lines.size();
			int k = Integer.parseInt(lines.get(i));
			currentFreq = currentFreq + k;

			if (freqs.contains(currentFreq)) {
				break;
			}

			freqs.add(currentFreq);
			count++;

			if (DEBUG && count % 100 == 0) {
				System.out.println(count + " times, " + freqs.size() + " frequencies");
			}
		}

		return currentFreq;
	}

}
