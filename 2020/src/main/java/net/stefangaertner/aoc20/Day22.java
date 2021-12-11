package net.stefangaertner.aoc20;

import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.Pair;

public class Day22 {

	public static void main(String[] args) {
		AOC.print(1, part1());
		AOC.print(2, part2());
	}

	static long part1() {
		List<List<String>> groups = AOC.readGroups("aoc20/022");

		Deque<Long> player = parseDeck(groups.get(0));
		Deque<Long> opponent = parseDeck(groups.get(1));

		play(player, opponent);

		Deque<Long> winner = player.size() > opponent.size() ? player : opponent;

		long i = 1;
		long out = 0;
		while (!winner.isEmpty()) {
			long card = winner.removeLast();
			long val = card * i;
			out += val;
			i++;
		}

		return out;
	}

	private static Deque<Long> parseDeck(List<String> list) {
		return list.stream().skip(1).map(Long::parseLong).collect(Collectors.toCollection(() -> new LinkedList<>()));
	}

	static long part2() {
		List<List<String>> groups = AOC.readGroups("aoc20/022");

		Deque<Long> player = parseDeck(groups.get(0));
		Deque<Long> opponent = parseDeck(groups.get(1));

		playRecursive(player, opponent);

		Deque<Long> winner = player.size() > opponent.size() ? player : opponent;

		long i = 1;
		long out = 0;
		while (!winner.isEmpty()) {
			long card = winner.removeLast();
			long val = card * i;
			out += val;
			i++;
		}

		return out;
	}

	private static void play(Deque<Long> player, Deque<Long> opponent) {
		while (!player.isEmpty() && !opponent.isEmpty()) {

			Long playerCard = player.pop();
			Long opponentCard = opponent.pop();

			if (playerCard > opponentCard) {
				player.addLast(playerCard);
				player.addLast(opponentCard);
			} else {
				opponent.addLast(opponentCard);
				opponent.addLast(playerCard);
			}

		}
	}

	private static long playRecursive(Deque<Long> player, Deque<Long> opponent) {

		Set<Pair<Deque<Long>, Deque<Long>>> previousConfigurations = new HashSet<>();

		while (!player.isEmpty() && !opponent.isEmpty()) {

			Pair<Deque<Long>, Deque<Long>> config = Pair.of(copyOf(player), copyOf(opponent));

			if (previousConfigurations.contains(Pair.of(player, opponent))) {
				return 1;
			}

			Long playerCard = player.pop();
			Long opponentCard = opponent.pop();

			if (playerCard <= player.size() && opponentCard <= opponent.size()) {
				// winner is determined by sub-game
				Deque<Long> playerSub = copyOf(player.stream().limit(playerCard).collect(Collectors.toList()));
				Deque<Long> opponentSub = copyOf(opponent.stream().limit(opponentCard).collect(Collectors.toList()));

				long winner = playRecursive(playerSub, opponentSub);

				if (winner == 1) {
					player.addLast(playerCard);
					player.addLast(opponentCard);
				} else {
					opponent.addLast(opponentCard);
					opponent.addLast(playerCard);
				}

			} else {

				if (playerCard > opponentCard) {
					player.addLast(playerCard);
					player.addLast(opponentCard);
				} else {
					opponent.addLast(opponentCard);
					opponent.addLast(playerCard);
				}

			}

			previousConfigurations.add(config);

		}

		return player.size() > opponent.size() ? 1 : 2;
	}

	private static Deque<Long> copyOf(Collection<Long> in) {
		return in.stream().collect(Collectors.toCollection(() -> new LinkedList<>()));
	}

}
