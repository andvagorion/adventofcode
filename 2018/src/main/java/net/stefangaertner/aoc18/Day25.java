package net.stefangaertner.aoc18;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.stefangaertner.aoc18.pojo.AdvancedSet;
import net.stefangaertner.aoc18.util.Advent;
import net.stefangaertner.aoc18.util.Point4D;

public class Day25 {

	public static void main(String[] args) {
		Advent.print(1, part1());
	}

	private static Pattern pattern = Pattern.compile("^\\s*(-*\\d+),\\s*(-*\\d+),\\s*(-*\\d+),\\s*(-*\\d+)\\s*$");

	static long part1() {
		List<String> lines = Advent.read("aoc18/025-data");
		AdvancedSet<Point4D> stars = parse(lines);

		List<AdvancedSet<Point4D>> constellations = new ArrayList<>();

		for (Point4D star : stars) {

			boolean addedToConstellation = false;

			for (AdvancedSet<Point4D> constellation : constellations) {
				for (Point4D other : constellation) {
					long dist = star.manhattanDistanceTo(other);
					if (dist <= 3) {
						constellation.add(star);
						addedToConstellation = true;
						break;
					}
				}
			}

			if (!addedToConstellation) {
				AdvancedSet<Point4D> constellation = new AdvancedSet<>();
				constellation.add(star);
				constellations.add(constellation);
			}
		}

		boolean done = false;

		while (!done) {
			done = true;

			for (int i = 0; i < constellations.size(); i++) {
				for (int j = 0; j < constellations.size(); j++) {
					if (i == j) {
						continue;
					}
					if (constellations.get(i).containsAny(constellations.get(j))) {
						AdvancedSet<Point4D> other = constellations.get(j);
						constellations.get(i).addAll(other);
						constellations.remove(j);
						done = false;
						break;
					}
				}
				if (!done) {
					break;
				}
			}
		}

		return constellations.size();
	}

	private static AdvancedSet<Point4D> parse(List<String> lines) {
		AdvancedSet<Point4D> stars = new AdvancedSet<>();

		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				int x = Integer.parseInt(matcher.group(1));
				int y = Integer.parseInt(matcher.group(2));
				int z = Integer.parseInt(matcher.group(3));
				int w = Integer.parseInt(matcher.group(4));

				Point4D star = Point4D.of(x, y, z, w);

				stars.add(star);
			}
		}

		return stars;
	}

}
