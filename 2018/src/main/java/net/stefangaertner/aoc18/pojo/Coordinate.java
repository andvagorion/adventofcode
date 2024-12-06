package net.stefangaertner.aoc18.pojo;

public class Coordinate {
	
	public int id;
	public int x;
	public int y;
	
	public int dx;
	public int dy;
	
	public Coordinate(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public void setVelocity(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void move() {
		this.x += this.dx;
		this.y += this.dy;
	}

	@Override
	public String toString() {
		return "Coordinate [id=" + id + ", x=" + x + ", y=" + y + "]";
	}

}
