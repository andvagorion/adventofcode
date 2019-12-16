package net.stefangaertner.aoc19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import net.stefangaertner.aoc18.pojo.Pair;
import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.util.ArrayUtils;
import net.stefangaertner.util.FileUtils;
import net.stefangaertner.util.PairUtils;
import net.stefangaertner.util.StringUtils;

public class Day16 {

	private static int[] basePattern = new int[] { 0, 1, 0, -1 };

	public static void main(String[] strings) throws IOException {
		
		// part1();
		part2();
		
	}
	
	private static void part1() {
		
		List<String> lines = FileUtils.read("aoc19/016-data");
		String inputStr = lines.get(0);
		
		int[] input = parse(inputStr);
		int max = 100;

		for (int t = 0; t < max; t++) {

			int[] out = new int[input.length];

			for (int i = 0; i < input.length; i++) {

				// base pattern repeat * i
				// shift 1 left
				int[] pattern = widen(basePattern, i + 1, input.length);

				int sum = 0;

				for (int j = 0; j < input.length; j++) {
					int x = input[j] * pattern[j];
					sum += x;
				}

				sum %= 10;

				if (sum < 0) {
					sum *= -1;
				}

				out[i] = sum;
			}

			input = out;

		}
		
		// take first 8 digits
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			sb.append(input[i]);
		}
		
		System.out.println(sb.toString());
		
		// System.out.println(Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining("")));
	}
	
	private static void part2() {

		List<String> lines = FileUtils.read("aoc19/016-data");
		String inputStr = lines.get(0);
		
		//inputStr = "03036732577212944063491565474664";
		
		String offsetStr = inputStr.substring(0, 7);
		int offset = Integer.parseInt(offsetStr);
		
		System.out.println(inputStr.length());
		
		// repeat 10000 times
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10000; i++) {
			sb.append(inputStr);
		}
		
		inputStr = sb.toString();
		
		System.out.println(inputStr.length());
		
		int[] input = parse(inputStr);
		int max = 100;

		for (int t = 0; t < max; t++) {
			
			System.out.println((t + 1) + " times");

			int[] out = new int[input.length];

			for (int i = 0; i < input.length; i++) {
				
				if (i % 10000 == 0) {
					System.out.println(i);
				}

				// base pattern repeat * i
				// shift 1 left
				int[] pattern = widen(basePattern, i + 1, input.length);

				int sum = 0;

				for (int j = 0; j < input.length; j++) {
					int x = input[j] * pattern[j];
					sum += x;
				}

				sum %= 10;

				if (sum < 0) {
					sum *= -1;
				}

				out[i] = sum;
			}

			input = out;

		}
		
		// take first 8 digits
		sb = new StringBuilder();
		for (int i = offset; i < offset + 8; i++) {
			sb.append(input[i]);
		}
		
		System.out.println(sb.toString());
		
	}

	private static int[] widen(int[] pattern, int times, int len) {
		int[] ret = new int[len + 1];

		int patternIdx = 0;
		outer: for (int i = 0; i <= len; i++) {

			for (int j = 0; j < times; j++) {
				int pos = i * times + j;
				if (pos > len) {
					break outer;
				}
				ret[pos] = pattern[patternIdx];
			}

			patternIdx++;
			patternIdx %= pattern.length;
		}

		int[] out = new int[ret.length - 1];
		System.arraycopy(ret, 1, out, 0, out.length);

		return out;
	}

	private static int[] parse(String string) {
		int[] x = new int[string.length()];
		for (int i = 0; i < string.length(); i++) {
			x[i] = Integer.parseInt(string.substring(i, i + 1));
		}

		return x;
	}

}
