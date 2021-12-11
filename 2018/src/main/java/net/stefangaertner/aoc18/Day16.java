package net.stefangaertner.aoc18;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.stefangaertner.aoc18.pojo.OP_CODE;
import net.stefangaertner.aoc18.util.Advent;

public class Day16 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = Advent.read("aoc18/016-data");

		int matches = 0;

		for (int i = 0; i < lines.size(); i += 4) {
			List<String> instruction = lines.subList(i, i + 3);
			int howmany = check(instruction);
			if (howmany >= 3) {
				matches++;
			}
		}

		return matches;
	}

	static long part2() {
		List<String> lines = Advent.read("aoc18/016-data");

		Map<OP_CODE, Integer> opCodeMap = findOpCodes(lines);
		OP_CODE[] ooo = new OP_CODE[opCodeMap.size()];
		for (Entry<OP_CODE, Integer> e : opCodeMap.entrySet()) {
			ooo[e.getValue()] = e.getKey();
		}

		List<String> program = Advent.read("aoc18/016-data2");

		int[] registers = new int[4];

		for (String line : program) {

			int[] instruction = parseOpCode(line);
			registers = apply(registers, ooo[instruction[0]], instruction);

		}

		System.out.println("#2: " + Arrays.toString(registers) + " => " + registers[0]);

		return registers[0];
	}

	private static Map<OP_CODE, Integer> findOpCodes(List<String> lines) {

		Map<OP_CODE, Integer> found = new HashMap<>();

		Map<OP_CODE, Set<Integer>> matches = new HashMap<>();

		for (int i = 0; i < lines.size(); i += 4) {
			List<String> instruction = lines.subList(i, i + 3);

			int[] before = parseRegisters(instruction.get(0));
			int[] opcode = parseOpCode(instruction.get(1));
			int[] after = parseRegisters(instruction.get(2));

			for (OP_CODE op : OP_CODE.values()) {
				int[] applied = apply(before, op, opcode);
				if (Arrays.equals(applied, after)) {
					// it's a match
					if (!matches.containsKey(op)) {
						matches.put(op, new HashSet<>());
					}
					matches.get(op)
							.add(opcode[0]);
				}
			}
		}

		while (!matches.entrySet()
				.isEmpty()) {
			OP_CODE opCode = null;
			Integer opNum = null;

			for (Entry<OP_CODE, Set<Integer>> e : matches.entrySet()) {
				// System.out.println(e.getKey() + ": " +
				// e.getValue().stream().map(String::valueOf).collect(Collectors.joining(",
				// ")));
				if (e.getValue()
						.size() == 1) {
					opCode = e.getKey();
					opNum = e.getValue()
							.iterator()
							.next();
					break;
				}
			}

			if (opCode != null) {
				found.put(opCode, opNum);
				matches.remove(opCode);

				for (Entry<OP_CODE, Set<Integer>> e : matches.entrySet()) {
					e.getValue()
							.remove(opNum);
				}

			} else {
				System.out.println("done prematurely.");
				break;
			}
		}

		return found;
	}

	private static int check(List<String> lines) {

		int[] before = parseRegisters(lines.get(0));
		int[] opcode = parseOpCode(lines.get(1));
		int[] after = parseRegisters(lines.get(2));

		// System.out.println("need: " + Arrays.toString(after));

		int sum = 0;

		for (OP_CODE op : OP_CODE.values()) {
			int[] applied = apply(before, op, opcode);

			// System.out.println(op + ": " + Arrays.toString(applied));

			if (Arrays.equals(applied, after)) {
				sum++;
			}
		}

		return sum;
	}

	private static int[] parseOpCode(String str) {
		String[] s = str.split(" ");
		int[] o = new int[s.length];
		for (int i = 0; i < s.length; i++) {
			o[i] = Integer.valueOf(s[i]);
		}
		return o;
	}

	private static int[] apply(int[] before, OP_CODE op, int[] vals) {

		int[] registers = new int[before.length];
		for (int i = 0; i < before.length; i++) {
			registers[i] = before[i];
		}

		int a = vals[1];
		int b = vals[2];
		int c = vals[3];

		switch (op) {
		case ADDI:
			registers[c] = registers[a] + b;
			break;
		case ADDR:
			registers[c] = registers[a] + registers[b];
			break;
		case BANI:
			registers[c] = registers[a] & b;
			break;
		case BANR:
			registers[c] = registers[a] & registers[b];
			break;
		case BORI:
			registers[c] = registers[a] | b;
			break;
		case BORR:
			registers[c] = registers[a] | registers[b];
			break;
		case EQIR:
			registers[c] = a == registers[b] ? 1 : 0;
			break;
		case EQRI:
			registers[c] = registers[a] == b ? 1 : 0;
			break;
		case EQRR:
			registers[c] = registers[a] == registers[b] ? 1 : 0;
			break;
		case GTIR:
			registers[c] = a > registers[b] ? 1 : 0;
			break;
		case GTRI:
			registers[c] = registers[a] > b ? 1 : 0;
			break;
		case GTRR:
			registers[c] = registers[a] > registers[b] ? 1 : 0;
			break;
		case MULI:
			registers[c] = registers[a] * b;
			break;
		case MULR:
			registers[c] = registers[a] * registers[b];
			break;
		case SETI:
			registers[c] = a;
			break;
		case SETR:
			registers[c] = registers[a];
			break;
		default:
			break;
		}

		return registers;
	}

	private static int[] parseRegisters(String str) {
		String s = str.substring(str.indexOf("[") + 1, str.lastIndexOf("]"));
		List<String> registerStrs = Arrays.asList(s.split("\\s*,\\s*"));

		int[] registers = new int[registerStrs.size()];
		for (int i = 0; i < registerStrs.size(); i++) {
			registers[i] = Integer.valueOf(registerStrs.get(i));
		}

		return registers;
	}

}
