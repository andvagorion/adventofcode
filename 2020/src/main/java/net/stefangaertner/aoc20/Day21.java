package net.stefangaertner.aoc20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.RegexUtil;

public class Day21 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	private static List<Set<String>> ingredientsList = new ArrayList<>();
	private static Map<String, Set<Set<String>>> a2i = new HashMap<>();
	private static Map<String, Set<String>> i2a = new HashMap<>();

	static long part1() {
		List<String> lines = AOC.read("aoc20/021");

		parse(lines);

		remove(i2a, a2i);

		return i2a.entrySet().stream().filter(e -> e.getValue().isEmpty()).map(e -> {
			String ingredient = e.getKey();
			return ingredientsList.stream().filter(set -> set.contains(ingredient)).count();
		}).reduce(0L, (a, b) -> a + b);
	}

	static String part2() {
		List<String> lines = AOC.read("aoc20/021");

		parse(lines);

		remove(i2a, a2i);

		Map<String, String> out = evaluate(i2a, a2i);

		return out.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.joining(","));
	}

	private static void parse(List<String> lines) {
		for (String line : lines) {
			String p = "(.*) \\(contains (.*)\\)";
			String in1 = RegexUtil.first(line, p);
			String in2 = RegexUtil.second(line, p);

			Set<String> ingredients = Arrays.stream(in1.split(" ")).collect(Collectors.toSet());
			ingredientsList.add(ingredients);

			Set<String> allergens = Arrays.stream(in2.split(", ")).collect(Collectors.toSet());

			for (String allergen : allergens) {
				a2i.putIfAbsent(allergen, new HashSet<>());
				a2i.get(allergen).add(ingredients);
			}

			for (String ingredient : ingredients) {
				i2a.putIfAbsent(ingredient, new HashSet<>());
				i2a.get(ingredient).addAll(allergens);
			}
		}
	}

	private static void remove(Map<String, Set<String>> i2a, Map<String, Set<Set<String>>> a2i) {

		Set<String> ingredients = i2a.keySet().stream().collect(Collectors.toSet());

		ingredients.forEach(ingredient -> {
			Set<String> possibleAllergens = i2a.get(ingredient).stream().collect(Collectors.toSet());

			// check if it's in all lists
			for (String allergen : possibleAllergens) {
				boolean possible = a2i.get(allergen).stream().allMatch(set -> set.contains(ingredient));
				if (!possible) {
					i2a.get(ingredient).remove(allergen);
				}
			}

		});

	}

	private static Map<String, String> evaluate(Map<String, Set<String>> i2a, Map<String, Set<Set<String>>> a2i) {

		// ingredient -> possible allergens
		Map<String, Set<String>> remaining = i2a.entrySet().stream().filter(e -> !e.getValue().isEmpty())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		// actual ingredients -> allergens
		Map<String, String> out = new TreeMap<>();

		while (!remaining.isEmpty()) {

			Set<String> singleAllergens = a2i.keySet().stream().filter(a -> {
				return remaining.values().stream().filter(s -> s.contains(a)).count() == 1;
			}).collect(Collectors.toSet());

			if (singleAllergens.isEmpty()) {
				break;
			}

			for (String a : singleAllergens) {
				String i = remaining.entrySet().stream().filter(e -> e.getValue().contains(a)).findFirst()
						.map(e -> e.getKey()).get();
				out.put(a, i);
				remaining.remove(i);
			}

		}

		return out;
	}

}
