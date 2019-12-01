package net.stefangaertner.aoc19;

import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.util.FileUtils;

public class Day01 {

	// example1
	// 2 + 2 + 654 + 33583 = 34241

	// example2
	// 2 + 966 + 50346 = 51314

	public static void main(String[] strings) {
		List<String> lines = FileUtils.read("aoc19/001-data1");

		long sum1 = lines.stream().map(Long::valueOf).map(Day01::calculateEnergy)
				.collect(Collectors.summarizingLong(l -> l)).getSum();
		System.out.println("Part 1: " + sum1);

		long sum2 = lines.stream().map(Long::valueOf).map(Day01::calculateExcessEnergy)
				.collect(Collectors.summarizingLong(l -> l)).getSum();
		System.out.println("Part 2: " + sum2);
	}

	/**
	 * @param mass Mass of module
	 * @return Returns the energy for the module (specified by its mass).
	 */
	public static long calculateEnergy(long mass) {
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
	public static long calculateExcessEnergy(long mass) {
		long sum = 0;
		long massLeft = mass;
		while (calculateEnergy(massLeft) > 0) {
			massLeft = calculateEnergy(massLeft);
			sum += massLeft;
		}
		return sum;
	}
}
