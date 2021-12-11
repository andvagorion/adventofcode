package net.stefangaertner.aoc20;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TestResults {

	private static Stream<Arguments> actualResults() {
		return Stream.of(

				Arguments.of(new TestExecution<>(Day01::part1, 928896L)),
				Arguments.of(new TestExecution<>(Day01::part2, 295668576L)),

				Arguments.of(new TestExecution<>(Day02::part1, 580L)),
				Arguments.of(new TestExecution<>(Day02::part2, 611L)),

				Arguments.of(new TestExecution<>(Day03::part1, 169L)),
				Arguments.of(new TestExecution<>(Day03::part2, 7560370818L)),

				Arguments.of(new TestExecution<>(Day04::part1, 190L)),
				Arguments.of(new TestExecution<>(Day04::part2, 121L)),

				Arguments.of(new TestExecution<>(Day05::part1, 980L)),
				Arguments.of(new TestExecution<>(Day05::part2, 607L)),

				Arguments.of(new TestExecution<>(Day06::part1, 6714L)),
				Arguments.of(new TestExecution<>(Day06::part2, 3435L)),

				Arguments.of(new TestExecution<>(Day07::part1, 300L)),
				Arguments.of(new TestExecution<>(Day07::part2, 8030L)),

				Arguments.of(new TestExecution<>(Day08::part1, 1614L)),
				Arguments.of(new TestExecution<>(Day08::part2, 1260L)),

				Arguments.of(new TestExecution<>(Day09::part1, 1309761972L)),
				Arguments.of(new TestExecution<>(Day09::part2, 177989832L)),

				Arguments.of(new TestExecution<>(Day10::part1, 2812L)),

				Arguments.of(new TestExecution<>(Day11::part1, 2418L)),
				Arguments.of(new TestExecution<>(Day11::part2, 2144L)),

				Arguments.of(new TestExecution<>(Day12::part1, 882L)),
				Arguments.of(new TestExecution<>(Day12::part2, 28885L)),

				Arguments.of(new TestExecution<>(Day13::part1, 4782L)),

				Arguments.of(new TestExecution<>(Day14::part1, 15018100062885L)),
				Arguments.of(new TestExecution<>(Day14::part2, 5724245857696L)),

				Arguments.of(new TestExecution<>(Day15::part1, 595L)),
				Arguments.of(new TestExecution<>(Day15::part2, 1708310L)),

				Arguments.of(new TestExecution<>(Day16::part1, 25788L)),
				Arguments.of(new TestExecution<>(Day16::part2, 3902565915559L)),

				Arguments.of(new TestExecution<>(Day17::part1, 368L)),
				Arguments.of(new TestExecution<>(Day17::part2, 2696L)),

				Arguments.of(new TestExecution<>(Day19::part1, 291L)),
				Arguments.of(new TestExecution<>(Day19::part2, 409L))

		);
	}

	@ParameterizedTest
	@MethodSource("actualResults")
	void testDays(TestExecution<?> ex) {
		assertEquals(ex.value(), ex.method().get());
	}

}