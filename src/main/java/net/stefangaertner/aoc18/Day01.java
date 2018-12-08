package net.stefangaertner.aoc18;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.stefangaertner.util.FileUtils;

public class Day01 {
	
	private static boolean DEBUG = false;

	public static void main(String[] strings) {
		List<String> lines = FileUtils.read("aoc18/001-data");

		part1(lines);
		part2(lines);

	}

	private static void part1(List<String> lines) {
		int result = lines.stream().collect(Collectors.summingInt(Integer::parseInt));
		System.out.println("#1 Sum is " + result);
	}

	private static void part2(List<String> lines) {
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

		System.out.println("#2 Found! " + currentFreq);
	}

}
