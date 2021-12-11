package net.stefangaertner.aoc18.pojo;

import java.util.Stack;

import net.stefangaertner.aoc18.util.Vector2D;

public class Grid {

	Stack<Vector2D> positions = new Stack<>();

	private Vector2D offset = new Vector2D(0, 0);

	public char[][] grid;
	
	private final int size = 1000;
	
	public Grid() {
		grid = createGrid(size, size);
		grid[size / 2][size / 2] = 'X';
		positions.push(new Vector2D(size / 2, size / 2));
	}

	public void move(Direction dir) {
		
		Vector2D refPos = positions.peek();

//		System.out.println("currently at " + (refPos.x + offset.x) + ", " + (refPos.y + offset.y) + " in " + grid.length
//				+ ", " + grid[0].length + " moving " + dir);

		if (dir.equals(Direction.UP) && refPos.y <= 5) {
			char[][] temp = createGrid(grid.length + size, grid[0].length);
			grid = copy(temp, size, 0);
			offset.y += size;
		} else if (dir.equals(Direction.DOWN) && (refPos.y + offset.y) >= grid.length - 5) {
			char[][] temp = createGrid(grid.length + size, grid[0].length);
			grid = copy(temp, 0, 0);
		} else if (dir.equals(Direction.LEFT) && refPos.x <= 5) {
			char[][] temp = createGrid(grid.length, grid[0].length + size);
			grid = copy(temp, 0, size);
			offset.x += size;
		} else if (dir.equals(Direction.RIGHT) && (refPos.x + offset.x) >= grid[0].length - 5) {
			char[][] temp = createGrid(grid.length, grid[0].length + size);
			grid = copy(temp, 0, 0);
		}
		
		if (dir.equals(Direction.UP)) {
			_move(refPos, new Vector2D(0, -1));
		} else if (dir.equals(Direction.DOWN)) {
			_move(refPos, new Vector2D(0, 1));
		} else if (dir.equals(Direction.LEFT)) {
			_move(refPos, new Vector2D(-1, 0));
		} else if (dir.equals(Direction.RIGHT)) {
			_move(refPos, new Vector2D(1, 0));
		}

	}

	private void _move(Vector2D refPos, Vector2D dir) {
		refPos.y += dir.y;
		refPos.x += dir.x;
		char door = dir.x != 0 ? '|' : '-';
		grid[refPos.y + offset.y][refPos.x + offset.x] = door;
		refPos.y += dir.y;
		refPos.x += dir.x;
		grid[refPos.y + offset.y][refPos.x + offset.x] = '.';
	}

	private char[][] copy(char[][] temp, int offsetY, int offsetX) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				temp[y + offsetY][x + offsetX] = grid[y][x];
			}
		}
		return temp;
	}

	private char[][] createGrid(int h, int w) {
		char[][] out = new char[h][];
		for (int i = 0; i < out.length; i++) {
			out[i] = new char[w];
		}
		return out;
	}

	public void push() {
		Vector2D current = this.positions.peek();
		this.positions.push(new Vector2D(current.x, current.y));
	}

	public void pop() {
		this.positions.pop();
	}

}
