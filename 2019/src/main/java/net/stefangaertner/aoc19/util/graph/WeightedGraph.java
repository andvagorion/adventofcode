package net.stefangaertner.aoc19.util.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WeightedGraph {

	private Map<Integer, Node> nodes = new HashMap<>();

	private Map<Integer, Map<Integer, Integer>> edges = new HashMap<>();

	public void addNode(Node node) {
		this.nodes.put(node.getId(), node);
	}

	public void addEdge(Node n, Node m, int weight) {
		this.edges.putIfAbsent(n.getId(), new HashMap<>());
		this.edges.get(n.getId())
				.put(m.getId(), weight);
	}

	public Collection<Node> getNodes() {
		return Collections.unmodifiableCollection(nodes.values());
	}

	public Node getNode(int id) {
		return nodes.get(id);
	}

	public Map<Integer, Integer> getEdges(Node node) {
		return this.getEdges(node.getId());
	}

	public Map<Integer, Integer> getEdges(int id) {
		return this.edges.get(id);
	}
	
	public void debugPrint() {
		for (Node node : this.getNodes()) {
			System.out.println(node.getLabel());
			Map<Integer, Integer> edges = this.getEdges(node);
			for (Map.Entry<Integer, Integer> edge : edges.entrySet()) {
				Node other = this.getNode(edge.getKey());
				System.out.println("- " + other.getLabel() + "(" + edge.getValue() + ")");
			}
		}
	}

}
