package net.stefangaertner.aoc18;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.stefangaertner.aoc18.pojo.Nanobot;
import net.stefangaertner.aoc18.pojo.Triple;
import net.stefangaertner.util.FileUtils;

public class Day23 {

	private static final boolean DEBUG = false;

	private static Pattern pattern = Pattern
			.compile("^pos\\=\\<\\s*(-*\\d+),\\s*(-*\\d+),\\s*(-*\\d+)\\>,\\s*r\\=\\s*(-*\\d+)$");

	public static void main(String[] args) {
		List<String> lines = FileUtils.read("aoc18/023-data");

		part1(lines);
	}

	private static void part2(List<String> lines) {
		List<Nanobot> bots = parse(lines);

		Triple chosen = null;
		int maxBots = 0;
		int minDist = Integer.MAX_VALUE;

		int i = 0;

		for (Nanobot bot : bots) {
			System.out.println("testing " + bot + "(" + i + "/" + bots.size() + ")");
			for (int z = bot.z - bot.r; z < bot.z + bot.r + 1; z++) {
				for (int y = bot.y - bot.r; y < bot.y + bot.r + 1; y++) {
					for (int x = bot.x - bot.r; x < bot.x + bot.r + 1; x++) {
						Triple point = new Triple(x, y, z);
						int sum = check(bots, point);

						int min = point.dist(new Triple(0, 0, 0));

						if (sum > maxBots || (sum == maxBots && min < minDist)) {
							chosen = point;
							maxBots = sum;
							minDist = min;
						}
					}
				}
			}
			i++;
		}

		System.out.println("#2 point at " + chosen + " is " + minDist + " away");

	}

	private static int check(List<Nanobot> bots, Triple point) {
		int sum = 0;
		for (Nanobot bot : bots) {
			if (bot.isInRange(point)) {
				sum++;
			}
		}
		return sum;
	}

	private static void part1(List<String> lines) {

		List<Nanobot> bots = parse(lines);

		Nanobot maxBot = bots.get(0);
		for (Nanobot bot : bots) {
			if (bot.r > maxBot.r) {
				maxBot = bot;
			}
		}

		int inRange = 0;
		for (Nanobot bot : bots) {
			if (maxBot.isInRange(bot)) {
				if (DEBUG) {
					System.out.println("bot [" + bot + "] is in range");
				}
				inRange++;
			} else {
				if (DEBUG) {
					System.out.println("bot [" + bot + "] is not in range");
				}
			}
		}

		System.out.println("#1 " + inRange);
	}

	private static List<Nanobot> parse(List<String> lines) {
		List<Nanobot> bots = new ArrayList<>();

		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				int x = Integer.parseInt(matcher.group(1));
				int y = Integer.parseInt(matcher.group(2));
				int z = Integer.parseInt(matcher.group(3));
				int r = Integer.parseInt(matcher.group(4));

				Nanobot bot = new Nanobot(x, y, z, r);

				bots.add(bot);
			}
		}

		return bots;
	}
}
