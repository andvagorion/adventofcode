package net.stefangaertner.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtils {
	
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	
	public static String repeat(String str, int n) {
		return Stream.generate(() -> str).limit(n).collect(Collectors.joining());
	}

}
