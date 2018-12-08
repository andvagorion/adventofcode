package net.stefangaertner.aoc18;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.stefangaertner.aoc18.pojo.Rectangle;
import net.stefangaertner.util.FileUtils;

public class Day03 {

	private static Pattern pattern = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");

	public static void main(String[] args) {
		List<String> lines = FileUtils.read("aoc18/003-data");

		part1(lines);
		part2(lines);
	}

	private static void part1(List<String> lines) {

		List<Rectangle> rects = getRectangles(lines);
				
		int[] grid = createGrid(rects);
		int overlapping = countOverlapping(grid);
		
		System.out.println("#1 Overlapping tiles: " + overlapping);
	}
	
	private static int[] createGrid(List<Rectangle> rects) {
		Rectangle bounds = getBounds(rects);
		int[] grid = new int[(bounds.w + 1) * (bounds.h + 1)];
				
		for (Rectangle rect : rects) {
			for (int y = rect.y; y < rect.y + rect.h; y++) {
				for (int x = rect.x; x < rect.x + rect.w; x++) {
					grid[y * bounds.w + x] += 1;
				}
			}
		}
		
		return grid;
	}
	
	private static int countOverlapping(int[] grid) {

		int c = 0;
		for (int i = 0; i < grid.length; i++) {
			if (grid[i] > 1) c++;
		}
		
		return c;
	}
	
	private static void part2(List<String> lines) {
		List<Rectangle> rects = getRectangles(lines);
		Set<Integer> possible = rects.stream().map(r -> r.num).collect(Collectors.toSet());

		for (Rectangle rect : rects) {
			for (Rectangle other : rects) {
				// don't compare against itself
				if (rect.num == other.num) continue;
				// already overlapping
				if (!possible.contains(rect.num)) continue;
				
				if (rect.intersects(other)) {
					possible.remove(rect.num);
					possible.remove(other.num);
				}
			}
		}

		String id = possible.iterator().hasNext() ? String.valueOf(possible.iterator().next()) : "none";
		System.out.println("#2 Not overlapping: claim " + id);
	}

	private static List<Rectangle> getRectangles(List<String> lines) {
		List<Rectangle> rects = new ArrayList<>();

		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {

				int num = Integer.parseInt(matcher.group(1));
				int x = Integer.parseInt(matcher.group(2));
				int y = Integer.parseInt(matcher.group(3));
				int w = Integer.parseInt(matcher.group(4));
				int h = Integer.parseInt(matcher.group(5));

				rects.add(new Rectangle(num, x, y, w, h));
			}
		}

		return rects;
	}

	private static Rectangle getBounds(List<Rectangle> rects) {

		Rectangle grid = new Rectangle(-1, 0, 0, 0, 0);

		for (Rectangle r : rects) {

			if (r.x + r.w > grid.w) {
				grid.w = r.x + r.w;
			}

			if (r.y + r.h > grid.h) {
				grid.h = r.y + r.h;
			}

		}

		return grid;
	}

}
