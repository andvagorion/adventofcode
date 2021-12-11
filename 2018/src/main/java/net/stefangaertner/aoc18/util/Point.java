package net.stefangaertner.aoc18.util;

import java.util.Set;

public interface Point {

	Set<? extends Point> getNeighbors();
}
