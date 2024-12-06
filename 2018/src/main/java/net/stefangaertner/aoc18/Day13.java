package net.stefangaertner.aoc18;

import java.util.List;

import net.stefangaertner.aoc18.pojo.Cart;
import net.stefangaertner.aoc18.pojo.Direction;
import net.stefangaertner.aoc18.pojo.Track;
import net.stefangaertner.aoc18.pojo.TrackType;
import net.stefangaertner.aoc18.util.Advent;

public class Day13 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static boolean crashed = false;
	static int firstX = -1;
	static int firstY = -1;

	static String part1() {
		List<String> lines = Advent.read("aoc18/013-data");
		Track[][] tracks = parse(lines);

		// printTrack(tracks);

		while (!crashed) {
			moveCarts(tracks);
		}

		// printTrack(tracks);

		// System.out.println("#1 first crash occurs at " + firstX + "," + firstY);

		return String.format("%s,%s", firstX, firstY);
	}

	static boolean lastRemaining = false;
	static int lastX = -1;
	static int lastY = -1;

	static String part2() {
		List<String> lines = Advent.read("aoc18/013-data");
		Track[][] tracks = parse(lines);

		int remaining = countCarts(tracks);

		while (!lastRemaining) {
			moveCarts2(tracks);
			int _remaining = countCarts(tracks);
			if (_remaining < remaining) {
				System.out.println("Carts remaining: " + remaining);
			}
			remaining = _remaining;
			lastRemaining = remaining == 1;
		}

		for (int y = 0; y < tracks.length; y++) {
			for (int x = 0; x < tracks[0].length; x++) {
				Track t = tracks[y][x];
				if (t != null && !t.carts.isEmpty()) {
					lastX = x;
					lastY = y;
				}
			}
		}

		// printTrack(tracks);

		// System.out.println("#2 last remaining is at " + lastX + "," + lastY);

		return String.format("%s,%s", lastX, lastY);
	}

	private static int countCarts(Track[][] tracks) {
		int sum = 0;
		for (int y = 0; y < tracks.length; y++) {
			for (int x = 0; x < tracks[0].length; x++) {
				Track t = tracks[y][x];
				if (t != null)
					sum += t.carts.size();
			}
		}
		return sum;
	}

	private static void moveCarts2(Track[][] tracks) {

		// move each cart
		for (int y = 0; y < tracks.length; y++) {
			for (int x = 0; x < tracks[0].length; x++) {
				Track t = tracks[y][x];
				if (t != null && !t.carts.isEmpty() && t.carts.size() == 1) {
					Cart c = t.carts.iterator()
							.next();
					if (c.moved)
						continue;

					int y1 = -1;
					int x1 = -1;

					if (c.dir == Direction.LEFT) {
						y1 = y;
						x1 = x - 1;
					} else if (c.dir == Direction.RIGHT) {
						y1 = y;
						x1 = x + 1;
					} else if (c.dir == Direction.UP) {
						y1 = y - 1;
						x1 = x;
					} else if (c.dir == Direction.DOWN) {
						y1 = y + 1;
						x1 = x;
					}

					tracks[y1][x1].carts.add(c);
					t.carts.remove(c);

					c.moved = true;

					if (tracks[y1][x1].carts.size() > 1) {
						tracks[y1][x1].carts.clear();
					}
				}
			}
		}

		// turn if necessary
		for (int y = 0; y < tracks.length; y++) {
			for (int x = 0; x < tracks[0].length; x++) {
				Track t = tracks[y][x];
				if (t != null && !t.carts.isEmpty() && t.carts.size() == 1) {
					Cart c = t.carts.iterator()
							.next();

					if (t.type == TrackType.INTERSECTION)
						c.turn();
					else if (t.type == TrackType.TURN)
						if (t.c == '\\') {
							if (c.dir == Direction.UP || c.dir == Direction.DOWN)
								c.turnLeft();
							else if (c.dir == Direction.RIGHT || c.dir == Direction.LEFT)
								c.turnRight();
						} else if (t.c == '/') {
							if (c.dir == Direction.UP || c.dir == Direction.DOWN)
								c.turnRight();
							else if (c.dir == Direction.RIGHT || c.dir == Direction.LEFT)
								c.turnLeft();
						}
				}
			}
		}

		for (int y = 0; y < tracks.length; y++) {
			for (int x = 0; x < tracks[0].length; x++) {
				Track t = tracks[y][x];

				if (t != null && !t.carts.isEmpty()) {
					for (Cart c : t.carts) {
						c.moved = false;
					}
				}
			}
		}
	}

	private static void moveCarts(Track[][] tracks) {

		for (int y = 0; y < tracks.length; y++) {
			for (int x = 0; x < tracks[0].length; x++) {
				Track t = tracks[y][x];
				if (t != null && !t.carts.isEmpty() && t.carts.size() == 1) {
					Cart c = t.carts.iterator()
							.next();
					if (c.moved)
						continue;

					// move each cart
					if (c.dir == Direction.LEFT)
						tracks[y][x - 1].carts.add(c);
					else if (c.dir == Direction.RIGHT)
						tracks[y][x + 1].carts.add(c);
					else if (c.dir == Direction.UP)
						tracks[y - 1][x].carts.add(c);
					else if (c.dir == Direction.DOWN)
						tracks[y + 1][x].carts.add(c);

					c.moved = true;

					t.carts.remove(c);
				}
			}
		}

		for (int y = 0; y < tracks.length; y++) {
			for (int x = 0; x < tracks[0].length; x++) {
				Track t = tracks[y][x];
				if (t != null && !t.carts.isEmpty() && t.carts.size() == 1) {
					Cart c = t.carts.iterator()
							.next();

					// turn if necessary
					if (t.type == TrackType.INTERSECTION)
						c.turn();
					else if (t.type == TrackType.TURN)
						if (t.c == '\\') {
							if (c.dir == Direction.UP || c.dir == Direction.DOWN)
								c.turnLeft();
							else if (c.dir == Direction.RIGHT || c.dir == Direction.LEFT)
								c.turnRight();
						} else if (t.c == '/') {
							if (c.dir == Direction.UP || c.dir == Direction.DOWN)
								c.turnRight();
							else if (c.dir == Direction.RIGHT || c.dir == Direction.LEFT)
								c.turnLeft();
						}
				}
			}
		}

		for (int y = 0; y < tracks.length; y++) {
			for (int x = 0; x < tracks[0].length; x++) {
				Track t = tracks[y][x];

				if (t != null && !t.carts.isEmpty()) {
					for (Cart c : t.carts) {
						c.moved = false;
					}
				}

				if (t != null && t.carts.size() > 1) {
					crashed = true;
					firstX = x;
					firstY = y;
					for (Cart c : t.carts)
						c.crashed = true;
				}
			}
		}
	}

	private static Track[][] parse(List<String> lines) {

		Track[][] y = new Track[lines.size()][];

		int max = 0;
		for (String line : lines) {
			int s = line.length();
			if (s > max)
				max = s;
		}

		for (int j = 0; j < lines.size(); j++) {

			String line = lines.get(j);

			Track[] x = new Track[max];

			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				if (c == '/' || c == '\\')
					x[i] = new Track(c, TrackType.TURN);
				else if (c == '-')
					x[i] = new Track(c, TrackType.STRAIGHT_HOR);
				else if (c == '|')
					x[i] = new Track(c, TrackType.STRAIGHT_VER);
				else if (c == '+')
					x[i] = new Track(c, TrackType.INTERSECTION);
				else if (c == '>')
					x[i] = new Track('-', TrackType.STRAIGHT_HOR);
				else if (c == '<')
					x[i] = new Track('-', TrackType.STRAIGHT_HOR);
				else if (c == 'v')
					x[i] = new Track('|', TrackType.STRAIGHT_VER);
				else if (c == '^')
					x[i] = new Track('|', TrackType.STRAIGHT_VER);
				else
					x[i] = null;

				if (c == '>')
					x[i].carts.add(new Cart(Direction.RIGHT));
				else if (c == '<')
					x[i].carts.add(new Cart(Direction.LEFT));
				else if (c == 'v')
					x[i].carts.add(new Cart(Direction.DOWN));
				else if (c == '^')
					x[i].carts.add(new Cart(Direction.UP));
			}

			y[j] = x;
		}

		return y;
	}

	@SuppressWarnings("unused")
	private static void printTrack(Track[][] tracks) {
		for (int y = 0; y < tracks.length; y++) {
			String s = "";
			for (int x = 0; x < tracks[0].length; x++) {
				Track t = tracks[y][x];
				if (t == null)
					s += " ";
				else
					s += t.toString();
			}
			System.out.println(s);
		}
	}

}
