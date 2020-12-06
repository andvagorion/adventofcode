package net.stefangaertner.aoc20;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.stefangaertner.util.FileUtils;

public class Day06 {

	public static void main(String[] args) {
		List<List<String>> groups = FileUtils.readGroups("aoc20/006");

		System.out.println(String.format("Part 1: %d", part1(groups)));
		System.out.println(String.format("Part 2: %d", part2(groups)));
	}

	static long part1(List<List<String>> groups) {
		return groups.stream().map(group -> {
			return group.stream().map(Day06::toSet).reduce(new HashSet<>(), (a, b) -> {
				a.addAll(b);
				return a;
			}).size();
		}).reduce(0, (a, b) -> a + b);
	}

	static long part2(List<List<String>> groups) {
		return groups.stream().map(group -> {
			return group.stream().map(Day06::toSet).reduce(new HashSet<>(alphabeth), (a, b) -> {
				a.retainAll(b);
				return a;
			}).size();
		}).reduce(0, (a, b) -> a + b);
	}

	private static Set<Character> alphabeth = IntStream.range('a', 'z' + 1).mapToObj(i -> (char) i)
			.collect(Collectors.toSet());

	private static Set<Character> toSet(String str) {
		return str.chars().mapToObj(i -> (char) i).collect(Collectors.toSet());
	}
}
