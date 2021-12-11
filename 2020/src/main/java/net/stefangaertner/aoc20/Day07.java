package net.stefangaertner.aoc20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.stefangaertner.aoc20.util.AOC;

public class Day07 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/007");
		Map<String, List<String>> map = new HashMap<>();
		lines.forEach(str -> addEntry(map, str));

		Set<String> bags = new HashSet<>();
		iterate(map, (list) -> bags.addAll(list));

		return bags.size();
	}

	static long part2() {
		List<String> lines = AOC.read("aoc20/007");
		Map<String, List<String>> bags = lines.stream().collect(Collectors.toMap(getColor, getList));

		List<Integer> nums = new ArrayList<>();
		iterate(bags, (list) -> nums.add(list.size()));

		return nums.stream().reduce(0, (a, b) -> a + b);
	}

	private static final String START = "shiny gold";

	private static void iterate(Map<String, List<String>> map, Consumer<List<String>> consumer) {
		Stack<String> stack = new Stack<>();
		stack.push(START);

		while (!stack.isEmpty()) {
			List<String> children = map.getOrDefault(stack.pop(), new ArrayList<>());
			if (children.isEmpty()) {
				continue;
			}

			consumer.accept(children);
			stack.addAll(children);
		}
	}

	private static final Pattern linePattern = Pattern.compile("([\\w\\s]+) bag(?:s)? contain ([\\w\\s,]+)\\.");
	private static final Pattern listPattern = Pattern.compile("(\\d+|no) ([\\w\\s]+) bag(?:s)?");

	private static Function<String, String> getColor = str -> {
		Matcher matcher = linePattern.matcher(str);
		matcher.matches();

		return matcher.group(1);
	};

	private static Function<String, List<String>> getList = str -> {
		Matcher matcher = linePattern.matcher(str);
		matcher.matches();

		List<String> list = new ArrayList<>();

		String[] bags = matcher.group(2).split(", ");
		for (String bag : bags) {
			Matcher subMatcher = listPattern.matcher(bag);
			subMatcher.matches();

			String numStr = subMatcher.group(1);
			String color = subMatcher.group(2);

			if (!"no".equals(numStr)) {

				Integer num = Integer.parseInt(numStr);
				for (int i = 0; i < num; i++) {
					list.add(color);
				}

			}

		}

		return list;
	};

	private static void addEntry(Map<String, List<String>> map, String str) {
		Matcher matcher = linePattern.matcher(str);
		if (!matcher.matches()) {
			return;
		}

		String in = matcher.group(1);
		String out = matcher.group(2);

		String[] outs = out.split(", ");

		for (String o : outs) {
			Matcher matcher2 = listPattern.matcher(o);
			matcher2.matches();

			String num = matcher2.group(1);
			String color = matcher2.group(2);
			List<String> l = map.getOrDefault(color, new ArrayList<>());

			if (!"no".equals(num)) {
				l.add(in);
			}
			map.put(color, l);
		}
	}

}
