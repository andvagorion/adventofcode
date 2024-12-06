package net.stefangaertner.aoc18;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.stefangaertner.aoc18.pojo.Army;
import net.stefangaertner.aoc18.pojo.ArmyType;
import net.stefangaertner.aoc18.pojo.AttackType;
import net.stefangaertner.aoc18.util.Advent;

public class Day24 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	private static final boolean DEBUG = false;

	static long part1() {
		List<String> lines = Advent.read("aoc18/024-data");
		List<Army> armies = parseArmies(lines);

		boolean ongoing = true;

		while (ongoing) {
			Map<Army, Army> targets = getTargets(armies);
			attack(armies, targets);
			ongoing = checkOutcome(armies);
		}

		// System.out.println("#1 " + remainingType(armies) + " win, remaining units: "
		// + countRemainingUnits(armies));
		return countRemainingUnits(armies);
	}

	static long part2() {
		List<String> lines = Advent.read("aoc18/024-data");

		boolean defended = false;
		int boost = 0;
		int remaining = 0;

		while (!defended) {
			List<Army> armies = parseArmies(lines);

			for (Army army : armies) {
				if (army.type.equals(ArmyType.defender)) {
					army.damage += boost;
				}
			}
			boost++;

			boolean ongoing = true;

			int hpPrev = 0;

			while (ongoing) {
				Map<Army, Army> targets = getTargets(armies);
				attack(armies, targets);

				int sum = 0;
				for (Army army : armies) {
					sum += army.hpEach * army.units;
				}
				if (sum == hpPrev) {
					// no change, break
					break;
				}
				hpPrev = sum;

				ongoing = checkOutcome(armies);
			}
			remaining = countRemainingUnits(armies);

			if (DEBUG) {
				System.out.println("#2 debug " + remainingType(armies) + " win, remaining units: " + remaining);
			}

			defended = remainingType(armies).equals(ArmyType.defender);
		}

		if (DEBUG) {
			System.out.println("#2 defenders win with " + boost + " boosted , remaining units: " + remaining);
		}

		return remaining;
	}

	private static ArmyType remainingType(List<Army> armies) {
		return armies.get(0).type;
	}

	private static int countRemainingUnits(List<Army> armies) {
		int sum = 0;
		for (Army army : armies) {
			sum += army.units;
		}
		return sum;
	}

	private static boolean checkOutcome(List<Army> armies) {
		Set<ArmyType> remainingTypes = new HashSet<>();
		for (Army army : armies) {
			remainingTypes.add(army.type);
		}

		return remainingTypes.size() > 1;
	}

	private static void attack(List<Army> armies, Map<Army, Army> targets) {
		// sort by initiative
		armies.sort(Comparator.comparing(Army::getInitiative)
				.reversed());

		for (Army army : armies) {
			if (!targets.containsKey(army)) {
				continue;
			}

			if (army.units < 1) {
				continue;
			}

			Army other = targets.get(army);

			int before = other.units;
			army.attack(other);
			int after = other.units;

			if (DEBUG) {
				System.out.println(army.type + " " + army.id + " attacks " + other.type + " " + other.id + ", killing "
						+ (before - after) + " units");
			}
		}

		for (int i = 0; i < armies.size(); i++) {
			Army army = armies.get(i);
			if (army.units < 1) {
				if (DEBUG) {
					System.out.println("DEFEATED: " + army);
				}
				armies.remove(i);
			}
		}
	}

	private static Map<Army, Army> getTargets(List<Army> armies) {
		// sort by effective damage, then initiative
		armies.sort(Comparator.comparing(Army::getEffectiveDamage)
				.thenComparing(Army::getInitiative)
				.reversed());

		Map<Army, Army> targets = new HashMap<>();
		for (Army attacker : armies) {
			// choose target
			Army chosen = null;
			int maxDamage = 0;

			for (Army other : armies) {
				if (attacker.equals(other) || attacker.type.equals(other.type) || targets.containsValue(other)) {
					continue;
				}
				// most damage
				int damage = attacker.damageTo(other);
				if (damage > maxDamage) {
					chosen = other;
					maxDamage = damage;
				}
			}

			if (chosen != null) {
				targets.put(attacker, chosen);

				if (DEBUG) {
					System.out.println(attacker);
					System.out.println("chooses");
					System.out.println(chosen);
					System.out.println();
				}
			}
		}

		return targets;
	}

	private static List<Army> parseArmies(List<String> lines) {

		List<String> defenderStrs = new ArrayList<>();
		List<String> attackerStrs = new ArrayList<>();

		boolean parseDefenders = false;
		boolean parseAttackers = false;

		for (String line : lines) {
			if (line == null || line.isEmpty()) {
				continue;
			} else if (line.contains("Immune System")) {
				parseAttackers = false;
				parseDefenders = true;
				continue;
			} else if (line.contains("Infection")) {
				parseDefenders = false;
				parseAttackers = true;
				continue;
			}

			if (parseDefenders) {
				defenderStrs.add(line);
			}
			if (parseAttackers) {
				attackerStrs.add(line);
			}
		}

		List<Army> armies = new ArrayList<>();

		for (String line : defenderStrs) {
			Army defender = parseArmy(line);
			defender.setArmyType(ArmyType.defender);
			armies.add(defender);
		}

		for (String line : attackerStrs) {
			Army attacker = parseArmy(line);
			attacker.setArmyType(ArmyType.attacker);
			armies.add(attacker);
		}

		return armies;
	}

	private static Army parseArmy(String line) {

		Army army = new Army();

		String[] s = line.split(" ");

		army.units = Integer.valueOf(s[0]);
		army.hpEach = Integer.valueOf(s[4]);

		int start = 7;
		int end = 7;
		if (s[start].startsWith("(")) {
			while (!s[end].endsWith(")")) {
				end++;
			}
		}

		if (line.contains("(")) {
			String wi = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
			String[] parts = wi.split("; ");
			for (String part : parts) {
				String[] typeStrs = part.split(" ");

				List<AttackType> types = new ArrayList<>();
				for (int i = 2; i < typeStrs.length; i++) {
					String type = typeStrs[i];
					if (type.endsWith(",")) {
						type = type.substring(0, type.length() - 1);
					}
					types.add(AttackType.valueOf(type));
				}

				if (part.startsWith("weak")) {
					army.weaknesses.addAll(types);
				} else if (part.startsWith("immune")) {
					army.immunities.addAll(types);
				}
			}
		} else {
			end--;
		}

		army.damage = Integer.valueOf(s[end + 6]);
		army.strength = AttackType.valueOf(s[end + 7]);
		army.initiative = Integer.valueOf(s[s.length - 1]);

		return army;
	}

}
