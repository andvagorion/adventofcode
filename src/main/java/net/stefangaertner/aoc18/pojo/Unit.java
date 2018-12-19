package net.stefangaertner.aoc18.pojo;

public class Unit {
	
	public int id;
	
	public Tile tile;
	public UnitType type;
	public Unit unit;
	
	public int hp = 200;
	public int power = 3;

	public Unit(Tile tile, UnitType type) {
		this.tile = tile;
		this.type = type;
		
		this.id = nextId();
	}

	public enum UnitType {
		ELF, GOBLIN;
	}

	@Override
	public String toString() {
		if (type.equals(UnitType.ELF)) return "E";
		else return "G";
	}
	
	private static int MAXID = 0;
	
	private static int nextId() {
		MAXID++;
		return MAXID;
	}
}
