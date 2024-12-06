package net.stefangaertner.aoc20;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.Filter;
import net.stefangaertner.aoc20.util.RegexUtil;
import net.stefangaertner.aoc20.util.StringUtils;

public class Day19 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<List<String>> groups = AOC.readGroups("aoc20/019");
		Map<Integer, String> ruleset = getRuleset(groups.get(0));
		return Filter.list(groups.get(1)).matchRegex(ruleset.get(0)).get().size();
	}

	static long part2() {
		List<List<String>> groups = AOC.readGroups("aoc20/019");
		List<String> initialRules = groups.get(0);

		List<String> filtered = Filter.list(initialRules).remove(str -> str.startsWith("8:") || str.startsWith("11: "))
				.get();

		Map<Integer, String> ruleset = getRuleset(filtered);

		ruleset = addAdditionalRules(ruleset);

		List<String> matching = Filter.list(groups.get(1)).matchRegex(ruleset.get(0)).get();

		return matching.size();
	}

	private static String basicRulePattern = "(\\d+): \"(\\w+)\"";

	private static Map<Integer, String> getRuleset(List<String> rules) {
		Map<Integer, String> ruleset = evaluateBasicRules(rules);

		List<String> compoundRules = Filter.list(rules).doesNotMatchRegex(basicRulePattern).get();

		ruleset = evaluateCompoundRules(ruleset, compoundRules);

		return ruleset;
	}

	private static Map<Integer, String> evaluateCompoundRules(Map<Integer, String> ruleset,
			List<String> compoundRules) {

		int prevSize = compoundRules.size() + 1;

		while (!compoundRules.isEmpty()) {

			if (prevSize == compoundRules.size()) {

				compoundRules.forEach(str -> {
					ruleset.put(getRuleNumber(str), getRuleString(str));
				});

				return ruleset;

			} else {
				prevSize = compoundRules.size();
			}

			for (String compoundRule : compoundRules) {

				Integer ruleNum = getRuleNumber(compoundRule);
				String right = getRuleString(compoundRule);

				Set<Integer> nums = StringUtils.getNumbersFromString(right, " ");
				if (!nums.stream().allMatch(num -> ruleset.containsKey(num))) {
					// not all sub-rules have been evaluated
					continue;
				}

				String subRule = evaluateCompoundRule(ruleset, right);

				ruleset.put(ruleNum, subRule);
				compoundRules.remove(compoundRule);
				break;

			}

		}

		return ruleset;
	}

	private static Integer getRuleNumber(String line) {
		return Integer.parseInt(line.split(": ")[0]);
	}

	private static String getRuleString(String line) {
		return line.split(": ")[1];
	}

	private static String evaluateCompoundRule(Map<Integer, String> ruleset, String right) {
		List<String> ruleParts = new ArrayList<>();
		for (String part : right.split(" \\| ")) {

			String out = "";
			for (String rule : part.split("\\s+")) {
				Integer num = Integer.parseInt(rule);
				out += ruleset.get(num);
			}

			ruleParts.add(out);
		}

		return ruleParts.stream().collect(Collectors.joining("|", "(", ")"));
	}

	private static Map<Integer, String> evaluateBasicRules(List<String> rules) {
		return rules.stream().filter(line -> line.matches(basicRulePattern))
				.collect(Collectors.toMap(str -> Integer.parseInt(RegexUtil.first(str, basicRulePattern)),
						str -> RegexUtil.second(str, basicRulePattern)));
	}

	private static Map<Integer, String> addAdditionalRules(Map<Integer, String> ruleset) {

		// "8: 42 | 42 8"
		// 8 is 42+
		ruleset.put(8, String.format("%s+", ruleset.get(42)));

		// 11: 42 31 | 42 11 31
		// 11 is 42{n} 31{n}

		int highestRuleNumber = ruleset.keySet().stream().max(Comparator.naturalOrder()).get();

		String out = "";
		List<Integer> additionalRuleNumbers = new ArrayList<>();
		for (int i = highestRuleNumber + 1; i < highestRuleNumber + 11; i++) {
			out = ruleset.get(42) + out + ruleset.get(31);
			ruleset.put(i, out);
			additionalRuleNumbers.add(i);
		}
		ruleset.put(11,
				additionalRuleNumbers.stream().map(i -> ruleset.get(i)).collect(Collectors.joining("|", "(", ")")));

		// rule 0 is always "8 11"
		ruleset.put(0, String.format("%s%s", ruleset.get(8), ruleset.get(11)));

		return ruleset;
	}

}
