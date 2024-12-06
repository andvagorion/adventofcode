package net.stefangaertner.aoc20.util;

public class IntPair {

	public int a;
	public int b;

	public IntPair (int a, int b) {
		this.a = a;
		this.b = b;
	}

	public static IntPair of(int a, int b) {
		return new IntPair(a, b);
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)", a, b);
	}
}
