package net.stefangaertner.aoc18;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import net.stefangaertner.aoc18.pojo.Direction;
import net.stefangaertner.aoc18.pojo.Grid;
import net.stefangaertner.aoc18.pojo.Node;
import net.stefangaertner.aoc18.util.Advent;
import net.stefangaertner.aoc18.util.StringUtils;
import net.stefangaertner.aoc18.util.Vector2D;

public class Day20 {

	public static void main(String[] args) {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	private static final boolean DEBUG = false;
	private static long part1 = -1;
	private static long part2 = -1;

	static long part1() {
		String line = Advent.readLine("aoc18/020-data");

		Grid gridObj = new Grid();
		char[] input = line.toCharArray();
		int idx = 0;

		char[][] grid = parse(gridObj, input, idx);

		walk(grid);

		return part1;
	}

	static long part2() {
		return part2;
	}

	private static void walk(char[][] grid) {
		List<Node<Vector2D>> nodes = new ArrayList<>();

		Vector2D start = findStart(grid);
		boolean[][] visited = initializeVisited(grid, start);

		Node<Vector2D> root = new Node<>(null, start);

		// use flood fill / BFS
		Deque<Node<Vector2D>> q = new ArrayDeque<>();
		q.addLast(root);

		while (!q.isEmpty()) {

			Node<Vector2D> node = q.removeFirst();

			nodes.add(node);

			// moving up
			if (node.data.y - 1 >= 0 && visited[node.data.y - 1][node.data.x] == false) {
				Vector2D pos = new Vector2D(node.data.x, node.data.y - 1);
				Node<Vector2D> child = new Node<>(node, pos);
				node.children.add(child);
				q.addLast(child);
				visited[child.data.y][child.data.x] = true;
			}

			// moving left
			if (node.data.x - 1 >= 0 && visited[node.data.y][node.data.x - 1] == false) {
				Vector2D pos = new Vector2D(node.data.x - 1, node.data.y);
				Node<Vector2D> child = new Node<>(node, pos);
				node.children.add(child);
				q.addLast(child);
				visited[child.data.y][child.data.x] = true;
			}

			// moving right
			if (node.data.x + 1 < grid[0].length && visited[node.data.y][node.data.x + 1] == false) {
				Vector2D pos = new Vector2D(node.data.x + 1, node.data.y);
				Node<Vector2D> child = new Node<>(node, pos);
				node.children.add(child);
				q.addLast(child);
				visited[child.data.y][child.data.x] = true;
			}

			// moving down
			if (node.data.y + 1 < grid.length && visited[node.data.y + 1][node.data.x] == false) {
				Vector2D pos = new Vector2D(node.data.x, node.data.y + 1);
				Node<Vector2D> child = new Node<>(node, pos);
				node.children.add(child);
				q.addLast(child);
				visited[child.data.y][child.data.x] = true;
			}
		}

		int max = 0;
		int sum = 0;

		if (!nodes.isEmpty()) {
			for (Node<Vector2D> node : nodes) {
				int steps = node.depth() - 1;
				if (steps > max) {
					max = steps;
				}
				if (steps % 2 == 0 && steps >= 2000) {
					sum++;
				}
			}
		}

		if (DEBUG)
			printSteps(grid, nodes);

		part1 = max / 2;
		part2 = sum;
	}

	private static char[][] parse(Grid grid, char[] input, int idx) {

		try {

			while (idx < input.length) {
				// System.out.println("index is " + idx + ", grid size " + grid.grid[0].length +
				// ", " + grid.grid.length);

				char c = input[idx];
				if (c == '(') {
					grid.push();
				} else if (c == ')') {
					grid.pop();
				} else if (c == '|') {
					grid.pop();
					grid.push();
				} else if (c == 'N') {
					grid.move(Direction.UP);
				} else if (c == 'S') {
					grid.move(Direction.DOWN);
				} else if (c == 'W') {
					grid.move(Direction.LEFT);
				} else if (c == 'E') {
					grid.move(Direction.RIGHT);
				}

				idx++;
			}

			char[][] clean = getCleanGrid(grid);

			return clean;

		} catch (Exception e) {
			System.out.println(e.getClass()
					.getName());
			System.out.println(input[idx]);
		}

		return null;
	}

	private static char[][] getCleanGrid(Grid grid) {
		char[][] g = grid.grid;
		int minX = g[0].length;
		int minY = g.length;
		int maxX = 0;
		int maxY = 0;
		for (int y = 0; y < g.length; y++) {
			for (int x = 0; x < g[0].length; x++) {
				if (g[y][x] > 1) {
					if (y > maxY) {
						maxY = y + 1;
					}
					if (x > maxX) {
						maxX = x + 1;
					}
					if (y < minY) {
						minY = y - 1;
					}
					if (x < minX) {
						minX = x - 1;
					}
				}
				if (g[y][x] < 1) {
					g[y][x] = '#';
				}
			}
		}

		char[][] ret = new char[maxY - minY + 1][];
		for (int y = minY; y <= maxY; y++) {
			ret[y - minY] = new char[maxX - minX + 1];
			for (int x = minX; x <= maxX; x++) {
				ret[y - minY][x - minX] = g[y][x];
			}
		}

		return ret;
	}

	private static void printSteps(char[][] grid, List<Node<Vector2D>> nodes) {
		int max = 0;

		String[][] stepGrid = new String[grid.length][];
		for (int y = 0; y < grid.length; y++) {
			char[] row = grid[y];
			stepGrid[y] = new String[row.length];
			for (int x = 0; x < grid.length; x++) {
				char c = row[x];
				if (c != '.') {
					stepGrid[y][x] = String.valueOf(c);
				} else {
					final int x1 = x;
					final int y1 = y;
					Node<Vector2D> node = nodes.stream()
							.filter(n -> n.data.x == x1 && n.data.y == y1)
							.findFirst()
							.get();
					int depth = node.depth() - 1;
					String xx = (depth < 10 ? " " : "") + String.valueOf(depth);
					stepGrid[y][x] = xx;
					if (depth > max) {
						max = depth;
					}
				}
			}
		}

		max = String.valueOf(max)
				.length();

		for (int y = 0; y < stepGrid.length; y++) {
			for (int x = 0; x < stepGrid.length; x++) {
				String s = stepGrid[y][x];
				if (s.length() == 1) {
					stepGrid[y][x] = StringUtils.fillWith(s, max, s.charAt(0));
				}
			}
		}

		StringUtils.print2Darray(stepGrid);

	}

	private static boolean[][] initializeVisited(char[][] grid, Vector2D start) {
		boolean[][] visited = new boolean[grid.length][grid[0].length];

		// mark all walls as visited
		for (int y = 0; y < grid.length; y++) {

			char[] row = grid[y];

			for (int x = 0; x < row.length; x++) {

				char c = row[x];

				if (c == '#') {
					visited[y][x] = true;
				} else {
					visited[y][x] = false;
				}
			}
		}

		return visited;
	}

	private static Vector2D findStart(char[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			char[] row = grid[y];
			for (int x = 0; x < row.length; x++) {
				if (row[x] == 'X') {
					return new Vector2D(x, y);
				}
			}
		}
		return null;
	}

}
