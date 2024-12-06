package net.stefangaertner.aoc19;

import java.util.Arrays;

import net.stefangaertner.aoc19.util.Advent;

public class Day02 {

	static String example1 = "1,9,10,3,2,3,11,0,99,30,40,50";

	public static void main(String[] strings) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	public static long part1() {
		String line = Advent.readLine("aoc19/002-data1");

		String[] parts = line.split(",");
		Integer[] codes = Arrays.stream(parts)
				.map(Integer::parseInt)
				.toArray(Integer[]::new);

		// before running the program, replace position 1 with the value 12 and replace
		// position 2 with the value 2.

		codes[1] = 12;
		codes[2] = 2;

		Integer[] result = runProgram(codes);

		// What value is left at position 0 after the
		// program halts?

		// String out =
		// Arrays.stream(codes).map(String::valueOf).collect(Collectors.joining(","));

		return result[0];
	}

	public static long part2() {
		String line = Advent.readLine("aoc19/002-data1");

		// determine what pair of inputs produces the output 19690720
		// Each of the two input values will be between 0 and 99, inclusive.

		for (int n0 = 0; n0 < 99; n0++) {
			for (int n1 = 0; n1 < 99; n1++) {

				String current = new String(line);

				String[] parts = current.split(",");
				Integer[] codes = Arrays.stream(parts)
						.map(Integer::parseInt)
						.toArray(Integer[]::new);

				codes[1] = n0;
				codes[2] = n1;

				Integer[] result = runProgram(codes);

				if (result[0] == 19690720) {

					// What is 100 * noun + verb?
					return (100 * n0 + n1);

				}
			}
		}

		return -1;
	}

	private static Integer[] runProgram(Integer[] codes) {
		for (int i = 0; i < codes.length; i += 4) {
			if (codes[i] == 99) {
				break;
			}

			if (codes[i] == 1) {
				// add
				int sum = codes[codes[i + 1]] + codes[codes[i + 2]];
				codes[codes[i + 3]] = sum;
			}

			if (codes[i] == 2) {
				// mult
				int prod = codes[codes[i + 1]] * codes[codes[i + 2]];
				codes[codes[i + 3]] = prod;
			}
		}

		return codes;
	}

}
