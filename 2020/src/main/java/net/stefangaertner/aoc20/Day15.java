package net.stefangaertner.aoc20;

import java.util.HashMap;
import java.util.Map;

import net.stefangaertner.aoc20.util.AOC;

public class Day15 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		return play(new long[] { 1, 17, 0, 10, 18, 11, 6 }, 2020);
	}

	static long part2() {
		return play(new long[] { 1, 17, 0, 10, 18, 11, 6 }, 30000000);
	}

	private static long play(long[] startingNumbers, long max) {
		Map<Long, Long> prevSpoken = new HashMap<>();
		Map<Long, Long> lastSpoken = new HashMap<>();

		for (int i = 0; i < startingNumbers.length; i++) {
			lastSpoken.put(startingNumbers[i], (long) i + 1);
		}

		long lastNum = startingNumbers[startingNumbers.length - 1];

		for (long turn = startingNumbers.length + 1; turn <= max; turn++) {

			long nextNum = -1;

			if (!prevSpoken.containsKey(lastNum)) {

				nextNum = 0;

				prevSpoken.put(nextNum, lastSpoken.getOrDefault(nextNum, turn));
				lastSpoken.put(nextNum, turn);

			} else {

				long prev = prevSpoken.get(lastNum);
				long last = lastSpoken.get(lastNum);

				nextNum = last - prev;

				if (lastSpoken.containsKey(nextNum)) {
					prevSpoken.put(nextNum, lastSpoken.get(nextNum));
				}
				lastSpoken.put(nextNum, turn);
			}

			lastNum = nextNum;

		}

		return lastNum;
	}

}
