package net.stefangaertner.aoc19;

import net.stefangaertner.aoc19.util.Advent;

public class Day04 {

	public static void main(String[] strings) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		// six-digit number.
		// within the range given in your puzzle input
		int start = 125730;
		int end = 579381;

		long count1 = 0;

		while (start <= end) {

			if (isValid(start)) {
				count1++;
			}

			start++;
		}

		return count1;
	}

	static long part2() {
		long count2 = 0;

		// six-digit number.
		// within the range given in your puzzle input
		int start = 125730;
		int end = 579381;

		while (start <= end) {

			if (isValid(start) && isValid2(start)) {
				count2++;
			}

			start++;
		}

		return count2;
	}

	private static boolean isValid(int num) {

		// Two adjacent digits are the same (like 22 in 122345).
		// Going from left to right, the digits never decrease; they only ever increase
		// or stay the same (like 111123 or 135679).

		String pw = String.valueOf(num);

		for (int i = 0; i < pw.length() - 1; i++) {
			if (pw.charAt(i) > pw.charAt(i + 1)) {
				return false;
			}
		}

		boolean same = false;
		if (pw.contains("00") || pw.contains("11") || pw.contains("22") || pw.contains("33") || pw.contains("44")
				|| pw.contains("55") || pw.contains("66") || pw.contains("77") || pw.contains("88")
				|| pw.contains("99")) {
			same = true;
		}

		return same;
	}

	private static boolean isValid2(int num) {

		// the two adjacent matching digits are not part of a larger group of matching
		// digits

		String test = "x" + String.valueOf(num) + "x";

		for (int i = 1; i < test.length() - 2; i++) {
			boolean prevSame = test.charAt(i - 1) == test.charAt(i);
			boolean currSame = test.charAt(i) == test.charAt(i + 1);
			boolean nextSame = test.charAt(i + 1) == test.charAt(i + 2);

			if (currSame && !prevSame && !nextSame) {
				return true;
			}

		}

		return false;

	}

}
