package net.stefangaertner.aoc19.util;

import java.util.Set;

public interface Point {

	Set<? extends Point> getNeighbors();
}
