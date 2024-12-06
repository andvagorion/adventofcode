package net.stefangaertner.aoc18;

import java.util.ArrayList;
import java.util.List;

import net.stefangaertner.aoc18.util.Advent;

public class Day14 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static String part1() {
		int after = 409551;
		boolean debug = false;

		// score 0 - 9
		List<Integer> output = new ArrayList<>();
		output.add(3);
		output.add(7);

		int[] currentRecipe = new int[] { 0, 1 };

		while (output.size() < (after + 10)) {

			// bake and add score to list
			int sum = 0;
			for (int r : currentRecipe) {
				sum += output.get(r);
			}

			if (sum >= 10) {
				output.add(1);
				output.add(sum % 10);
			} else {
				output.add(sum);
			}

			// look for next recipe
			for (int r = 0; r < currentRecipe.length; r++) {
				currentRecipe[r] = currentRecipe[r] + output.get(currentRecipe[r]) + 1;
				currentRecipe[r] %= output.size();
			}

		}

		if (debug)
			print(output, currentRecipe);

		String retVal = "";
		for (int i = after; i < after + 10; i++) {
			retVal += output.get(i);
		}

		return retVal;
	}

	static long part2() {
		String needle = "409551";
		boolean debug = false;

		List<Integer> output = new ArrayList<>();
		output.add(3);
		output.add(7);

		int[] currentRecipe = new int[] { 0, 1 };

		int c = 0;

		while (true) {

			// bake and add score to list
			int sum = 0;
			for (int r : currentRecipe) {
				sum += output.get(r);
			}

			if (sum >= 10) {
				output.add(1);
				output.add(sum % 10);
			} else {
				output.add(sum);
			}

			// look for next recipe
			for (int r = 0; r < currentRecipe.length; r++) {
				currentRecipe[r] = currentRecipe[r] + output.get(currentRecipe[r]) + 1;
				currentRecipe[r] %= output.size();
			}

			if (output.size() > needle.length()) {

				int start = output.size() - needle.length() - 1;

				String check = "";
				for (int i = 0; i <= needle.length(); i++) {
					check += output.get(start + i);
				}

				if (check.startsWith(needle)) {
					return output.size() - needle.length() - 1;
				} else if (check.endsWith(needle)) {
					return output.size() - needle.length();
				}
			}

			if (debug && c % 100000 == 0) {
				System.out.println("iterations: " + c);
			}

			c++;
		}
	}

	private static void print(List<Integer> output, int[] currentRecipe) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < output.size(); i++) {
			if (i == currentRecipe[0])
				sb.append("(" + output.get(i) + ")");
			else if (i == currentRecipe[1])
				sb.append("[" + output.get(i) + "]");
			else
				sb.append(" " + output.get(i) + " ");
		}

		System.out.println(sb.toString());
	}

}
