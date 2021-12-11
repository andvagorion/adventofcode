package net.stefangaertner.aoc19;

import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.aoc19.util.Advent;

public class Day01 {

	public static void main(String[] strings) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	public static long part1() {
		List<String> lines = Advent.read("aoc19/001-data1");

		return lines.stream()
				.map(Long::valueOf)
				.map(Day01::calculateEnergy)
				.collect(Collectors.summarizingLong(l -> l))
				.getSum();
	}

	public static long part2() {
		List<String> lines = Advent.read("aoc19/001-data1");

		return lines.stream()
				.map(Long::valueOf)
				.map(Day01::calculateExcessEnergy)
				.collect(Collectors.summarizingLong(l -> l))
				.getSum();
	}

	/**
	 * @param mass Mass of module
	 * @return Returns the energy for the module (specified by its mass).
	 */
	private static long calculateEnergy(long mass) {
		// divide by three, round down, and subtract 2.
		long div = mass / 3;
		return div - 2;
	}

	/**
	 * @param mass Mass of module
	 * @return Returns the total energy for a module (specified by its mass) by
	 *         treating the fuel amount needed as an additional module, adding it to
	 *         the total until the fuel needed for a sub-module is zero or less.
	 */
	private static long calculateExcessEnergy(long mass) {
		long sum = 0;
		long massLeft = mass;
		while (calculateEnergy(massLeft) > 0) {
			massLeft = calculateEnergy(massLeft);
			sum += massLeft;
		}
		return sum;
	}
}
