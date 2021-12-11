package net.stefangaertner.aoc19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.GridUtils;
import net.stefangaertner.aoc19.util.Point2D;
import net.stefangaertner.aoc19.util.graph.Node;
import net.stefangaertner.aoc19.util.graph.WeightedGraph;

public class Day18 {

	private static final Predicate<Character> isWall = c -> c == '#';
	private static final Predicate<Character> isEmpty = c -> c == '.';
	private static final Predicate<Character> isItem = isWall.negate().and(isEmpty.negate());

	private static final Predicate<Integer> isKey = c -> c >= 'a' && c <= 'z';
	private static final Predicate<Integer> isDoor = c -> c >= 'A' && c <= 'Z';

	public static void main(String[] strings) throws IOException {

		List<String> lines = Advent.read("aoc19/018-data");

		char[][] grid = GridUtils.toGrid(lines);

		part1(grid);

	}

	private static void part1(char[][] grid) {
		List<List<Point2D>> solutions = new ArrayList<>();
		solve(grid, new ArrayList<>(), solutions);

		List<Point2D> path = null;
		int min = Integer.MAX_VALUE;

		for (List<Point2D> solution : solutions) {

			int sum = 0;

			for (Point2D p : solution) {
				sum += p.y;
			}

			if (sum < min) {
				min = sum;
				path = solution;
			}
		}

		String pathStr = path.stream().map(p -> ((char) p.x) + " (" + p.y + ")").collect(Collectors.joining(" => "));
		System.out.println(pathStr);
		System.out.println(min);
	}

	private static WeightedGraph calculateGraph(char[][] grid) {

		WeightedGraph graph = new WeightedGraph();

		// scan whole grid and create nodes
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				char c = grid[y][x];
				if (isItem.test(c)) {
					graph.addNode(new Node((int) c, String.valueOf(c)));
				}
			}
		}

		for (Node node : graph.getNodes()) {
			char c = (char) node.getId();
			Point2D pos = GridUtils.find(grid, c);

			// flood fill, find all items and doors accessible from current
			// position
			Map<Point2D, Integer> reachable = GridUtils.findReachable(grid, pos, isEmpty, isWall, isItem);

			for (Map.Entry<Point2D, Integer> tile : reachable.entrySet()) {
				Point2D pair = tile.getKey();
				int dist = tile.getValue();

				char c1 = grid[pair.y][pair.x];

				graph.addEdge(graph.getNode(c), graph.getNode(c1), dist);
			}
		}

		// debugPrint(graph);

		return graph;
	}

	private static long minimum = Integer.MAX_VALUE;

	private static void solve(char[][] grid, List<Point2D> visitedBefore, List<List<Point2D>> solutions) {

		WeightedGraph graph = calculateGraph(grid);

		// check for being done
		if (graph.getNodes().size() == 1) {
			solutions.add(visitedBefore);

			long sum = visitedBefore.stream().mapToLong(p -> (long) p.y).sum();
			if (sum < minimum) {
				String pathStr = visitedBefore.stream().map(p -> ((char) p.x) + " (" + p.y + ")")
						.collect(Collectors.joining(" => "));
				System.out.println(pathStr);
				System.out.println(minimum);
				minimum = sum;
			}

			return;
		}

		Map<Integer, Integer> edges = graph.getEdges('@');

		for (Integer otherId : edges.keySet()) {

			List<Point2D> visited = new ArrayList<>(visitedBefore);

			int dist = edges.get(otherId);
			char c1 = (char) otherId.intValue();

			if (isKey.test(otherId) || (isDoor.test(otherId) && keyCollected(visited, otherId))) {

				visited.add(Point2D.of(otherId, dist));
				char[][] newGrid = updateGrid(grid, c1);

				solve(newGrid, visited, solutions);

			}

		}

	}

	private static boolean keyCollected(List<Point2D> visited, Integer otherId) {
		int key = String.valueOf((char) otherId.intValue()).toLowerCase().charAt(0);
		return visited.stream().map(p -> p.x).anyMatch(i -> i.intValue() == key);
	}

	private static char[][] updateGrid(char[][] grid, char c) {
		char[][] newGrid = GridUtils.clone(grid);

		GridUtils.change(newGrid, '@', '.');
		GridUtils.change(newGrid, c, '@');

		return newGrid;
	}

}
