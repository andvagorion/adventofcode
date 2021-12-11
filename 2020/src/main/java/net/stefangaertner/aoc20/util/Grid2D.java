package net.stefangaertner.aoc20.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class Grid2D<T> {

	private Class<T> clazz;
	private int width;
	private int height;

	private T[][] grid;

	public Grid2D(Class<T> clazz, int width, int height) {
		this.clazz = clazz;
		this.width = width;
		this.height = height;

		this.grid = (T[][]) Array.newInstance(clazz, width, height);
	}

	public Grid2D<T> set(int x, int y, T val) {
		this.grid[x][y] = val;
		return this;
	}

	public T get(int x, int y) {
		return this.grid[x][y];
	}

	public T[] row(int num) {
		T[] row = (T[]) Array.newInstance(this.clazz, this.width);
		for (int x = 0; x < this.width; x++) {
			row[x] = this.grid[x][num];
		}
		return row;
	}

	public T[] col(int num) {
		T[] col = (T[]) Array.newInstance(this.clazz, this.height);
		for (int y = 0; y < this.height; y++) {
			col[y] = this.grid[num][y];
		}
		return col;
	}

	public int height() {
		return this.height;
	}

	public int width() {
		return this.width;
	}

	public T[] firstCol() {
		return this.col(0);
	}

	public T[] lastCol() {
		return this.col(this.width - 1);
	}

	public T[] firstRow() {
		return this.row(0);
	}

	public T[] lastRow() {
		return this.row(this.height - 1);
	}

	public void print(Function<T, String> fn) {
		Arrays.stream(this.grid).map(row -> Arrays.stream(row).map(fn).collect(Collectors.joining()))
				.forEach(System.out::println);
	}

}
