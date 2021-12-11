package net.stefangaertner.aoc20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import net.stefangaertner.aoc20.util.AOC;

public class Day04 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<String> lines = AOC.read("aoc20/004");
		List<Map<String, String>> passports = parse(lines);
		return passports.stream().filter(isValid).count();
	}

	static long part2() {
		List<String> lines = AOC.read("aoc20/004");
		List<Map<String, String>> passports = parse(lines);
		return passports.stream().filter(isValid.and(checkFields)).count();
	}

	private static final List<String> fields = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid");

	private static Predicate<Map<String, String>> isValid = pp -> {
		if (pp.keySet().size() == fields.size()) {
			return true;
		}

		if (pp.keySet().size() == fields.size() - 1 && !pp.keySet().contains("cid")) {
			return true;
		}

		return false;
	};

	private static boolean isValidNumber(String numStr, int min, int max) {
		int num = Integer.parseInt(numStr);
		return num >= min && num <= max;
	}

	private static boolean isValidNumber(Map<String, String> passport, String key, int min, int max) {
		if (!passport.containsKey(key)) {
			return false;
		}
		return isValidNumber(passport.get(key), min, max);
	}

	// hgt (Height) - a number followed by either cm or in:
	// If cm, the number must be at least 150 and at most 193.
	// If in, the number must be at least 59 and at most 76.
	private static Predicate<Map<String, String>> isValidHeight = pp -> {
		if (!pp.containsKey("hgt")) {
			return false;
		}

		String hgt = pp.get("hgt");

		if (!hgt.matches("\\d+(in|cm)")) {
			return false;
		}

		if (hgt.endsWith("cm")) {
			return isValidNumber(hgt.substring(0, hgt.length() - 2), 150, 193);
		}

		if (hgt.endsWith("in")) {
			return isValidNumber(hgt.substring(0, hgt.length() - 2), 59, 76);
		}

		return false;
	};

	// byr (Birth Year) - four digits; at least 1920 and at most 2002.
	private static Predicate<Map<String, String>> isValidBirthYear = pp -> isValidNumber(pp, "byr", 1920, 2002);

	// iyr (Issue Year) - four digits; at least 2010 and at most 2020.
	private static Predicate<Map<String, String>> isValidIssueYear = pp -> isValidNumber(pp, "iyr", 2010, 2020);

	// eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
	private static Predicate<Map<String, String>> isValidExpirationYear = pp -> isValidNumber(pp, "eyr", 2020, 2030);

	// hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
	private static Predicate<Map<String, String>> isValidHairColor = pp -> {
		if (!pp.containsKey("hcl")) {
			return false;
		}

		return pp.get("hcl").matches("#[0-9a-f]{6}");
	};

	// ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
	private static final List<String> validEyeColors = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
	private static Predicate<Map<String, String>> isValidEyeColor = pp -> {
		if (!pp.containsKey("ecl")) {
			return false;
		}

		return validEyeColors.contains(pp.get("ecl"));
	};

	// pid (Passport ID) - a nine-digit number, including leading zeroes.
	private static Predicate<Map<String, String>> isValidPassportId = pp -> {
		if (!pp.containsKey("pid")) {
			return false;
		}

		return pp.get("pid").matches("\\d{9}");
	};

	private static Predicate<Map<String, String>> checkFields = pp -> {
		return isValidBirthYear.and(isValidIssueYear).and(isValidExpirationYear).and(isValidHeight)
				.and(isValidHairColor).and(isValidEyeColor).and(isValidPassportId).test(pp);
	};

	private static List<Map<String, String>> parse(List<String> lines) {
		List<Map<String, String>> passports = new ArrayList<>();

		Map<String, String> current = new HashMap<>();

		for (String line : lines) {
			if (line.isEmpty()) {
				passports.add(current);
				current = new HashMap<>();
				continue;
			}

			current.putAll(parseLine(line));
		}

		return passports;
	}

	private static Map<String, String> parseLine(String line) {
		Map<String, String> current = new HashMap<>();

		String[] parts = line.split(" ");
		for (String part : parts) {
			String[] str = part.split(":");
			current.put(str[0], str[1]);
		}

		return current;
	}

}
