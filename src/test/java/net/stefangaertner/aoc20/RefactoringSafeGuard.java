package net.stefangaertner.aoc20;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import net.stefangaertner.util.FileUtils;

class RefactoringSafeGuard {

	@Test
	void day01() {
		List<String> lines = FileUtils.read("aoc20/001");

		Assert.assertEquals(928896L, Day01.part1(lines));
		Assert.assertEquals(295668576L, Day01.part2(lines));
	}

	@Test
	void day02() {
		List<String> lines = FileUtils.read("aoc20/002");

		Assert.assertEquals(580L, Day02.part1(lines));
		Assert.assertEquals(611L, Day02.part2(lines));
	}

	@Test
	void day03() {
		List<String> lines = FileUtils.read("aoc20/003");

		Assert.assertEquals(169L, Day03.part1(lines));
		Assert.assertEquals(7560370818L, Day03.part2(lines));
	}

	@Test
	void day04() {
		List<String> lines = FileUtils.read("aoc20/004");

		Assert.assertEquals(190L, Day04.part1(lines));
		Assert.assertEquals(121L, Day04.part2(lines));
	}

	@Test
	void day05() {
		List<String> lines = FileUtils.read("aoc20/005");

		Assert.assertEquals(980L, Day05.part1(lines));
		Assert.assertEquals(607L, Day05.part2(lines));
	}

	@Test
	void day06() {
		List<List<String>> groups = FileUtils.readGroups("aoc20/006");

		Assert.assertEquals(6714L, Day06.part1(groups));
		Assert.assertEquals(3435L, Day06.part2(groups));
	}

}
