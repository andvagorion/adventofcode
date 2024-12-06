package net.stefangaertner.aoc18;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.stefangaertner.aoc18.util.Advent;

public class Day09 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		return computeScore(412, 71646);
	}

	static long part2() {
		return computeScore(412, 7164600);
	}

	private static long computeScore(long players, long max) {

		// use double ended queue for circular list functionalities
		Deque<Long> list = new ArrayDeque<>();
		list.addLast(0L);

		Map<Integer, Long> scores = new HashMap<>();

		int currentPlayer = 1;

		for (long i = 1; i < max; i++) {

			if (i % 23 == 0) {

				// add current marble to score
				long score = i;

				// shift by 7 counter-clockwise
				for (int j = 0; j < 7; j++) {
					long last = list.removeLast();
					list.addFirst(last);
				}

				// remove and add other marble to score
				// next marble automatically becomes new current marble
				long first = list.removeFirst();
				score += first;

				if (!scores.containsKey(currentPlayer)) {
					scores.put(currentPlayer, score);
				} else {
					scores.put(currentPlayer, score + scores.get(currentPlayer));
				}

			} else {

				// shift by 2 clockwise, add new marble
				for (int j = 0; j < 2; j++) {
					long last = list.removeFirst();
					list.addLast(last);
				}
				list.addFirst(i);
			}

			currentPlayer++;
			if (currentPlayer > players)
				currentPlayer = 1;

			// print(list, i);
		}

		long highScore = 0;

		for (Entry<Integer, Long> score : scores.entrySet()) {
			long hs = score.getValue();
			if (hs > highScore)
				highScore = hs;
		}

		return highScore;
	}

	@SuppressWarnings("unused")
	private static void print(Deque<Long> list, long idx) {

		String s = "";

		for (Long m : list) {
			s += " ";
			if (m == idx)
				s += "(" + m + ")";
			else
				s += m;
		}

		System.out.println(s);
	}

}
