package net.stefangaertner.aoc20;

import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.util.FileUtils;

public class Day01 {

	private static int part1(List<String> lines) {
		List<Integer> vals = lines.stream().map(Integer::valueOf).collect(Collectors.toList());
		List<Integer> out = vals.stream()
				.filter(i1 -> vals.stream().filter(i2 -> i1 + i2 == 2020).findAny().orElse(0) * i1 != 0)
				.collect(Collectors.toList());
		return out.stream().reduce(1, (a, b) -> a * b);
	}

	private static int part2(List<String> lines) {
		List<Integer> vals = lines.stream().map(Integer::valueOf).collect(Collectors.toList());
		List<Integer> out = vals.stream()
				.filter(i1 -> vals.stream()
						.filter(i2 -> vals.stream().filter(i3 -> i3 + i2 + i1 == 2020).findAny().orElse(0) != 0)
						.findAny().orElse(0) != 0)
				.collect(Collectors.toList());
		return out.stream().reduce(1, (a, b) -> a * b);
	}

	public static void main(String[] args) {

		List<String> lines = FileUtils.read("aoc20/001");

		System.out.println(String.format("Part 1: %d", part1(lines)));
		System.out.println(String.format("Part 1: %d", part2(lines)));

	}

}
