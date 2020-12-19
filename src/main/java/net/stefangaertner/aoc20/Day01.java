package net.stefangaertner.aoc20;

import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.util.Advent;
import net.stefangaertner.util.FileUtils;

public class Day01 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	public static long part1() {
		List<String> lines = FileUtils.read("aoc20/001");

		List<Integer> vals = lines.stream()
				.map(Integer::valueOf)
				.collect(Collectors.toList());
		List<Integer> out = vals.stream()
				.filter(i1 -> vals.stream()
						.filter(i2 -> i1 + i2 == 2020)
						.findAny()
						.orElse(0) * i1 != 0)
				.collect(Collectors.toList());

		return out.stream()
				.reduce(1, (a, b) -> a * b);
	}

	public static long part2() {
		List<String> lines = FileUtils.read("aoc20/001");

		List<Integer> vals = lines.stream()
				.map(Integer::valueOf)
				.collect(Collectors.toList());
		List<Integer> out = vals.stream()
				.filter(i1 -> vals.stream()
						.filter(i2 -> vals.stream()
								.filter(i3 -> i3 + i2 + i1 == 2020)
								.findAny()
								.orElse(0) != 0)
						.findAny()
						.orElse(0) != 0)
				.collect(Collectors.toList());

		return out.stream()
				.reduce(1, (a, b) -> a * b);
	}

}
