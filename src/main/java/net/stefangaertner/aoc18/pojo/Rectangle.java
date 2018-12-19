package net.stefangaertner.aoc18.pojo;

public class Rectangle {
	public int num;
	public int x;
	public int y;
	public int w;
	public int h;

	public Rectangle() {
	}

	public Rectangle(int num, int x, int y, int w, int h) {
		this.num = num;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public boolean intersects(Rectangle other) {
		if (this.x > other.x + other.w || this.x + this.w < other.x || this.y > other.y + other.h
				|| this.y + this.h < other.y) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Rectangle [num=" + num + ", x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + "]";
	}

}
