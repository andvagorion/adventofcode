package net.stefangaertner.aoc18.pojo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.stefangaertner.aoc18.pojo.Tile.TileType;
import net.stefangaertner.aoc18.pojo.Unit.UnitType;
import net.stefangaertner.aoc18.util.StringUtils;

public class Battlefield {

	public static boolean debug = false;

	public int rounds = 0;

	public Tile[][] grid;

	public void simulateRound() {
		this.rounds++;

		// units in reading order
		List<Unit> units = this.findUnits();

		for (Unit unit : units) {
			this.move(unit);
			this.attack(unit);
		}

		if (debug) {
			System.out.println();
			this.printCave();
		}
	}

	private List<Unit> findUnits() {

		List<Unit> units = new ArrayList<>();

		for (int y = 0; y < this.grid.length; y++) {
			for (int x = 0; x < this.grid.length; x++) {
				Unit unit = this.grid[y][x].unit;
				if (unit != null) {
					units.add(unit);
				}
			}
		}

		return units;
	}

	private void move(Unit unit) {

		List<Unit> units = this.findUnits();

		// check if next to another unit
		for (Unit other : units) {
			if (other.equals(unit)) {
				continue;
			}

			// unit is adjacing to enemy
			if (nextToEnemy(unit, other)) {
				return;
			}
		}

		// find path to closest spot
		// next to enemy unit and move
		this.move(unit, units);
	}

	private void move(Unit unit, List<Unit> units) {

		List<Node<Tile>> nodes = new ArrayList<>();

		boolean[][] visited = initializeVisited(this.grid, unit);

		// tree with root node
		Node<Tile> root = new Node<>(null, unit.tile);

		// use flood fill / BFS
		Deque<Node<Tile>> q = new ArrayDeque<>();
		q.addLast(root);

		while (!q.isEmpty()) {

			Node<Tile> node = q.removeFirst();

			// next to an enemy
			if (this.nextToEnemy(node.data, unit)) {
				nodes.add(node);
			}

			// moving up
			if (node.data.y - 1 >= 0 && visited[node.data.y - 1][node.data.x] == false) {
				Tile tile = this.grid[node.data.y - 1][node.data.x];
				Node<Tile> child = new Node<>(node, tile);
				node.children.add(child);
				q.addLast(child);
				visited[child.data.y][child.data.x] = true;
			}

			// moving left
			if (node.data.x - 1 >= 0 && visited[node.data.y][node.data.x - 1] == false) {
				Tile tile = this.grid[node.data.y][node.data.x - 1];
				Node<Tile> child = new Node<>(node, tile);
				node.children.add(child);
				q.addLast(child);
				visited[child.data.y][child.data.x] = true;
			}

			// moving right
			if (node.data.x + 1 < this.grid[0].length && visited[node.data.y][node.data.x + 1] == false) {
				Tile tile = this.grid[node.data.y][node.data.x + 1];
				Node<Tile> child = new Node<>(node, tile);
				node.children.add(child);
				q.addLast(child);
				visited[child.data.y][child.data.x] = true;
			}

			// moving down
			if (node.data.y + 1 < this.grid.length && visited[node.data.y + 1][node.data.x] == false) {
				Tile tile = this.grid[node.data.y + 1][node.data.x];
				Node<Tile> child = new Node<>(node, tile);
				node.children.add(child);
				q.addLast(child);
				visited[child.data.y][child.data.x] = true;
			}
		}

		if (!nodes.isEmpty()) {

			// find closest
			Node<Tile> closest = this.findClosest(nodes);

			// printTargets(nodes, closest);

			// trace back the steps
			Node<Tile> prev = null;
			while (closest.parent != null) {
				prev = closest;
				closest = closest.parent;
			}

			// move unit
			Tile previousTile = unit.tile;
			previousTile.unit = null;

			prev.data.unit = unit;
			unit.tile = prev.data;

		}

	}

