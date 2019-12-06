package net.stefangaertner.aoc19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.stefangaertner.util.FileUtils;

public class Day05 {

	public static void main(String[] strings) {

		List<String> lines = FileUtils.read("aoc19/005-data1");

		// child -> parent
		Map<String, String> orbits = new HashMap<>();

		for (String line : lines) {
			String[] objs = line.split("\\)");
			orbits.put(objs[1], objs[0]);
		}

		part1(orbits);
		part2(orbits);
	}

	private static void part1(Map<String, String> orbits) {

		int count = 0;

		for (String p : orbits.keySet()) {
			count += determineParentCount(p, orbits);
		}

		System.out.println("Part 1 : " + count);
	}

	private static void part2(Map<String, String> orbits) {
		List<String> parentsYOU = getParents(orbits, "YOU");
		List<String> parentsSAN = getParents(orbits, "SAN");

		// System.out.println("YOU => " +
		// parentsYOU.stream().collect(Collectors.joining(" => ")));
		// System.out.println("SAN => " +
		// parentsSAN.stream().collect(Collectors.joining(" => ")));

		int minDist = Integer.MAX_VALUE;
		for (int i = 0; i < parentsYOU.size(); i++) {
			String other = parentsYOU.get(i);

			int i2 = parentsSAN.indexOf(other);

			if (i2 > 0 && (i + i2 < minDist)) {
				minDist = i + i2;
			}
		}

		System.out.println("Part 2: " + minDist);
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
