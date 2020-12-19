package net.stefangaertner.aoc20.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BootCode {

	List<String> code;

	int idx = 0;
	public long acc = 0;

	int cycles = 0;

	public boolean loop = false;
	Set<Integer> visited = new HashSet<>();

	public BootCode (List<String> lines) {
		code = lines;
	}

	public void execute() {

		while (idx < code.size() && cycles < 10000) {

			if (visited.contains(idx)) {
				loop = true;
				return;
			}

			visited.add(idx);

			String line = code.get(idx);
			String op = line.split(" ")[0];
			long arg = Long.valueOf(line.split(" ")[1]);

			if ("acc".equals(op)) {
				acc += arg;
				idx++;
			}

			if ("jmp".equals(op)) {
				idx += arg;
			}

			if ("nop".equals(op)) {
				idx++;
			}

			cycles++;
		}

	}

}