	private Node<Tile> findClosest(List<Node<Tile>> nodes) {

		int min = Integer.MAX_VALUE;
		Node<Tile> closest = null;

		for (Node<Tile> node : nodes) {
			int distance = node.depth();
			if (distance < min) {

				min = distance;
				closest = node;

			} else if (distance == min) {
				// edge case: both are the same number of steps away
				// use reading order
				if (node.data.y * this.grid[0].length + node.data.x < closest.data.y * this.grid[0].length
						+ closest.data.x) {
					min = distance;
					closest = node;
				}
			}
		}

		return closest;
	}

	@SuppressWarnings("unused")
	private void printTargets(List<Node<Tile>> nodes, Node<Tile> chosen) {
		String[][] printGrid = new String[this.grid.length][];

		for (int y = 0; y < this.grid.length; y++) {
			printGrid[y] = new String[this.grid[y].length];
			for (int x = 0; x < this.grid[y].length; x++) {
				printGrid[y][x] = this.grid[y][x].toString();
			}
		}

		for (Node<Tile> node : nodes) {
			printGrid[node.data.y][node.data.x] = "?";
		}

		if (chosen != null) {
			printGrid[chosen.data.y][chosen.data.x] = "!";
		}

		StringUtils.print2Darray(printGrid);

	}

	private boolean nextToEnemy(Tile tile, Unit unit) {
		Unit top = this.grid[tile.y - 1][tile.x].unit;
		Unit left = this.grid[tile.y][tile.x - 1].unit;
		Unit right = this.grid[tile.y][tile.x + 1].unit;
		Unit bottom = this.grid[tile.y + 1][tile.x].unit;

		return top != null && !top.type.equals(unit.type) || left != null && !left.type.equals(unit.type)
				|| right != null && !right.type.equals(unit.type) || bottom != null && !bottom.type.equals(unit.type);
	}

	private static boolean[][] initializeVisited(Tile[][] grid, Unit unit) {
		boolean[][] visited = new boolean[grid.length][grid[0].length];

		// mark all walls as visited
		for (int y = 0; y < grid.length; y++) {

			Tile[] row = grid[y];

			for (int x = 0; x < row.length; x++) {

				Tile t = row[x];

				if (t.type.equals(TileType.WALL) || t.unit != null) {
					visited[y][x] = true;
				} else {
					visited[y][x] = false;
				}

				visited[unit.tile.y][unit.tile.x] = true;
			}
		}

		return visited;
	}

	private void attack(Unit unit) {

		if (unit.hp <= 0) {
			return;
		}

		Unit enemy = this.findTarget(unit);

		if (enemy != null) {

			if (debug) {
				System.out.print(
						unit.type + " #" + unit.id + " attacks " + enemy.type + " #" + enemy.id + "(" + enemy.hp);
			}

			// attack
			enemy.hp -= unit.power;

			if (debug) {
				System.out.println(" => " + enemy.hp + ")");
			}

			if (enemy.hp <= 0) {
				enemy.tile.unit = null;
			}

		} else {

			if (debug) {
				System.out.println(unit.type + " #" + unit.id + " does not attack");
			}

		}
	}

	private Unit findTarget(Unit unit) {

		List<Unit> inRange = new ArrayList<>();

		for (Unit other : this.findUnits()) {
			if (other.equals(unit)) {
				continue;
			}

			// unit is adjacing to enemy
			if (nextToEnemy(unit, other)) {
				inRange.add(other);
			}
		}

		if (inRange.isEmpty()) {
			return null;
		}

		Unit chosen = null;
		int minHP = Integer.MAX_VALUE;

		for (Unit u : inRange) {
			if (u.hp < minHP) {
				chosen = u;
				minHP = u.hp;
			}
		}

		return chosen;
	}

	private static boolean nextToEnemy(Unit u1, Unit u2) {
		return !u1.type.equals(u2.type) && dist(u1, u2) == 1;
	}

	private static int dist(Unit u1, Unit u2) {
		return Math.abs(u1.tile.x - u2.tile.x) + Math.abs(u1.tile.y - u2.tile.y);
	}

