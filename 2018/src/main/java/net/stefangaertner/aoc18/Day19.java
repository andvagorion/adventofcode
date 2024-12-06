package net.stefangaertner.aoc18;

import java.util.List;

import net.stefangaertner.aoc18.util.Advent;

public class Day19 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = Advent.read("aoc18/019-data");

		int ip = 0;
		Integer ipRegister = Integer.valueOf(lines.remove(0)
				.split(" ")[1]);

		int[] registers = new int[6];

		while (ip < lines.size()) {

			registers[ipRegister] = ip;

			registers = apply(registers, ip, lines);

			ip = registers[ipRegister];
			ip++;
		}

		// System.out.println("#1: " + Arrays.toString(registers) + " => " +
		// registers[0]);

		return registers[0];
	}

	static long part2() {
		List<String> lines = Advent.read("aoc18/019-data");

		int ip = 0;
		Integer ipRegister = Integer.valueOf(lines.remove(0)
				.split(" ")[1]);

		int[] registers = new int[6];

		registers[0] = 1;

		// first it is increasing register 1 to the maximum value

		int max = -1;
		int c = 0;

		while (true) {

			registers[ipRegister] = ip;

			registers = apply(registers, ip, lines);

			if (max == registers[1] && c > 10) {
				// we've hit the maximum value 10 times in a row
				break;
			}

			if (max == registers[1]) {
				c++;
			} else {
				c = 0;
			}

			max = registers[1];

			ip = registers[ipRegister];
			ip++;
		}

		// then the algorithm is summing the prime factors of that number
		long sum = 0;
		for (int i = 1; i <= max; i++) {
			if (max % i == 0) {
				sum += i;
			}
		}

		return sum;
	}

	private static int[] apply(int[] registers, int ip, List<String> lines) {

		int[] out = new int[registers.length];
		for (int i = 0; i < registers.length; i++) {
			out[i] = registers[i];
		}

		String opline = lines.get(ip);
		String[] instr = opline.split(" ");

		String op = instr[0];

		int a = Integer.valueOf(instr[1]);
		int b = Integer.valueOf(instr[2]);
		int c = Integer.valueOf(instr[3]);

		if (op.equals("seti")) {
			out[c] = a;
		} else if (op.equals("setr")) {
			out[c] = registers[a];
		} else if (op.equals("addi")) {
			out[c] = registers[a] + b;
		} else if (op.equals("addr")) {
			out[c] = registers[a] + registers[b];
		} else if (op.equals("muli")) {
			out[c] = registers[a] * b;
		} else if (op.equals("mulr")) {
			out[c] = registers[a] * registers[b];
		} else if (op.equals("gtir")) {
			out[c] = a > registers[b] ? 1 : 0;
		} else if (op.equals("gtri")) {
			out[c] = registers[a] > b ? 1 : 0;
		} else if (op.equals("gtrr")) {
			out[c] = registers[a] > registers[b] ? 1 : 0;
		} else if (op.equals("eqir")) {
			out[c] = a == registers[b] ? 1 : 0;
		} else if (op.equals("eqri")) {
			out[c] = registers[a] == b ? 1 : 0;
		} else if (op.equals("eqrr")) {
			out[c] = registers[a] == registers[b] ? 1 : 0;
		} else {
			return null;
		}

		return out;
	}

}
