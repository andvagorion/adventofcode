package net.stefangaertner.aoc19;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.stefangaertner.aoc18.pojo.Point;
import net.stefangaertner.util.ArrayUtils;
import net.stefangaertner.util.FileUtils;
import net.stefangaertner.util.GridUtils;

public class Day24 {

	private static final int SIZE = 5;

	private static final int CENTER = (SIZE - 1) / 2;
	private static final Point OUTER_TOP = Point.of(CENTER, CENTER - 1);
	private static final Point OUTER_LEFT = Point.of(CENTER - 1, CENTER);
	private static final Point OUTER_RIGHT = Point.of(CENTER + 1, CENTER);
	private static final Point OUTER_BOTTOM = Point.of(CENTER, CENTER + 1);

	public static void main(String[] strings) throws IOException {

		List<String> lines = FileUtils.read("aoc19/024-data");

		boolean[][] grid = GridUtils.toGrid(lines, c -> c == '#');

		part1(grid);
		part2(grid);
	}

	private static void part2(boolean[][] grid) {
		Map<Integer, boolean[][]> levels = new TreeMap<>();
		levels.put(0, grid);
		addIfNotExists(levels, -1);
		addIfNotExists(levels, 1);

		for (int i = 0; i < 200; i++) {
			if (i % 2 == 0) {
				int min = levels.keySet().stream().min(Integer::compare).get();
				int max = levels.keySet().stream().max(Integer::compare).get();

				addIfNotExists(levels, min - 1);
				addIfNotExists(levels, max + 1);
			}

			levels = runMultidimensional(levels);
		}

//		for (Map.Entry<Integer, boolean[][]> e : levels.entrySet()) {
//			System.out.println("Level " + e.getKey());
//			StringUtils.print2Darray(e.getValue());
//			System.out.println(" ");
//		}

		System.out.println("Part 2: " + getSum(levels.values()));

	}

	private static int getSum(Collection<boolean[][]> levels) {
		int sum = 0;

		for (boolean[][] level : levels) {
			sum += GridUtils.countValues(level, b -> b);
		}

		return sum;
	}

	private static Map<Integer, boolean[][]> runMultidimensional(Map<Integer, boolean[][]> levels) {

		// get outer edge cells
		Set<Point> outerEdges = getOuterCells();

		// get inner edge cells
		Set<Point> innerEdges = getInnerCells();

		// get all other cells
		Set<Point> otherCells = getOtherCells(outerEdges, innerEdges);

		// clone current state
		Map<Integer, boolean[][]> ret = new TreeMap<>();
		for (Integer key : levels.keySet()) {
			ret.put(key, GridUtils.createGrid(SIZE, SIZE, false));
		}

		int min = levels.keySet().stream().min(Integer::compare).get();
		int max = levels.keySet().stream().max(Integer::compare).get();

		for (int i = min + 1; i < max; i++) {
			walk(levels, i, ret, outerEdges, innerEdges, otherCells);
		}

		return ret;

	}

	private static void walk(Map<Integer, boolean[][]> levels, int i, Map<Integer, boolean[][]> ret,
			Set<Point> outerEdges, Set<Point> innerEdges, Set<Point> otherCells) {

		boolean[][] currentLevel = levels.get(i);
		boolean[][] nextLevel = ret.get(i);

		boolean[][] outer = levels.get(i - 1);
		boolean[][] inner = levels.get(i + 1);

		// consider only cells on edges, compare with outer
		for (Point cell : outerEdges) {

			Set<Point> checkInUpper = getCellsToCheckInOuter(cell);

			int innerNeighbors = ArrayUtils.countNeighbors(currentLevel, Point.of(cell.x, cell.y), b -> b);
			int outerNeighbors = (int) checkInUpper.stream().filter(c -> outer[c.y][c.x]).count();

			boolean nextState = nextState(currentLevel[cell.y][cell.x], innerNeighbors + outerNeighbors);

			nextLevel[cell.y][cell.x] = nextState;
		}

		// consider only cells on inner edge of outer grid, compare with inner
		for (Point cell : innerEdges) {

			Set<Point> checkInInner = getCellsToCheckInInner(cell);

			int outerNeighbors = ArrayUtils.countNeighbors(currentLevel, Point.of(cell.x, cell.y), b -> b);
			int innerNeighbors = (int) checkInInner.stream().filter(c -> inner[c.y][c.x]).count();

			boolean nextState = nextState(currentLevel[cell.y][cell.x], innerNeighbors + outerNeighbors);

			nextLevel[cell.y][cell.x] = nextState;
		}

		// consider all cells that have been missed (apart from center cell)
		for (Point cell : otherCells) {
			int neighbors = ArrayUtils.countNeighbors(currentLevel, Point.of(cell.x, cell.y), b -> b);
			boolean nextState = nextState(currentLevel[cell.y][cell.x], neighbors);
			nextLevel[cell.y][cell.x] = nextState;
		}

	}

