package net.stefangaertner.aoc20;

import java.util.function.Supplier;

public class TestExecution<T> {

	private Supplier<T> method;
	private T value;

	public TestExecution (Supplier<T> method, T value) {
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
