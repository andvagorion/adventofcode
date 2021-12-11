package net.stefangaertner.aoc18;

import java.util.List;

import net.stefangaertner.aoc18.pojo.Battlefield;
import net.stefangaertner.aoc18.pojo.Tile;
import net.stefangaertner.aoc18.pojo.Tile.TileType;
import net.stefangaertner.aoc18.pojo.Unit;
import net.stefangaertner.aoc18.pojo.Unit.UnitType;
import net.stefangaertner.aoc18.util.Advent;
import net.stefangaertner.aoc18.util.StringUtils;

public class Day15 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = Advent.read("aoc18/015-data");

		Battlefield battlefield = readInput(lines);
		Battlefield.debug = false;

		// battlefield.printCave();
		// System.out.println();

		// each turn
		while (!battlefield.checkFinished()) {
			battlefield.simulateRound();
		}

		// System.out.println("after " + battlefield.rounds + " rounds: ");
		// battlefield.printCave();
		// battlefield.printUnits();
		// System.out.println();

		System.out.println("#1 ");
		System.out.println(StringUtils.repeat("-", 30));
		battlefield.printOutcome();
		System.out.println(StringUtils.repeat("-", 30));

		return battlefield.getOutcome();
	}

	static long part2() {
		List<String> lines = Advent.read("aoc18/015-data");

		int power = 3;

		while (power < 100) {

			Battlefield battlefield = readInput(lines);
			Battlefield.debug = false;

			power++;

			// System.out.println("trying " + power);

			int elves = battlefield.count(UnitType.ELF);
			battlefield.superchargeElves(power);

			while (!battlefield.checkFinished()) {
				battlefield.simulateRound();
				if (battlefield.count(UnitType.ELF) < elves) {
					// System.out.println("an elf has died");
					break;
				}
			}

			if (battlefield.elvesHaveWon() && battlefield.count(UnitType.ELF) == elves) {
				System.out.println("#2 ");
				System.out.println(StringUtils.repeat("-", 30));
				battlefield.printOutcome();

				System.out.println(battlefield.count(UnitType.ELF) + " of " + elves + " remaining");
				System.out.println("elves have won with power " + power);

				return battlefield.getOutcome();
			}
		}

		return -1;
	}

	private static Battlefield readInput(List<String> lines) {
		Battlefield cave = new Battlefield();

		cave.grid = new Tile[lines.size()][];

		for (int y = 0; y < lines.size(); y++) {
			String line = lines.get(y);
			cave.grid[y] = new Tile[line.length()];

			for (int x = 0; x < line.length(); x++) {
				char c = line.charAt(x);
				if (c == '.' || c == 'E' || c == 'G') {
					cave.grid[y][x] = new Tile(x, y, TileType.FLOOR);
				}
				if (c == '#') {
					cave.grid[y][x] = new Tile(x, y, TileType.WALL);
				}

				if (c == 'E') {
					cave.grid[y][x].unit = new Unit(cave.grid[y][x], UnitType.ELF);
				}
				if (c == 'G') {
					cave.grid[y][x].unit = new Unit(cave.grid[y][x], UnitType.GOBLIN);
				}
			}

		}

		return cave;
	}

}
