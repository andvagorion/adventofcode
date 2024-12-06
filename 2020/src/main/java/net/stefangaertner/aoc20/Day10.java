package net.stefangaertner.aoc20;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import net.stefangaertner.aoc20.util.AOC;

public class Day10 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/010");
		Deque<Long> adapters = lines.stream().map(s -> Long.valueOf(s)).sorted()
				.collect(Collectors.toCollection(LinkedList::new));

		// device always has joltage of 3 higher than highest adapter
		adapters.addLast(adapters.getLast() + 3);

		Map<Long, Long> differences = new HashMap<>();

		long joltage = 0;

		while (!adapters.isEmpty()) {
			long adapter = adapters.pop();

			long diff = adapter - joltage;
			differences.merge(diff, 1L, (a, b) -> a + b);

			joltage = adapter;
		}

		return differences.getOrDefault(1L, 0L) * differences.getOrDefault(3L, 0L);
	}

	static long part2() {
		List<String> lines = AOC.read("aoc20/010-example1");
		Deque<Long> adapters = lines.stream().map(s -> Long.valueOf(s)).sorted()
				.collect(Collectors.toCollection(LinkedList::new));
		adapters.addFirst(0L);
		adapters.addLast(adapters.getLast() + 3);

		// take prev and next and permutate
		Set<String> perms = new HashSet<>();

		while (!adapters.isEmpty()) {
			long val = adapters.pop();
			Deque<Long> nums = adapters.stream().filter(j -> j > val && j <= val + 3).sorted()
					.collect(Collectors.toCollection(LinkedList::new));

			if (nums.size() == 2) {
				long first = val;
				long second = nums.pop();
				long third = nums.pop();

				perms.add(String.format("%s %s %s", first, second, third));
				perms.add(String.format("%s %s", first, third));

				// first, second only if gap to next is valid
				if (adapters.peek() - second <= 3) {
					perms.add(String.format("%s %s", first, second));
				}
			}

			if (nums.size() == 3) {
				long first = val;
				long second = nums.pop();
				long third = nums.pop();
				long fourth = nums.pop();

				perms.add(String.format("%s %s %s %s", first, second, third, fourth));
				perms.add(String.format("%s %s %s", first, second, fourth));
				perms.add(String.format("%s %s %s", first, third, fourth));
				perms.add(String.format("%s %s", first, fourth));

				if (adapters.peek() - second <= 3) {
					perms.add(String.format("%s %s", first, second));
				}

				if (adapters.peek() - third <= 3) {
					perms.add(String.format("%s %s", first, third));
				}
			}
		}

		return perms.size();
	}

}
