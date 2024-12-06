package net.stefangaertner.aoc18.pojo;

public class Tile {
	
	public int x = -1;
	public int y = -1;
	
	public TileType type;
	public Unit unit;
	
	public enum TileType {
		WALL, FLOOR;
	}
	
	public Tile(int x, int y, TileType type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	@Override
	public String toString() {
		if (unit != null) {
			return unit.toString();
		}
		
		if (this.type.equals(TileType.WALL)) return "#";
		if (this.type.equals(TileType.FLOOR)) return ".";
		
		return null;
	}

}
