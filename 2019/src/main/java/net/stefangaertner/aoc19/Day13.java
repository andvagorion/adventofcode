package net.stefangaertner.aoc19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.Parser;
import net.stefangaertner.aoc19.util.StringUtils;
import net.stefangaertner.aoc19.util.TileType;

public class Day13 {

	public static void main(String[] strings) throws IOException {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		String code = Advent.readLine("aoc19/013-data");

		Parser p = Parser.create(code).run();

		List<String> out = p.getOutput();

		int c = 0;

		for (int i = 0; i < out.size(); i += 3) {

			String s = out.get(i + 2);
			if ("2".equals(s)) {
				c++;
			}

		}

		return c;
	}

	static long part2() {
		boolean manualInput = false;
		boolean debugPrint = false;

		try {

			boolean won = false;

			String file;
			if (manualInput) {
				file = "aoc19/013-data";
			} else {
				file = "aoc19/013-data-more-walls";
			}

			String code = Advent.readLine(file);

			// screen is 36 x 24
			TileType[][] grid = new TileType[24][];
			for (int i = 0; i < grid.length; i++) {
				grid[i] = new TileType[36];
			}

			Parser p = Parser.create(code).stopOnOutput().stopOnInput();

			// set memory address 0 to 2
			p.setMemoryAddress(0, 2);

			long score = 0;

			BufferedReader reader = null;

			while (!p.isFinished() && !won) {

				while (!p.needsInput()) {

					p.run();
					int x = Integer.parseInt(p.getLastOutput());
					p.run();
					int y = Integer.parseInt(p.getLastOutput());
					p.run();
					int tileId = Integer.parseInt(p.getLastOutput());

					if (x == -1 && y == 0) {

						// score display
						score = tileId;

					} else if (x >= 0 && x < 36 && y >= 0 && y < 24) {

						TileType type = TileType.forId(tileId);
						grid[y][x] = type;

					} else {

						won = true;
						break;

					}

				}

				if (debugPrint) {
					System.out.println("Score: " + score);
					StringUtils.print2Darray(grid);
				}

				if (manualInput) {

					if (reader == null) {
						reader = new BufferedReader(new InputStreamReader(System.in));
					}

					String key = reader.readLine();
					if ("a".equals(key)) {
						p.input("-1");
					} else if ("d".equals(key)) {
						p.input("1");
					} else {
						p.input("0");
					}
				} else {

					// don't move the paddle, there are walls in place
					p.input("0");

				}

			}

			if (debugPrint) {
				System.out.println("");
				System.out.println("YOU WON!");
				System.out.println("");
				System.out.println("SCORE: " + score);
			}

			if (reader != null) {
				reader.close();
			}

			return score;

		} catch (IOException e) {
			return -1;
		}
	}

}
