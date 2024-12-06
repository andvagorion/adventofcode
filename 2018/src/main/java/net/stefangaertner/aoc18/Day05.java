package net.stefangaertner.aoc18;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.aoc18.util.Advent;
import net.stefangaertner.aoc18.util.StringUtils;

public class Day05 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		String input = Advent.readLine("aoc18/005-data");

		List<Character> characters = input.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
		int len = compute(characters);

		return len;
	}

	static long part2() {
		String input = Advent.readLine("aoc18/005-data");

		List<Character> characters = input.chars().mapToObj(i -> (char) i).collect(Collectors.toList());

		int min = input.length();

		// System.out.print("Checking... ");

		for (int i = 0; i < StringUtils.ALPHABET.length(); i++) {
			char lower = StringUtils.ALPHABET.charAt(i);
			char upper = StringUtils.ALPHABET.toUpperCase().charAt(i);

			// System.out.print(lower + "/" + upper);

			List<Character> removed = new ArrayList<>();

			for (char c : characters) {
				if (c == lower || c == upper) {
					continue;
				}
				removed.add(c);
			}

			int size = compute(removed);

			if (size < min) {
				min = size;
			}

		}

		return min;
	}

	public static int compute(List<Character> strList) {

		List<Character> finalList = new ArrayList<>();
		finalList.addAll(strList);

		boolean finished = false;

		while (!finished) {
			List<Character> reduced = reduce(finalList);
			finished = reduced.size() == finalList.size();
			finalList = reduced;
		}

		return finalList.size();
	}

	public static List<Character> reduce(List<Character> list) {

		List<Character> input = new ArrayList<>();
		input.addAll(list);
		input.add('-');

		List<Character> strList = new ArrayList<>();

		int i = 0;
		while (i < input.size() - 1) {
			char c1 = input.get(i);
			char c2 = input.get(i + 1);

			if (c1 != c2 && (c1 + 32 == c2 || c2 + 32 == c1)) {
				i++;
			} else {
				strList.add(c1);
			}

			i++;
		}

		return strList;
	}

}
