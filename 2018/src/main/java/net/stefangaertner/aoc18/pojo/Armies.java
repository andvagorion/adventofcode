package net.stefangaertner.aoc18.pojo;

import java.util.ArrayList;
import java.util.List;

public class Armies {
	
	public List<Army> defenders;
	public List<Army> attackers;
	
	public Armies(List<Army> defenders, List<Army> attackers) {
		this.defenders = defenders;
		this.attackers = attackers;
	}

	public List<Army> getAll() {
		List<Army> all = new ArrayList<>();
		all.addAll(attackers);
		all.addAll(defenders);
		return all;
	}
}
