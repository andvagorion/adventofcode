package net.stefangaertner.aoc19;

import java.util.List;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.Parser;

public class Day07 {

	static String example1 = "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0";
	static String example2 = "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0";

	static String example1_p2 = "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5";
	static String example2_p2 = "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10";

	public static void main(String[] strings) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		String code = Advent.readLine("aoc19/007-data1");

		List<String> permutations = Advent.read("aoc19/007-permutations");

		int maxAmp = 0;

		for (int i = 0; i < permutations.size(); i++) {

			String[] input = permutations.get(i)
					.split(",");
			int amplitude = 0;

			for (int j = 0; j < input.length; j++) {

				String in = input[j] + "," + amplitude;
				Parser p = Parser.create(code)
						.input(in)
						.run();

				int out = Integer.parseInt(p.getOutput()
						.get(0));

				amplitude = out;
			}

			if (amplitude > maxAmp) {
				maxAmp = amplitude;
			}
		}

		return maxAmp;
	}

	static long part2() {
		String code = Advent.readLine("aoc19/007-data1");

		List<String> permutations = Advent.read("aoc19/007-permutations2");

		int maxAmp = 0;

		for (int i = 0; i < permutations.size(); i++) {

			String[] input = permutations.get(i)
					.split(",");

			int out = runLooped(code, input);

			if (out > maxAmp) {
				maxAmp = out;
			}
		}

		return maxAmp;
	}

	private static int runLooped(String code, String[] input) {
		int amplitude = 0;

		int outAmp = 0;

		int idx = 0;
		boolean finished = false;

		Parser[] parsers = new Parser[5];
		for (int i = 0; i < 5; i++) {
			parsers[i] = Parser.create(code)
					.stopOnOutput()
					.input(input[i]);
		}

		while (!finished) {

			Parser p = parsers[idx];
			p.input(String.valueOf(amplitude))
					.run();

			int out = Integer.parseInt(p.getLastOutput());

			amplitude = out;

			if (idx == 4) {
				outAmp = amplitude;
			}

			if (p.isFinished()) {
				finished = true;
			}

			idx = (idx + 1) % 5;
		}

		return outAmp;
	}

}
