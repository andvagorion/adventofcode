package net.stefangaertner.test;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import net.stefangaertner.util.Grid2D;

public class Grid2DTest {

	@Test
	public void create() {
		Grid2D<Integer> grid = new Grid2D<>(Integer.class, 2, 8);
		Assert.assertEquals(2, grid.width());
		Assert.assertEquals(8, grid.height());
	}

	@Test
	public void setGetValue() {
		Grid2D<Integer> grid = new Grid2D<>(Integer.class, 2, 8);
		Integer val = -5;

		grid.set(1, 3, val);
		Assert.assertEquals(val, grid.get(1, 3));
	}

	@Test
	public void firstCol() {
		final int width = 10;
		final int height = 7;

		Grid2D<Integer> grid = this.createIndexedArray(width, height);

		int[] expected = IntStream.range(0, height).map(i -> i * width).toArray();
		int[] actual = Arrays.stream(grid.firstCol()).mapToInt(Integer::intValue).toArray();

		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void secondCol() {
		final int width = 10;
		final int height = 7;

		Grid2D<Integer> grid = this.createIndexedArray(width, height);

		int[] expected = IntStream.range(0, height).map(i -> i * width + 1).toArray();
		int[] actual = Arrays.stream(grid.col(1)).mapToInt(Integer::intValue).toArray();

		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void lastRow() {
		final int width = 4;
		final int height = 7;

		Grid2D<Integer> grid = this.createIndexedArray(width, height);

		int[] expected = IntStream.range(0, width).map(i -> (height - 1) * width + i).toArray();
		int[] actual = Arrays.stream(grid.lastRow()).mapToInt(Integer::intValue).toArray();

		Assert.assertArrayEquals(expected, actual);
	}

	private Grid2D<Integer> createIndexedArray(int width, int height) {
		Grid2D<Integer> grid = new Grid2D<>(Integer.class, width, height);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				grid.set(x, y, y * width + x);
			}
		}

		return grid;
	}

}
