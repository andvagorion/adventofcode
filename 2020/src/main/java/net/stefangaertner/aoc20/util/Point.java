package net.stefangaertner.aoc20.util;

import java.util.Set;

public interface Point {

	Set<? extends Point> getNeighbors();
}
