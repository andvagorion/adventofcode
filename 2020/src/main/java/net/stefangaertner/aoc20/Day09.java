package net.stefangaertner.aoc20;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.IntPair;

public class Day09 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/009");
		List<Long> numbers = lines.stream().map(s -> Long.valueOf(s)).collect(Collectors.toList());
		return findOddOneOut(numbers, 25);
	}

	static long part2() {
		List<String> lines = AOC.read("aoc20/009");
		List<Long> numbers = lines.stream().map(s -> Long.valueOf(s)).collect(Collectors.toList());
		long oddOne = findOddOneOut(numbers, 25);

		IntPair range = findRange(numbers, oddOne);

		long min = numbers.stream().skip(range.a).limit(range.b - range.a + 1).min(Comparator.naturalOrder()).get();
		long max = numbers.stream().skip(range.a).limit(range.b - range.a + 1).max(Comparator.naturalOrder()).get();

		return min + max;
	}

	private static long findOddOneOut(List<Long> numbers, int preamble) {
		for (int k = preamble; k < numbers.size(); k++) {
			boolean found = false;
			for (int i = k - preamble; i < k; i++) {
				for (int j = k - preamble; j < k; j++) {
					if (numbers.get(i) + numbers.get(j) == numbers.get(k)) {
						found = true;
					}
				}
			}

			if (!found) {
				return numbers.get(k);
			}
		}

		return -1;
	}

	private static IntPair findRange(List<Long> numbers, long oddOne) {
		for (int idx = 0; idx < numbers.size(); idx++) {

			int other = idx + 1;
			long sum = numbers.get(idx);

			while (sum <= oddOne) {
				sum += numbers.get(other);

				if (sum == oddOne) {
					return IntPair.of(idx, other);
				}

				other++;
			}

		}

		return null;
	}

}
