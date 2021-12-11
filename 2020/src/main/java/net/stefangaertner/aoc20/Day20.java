package net.stefangaertner.aoc20;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.stefangaertner.aoc20.util.AOC;
import net.stefangaertner.aoc20.util.Grid2D;
import net.stefangaertner.aoc20.util.RegexUtil;
import net.stefangaertner.aoc20.util.StringUtils;

public class Day20 {

	public static void main(String[] args) {
		AOC.print(1, part1());
	}

	static long part1() {
		List<List<String>> images = AOC.readGroups("aoc20/020");
		Map<Long, Grid2D<Boolean>> grids = images.stream().collect(Collectors.toMap(parseId, parseGrid));

		Set<Long> edges = findEdges(grids);

		return edges.stream().reduce(1L, (a, b) -> a * b);
	}

	private static Set<Long> findEdges(Map<Long, Grid2D<Boolean>> grids) {
		Map<Long, List<String>> edgeValues = grids.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, bordersToString));

		Set<Long> ids = edgeValues.entrySet().stream().filter(e -> {
			Long id = e.getKey();
			List<String> edges = e.getValue();
			List<List<String>> otherGrids = edgeValues.entrySet().stream().filter(other -> !other.getKey().equals(id))
					.map(Map.Entry::getValue).collect(Collectors.toList());

			// edge should have exactly 2 matches with others
			return edges.stream().filter(edge -> {
				return otherGrids.stream().anyMatch(other -> {
					return other.stream().anyMatch(str -> {
						return str.equals(edge) || str.equals(StringUtils.reverse(edge));
					});
				});
			}).count() == 2;

		}).map(e -> e.getKey()).collect(Collectors.toSet());

		return ids;
	}

	private static final Function<Map.Entry<Long, Grid2D<Boolean>>, List<String>> bordersToString = e -> {
		Grid2D<Boolean> grid = e.getValue();
		List<Boolean[]> edges = Arrays.asList(grid.firstRow(), grid.lastRow(), grid.firstCol(), grid.lastCol());
		return edges.stream().map(arr -> {
			return Arrays.stream(arr).map(b -> b ? "1" : "0").collect(Collectors.joining());
		}).collect(Collectors.toList());
	};

	private static final Function<List<String>, Long> parseId = lines -> {
		return Long.parseLong(RegexUtil.firstOccurence(lines.get(0), "(\\d+)"));
	};

	private static final Function<List<String>, Grid2D<Boolean>> parseGrid = lines -> {
		final int width = lines.get(1).length();
		final int height = lines.size() - 1;

		Grid2D<Boolean> grid = new Grid2D<>(Boolean.class, width, height);

		for (int y = 1; y < lines.size(); y++) {
			String line = lines.get(y);
			for (int x = 0; x < line.length(); x++) {
				grid.set(x, y - 1, line.charAt(x) == '#');
			}
		}

		return grid;
	};

}
