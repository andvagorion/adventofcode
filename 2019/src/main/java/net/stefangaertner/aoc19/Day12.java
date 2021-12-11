package net.stefangaertner.aoc19;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.Moon;

public class Day12 {

	public static void main(String[] strings) {
		Advent.print(1, part1());
	}

	static long part1() {
		List<Moon> moons = getMoons();

		// simulate in time steps
		for (int i = 0; i < 1000; i++) {

			updateVelocity(moons);

			// update position by applying velocity
			updatePosition(moons);

		}

		return calculateEnergy(moons);
	}

	static long part2() {
		List<Moon> moons = getMoons();

		List<Moon> originals = new ArrayList<>();
		moons.stream().forEach(m -> originals.add(new Moon(m.x, m.y, m.z)));

		int c = 0;
		while (!moons.stream().allMatch(m -> m.rotated)) {
			updateVelocity(moons);
			updatePosition(moons);

			c++;

			for (int i = 0; i < moons.size(); i++) {
				Moon m = moons.get(i);
				if (!m.rotated && m.samePosition(originals.get(i))) {
					m.rot = c;
					m.rotated = true;
				}
			}
		}

		String numbers = moons.stream().map(m -> String.valueOf(m.rot)).collect(Collectors.joining(", "));

		BigInteger total = BigInteger.ONE;
		for (Moon m : moons) {
			total = total.multiply(BigInteger.valueOf(m.rot));
		}

		// 92258534, 194754945, 81661887, 159812653

		System.out.println(numbers);

		return total.longValue();
	}

	private static List<Moon> getMoons() {
		List<Moon> moons = new ArrayList<>();
		moons.add(new Moon(-10, -13, 7));
		moons.add(new Moon(1, 2, 1));
		moons.add(new Moon(-15, -3, 13));
		moons.add(new Moon(3, 7, -4));
		return moons;
	}

	@SuppressWarnings("unused")
	private static List<Moon> getExample1() {
		List<Moon> moons = new ArrayList<>();
		moons.add(new Moon(-1, 0, 2));
		moons.add(new Moon(2, -10, -7));
		moons.add(new Moon(4, -8, 8));
		moons.add(new Moon(3, 5, -1));
		return moons;
	}

	@SuppressWarnings("unused")
	private static List<Moon> getExample2() {
		List<Moon> moons = new ArrayList<>();
		moons.add(new Moon(-8, -10, 0));
		moons.add(new Moon(5, 5, 10));
		moons.add(new Moon(2, -7, 3));
		moons.add(new Moon(9, -8, -3));
		return moons;
	}

	private static void updatePosition(List<Moon> moons) {
		for (Moon moon : moons) {
			moon.x += moon.vx;
			moon.y += moon.vy;
			moon.z += moon.vz;
		}
	}

	private static void updateVelocity(List<Moon> moons) {

		// update velocity by applying gravity to all moons
		for (int i = 0; i < moons.size(); i++) {
			Moon moon = moons.get(i);

			for (int j = i + 1; j < moons.size(); j++) {

				Moon other = moons.get(j);

				if (moon.x < other.x) {
					moon.vx += 1;
					other.vx -= 1;
				} else if (moon.x > other.x) {
					moon.vx -= 1;
					other.vx += 1;
				}

				if (moon.y < other.y) {
					moon.vy += 1;
					other.vy -= 1;
				} else if (moon.y > other.y) {
					moon.vy -= 1;
					other.vy += 1;
				}

				if (moon.z < other.z) {
					moon.vz += 1;
					other.vz -= 1;
				} else if (moon.z > other.z) {
					moon.vz -= 1;
					other.vz += 1;
				}

			}
		}

	}

	private static int calculateEnergy(List<Moon> moons) {

		int sum = 0;

		for (Moon moon : moons) {
			sum += calculateEnergy(moon);
		}

		return sum;
	}

	private static int calculateEnergy(Moon moon) {

		// potential energy is the sum of the absolute values of its x, y, and z
		// position coordinates
		int pot = Math.abs(moon.x) + Math.abs(moon.y) + Math.abs(moon.z);

		// kinetic energy is the sum of the absolute values of its velocity
		// coordinates
		int kin = Math.abs(moon.vx) + Math.abs(moon.vy) + Math.abs(moon.vz);

		// potential energy multiplied by its kinetic energy
		return pot * kin;
	}

}
