package net.stefangaertner.aoc19;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.stefangaertner.aoc19.util.Advent;

public class Day08 {

	private static final boolean DEBUG = false;

	public static void main(String[] strings) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		String image = Advent.readLine("aoc19/008-data1");

		// find layer with fewest '0' digits
		long minZeroes = Integer.MAX_VALUE;
		long product = 0;

		int layerSize = 25 * 6;

		int layers = image.length() / layerSize;
		for (int i = 0; i < layers; i++) {
			int n0 = layerSize * i;
			int n1 = n0 + layerSize;

			long zeroes = image.subSequence(n0, n1)
					.chars()
					.filter(c -> c == '0')
					.count();
			if (zeroes < minZeroes) {
				minZeroes = zeroes;
				long ones = image.subSequence(n0, n1)
						.chars()
						.filter(c -> c == '1')
						.count();
				long twos = image.subSequence(n0, n1)
						.chars()
						.filter(c -> c == '2')
						.count();
				product = ones * twos;
			}

		}

		return product;
	}

	static String part2() {
		String image = Advent.readLine("aoc19/008-data1");

		int width = 25;
		int height = 6;

		char black = ' ';
		char white = '0';

		int[][][] decoded = decode(image, width, height);

		if (DEBUG)
			print(decoded);

		int layers = decoded.length;

		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				for (int l = 0; l < layers; l++) {
					int val = decoded[l][y][x];
					if (val == 2) {
						continue;
					}
					if (val == 0) {
						sb.append(black);
					}
					if (val == 1) {
						sb.append(white);
					}
					break;
				}

			}
			sb.append("\n");
		}

		System.out.println(sb.toString());
		return "EBZUR";
	}

	private static int[][][] decode(String image, int width, int height) {

		int layers = image.length() / (width * height);
		int[][][] retVal = new int[layers][][];

		for (int layer = 0; layer < layers; layer++) {

			retVal[layer] = new int[height][];

			for (int y = 0; y < height; y++) {

				int n0 = (layer * height * width) + (width * y);
				int n1 = n0 + width;

				retVal[layer][y] = image.substring(n0, n1)
						.chars()
						.map(Character::getNumericValue)
						.toArray();

			}

		}

		return retVal;
	}

	private static void print(int[][][] img) {

		for (int layer = 0; layer < img.length; layer++) {

			System.out.println("layer " + (layer + 1));

			int[][] imgLayer = img[layer];
			print(imgLayer);
		}
	}

	private static void print(int[][] layer) {
		for (int row = 0; row < layer.length; row++) {
			System.out.println(Arrays.stream(layer[row])
					.mapToObj(String::valueOf)
					.collect(Collectors.joining("")));
		}
	}
}
