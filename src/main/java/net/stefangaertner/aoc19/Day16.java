package net.stefangaertner.aoc19;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.util.FileUtils;

public class Day16 {

	private static int[] basePattern = new int[] { 0, 1, 0, -1 };

	public static void main(String[] strings) throws IOException {

		List<String> lines = FileUtils.read("aoc19/016-data");
		String inputStr = lines.get(0);

//		System.out.println(getFirst8(calc("80871224585914546619083218645595", 100)));
//		System.out.println(getFirst8(calc("19617804207202209144916044189917", 100)));
//		System.out.println(getFirst8(calc("69317163492948606335995924319873", 100)));

		System.out.println("Part 1: " + getFirst8(calc(inputStr, 100)));

		// printFirst8(calc(StringUtils.repeat(inputStr, 10000), 100));

	}

	private static int[] calc(String inputStr, int phases) {
		int[] arr1 = parse(inputStr);
		int[] arr2 = new int[arr1.length];

		for (int times = 0; times < phases; times++) {

//			long start = System.currentTimeMillis();
//			System.out.print("running iteration " + (times + 1));

			boolean swapped = times % 2 == 1;

			int[] in = swapped ? arr2 : arr1;
			int[] out = swapped ? arr1 : arr2;

			for (int phase = 0; phase < in.length; phase++) {

				int sum = 0;

				// pattern for 1s, starts at i
				int pointer = phase;
				while (pointer < in.length) {

					for (int i = pointer; i < pointer + (phase + 1) && i < in.length; i++) {
						// System.out.println("adding " + in[i]);
						sum += in[i];
					}

					pointer += (phase + 1) * 4;
				}

				// pattern for -1s
				pointer = ((phase + 1) * 3) - 1;
				while (pointer < in.length) {

					for (int i = pointer; i < pointer + (phase + 1) && i < in.length; i++) {
						// System.out.println("subtracting " + in[i]);
						sum -= in[i];
					}

					pointer += (phase + 1) * 4;
				}

				sum %= 10;
				out[phase] = Math.abs(sum);

			}

//			long end = System.currentTimeMillis();
//			System.out.println(" " + (end - start) + " ms");
		}

		return (phases % 2 == 0) ? arr1 : arr2;

	}

	private static int getOffset(String inputStr) {
		String offsetStr = inputStr.substring(0, 7);
		return Integer.parseInt(offsetStr);
	}

	private static int[] parse(String string) {
		int[] x = new int[string.length()];
		for (int i = 0; i < string.length(); i++) {
			x[i] = Integer.parseInt(string.substring(i, i + 1));
		}

		return x;
	}

	private static String getFirst8(int[] arr) {
		return Arrays.stream(arr).limit(8).mapToObj(String::valueOf).collect(Collectors.joining());
	}

}
