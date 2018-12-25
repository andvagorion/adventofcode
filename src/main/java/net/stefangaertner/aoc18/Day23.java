package net.stefangaertner.aoc18;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.stefangaertner.aoc18.pojo.Nanobot;
import net.stefangaertner.util.FileUtils;

public class Day23 {

	private static final boolean DEBUG = false;

	private static Pattern pattern = Pattern
			.compile("^pos\\=\\<\\s*(-*\\d+),\\s*(-*\\d+),\\s*(-*\\d+)\\>,\\s*r\\=\\s*(-*\\d+)$");

	public static void main(String[] args) {
		List<String> lines = FileUtils.read("aoc18/023-data");

		part1(lines);
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
