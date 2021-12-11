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

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.ArrayUtils;
import net.stefangaertner.aoc19.util.GridUtils;
import net.stefangaertner.aoc19.util.Point2D;

public class Day24 {

	private static final int SIZE = 5;

	private static final int CENTER = (SIZE - 1) / 2;
	private static final Point2D OUTER_TOP = Point2D.of(CENTER, CENTER - 1);
	private static final Point2D OUTER_LEFT = Point2D.of(CENTER - 1, CENTER);
	private static final Point2D OUTER_RIGHT = Point2D.of(CENTER + 1, CENTER);
	private static final Point2D OUTER_BOTTOM = Point2D.of(CENTER, CENTER + 1);

	public static void main(String[] strings) throws IOException {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		List<String> lines = Advent.read("aoc19/024-data");
		boolean[][] grid = GridUtils.toGrid(lines, c -> c == '#');

		Set<Integer> hashes = new HashSet<>();
		hashes.add(hash(grid));

		while (true) {

			grid = run(grid);

			int hash = hash(grid);

			if (hashes.contains(hash)) {
				BigInteger i = calculateBiodiversityRating(grid);

				return i.longValue();
			}

			hashes.add(hash);
		}

	}

	static long part2() {
		List<String> lines = Advent.read("aoc19/024-data");
		boolean[][] grid = GridUtils.toGrid(lines, c -> c == '#');

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

		// for (Map.Entry<Integer, boolean[][]> e : levels.entrySet()) {
		// System.out.println("Level " + e.getKey());
		// StringUtils.print2Darray(e.getValue());
		// System.out.println(" ");
		// }

		return getSum(levels.values());
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
		Set<Point2D> outerEdges = getOuterCells();

		// get inner edge cells
		Set<Point2D> innerEdges = getInnerCells();

		// get all other cells
		Set<Point2D> otherCells = getOtherCells(outerEdges, innerEdges);

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
			Set<Point2D> outerEdges, Set<Point2D> innerEdges, Set<Point2D> otherCells) {

		boolean[][] currentLevel = levels.get(i);
		boolean[][] nextLevel = ret.get(i);

		boolean[][] outer = levels.get(i - 1);
		boolean[][] inner = levels.get(i + 1);

		// consider only cells on edges, compare with outer
		for (Point2D cell : outerEdges) {

			Set<Point2D> checkInUpper = getCellsToCheckInOuter(cell);

			int innerNeighbors = ArrayUtils.countNeighbors(currentLevel, Point2D.of(cell.x, cell.y), b -> b);
			int outerNeighbors = (int) checkInUpper.stream().filter(c -> outer[c.y][c.x]).count();

			boolean nextState = nextState(currentLevel[cell.y][cell.x], innerNeighbors + outerNeighbors);

			nextLevel[cell.y][cell.x] = nextState;
		}

		// consider only cells on inner edge of outer grid, compare with inner
		for (Point2D cell : innerEdges) {

			Set<Point2D> checkInInner = getCellsToCheckInInner(cell);

			int outerNeighbors = ArrayUtils.countNeighbors(currentLevel, Point2D.of(cell.x, cell.y), b -> b);
			int innerNeighbors = (int) checkInInner.stream().filter(c -> inner[c.y][c.x]).count();

			boolean nextState = nextState(currentLevel[cell.y][cell.x], innerNeighbors + outerNeighbors);

			nextLevel[cell.y][cell.x] = nextState;
		}

		// consider all cells that have been missed (apart from center cell)
		for (Point2D cell : otherCells) {
			int neighbors = ArrayUtils.countNeighbors(currentLevel, Point2D.of(cell.x, cell.y), b -> b);
			boolean nextState = nextState(currentLevel[cell.y][cell.x], neighbors);
			nextLevel[cell.y][cell.x] = nextState;
		}

	}

	private static Set<Point2D> getOtherCells(Set<Point2D> outerEdges, Set<Point2D> innerEdges) {
		Set<Point2D> cells = new HashSet<>();

		int center = (SIZE - 1) / 2;

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {

				if (y == center && x == center) {
					continue;
				}

				Point2D cell = Point2D.of(x, y);
				if (!outerEdges.contains(cell) && !innerEdges.contains(cell)) {
					cells.add(cell);
				}
			}
		}

		return cells;
	}

	private static Set<Point2D> getCellsToCheckInInner(Point2D cell) {
		Set<Point2D> cells = new HashSet<>();

		if (OUTER_TOP.equals(cell)) {
			for (int x = 0; x < SIZE; x++) {
				cells.add(Point2D.of(x, 0));
			}
		}

		if (OUTER_BOTTOM.equals(cell)) {
			for (int x = 0; x < SIZE; x++) {
				cells.add(Point2D.of(x, SIZE - 1));
			}
		}

		if (OUTER_LEFT.equals(cell)) {
			for (int y = 0; y < SIZE; y++) {
				cells.add(Point2D.of(0, y));
			}
		}

		if (OUTER_RIGHT.equals(cell)) {
			for (int y = 0; y < SIZE; y++) {
				cells.add(Point2D.of(SIZE - 1, y));
			}
		}

		return cells;
	}

	private static Set<Point2D> getCellsToCheckInOuter(Point2D cell) {
		Set<Point2D> cells = new HashSet<>();

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

	private static Set<Point2D> getOuterCells() {
		Set<Point2D> edgeCells = new HashSet<>();

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				if (y == 0 || x == 0 || y == SIZE - 1 || x == SIZE - 1) {
					edgeCells.add(Point2D.of(x, y));
				}
			}
		}

		return edgeCells;
	}

	private static Set<Point2D> getInnerCells() {
		Set<Point2D> edgeCells = new HashSet<>();

		int center = (SIZE - 1) / 2;

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {

				boolean top = x == center && y == center - 1;
				boolean left = x == center - 1 && y == center;
				boolean right = x == center + 1 && y == center;
				boolean bottom = x == center && y == center + 1;

				if (top || left || right || bottom) {
					edgeCells.add(Point2D.of(x, y));
				}
			}
		}

		return edgeCells;
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
		int livingNeighbors = ArrayUtils.countNeighbors(grid, Point2D.of(x, y), b -> b);
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
