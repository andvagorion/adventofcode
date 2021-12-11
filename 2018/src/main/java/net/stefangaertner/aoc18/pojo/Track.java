package net.stefangaertner.aoc18.pojo;

import java.util.HashSet;
import java.util.Set;

public class Track {

	public char c = '@';
	public TrackType type;
	public Set<Cart> carts = new HashSet<>();

	public Track(char c, TrackType type) {
		this.c = c;
		this.type = type;
	}

	@Override
	public String toString() {
		if (carts.size() == 1) return carts.iterator().next().toString();
		else if (carts.size() > 1) return "X";
		
		return "" + c;
	}
}
