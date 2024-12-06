package net.stefangaertner.aoc19.util.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

	private List<Node> nodes = new ArrayList<>();

	private Map<Node, List<Node>> edges = new HashMap<>();

	public void addNode(Node node) {
		this.nodes.add(node);
	}

	public void addEdge(Node n, Node m) {
		this.edges.put(n, new ArrayList<>());
		this.edges.get(n)
				.add(m);
	}

}
