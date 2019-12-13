package net.stefangaertner.util;

import java.util.Collection;

import net.stefangaertner.aoc18.pojo.Pair;

public class PairUtils {

	public static Pair[] getMinAndMax(Collection<Pair> pairs) {

		Pair min = new Pair(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Pair max = new Pair(Integer.MIN_VALUE, Integer.MIN_VALUE);

		for (Pair p : pairs) {
			if (p.x < min.x) {
				min.x = p.x;
			}
			if (p.x > max.x) {
				max.x = p.x;
			}
			if (p.y < min.y) {
				min.y = p.y;
			}
			if (p.y > max.y) {
				max.y = p.y;
			}
		}

		return new Pair[] { min, max };
	}

}
