package net.stefangaertner.aoc19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import net.stefangaertner.aoc19.util.Advent;

public class Day22 {

	private static final Collector<Integer, ?, List<Integer>> collector = Collectors.toCollection(ArrayList::new);

	public static void main(String[] strings) throws IOException {
		Advent.print(1, part1());
	}

	static long part1() {
		List<String> lines = Advent.read("aoc19/022-data");

		List<Integer> deck = generateDeck(10007);

		List<Integer> shuffled = run(deck, lines);

		return shuffled.indexOf(2019);
	}

	private static List<Integer> run(List<Integer> deck, List<String> lines) {

		List<Integer> retVal = new ArrayList<>(deck);

		for (String line : lines) {

			if (line.equals("deal into new stack")) {
				Collections.reverse(retVal);
			} else if (line.startsWith("deal with increment")) {
				String[] parts = line.split(" ");
				int num = Integer.parseInt(parts[parts.length - 1]);
				retVal = deal(retVal, num);
			} else if (line.startsWith("cut")) {
				String[] parts = line.split(" ");
				int num = Integer.parseInt(parts[parts.length - 1]);
				retVal = cut(retVal, num);
			}

		}

		return retVal;
	}

	private static List<Integer> generateDeck(int i) {
		return IntStream.range(0, i)
				.mapToObj(Integer::valueOf)
				.collect(collector);
	}

	// cut n cards
	private static List<Integer> cut(List<Integer> stack, int n) {

		if (n < 0) {

			int n1 = stack.size() + n;
			return Stream.concat(stack.stream()
					.skip(n1),
					stack.stream()
							.limit(n1))
					.collect(collector);

		} else {

			return Stream.concat(stack.stream()
					.skip(n),
					stack.stream()
							.limit(n))
					.collect(collector);

		}

	}

	private static List<Integer> deal(List<Integer> stack, int n) {

		List<Integer> retVal = new ArrayList<>(stack);

		int max = stack.size();
		int idx = 0;

		for (int i = 0; i < stack.size(); i++) {
			Integer card = stack.get(i);

			retVal.set(idx, card);

			idx += n;

			if (idx >= max) {
				idx %= max;
			}
		}

		return retVal;
	}

}
