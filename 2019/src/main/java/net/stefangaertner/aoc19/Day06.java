package net.stefangaertner.aoc19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.stefangaertner.aoc19.util.Advent;

public class Day06 {

	public static void main(String[] strings) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = Advent.read("aoc19/006-data1");

		// child -> parent
		Map<String, String> orbits = parseOrbits(lines);

		int count = 0;

		for (String p : orbits.keySet()) {
			count += determineParentCount(p, orbits);
		}

		return count;
	}

	static long part2() {
		List<String> lines = Advent.read("aoc19/006-data1");

		// child -> parent
		Map<String, String> orbits = parseOrbits(lines);

		List<String> parentsYOU = getParents(orbits, "YOU");
		List<String> parentsSAN = getParents(orbits, "SAN");

		int minDist = Integer.MAX_VALUE;
		for (int i = 0; i < parentsYOU.size(); i++) {
			String other = parentsYOU.get(i);

			int i2 = parentsSAN.indexOf(other);

			if (i2 > 0 && (i + i2 < minDist)) {
				minDist = i + i2;
			}
		}

		return minDist;
	}

	private static Map<String, String> parseOrbits(List<String> lines) {
		Map<String, String> orbits = new HashMap<>();

		for (String line : lines) {
			String[] objs = line.split("\\)");
			orbits.put(objs[1], objs[0]);
		}

		return orbits;
	}

	private static List<String> getParents(Map<String, String> orbits, String p) {
		List<String> parents = new ArrayList<>();

		String parent = p;

		while (parent != null) {
			parent = orbits.get(parent);
			parents.add(parent);
		}

		return parents;
	}

	private static int determineParentCount(String p, Map<String, String> orbits) {
		int count = 0;

		String parent = orbits.get(p);

		while (parent != null) {
			parent = orbits.get(parent);
			count++;
		}

		return count;
	}

}
