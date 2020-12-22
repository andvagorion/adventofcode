package net.stefangaertner.test;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import net.stefangaertner.util.Pair;

public class PairTest {

	@Test
	public void testContains() {
		Set<Pair<Deque<Long>, Deque<Long>>> set = new HashSet<>();
		set.add(Pair.of(this.createDeque(0, 100), this.createDeque(100, 200)));
		var pair = Pair.of(this.createDeque(0, 100), this.createDeque(100, 200));

		Assert.assertTrue(set.contains(pair));
	}

	private Deque<Long> createDeque(int i, int j) {
		return LongStream.range(0, 10)
				.mapToObj(Long::valueOf)
				.collect(Collectors.toCollection(() -> new LinkedList<>()));
	}

}
