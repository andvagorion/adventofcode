package net.stefangaertner.aoc20;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.RegexUtil;
import net.stefangaertner.aoc20.util.StringUtils;

public class Day14 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/014");
		Map<Integer, Long> memory = new HashMap<>();
		String mask = null;

		for (String line : lines) {
			if (line.startsWith("mask")) {

				mask = line.split(" = ")[1];

			} else {

				int memoryBank = Integer.parseInt(RegexUtil.first(line, memPattern));
				String bin = toBinary(RegexUtil.second(line, memPattern));

				long masked = mask1(bin, mask);
				memory.put(memoryBank, masked);
			}
		}

		return memory.values().stream().collect(Collectors.summarizingLong(l -> l)).getSum();
	}

	static long part2() {
		List<String> lines = AOC.read("aoc20/014");
		Map<Long, Long> memory = new HashMap<>();
		String mask = null;

		for (String line : lines) {
			if (line.startsWith("mask")) {

				mask = line.split(" = ")[1];

			} else {

				long value = Long.parseLong(RegexUtil.second(line, memPattern));
				String bin = toBinary(RegexUtil.first(line, memPattern));

				Set<Long> memoryBanks = mask2(bin, mask);

				memoryBanks.forEach(memoryBank -> {
					memory.put(memoryBank, value);
				});

			}
		}

		return memory.values().stream().collect(Collectors.summarizingLong(l -> l)).getSum();
	}

	private static Pattern memPattern = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");

	private static String toBinary(String str) {
		long value = Long.parseLong(str);
		String bin = StringUtils.fillWith(Long.toBinaryString(value), 36, '0');
		return bin;
	}

	private static long mask1(String bin, String mask) {
		String masked = _mask(bin, mask, 'X');
		return new BigInteger(masked, 2).longValue();
	}

	private static Set<Long> mask2(String bin, String mask) {
		String masked = _mask(bin, mask, '0');
		return floating(masked);
	}

	private static Set<Long> floating(String current) {
		Set<Long> set = new HashSet<>();

		int idx = current.indexOf('X');

		if (idx == -1) {
			set.add(new BigInteger(current, 2).longValue());
			return set;
		}

		set.addAll(floating(current.replaceFirst("X", "0")));
		set.addAll(floating(current.replaceFirst("X", "1")));

		return set;
	}

	private static String _mask(String bin, String mask, char unchanged) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mask.length(); i++) {
			if (mask.charAt(i) == unchanged) {
				sb.append(bin.charAt(i));
			} else {
				sb.append(mask.charAt(i));
			}
		}

		return sb.toString();
	}
}
