package net.stefangaertner.aoc19;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.Reagent;

public class Day14 {

	public static void main(String[] strings) throws IOException {

		List<String> lines = Advent.read("aoc19/014-example");

		Map<Reagent, Reagent[]> reactions = new HashMap<>();

		for (String line : lines) {

			String[] parts = line.split("=>");

			Reagent out = new Reagent(parts[1]);

			String[] inParts = parts[0].split(",");
			Reagent[] reagents = Arrays.stream(inParts)
					.map(s -> new Reagent(s))
					.toArray(Reagent[]::new);

			reactions.put(out, reagents);

		}

		Deque<Reagent> fifo = new ArrayDeque<>();
		Reagent fuel = reactions.entrySet()
				.stream()
				.filter(e -> e.getKey()
						.isFuel())
				.findFirst()
				.get()
				.getKey();
		fifo.add(fuel);

		Map<String, Integer> howManyAreNeeded = new HashMap<>();

		while (!fifo.isEmpty()) {

			Reagent r = fifo.pop();

			System.out.println(r.name + " needs: ");

			if (!r.isOre()) {

				Entry<Reagent, Reagent[]> e = reactions.entrySet()
						.stream()
						.filter(r1 -> r1.getKey().name.equals(r.name))
						.findFirst()
						.get();

				// int times =
				Reagent[] needs = e.getValue();

				for (Reagent sub : needs) {

					howManyAreNeeded.put(e.getKey().name, 5);

					fifo.add(sub);

					System.out.println("- " + sub.num + " " + sub.name);
				}

			}

		}

		// System.out.println(ores);

	}

}
