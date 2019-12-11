package net.stefangaertner.aoc19;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import net.stefangaertner.aoc18.pojo.Pair;
import net.stefangaertner.util.FileUtils;
import net.stefangaertner.util.StringUtils;
import net.stefangaertner.aoc19.util.Parser;

public class Day11 {
	
	public static void main(String[] strings) {

		List<String> lines = FileUtils.read("aoc19/011-data");
		String code = lines.get(0);
		
		part2(code);
	}
	
	private static void part1(String code) {
		Map<Pair, String> visited = runRobot(code, "0", false);
		System.out.println("Part 1: " + visited.size());
	}
	
	private static void part2(String code) {
		Map<Pair, String> visited = runRobot(code, "1", false);
		
		System.out.println("Visited: " + visited.size());
		
		Pair min = new Pair(0,0);
		Pair max = new Pair(0,0);
		
		for (Pair p : visited.keySet()) {
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
		
		int offsetY = Math.abs(min.y);
		int offsetX = Math.abs(min.x);
		int sizeY = offsetY + max.y + 1;
		int sizeX = offsetX + max.x + 1;
		
		char[][] image = new char[sizeY][];
		for (int y = 0; y < sizeY; y++) {
			image[y] = new char[sizeX];
			for (int x = 0; x < sizeX; x++) {
				image[y][x] = '.';
			}
		}
		
		image[offsetY][offsetX] = '-';
		StringUtils.print2Darray(image);
		
		for (Entry<Pair, String> e : visited.entrySet()) {
			Pair p = e.getKey();
			if (e.getValue().equals("1")) {
				image[p.y + offsetY][p.x + offsetX] = 'X';
			}
		}
		
		System.out.println("Part 2: ");
		StringUtils.print2Darray(image);
	}
	
	private static Map<Pair, String> runRobot(String code, String initialPanelColor, boolean debugPrint) {
		
		Pair dir = new Pair(0,-1);
		Pair pos = new Pair(0,0);
		
		Map<Pair, String> visited = new HashMap<>();
		visited.put(pos, initialPanelColor);
		
		Parser p = Parser.create(code).stopOnOutput();
		
		int c = 0;
		
		while (!p.isFinished()) {
			c++;
			
			if (debugPrint && c % 1000 == 0) {
				System.out.println(c + " moves.");
			}
			
			String tileCol = !visited.containsKey(pos) ? "0" : visited.get(pos);
			p.input(tileCol);
			
			p.run();
			String color = p.getLastOutput();
			visited.put(pos, color);
			
			p.run();
			String move = p.getLastOutput();
			
			if ("0".equals(move)) {
				// turn left
				
				if (Pair.of(0,-1).equals(dir)) {
					dir = Pair.of(-1,0);
				} else if (Pair.of(-1,0).equals(dir)) {
					dir = Pair.of(0,1);
				} else if (Pair.of(0,1).equals(dir)) {
					dir = Pair.of(1,0);
				} else if (Pair.of(1,0).equals(dir)) {
					dir = Pair.of(0,-1);
				}
				
			} else {
				// turn right
				
				if (Pair.of(0,-1).equals(dir)) {
					dir = Pair.of(1,0);
				} else if (Pair.of(1,0).equals(dir)) {
					dir = Pair.of(0,1);
				} else if (Pair.of(0,1).equals(dir)) {
					dir = Pair.of(-1,0);
				} else if (Pair.of(-1,0).equals(dir)) {
					dir = Pair.of(0,-1);
				}
				
			}
			
			// move robot
			pos = Pair.of(pos.x + dir.x, pos.y + dir.y);
			
		}
		
		if (debugPrint) {
			String out = visited.keySet().stream().map(t -> "[" + t.x + ", " + t.y + "]").collect(Collectors.joining(", "));
			System.out.println(out);
		}
		
		return visited;
	}

}
