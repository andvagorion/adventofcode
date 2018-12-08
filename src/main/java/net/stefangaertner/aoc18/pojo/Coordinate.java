package net.stefangaertner.aoc18.pojo;

public class Coordinate {
	
	public int id;
	public int x;
	public int y;
	
	public Coordinate(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Coordinate [id=" + id + ", x=" + x + ", y=" + y + "]";
	}

}
