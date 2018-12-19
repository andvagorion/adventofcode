package net.stefangaertner.aoc18.pojo;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    
	public T data;
    public Node<T> parent;
    public List<Node<T>> children;
    
    public Node(Node<T> parent, T data) {
    	this.parent = parent;
		this.data = data;
		this.children = new ArrayList<>();
	}

	public int depth() {
		int depth = 1;
		Node<T> node = this;
		while (node.parent != null) {
			depth++;
			node = node.parent;
		}
		
		return depth;
	}
}