	@SuppressWarnings("unused")
	private static void printVisited(boolean[][] visited) {
		for (int y = 0; y < visited.length; y++) {
			for (int x = 0; x < visited[0].length; x++) {
				String s = "";
				for (boolean b : visited[x]) {
					s += b ? "X" : ".";
				}
				System.out.println(s);
			}
		}
	}

	@SuppressWarnings("unused")
	private static void printDistances(Tile start, boolean[][] visited, Map<Tile, Integer> dist) {
		int[][] printGrid = new int[visited.length][visited[0].length];

		for (int y = 0; y < printGrid.length; y++) {
			for (int x = 0; x < printGrid[0].length; x++) {
				printGrid[y][x] = -1;
			}
		}

		for (Entry<Tile, Integer> e : dist.entrySet()) {
			Tile t = e.getKey();
			printGrid[t.y][t.x] = e.getValue();
		}

		printGrid[start.y][start.x] = 0;

		for (int i = 0; i < printGrid.length; i++) {
			String s = "";
			for (int j = 0; j < printGrid[i].length; j++) {
				s += StringUtils.fill(String.valueOf(printGrid[i][j]), 3) + " ";
			}
			System.out.println(s);
		}
	}

	@SuppressWarnings("unused")
	private void printCave(Unit chosen, Tile marked, char c) {
		for (int y = 0; y < this.grid.length; y++) {
			Tile[] row = this.grid[y];
			String rowStr = "";
			for (int x = 0; x < row.length; x++) {
				if (y == marked.y && x == marked.x) {
					rowStr += c;
				} else if (y == chosen.tile.y && x == chosen.tile.x) {
					rowStr += chosen.toString().toLowerCase();
				} else {
					rowStr += row[x].toString();
				}
			}
			System.out.println(rowStr);
		}
	}

	public void printCave() {

		for (int y = 0; y < this.grid.length; y++) {
			Tile[] row = this.grid[y];
			String rowStr = "";
			for (int x = 0; x < row.length; x++) {
				rowStr += row[x].toString();
			}
			System.out.println(rowStr);
		}

	}

	public void printUnits() {
		for (Unit u : this.findUnits()) {
			System.out.println(u.type + " #" + u.id + " (" + u.hp + ")");
		}
	}

	public boolean checkFinished() {
		return this.getTypesAlive().size() == 1;
	}

	private Set<UnitType> getTypesAlive() {
		Set<UnitType> typesAlive = new HashSet<>();
		for (Unit u : this.findUnits()) {
			typesAlive.add(u.type);
		}
		return typesAlive;
	}

	private int getTotalHP() {
		int sum = 0;
		for (Unit u : this.findUnits()) {
			sum += u.hp;
		}
		return sum;
	}

	public void printOutcome() {

		if (this.getTypesAlive().size() > 1) {

			System.out.println("nobody has won yet.");

		} else {

			System.out.println("Combat ends after " + (this.rounds - 1) + " full rounds");
			UnitType winner = this.getTypesAlive().iterator().next();
			int totalHP = this.getTotalHP();
			System.out.println(winner + " win with " + totalHP + " HP left");
			System.out
					.println("outcome: " + (this.rounds - 1) + " * " + totalHP + " = " + ((this.rounds - 1) * totalHP));

		}

	}

	public int getOutcome() {
		return (this.rounds - 1) * this.getTotalHP();
	}

	public void superchargeElves(int power) {
		for (Unit u : this.findUnits()) {
			if (u.type.equals(UnitType.ELF)) {
				u.power = power;
			}
		}
	}

	public int count(UnitType elf) {
		int sum = 0;
		for (Unit u : this.findUnits()) {
			if (u.type.equals(UnitType.ELF)) {
				sum++;
			}
		}
		return sum;
	}

	public boolean elvesHaveWon() {
		return this.getTypesAlive().size() == 1 && this.getTypesAlive().iterator().next().equals(UnitType.ELF);
	}
}
