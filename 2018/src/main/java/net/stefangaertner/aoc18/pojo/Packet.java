package net.stefangaertner.aoc18.pojo;

import java.util.ArrayList;
import java.util.List;

public class Packet {
	
	public String name;

	public 	int metaNum = -1;
	public int childNum = -1;
	
	public Packet parent;
	public List<Packet> children = new ArrayList<>();
	public String[] meta;

}
