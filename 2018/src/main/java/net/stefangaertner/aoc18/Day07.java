package net.stefangaertner.aoc18;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import net.stefangaertner.aoc18.pojo.Worker;
import net.stefangaertner.aoc18.util.Advent;

public class Day07 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	private static boolean DEBUG = false;
	private static int workerNum = 5;
	private static int offset = 60;

	static String part1() {
		List<String> lines = Advent.read("aoc18/007-data");

		Map<String, Set<String>> steps = getSteps(lines);
		Set<String> start = getStartValues(steps);

		String output = "";

		Set<String> done = new TreeSet<>();

		while (!start.isEmpty()) {

			String current = null;
			for (String c : start) {
				if (canBeDone(steps, c)) {
					current = c;
					break;
				}
			}

			output += current;
			done.add(current);

			Set<String> nextSteps = steps.get(current);

			if (nextSteps != null) {
				start.addAll(nextSteps);
			}

			for (String d : done) {
				start.remove(d);
			}

			steps.put(current, null);
			start.remove(current);

		}

		return output;
	}

	static long part2() {
		List<String> lines = Advent.read("aoc18/007-data");

		Map<String, Set<String>> steps = getSteps(lines);
		Set<String> start = getStartValues(steps);
		String end = getEndValue(steps);

		List<String> output = new ArrayList<>();

		int t = 0;

		List<Worker> workers = new ArrayList<>();
		for (int i = 0; i < workerNum; i++) {
			workers.add(new Worker(i, offset));
		}

		while (true && t < 10000) {

			// each thread works
			for (Worker w : workers) {
				w.work();
			}

			// check if any is done, grab output and reset
			for (Worker w : workers) {
				if (w.done()) {
					output.add(w.getPayload());
					if (steps.containsKey(w.getPayload())) {
						start.addAll(steps.get(w.getPayload()));
					}
					steps.put(w.getPayload(), null);
					w.reset();
				}
			}

			// check if any thread is free to work
			for (Worker w : workers) {
				if (w.isFree()) {
					// thread is free, check if work is to be done
					if (!start.isEmpty()) {
						for (String c : start) {

							if (canBeDone(steps, c)) {
								w.start(c);
								start.remove(c);
								break;
							}

						}
					}
				}
			}

			if (DEBUG) {
				System.out.println("second " + t);
				log(workers);
				System.out.println(output);
			}

			if (output.contains(end)) {
				break;
			}

			t++;
		}

		return t;
	}

	private static void log(List<Worker> workers) {
		for (Worker w : workers) {
			System.out.println(w.toString());
		}
	}

	private static Map<String, Set<String>> getSteps(List<String> lines) {
		Map<String, Set<String>> steps = new TreeMap<>();

		for (String line : lines) {
			String[] s = line.split(" ");
			String from = s[1];
			String to = s[7];

			if (!steps.containsKey(from)) {
				steps.put(from, new TreeSet<>());
			}

			steps.get(from)
					.add(to);
		}

		return steps;
	}

	private static Set<String> getStartValues(Map<String, Set<String>> steps) {

		Set<String> startValues = new TreeSet<>();

		startValues.addAll(steps.keySet());

		for (Set<String> o : steps.values()) {
			for (String s : o) {
				startValues.remove(s);
			}
		}

		return startValues;
	}

	private static String getEndValue(Map<String, Set<String>> steps) {
		Set<String> outputs = new TreeSet<>();
		for (Set<String> o : steps.values()) {
			outputs.addAll(o);
		}

		for (String o : outputs) {
			if (!steps.containsKey(o)) {
				return o;
			}
		}

		return null;
	}

	private static boolean canBeDone(Map<String, Set<String>> steps, String c) {
		for (Set<String> s : steps.values()) {
			if (s != null && s.contains(c)) {
				return false;
			}
		}
		return true;
	}

}
