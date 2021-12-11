package net.stefangaertner.aoc20.util;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Filter {

	private List<String> data;

	private Filter (List<String> str) {
		this.data = str;
	}

	public static Filter list(List<String> str) {
		return new Filter(str);
	}

	public Filter keep(Predicate<String> predicate) {
		this.data = data.stream()
				.filter(predicate)
				.collect(Collectors.toList());
		return this;
	}

	public Filter remove(Predicate<String> predicate) {
		this.data = data.stream()
				.filter(predicate.negate())
				.collect(Collectors.toList());
		return this;
	}

	public Filter matchRegex(String regex) {
		this.data = data.stream()
				.filter(str -> str.matches(regex))
				.collect(Collectors.toList());
		return this;
	}

	public Filter doesNotMatchRegex(String regex) {
		this.data = data.stream()
				.filter(str -> !str.matches(regex))
				.collect(Collectors.toList());
		return this;
	}

	public List<String> get() {
		return data;
	}

}
