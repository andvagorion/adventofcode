package net.stefangaertner.aoc18.pojo;

import java.util.HashSet;

public class AdvancedSet<E> extends HashSet<E> {

	private static final long serialVersionUID = 1L;

	public boolean containsAny(AdvancedSet<Coordinate4D> other) {
		for (Coordinate4D c : other) {
			if (this.contains(c)) {
				return true;
			}
		}
		return false;
	}

}
