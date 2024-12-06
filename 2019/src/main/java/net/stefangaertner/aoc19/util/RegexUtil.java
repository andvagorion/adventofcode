package net.stefangaertner.aoc19.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	public static String firstOccurence(String in, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(in);
		if (matcher.find()) {
			return matcher.group();
		}

		return null;
	}

	public static String first(String in, String pattern) {
		return first(in, Pattern.compile(pattern));
	}

	public static String second(String in, String pattern) {
		return second(in, Pattern.compile(pattern));
	}

	public static String first(String in, Pattern pattern) {
		return group(in, pattern, 1);
	}

	public static String second(String in, Pattern pattern) {
		return group(in, pattern, 2);
	}

	public static String group(String in, Pattern pattern, int groupNum) {
		Matcher m = pattern.matcher(in);
		m.matches();
		return m.group(groupNum);
	}

}
