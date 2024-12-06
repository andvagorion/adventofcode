package net.stefangaertner.aoc18;

import java.util.Arrays;

import net.stefangaertner.aoc18.pojo.Packet;
import net.stefangaertner.aoc18.util.Advent;
import net.stefangaertner.aoc18.util.StringUtils;

public class Day08 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		String data = Advent.readLine("aoc18/008-data");
		Packet root = computeTree(data);

		return getSum(root);
	}

	static long part2() {
		String data = Advent.readLine("aoc18/008-data");
		Packet root = computeTree(data);

		return getOtherSum(root);
	}

	private static Packet computeTree(String line) {
		String[] remainder = line.split(" ");

		int id = 0;
		Packet current = new Packet();
		current.name = "" + id;

		while (remainder.length > 0) {

			if (current.childNum > 0 && current.children.size() == current.childNum) {

				// process meta
				current.meta = Arrays.copyOfRange(remainder, 0, current.metaNum);

				remainder = Arrays.copyOfRange(remainder, current.metaNum, remainder.length);
				if (current.parent != null) {
					current = current.parent;
				}
				continue;
			}

			if (current.children.size() < current.childNum) {
				Packet child = new Packet();
				id++;
				child.name = "" + id;
				child.parent = current;
				current.children.add(child);
				current = child;
			}

			int childNum = Integer.parseInt(remainder[0]);
			int metaNum = Integer.parseInt(remainder[1]);

			if (current.metaNum < 0) {
				current.metaNum = metaNum;
			}
			if (current.childNum < 0) {
				current.childNum = childNum;
			}

			if (childNum == 0) {

				current.meta = Arrays.copyOfRange(remainder, 2, 2 + metaNum);
				remainder = Arrays.copyOfRange(remainder, 2 + current.metaNum, remainder.length);
				current = current.parent;

			} else {

				remainder = Arrays.copyOfRange(remainder, 2, remainder.length);
				Packet child = new Packet();
				id++;
				child.name = "" + id;
				child.parent = current;
				current.children.add(child);
				current = child;

			}
		}

		return getRoot(current);
	}

	private static Packet getRoot(Packet p) {
		Packet x = p;
		while (x.parent != null) {
			x = x.parent;
		}
		return x;
	}

	private static int getOtherSum(Packet p) {

		if (p.childNum == 0) {
			int sum = 0;
			for (int i = 0; i < p.meta.length; i++) {
				sum += Integer.parseInt(p.meta[i]);
			}
			return sum;
		}

		int sum = 0;
		for (int i = 0; i < p.meta.length; i++) {
			int idx = Integer.parseInt(p.meta[i]) - 1;

			if (idx < p.children.size()) {
				sum += getOtherSum(p.children.get(idx));
			}

		}

		return sum;
	}

	private static int getSum(Packet p) {
		int sum = 0;
		for (int i = 0; i < p.meta.length; i++) {
			sum += Integer.parseInt(p.meta[i]);
		}
		for (Packet c : p.children) {
			sum += getSum(c);
		}
		return sum;
	}

	@SuppressWarnings("unused")
	private static void printTree(Packet root) {
		print(root, 0);
	}

	private static void print(Packet p, int padding) {
		String node = "packet " + p.name + " with " + p.children.size() + " children " + Arrays.toString(p.meta);
		System.out.println(StringUtils.repeat(" ", padding) + node);
		for (Packet c : p.children) {
			print(c, padding + 4);
		}
	}

}
