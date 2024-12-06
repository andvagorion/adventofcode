package net.stefangaertner.aoc18.pojo;

import java.util.ArrayList;
import java.util.List;

public class WaterNode {

	public int x;
	public int y;
	
	public boolean still;
	
	public WaterNode parent;
	public List<WaterNode> children = new ArrayList<>();
	
	public WaterNode(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Pair [x=" + x + ", y=" + y + "]";
	}
}
