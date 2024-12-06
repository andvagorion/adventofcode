package net.stefangaertner.aoc19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.aoc19.util.RegexUtil;

public class Day25 {

	private static final List<String> allowedCommands = Arrays.asList("north", "south", "east", "west", "take", "drop",
			"inv");

	private static final List<String> allItemsPath = Arrays.asList("south", "south", "take hypercube", "north", "north",
			"north", "take tambourine", "east", "take astrolabe", "south", "take shell", "north", "east", "north",
			"take klein bottle", "north", "take easter egg", "south", "south", "west", "west", "south", "west",
			"take dark matter", "west", "north", "west", "take coin", "south", "inv");

	private static final List<String> items = Arrays.asList("hypercube", "coin", "klein bottle", "shell", "easter egg",
			"astrolabe", "tambourine", "dark matter");

	public static void main(String[] strings) {
		Advent.print(1, part1());
	}

	static long part1() {
		String code = Advent.readLine("aoc19/025-data");

		// List<String> combination = bruteForceCombination(code);

		List<String> combination = Arrays.asList("hypercube", "dark matter", "coin", "tambourine");

		// solve
		Parser p = Parser.create(code).stopOnInput();
		prepare(p);
		dropEverything(p);

		combination.forEach(s -> take(p, s));

		// switchToManual(p);

		p.run();

		// clear output buffer
		p.getUnhandledOutput();

		p.asciiInput("south");
		p.run();

		String message = toString(p.getUnhandledOutput());
		message = message.replaceAll("\\n", "");

		String result = RegexUtil.firstOccurence(message, "(\\d+)");

		return Long.valueOf(result);
	}

	@SuppressWarnings("unused")
	private static List<String> bruteForceCombination(String code) {
		Parser p = Parser.create(code).stopOnInput();

		prepare(p);
		dropEverything(p);
		p.run();

		List<String> items = null;

		while (items == null) {
			items = useRandomItems(p);
		}

		return items;
	}

	private static List<String> useRandomItems(Parser p) {

		// randomly take items and go south
		Collections.shuffle(items);
		for (int i = 1; i < items.size(); i++) {
			for (int j = 0; j < i; j++) {
				take(p, items.get(j));
			}
			p.asciiInput("south");
			p.run();

			String val = toString(p.getUnhandledOutput());

			if (!val.contains("Alert! Droids on this ship are")) {
				// clear output buffer
				p.getUnhandledOutput();

				return items.stream().limit(i).collect(Collectors.toList());
			}
			dropEverything(p);
			p.run();
		}

		return null;
	}

	@SuppressWarnings("unused")
	private static void switchToManual(Parser p) throws IOException {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

			while (!p.isFinished()) {

				p.run();

				List<String> out = p.getUnhandledOutput();
				out.stream().map(s -> Integer.parseInt(s)).forEach(i -> System.out.print((char) i.intValue()));

				String in = reader.readLine();
				if (allowedCommands.stream().filter(s -> in.startsWith(s)).findAny().isPresent()) {
					p.asciiInput(in);
				}

			}

		}
	}

	private static void prepare(Parser p) {
		allItemsPath.forEach(s -> p.asciiInput(s));
	}

	private static void dropEverything(Parser p) {
		items.forEach(s -> drop(p, s));
	}

	private static void take(Parser p, String s) {
		p.asciiInput("take " + s);
	}

	private static void drop(Parser p, String s) {
		p.asciiInput("drop " + s);
	}

	private static String toString(List<String> out) {
		return out.stream().map(s -> String.valueOf((char) Integer.parseInt(s))).collect(Collectors.joining());
	}
}
