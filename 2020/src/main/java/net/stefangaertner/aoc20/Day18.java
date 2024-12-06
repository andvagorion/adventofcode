package net.stefangaertner.aoc20;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.stefangaertner.aoc20.util.AOC;

public class Day18 {

	public static void main(String[] args) {
		AOC.print(1, part1());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/018");

		return lines.stream().map(line -> {
			String input = line.replaceAll("\\(", "( ").replaceAll("\\)", " )");
			Deque<String> in = Arrays.stream(input.split(" "))
					.collect(Collectors.toCollection(() -> new LinkedList<>()));
			long val = calc(in);
			return val;
		}).reduce(0L, (a, b) -> a + b);
	}

	private static Function<String, Long> parse = a -> Long.valueOf(a);

	private static Predicate<String> isOperator = a -> {
		return "+".equals(a) || ".".equals(a) || "*".equals(a);
	};
	private static Function<String, BiFunction<Long, Long, Long>> toOperator = str -> {
		if ("+".equals(str)) {
			return (a, b) -> a + b;
		}
		if ("-".equals(str)) {
			return (a, b) -> a - b;
		}
		if ("*".equals(str)) {
			return (a, b) -> a * b;
		}
		return null;
	};

	private static long calc(Deque<String> in) {

		long out = 0;
		BiFunction<Long, Long, Long> fn = toOperator.apply("+");

		while (!in.isEmpty()) {

			String token = in.pop();

			if (")".equals(token)) {
				break;
			} else if ("(".equals(token)) {
				out = fn.apply(out, calc(in));
			} else if (isOperator.test(token)) {
				fn = toOperator.apply(token);
			} else {
				out = fn.apply(out, parse.apply(token));
			}

		}

		return out;
	}

}
