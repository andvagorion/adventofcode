package net.stefangaertner.aoc20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.IntPair;
import net.stefangaertner.aoc20.util.RegexUtil;

public class Day16 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/016");
		lines.add("");
		List<Set<IntPair>> validRanges = parseValidRanges(lines);
		Set<List<Integer>> nearby = parseTickets(lines, "nearby tickets");

		List<Integer> found = nearby.stream().flatMap(set -> set.stream()).filter(i -> {
			return !validRanges.stream().flatMap(set -> set.stream()).anyMatch(pair -> i >= pair.a && i <= pair.b);
		}).collect(Collectors.toList());

		return found.stream().collect(Collectors.summarizingInt(l -> l)).getSum();
	}

	static long part2() {
		List<String> lines = AOC.read("aoc20/016");
		lines.add("");
		List<Set<IntPair>> validRanges = parseValidRanges(lines);
		Set<List<Integer>> nearby = parseTickets(lines, "nearby tickets");
		List<Integer> ownTicket = parseTickets(lines, "your ticket").iterator().next();

		Set<List<Integer>> validTickets = nearby.stream().filter(list -> {
			return list.stream().allMatch(i -> {
				return validRanges.stream().flatMap(set -> set.stream()).anyMatch(pair -> i >= pair.a && i <= pair.b);
			});
		}).collect(Collectors.toSet());

		Set<IntPair> possibleCombinations = new HashSet<>();
		for (int i = 0; i < validRanges.size(); i++) {
			for (int j = 0; j < validRanges.size(); j++) {
				possibleCombinations.add(IntPair.of(i, j));
			}
		}

		for (List<Integer> ticket : validTickets) {
			possibleCombinations = possibleCombinations.stream().filter(possibleCombination -> {
				int fieldRow = possibleCombination.a;
				int ticketIndex = possibleCombination.b;

				Set<IntPair> ranges = validRanges.get(fieldRow);

				int ticketValue = ticket.get(ticketIndex);
				return ranges.stream().anyMatch(p -> ticketValue >= p.a && ticketValue <= p.b);
			}).collect(Collectors.toSet());
		}

		Map<Integer, List<IntPair>> possibilities = possibleCombinations.stream()
				.collect(Collectors.groupingBy(pair -> pair.a));

		// field row -> ticket index
		Set<IntPair> finalMapping = new HashSet<>();

		while (!possibilities.isEmpty()) {
			Map<Integer, List<IntPair>> changed = new HashMap<>();

			List<IntPair> match = possibilities.values().stream().filter(list -> list.size() == 1).findFirst().get();
			IntPair single = match.get(0);
			finalMapping.add(single);

			possibilities.forEach((k, v) -> {
				changed.put(k, v.stream().filter(pair -> pair.b != single.b).collect(Collectors.toList()));
			});

			possibilities = changed.entrySet().stream().filter(e -> !e.getValue().isEmpty())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}

		List<String> fields = lines.subList(0, validRanges.size());

		return finalMapping.stream().filter(pair -> {
			return fields.get(pair.a).startsWith("departure");
		}).map(pair -> {
			return Long.valueOf(ownTicket.get(pair.b));
		}).reduce(1L, (a, b) -> a * b);
	}

	private static Set<List<Integer>> parseTickets(List<String> lines, String header) {
		int lineIndex = 0;
		while (!lines.get(lineIndex).startsWith(header)) {
			lineIndex++;
		}

		Set<List<Integer>> out = new HashSet<>();

		lineIndex++;
		while (!lines.get(lineIndex).isEmpty()) {
			out.add(Arrays.stream(lines.get(lineIndex++).split(",")).map(Integer::parseInt)
					.collect(Collectors.toList()));
		}

		return out;
	}

	private static List<Set<IntPair>> parseValidRanges(List<String> lines) {
		Pattern pattern = Pattern.compile("^.*: (\\d+-\\d+) or (\\d+-\\d+)");

		List<Set<IntPair>> allValidRanges = new ArrayList<>();

		int row = 0;
		while (!lines.get(row).trim().isEmpty()) {
			Set<IntPair> validRanges = new HashSet<>();
			String line = lines.get(row);
			validRanges.add(parseRange(RegexUtil.first(line, pattern)));
			validRanges.add(parseRange(RegexUtil.second(line, pattern)));
			allValidRanges.add(validRanges);
			row++;
		}

		return allValidRanges;
	}

	private static IntPair parseRange(String str) {
		int from = Integer.parseInt(str.split("-")[0]);
		int to = Integer.parseInt(str.split("-")[1]);
		return IntPair.of(from, to);
	}

}
