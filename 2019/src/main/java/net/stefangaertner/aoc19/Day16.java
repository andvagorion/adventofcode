package net.stefangaertner.aoc19;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.stefangaertner.aoc19.util.Advent;

public class Day16 {

	public static void main(String[] strings) {
		Advent.print(1, part1());
	}

	static long part1() {
		String input = Advent.readLine("aoc19/016-data");

		String str = getFirst8(calc(input, 100));
		return Long.valueOf(str);
	}

	private static int[] calc(String inputStr, int phases) {
		int[] arr1 = parse(inputStr);
		int[] arr2 = new int[arr1.length];

		for (int times = 0; times < phases; times++) {

			boolean swapped = times % 2 == 1;

			int[] in = swapped ? arr2 : arr1;
			int[] out = swapped ? arr1 : arr2;

			for (int phase = 0; phase < in.length; phase++) {

				int sum = 0;

				// pattern for 1s, starts at i
				int pointer = phase;
				while (pointer < in.length) {

					for (int i = pointer; i < pointer + (phase + 1) && i < in.length; i++) {
						sum += in[i];
					}

					pointer += (phase + 1) * 4;
				}

				// pattern for -1s
				pointer = ((phase + 1) * 3) - 1;
				while (pointer < in.length) {

					for (int i = pointer; i < pointer + (phase + 1) && i < in.length; i++) {
						sum -= in[i];
					}

					pointer += (phase + 1) * 4;
				}

				sum %= 10;
				out[phase] = Math.abs(sum);

			}

		}

		return (phases % 2 == 0) ? arr1 : arr2;

	}

	private static int[] parse(String string) {
		int[] x = new int[string.length()];
		for (int i = 0; i < string.length(); i++) {
			x[i] = Integer.parseInt(string.substring(i, i + 1));
		}

		return x;
	}

	private static String getFirst8(int[] arr) {
		return Arrays.stream(arr)
				.limit(8)
				.mapToObj(String::valueOf)
				.collect(Collectors.joining());
	}

}
