package net.stefangaertner.aoc18.pojo;

import net.stefangaertner.aoc18.util.Vector2D;

public class Cave {

	public int depth;
	public Vector2D target;

	public int[][] geological;
	public int[][] erosion;
	public int[][] grid;

	public Cave(int depth, Vector2D target) {
		this.depth = depth;
		this.target = target;

		this.initializeGrid();
	}

	private void initializeGrid() {
		geological = new int[target.y + 1][];
		erosion = new int[target.y + 1][];
		grid = new int[target.y + 1][];

		for (int y = 0; y <= target.y; y++) {
			geological[y] = new int[target.x + 1];
			erosion[y] = new int[target.x + 1];
			grid[y] = new int[target.x + 1];

			for (int x = 0; x <= target.x; x++) {
				int geoIndex = getGeologicalIndex(x, y);
				geological[y][x] = geoIndex;

				int erosionIdx = getErosion(geoIndex);
				erosion[y][x] = erosionIdx;

				grid[y][x] = erosionIdx % 3;
			}
		}
	}

	private int getErosion(int geoIndex) {
		int ero = geoIndex + depth;
		ero %= 20183;
		return ero;
	}

	private int getGeologicalIndex(int x, int y) {
		if (x == 0 && y == 0) {
			return 0;
		} else if (x == target.x && y == target.y) {
			return 0;
		} else if (y == 0) {
			return x * 16807;
		} else if (x == 0) {
			return y * 48271;
		} else {
			return erosion[y][x - 1] * erosion[y - 1][x];
		}
	}

	public void print() {
		for (int y = 0; y < grid.length; y++) {
			int[] row = grid[y];
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < row.length; x++) {
				if (x == 0 && y == 0) {
					sb.append('M');
				} else if (x == target.x && y == target.y) {
					sb.append('T');
				} else if (row[x] == 0) {
					sb.append('.');
				} else if (row[x] == 1) {
					sb.append('=');
				} else if (row[x] == 2) {
					sb.append('|');
				}
			}
			System.out.println(sb.toString());
		}
	}

	public int getTotalRisk() {
		int sum = 0;
		for (int y = 0; y < grid.length; y++) {
			int[] row = grid[y];
			for (int x = 0; x < row.length; x++) {
				sum += grid[y][x];
			}
		}
		return sum;
	}
}
