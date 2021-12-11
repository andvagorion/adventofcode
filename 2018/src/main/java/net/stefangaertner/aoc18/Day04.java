package net.stefangaertner.aoc18;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.stefangaertner.aoc18.pojo.Day;
import net.stefangaertner.aoc18.pojo.LogEvent;
import net.stefangaertner.aoc18.util.Advent;

public class Day04 {

	private static Pattern pattern = Pattern.compile("^\\[(\\d+)\\-(\\d+)\\-(\\d+) (\\d+)\\:(\\d+)\\] (.*)$");

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = Advent.read("aoc18/004-data");

		Map<String, Day> log = createLog(lines);
		Map<Integer, Integer> guardSleep = getGuardSleep(log);

		int guardId = 0;
		int sleept = 0;

		for (Entry<Integer, Integer> s : guardSleep.entrySet()) {
			int guard = s.getKey();
			int sleep = s.getValue();
			if (sleep > sleept) {
				guardId = guard;
				sleept = sleep;
			}
		}

		int[] schedFull = new int[60];

		// find minute he is asleep the most
		for (Day day : log.values()) {
			int guard = day.getGuardId();
			if (guard == guardId) {
				int[] sched = day.getSchedule();
				for (int i = 0; i < 60; i++) {
					if (sched[i] == 1) {
						schedFull[i] += 1;
					}
				}
			}
		}

		int max = 0;
		int chosenMin = -1;

		for (int i = 0; i < schedFull.length; i++) {
			if (schedFull[i] > max) {
				max = schedFull[i];
				chosenMin = i;
			}
		}

		int result = guardId * chosenMin;

		System.out.println("#1 Guard " + guardId + " was asleep for " + sleept + " minutes. Mostly in minute "
				+ chosenMin + " => " + result);

		return result;
	}

	static long part2() {
		List<String> lines = Advent.read("aoc18/004-data");
		Map<String, Day> log = createLog(lines);

		Map<Integer, int[]> guardSleepTotal = new HashMap<>();

		for (Day day : log.values()) {
			int guard = day.getGuardId();
			int[] sched = day.getSchedule();

			if (!guardSleepTotal.containsKey(guard)) {
				guardSleepTotal.put(guard, new int[60]);
			}

			int[] y = guardSleepTotal.get(guard);
			for (int i = 0; i < y.length; i++) {
				y[i] += sched[i];
			}
		}

		int chosenGuard = -1;
		int max = -1;
		int chosenMin = -1;

		for (Entry<Integer, int[]> e : guardSleepTotal.entrySet()) {
			int g = e.getKey();
			int[] loo = e.getValue();
			for (int i = 0; i < loo.length; i++) {
				if (loo[i] > max) {
					chosenGuard = g;
					max = loo[i];
					chosenMin = i;
				}
			}

		}

		int result = chosenGuard * chosenMin;
		System.out.println("#2 Guard " + chosenGuard + " was asleep for a total of " + max + " minutes in minute "
				+ chosenMin + " => " + result);

		return result;
	}

	private static Map<Integer, Integer> getGuardSleep(Map<String, Day> log) {
		Map<Integer, Integer> guardSleep = new HashMap<>();

		for (Day day : log.values()) {
			Integer guardId = day.getGuardId();
			Integer sleepTime = day.getSleepTime();

			if (!guardSleep.containsKey(guardId)) {
				guardSleep.put(guardId, sleepTime);
			} else {
				int sleep = guardSleep.get(guardId);
				guardSleep.put(guardId, sleepTime + sleep);
			}
		}

		return guardSleep;
	}

	private static Map<String, Day> createLog(List<String> lines) {
		Map<String, Day> log = new TreeMap<>();

		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				int year = Integer.parseInt(matcher.group(1));
				int month = Integer.parseInt(matcher.group(2));
				int day = Integer.parseInt(matcher.group(3));

				int hour = Integer.parseInt(matcher.group(4));
				int min = Integer.parseInt(matcher.group(5));
				String event = matcher.group(6);

				if (hour == 23) {
					day++;
					hour = 0;
					min = 0;
				}

				String key = year + "-" + month + "- " + day;

				if (!log.containsKey(key)) {
					Day date = new Day(year, month, day);
					log.put(key, date);
				}
				log.get(key)
						.addEvent(new LogEvent(hour, min, event));
			}
		}

		return log;
	}

}
