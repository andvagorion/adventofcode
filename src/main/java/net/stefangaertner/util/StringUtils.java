package net.stefangaertner.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtils {

	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	public static String repeat(String str, int n) {
		return Stream.generate(() -> str).limit(n).collect(Collectors.joining());
	}

	public static String fill(String str, int len) {
		while (str.length() < len) {
			str = " " + str;
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
		for (int y = 0; y < arr.length; y++) {
			char[] row = arr[y];
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < row.length; x++) {
				sb.append(row[x]);
			}
			System.out.println(sb.toString());
		}
	}

}
