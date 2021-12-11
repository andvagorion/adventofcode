package net.stefangaertner.aoc19.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Advent {

	public static List<String> read(String filename) {

		List<String> result = new ArrayList<>();

		// This will reference one line at a time
		String line = null;

		try (InputStream is = Advent.class.getClassLoader().getResourceAsStream(filename);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {

			while ((line = bufferedReader.readLine()) != null) {
				result.add(line);
			}

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filename + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + filename + "'");
		}

		return result;
	}

	public static List<List<String>> readGroups(String filename) {
		List<String> lines = read(filename);

		List<List<String>> groups = new ArrayList<>();

		List<String> current = new ArrayList<>();

		for (String line : lines) {
			if (line.isEmpty()) {
				groups.add(current);
				current = new ArrayList<>();
				continue;
			}

			current.add(line);
		}

		if (!current.isEmpty()) {
			groups.add(current);
		}

		return groups;
	}

	public static String readLine(String filename) {
		List<String> result = read(filename);

		if (result.isEmpty()) {
			return null;
		}

		return result.get(0);
	}

	public static void print(int num, Object obj) {
		System.out.println(String.format("Part %s: %s", num, obj));
	}

}
