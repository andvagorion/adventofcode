package net.stefangaertner.aoc18;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TestResults {

	private static Stream<Arguments> actualResults() {
		return Stream.of(

				Arguments.of(new TestExecution<>(Day01::part1, 437L)),
				Arguments.of(new TestExecution<>(Day01::part2, 655L)),

				Arguments.of(new TestExecution<>(Day02::part1, 6642L)),
				Arguments.of(new TestExecution<>(Day02::part2, "cvqlbidheyujgtrswxmckqnap")),

				Arguments.of(new TestExecution<>(Day03::part1, 107043L)),
				Arguments.of(new TestExecution<>(Day03::part2, 346L)),

				Arguments.of(new TestExecution<>(Day04::part1, 76357L)),
				Arguments.of(new TestExecution<>(Day04::part2, 41668L)),

				Arguments.of(new TestExecution<>(Day05::part1, 10762L)),
				Arguments.of(new TestExecution<>(Day05::part2, 6946L)),

				Arguments.of(new TestExecution<>(Day06::part1, 6047L)),
				Arguments.of(new TestExecution<>(Day06::part2, 46320L)),

				Arguments.of(new TestExecution<>(Day07::part1, "ADEFKLBVJQWUXCNGORTMYSIHPZ")),
				Arguments.of(new TestExecution<>(Day07::part2, 1120L)),

				Arguments.of(new TestExecution<>(Day08::part1, 41926L)),
				Arguments.of(new TestExecution<>(Day08::part2, 24262L)),

				Arguments.of(new TestExecution<>(Day09::part1, 439635L)),
				Arguments.of(new TestExecution<>(Day09::part2, 3562722971L)),

				Arguments.of(new TestExecution<>(Day10::part1, "LKPHZHHJ")),
				Arguments.of(new TestExecution<>(Day10::part2, 10159L)),

				Arguments.of(new TestExecution<>(Day11::part1, "20,62")),
				Arguments.of(new TestExecution<>(Day11::part2, "229,61,16")),

				Arguments.of(new TestExecution<>(Day12::part1, 2571L)),
				Arguments.of(new TestExecution<>(Day12::part2, 3100000000655L)),

				Arguments.of(new TestExecution<>(Day13::part1, "80,100")),
				Arguments.of(new TestExecution<>(Day13::part2, "16,99")),

				Arguments.of(new TestExecution<>(Day14::part1, "1631191756")),
				Arguments.of(new TestExecution<>(Day14::part2, 20219475L)),

				Arguments.of(new TestExecution<>(Day15::part1, 198744L)),
				Arguments.of(new TestExecution<>(Day15::part2, 66510L)),

				Arguments.of(new TestExecution<>(Day16::part1, 596L)),
				Arguments.of(new TestExecution<>(Day16::part2, 554L)),

				Arguments.of(new TestExecution<>(Day17::part1, 27042L)),
				Arguments.of(new TestExecution<>(Day17::part2, 22214L)),

				Arguments.of(new TestExecution<>(Day18::part1, 621205L)),
				Arguments.of(new TestExecution<>(Day18::part2, 228490L)),

				Arguments.of(new TestExecution<>(Day19::part1, 1228L)),
				Arguments.of(new TestExecution<>(Day19::part2, 15285504L)),

				Arguments.of(new TestExecution<>(Day20::part1, 3983L)),
				Arguments.of(new TestExecution<>(Day20::part2, 8486L)),

				// code is missing?
				// Arguments.of(new TestExecution<>(Day21::part1, 9959629L)),
				// Arguments.of(new TestExecution<>(Day21::part2, 12691260L)),

				Arguments.of(new TestExecution<>(Day22::part1, 11359L)),

				Arguments.of(new TestExecution<>(Day23::part1, 721L)),

				Arguments.of(new TestExecution<>(Day24::part1, 19381L)),
				Arguments.of(new TestExecution<>(Day24::part2, 3045L)),

				Arguments.of(new TestExecution<>(Day25::part1, 430L))

		);
	}

	@ParameterizedTest
	@MethodSource("actualResults")
	void testDays(TestExecution<?> ex) {
		assertEquals(ex.value(), ex.method().get());
	}

}
