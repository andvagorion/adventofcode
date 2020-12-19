package net.stefangaertner.aoc20;

import java.util.function.Supplier;

public class Execution<T> {

	private Supplier<T> method;
	private T value;

	public Execution (Supplier<T> method, T value) {
		this.method = method;
		this.value = value;
	}

	public Supplier<T> method() {
		return method;
	}

	public T value() {
		return value;
	}
	
	@Override
	public String toString() {
		return String.format("should be %s", value);
	}
}