package net.stefangaertner.aoc20;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.IntPair;

public class Day05 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/005");
		return lines.stream().map(parse).map(getSeatId).max(Comparator.naturalOrder()).get();
	}

	static long part2() {
		List<String> lines = AOC.read("aoc20/005");
		List<Integer> occupied = lines.stream().map(parse).map(getSeatId).collect(Collectors.toList());

		int minId = occupied.stream().min(Comparator.naturalOrder()).get();
		int maxId = occupied.stream().max(Comparator.naturalOrder()).get();

		return IntStream.range(minId, maxId + 1).filter(id -> !occupied.contains(id)).findFirst().getAsInt();
	}

	private static final int ROWS = 128;
	private static final int COLS = 8;

	// multiply the row by 8, then add the column
	private static Function<IntPair, Integer> getSeatId = pair -> pair.a * 8 + pair.b;

	private static Function<String, IntPair> parse = str -> {
		int row = binSearch(str.substring(0, 7), 0, ROWS, 'F', 'B');
		int col = binSearch(str.substring(7), 0, COLS, 'L', 'R');
		return new IntPair(row, col);
	};

	private static int binSearch(String str, int from, int to, char left, char right) {
		return str.chars().mapToObj(i -> (char) i).reduce(IntPair.of(from, to), (curr, c) -> {
			if (c == left) {
				return IntPair.of(curr.a, curr.a + (curr.b - curr.a) / 2);
			} else if (c == right) {
				return IntPair.of(curr.a + (curr.b - curr.a) / 2, curr.b);
			}
			return curr;
		}, (a, b) -> a = b).a;
	}

}
