package net.stefangaertner.aoc20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.StringUtils;

public class Day23 {

	static boolean DEBUG = false;

	public static void main(String[] args) {
		AOC.print(1, part1());
	}

	static LinkedList<Integer> example = Arrays.asList(3, 8, 9, 1, 2, 5, 4, 6, 7).stream()
			.collect(Collectors.toCollection(LinkedList::new));
	static LinkedList<Integer> config = Arrays.asList(9, 6, 3, 2, 7, 5, 4, 8, 1).stream()
			.collect(Collectors.toCollection(LinkedList::new));

	static String part1() {

		config = calc(config, 10);

		int skip = config.indexOf(1) + 1;
		if (skip >= config.size()) {
			skip = 0;
		}

		if (DEBUG) {
			System.out.println(config);
			System.out.println(skip);
		}
		List<Integer> out = new ArrayList<>();
		out.addAll(config.stream().skip(skip).collect(Collectors.toList()));
		out.addAll(config.stream().limit(skip).collect(Collectors.toList()));

		return out.stream().filter(num -> num.intValue() != 1).map(String::valueOf).collect(Collectors.joining());
	}

	private static LinkedList<Integer> calc(LinkedList<Integer> config, int cycles) {

		int max = config.size();
		int idx = 0;

		for (int cycle = 0; cycle < cycles; cycle++) {

			if (DEBUG && cycle > 0 && cycle % (cycles / 10) == 0) {
				System.out.println(((double) cycle / cycles) * 100 + "%");
			}

			int numAtIndex = config.get(idx);

			if (DEBUG) {
				System.out.println("-- move " + (cycle + 1) + " --");
				StringUtils.printWithSelected(config, config.get(idx));
			}

			List<Integer> pickedUp = new LinkedList<>();
			for (int i = 1; i < 4; i++) {
				int pidx = (idx + i) % config.size();
				pickedUp.add(config.get(pidx));
			}

			if (DEBUG) {
				System.out.println("pick up: " + pickedUp);
			}

			config.removeAll(pickedUp);
			int destination = numAtIndex - 1;
			if (destination <= 0) {
				destination = max;
			}
			while (pickedUp.contains(destination)) {
				destination--;
				if (destination <= 0) {
					destination = max;
				}
			}

			if (DEBUG) {
				System.out.println("destination: " + destination);
			}

			int destIndex = config.indexOf(destination) + 1;

			for (int i = pickedUp.size() - 1; i >= 0; i--) {
				config.add(destIndex, pickedUp.get(i));
			}

			idx = config.indexOf(numAtIndex);
			idx++;
			if (idx >= config.size()) {
				idx = 0;
			}

			if (DEBUG) {
				System.out.println();
			}
		}

		return config;
	}

}