	private static Set<Point> getOtherCells(Set<Point> outerEdges, Set<Point> innerEdges) {
		Set<Point> cells = new HashSet<>();

		int center = (SIZE - 1) / 2;

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {

				if (y == center && x == center) {
					continue;
				}

				Point cell = Point.of(x, y);
				if (!outerEdges.contains(cell) && !innerEdges.contains(cell)) {
					cells.add(cell);
				}
			}
		}

		return cells;
	}

	private static Set<Point> getCellsToCheckInInner(Point cell) {
		Set<Point> cells = new HashSet<>();

		if (OUTER_TOP.equals(cell)) {
			for (int x = 0; x < SIZE; x++) {
				cells.add(Point.of(x, 0));
			}
		}

		if (OUTER_BOTTOM.equals(cell)) {
			for (int x = 0; x < SIZE; x++) {
				cells.add(Point.of(x, SIZE - 1));
			}
		}

		if (OUTER_LEFT.equals(cell)) {
			for (int y = 0; y < SIZE; y++) {
				cells.add(Point.of(0, y));
			}
		}

		if (OUTER_RIGHT.equals(cell)) {
			for (int y = 0; y < SIZE; y++) {
				cells.add(Point.of(SIZE - 1, y));
			}
		}

		return cells;
	}

	private static Set<Point> getCellsToCheckInOuter(Point cell) {
		Set<Point> cells = new HashSet<>();

		if (cell.y == 0) {
			cells.add(OUTER_TOP);
		}
		if (cell.y == SIZE - 1) {
			cells.add(OUTER_BOTTOM);
		}
		if (cell.x == 0) {
			cells.add(OUTER_LEFT);
		}
		if (cell.x == SIZE - 1) {
			cells.add(OUTER_RIGHT);
		}

		return cells;
	}

	private static boolean[][] addIfNotExists(Map<Integer, boolean[][]> levels, int i) {
		if (!levels.containsKey(i)) {
			levels.put(i, GridUtils.createGrid(SIZE, SIZE, false));
		}

		return levels.get(i);
	}

	private static Set<Point> getOuterCells() {
		Set<Point> edgeCells = new HashSet<>();

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				if (y == 0 || x == 0 || y == SIZE - 1 || x == SIZE - 1) {
					edgeCells.add(Point.of(x, y));
				}
			}
		}

		return edgeCells;
	}

	private static Set<Point> getInnerCells() {
		Set<Point> edgeCells = new HashSet<>();

		int center = (SIZE - 1) / 2;

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {

				boolean top = x == center && y == center - 1;
				boolean left = x == center - 1 && y == center;
				boolean right = x == center + 1 && y == center;
				boolean bottom = x == center && y == center + 1;

				if (top || left || right || bottom) {
					edgeCells.add(Point.of(x, y));
				}
			}
		}

		return edgeCells;
	}

	private static void part1(boolean[][] grid) {

		Set<Integer> hashes = new HashSet<>();
		hashes.add(hash(grid));

		while (true) {

			grid = run(grid);

			int hash = hash(grid);

			if (hashes.contains(hash)) {
				BigInteger i = calculateBiodiversityRating(grid);
				System.out.println("Part 1: " + i);

				break;
			}

			hashes.add(hash);
		}

	}

	private static BigInteger calculateBiodiversityRating(boolean[][] grid) {
		BigInteger rating = BigInteger.ZERO;

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {

				if (grid[y][x]) {
					int power = y * SIZE + x;

					rating = rating.add(BigInteger.valueOf(2).pow(power));

				}
			}
		}

		return rating;
	}

	private static int hash(boolean[][] grid) {
		boolean[] arr = new boolean[SIZE * SIZE];
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				arr[y * SIZE + x] = grid[y][x];
			}
		}

		return Arrays.hashCode(arr);
	}

	private static boolean[][] run(boolean[][] grid) {

		boolean[][] next = GridUtils.clone(grid);

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				next[y][x] = calc(grid, x, y);
			}
		}

		return next;
	}

	private static boolean calc(boolean[][] grid, int x, int y) {
		int livingNeighbors = ArrayUtils.countNeighbors(grid, Point.of(x, y), b -> b);
		return nextState(grid[y][x], livingNeighbors);
	}

	private static boolean nextState(boolean currentValue, int livingNeighbors) {
		if (currentValue) {
			// living bug
			return livingNeighbors == 1;
		} else {
			// empty space
			return livingNeighbors == 1 || livingNeighbors == 2;
		}
	}

}
