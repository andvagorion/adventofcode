package net.stefangaertner.aoc20.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StringUtils {

	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	public static String repeat(String str, int n) {
		return Stream.generate(() -> str)
				.limit(n)
				.collect(Collectors.joining());
	}

	public static String fill(String str, int len) {
		return fillWith(str, len, ' ');
	}

	public static String fillWith(String str, int len, char c) {
		while (str.length() < len) {
			str = c + str;
		}
		return str;
	}

	public static String appendWith(String str, int len, char c) {
		while (str.length() < len) {
			str = str + c;
		}
		return str;
	}

	public static <T> void print2Darray(T[][] arr) {

		for (int y = 0; y < arr.length; y++) {
			T[] row = arr[y];
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < row.length; x++) {
				sb.append(row[x]);
			}
			System.out.println(sb.toString());
		}

	}

	public static void print2Darray(char[][] arr) {
		if (arr == null) {
			return;
		}

		for (int y = 0; y < arr.length; y++) {
			char[] row = arr[y];
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < row.length; x++) {
				sb.append(row[x]);
			}
			System.out.println(sb.toString());
		}
	}

	public static void print2Darray(boolean[][] arr, char t, char f) {
		if (arr == null) {
			return;
		}

		for (int y = 0; y < arr.length; y++) {
			boolean[] row = arr[y];
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < row.length; x++) {
				sb.append(row[x] ? t : f);
			}
			System.out.println(sb.toString());
		}
	}

	public static void print2Darray(boolean[][] arr) {
		print2Darray(arr, '1', '0');
	}

	public static void print2Darray(int[][] arr) {
		if (arr == null) {
			return;
		}

		for (int y = 0; y < arr.length; y++) {
			int[] row = arr[y];
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < row.length; x++) {
				sb.append(row[x]);
			}
			System.out.println(sb.toString());
		}
	}

	public static String[] parse(String input, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(input);
		m.matches();

		int groups = m.groupCount();
		return IntStream.range(1, groups + 1)
				.mapToObj(i -> m.group(i))
				.toArray(size -> new String[size]);
	}

	public static Set<Integer> getNumbersFromString(String str, String divider) {
		Set<Integer> out = new HashSet<>();
		String[] parts = str.split(divider);
		for (String part : parts) {
			if (part.matches("\\d+")) {
				out.add(Integer.parseInt(part));
			}
		}
		return out;
	}

	public static Object reverse(String edge) {
		return new StringBuilder(edge).reverse()
				.toString();
	}

	public static <T> void printWithSelected(List<T> list, T selected) {
		String out = list.stream()
				.map(i -> {
					if (Objects.equals(i, selected)) {
						return "(" + i + ")";
					}
					return "" + i;
				})
				.collect(Collectors.joining(" "));
		System.out.println(out);
	}

	public static <T extends Enum<?>> LinkedList<T> parseSymbols(String line, List<T> symbols) {
		LinkedList<T> out = new LinkedList<>();
		LinkedList<Character> characters = StringUtils.asList(line);

		while (!characters.isEmpty()) {
			final StringBuilder symbol = new StringBuilder();
			while (!symbols.stream()
					.anyMatch(e -> e.name()
							.equals(symbol.toString()
									.toUpperCase()))) {
				symbol.append(characters.pop());
			}

			T obj = symbols.stream()
					.filter(e -> e.name()
							.equals(symbol.toString()
									.toUpperCase()))
					.findFirst()
					.orElse(null);

			if (obj != null) {
				out.add(obj);
			}

		}

		return out;
	}

	public static LinkedList<Character> asList(final String string) {
		return string.chars()
				.mapToObj(e -> (char) e)
				.collect(Collectors.toCollection(() -> new LinkedList<>()));
	}

}
