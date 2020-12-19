package net.stefangaertner.util;

import java.util.Set;

public interface Point {

	Set<? extends Point> getNeighbors();
}
