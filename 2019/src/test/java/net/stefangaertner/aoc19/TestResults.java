package net.stefangaertner.aoc19;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TestResults {

	private static Stream<Arguments> actualResults() {
		return Stream.of(

				Arguments.of(new TestExecution<>(Day01::part1, 3380731L)),
				Arguments.of(new TestExecution<>(Day01::part2, 5068210L)),

				Arguments.of(new TestExecution<>(Day02::part1, 5098658L)),
				Arguments.of(new TestExecution<>(Day02::part2, 5064L)),

				Arguments.of(new TestExecution<>(Day03::part1, 896L)),
				Arguments.of(new TestExecution<>(Day03::part2, 16524L)),

				Arguments.of(new TestExecution<>(Day04::part1, 2081L)),
				Arguments.of(new TestExecution<>(Day04::part2, 1411L)),

				Arguments.of(new TestExecution<>(Day05::part1, 15386262L)),
				Arguments.of(new TestExecution<>(Day05::part2, 10376124L)),

				Arguments.of(new TestExecution<>(Day06::part1, 223251L)),
				Arguments.of(new TestExecution<>(Day06::part2, 430L)),

				Arguments.of(new TestExecution<>(Day07::part1, 18812L)),
				Arguments.of(new TestExecution<>(Day07::part2, 25534964L)),

				Arguments.of(new TestExecution<>(Day08::part1, 1224L)),
				Arguments.of(new TestExecution<>(Day08::part2, "EBZUR")),

				Arguments.of(new TestExecution<>(Day09::part1, 3533056970L)),
				Arguments.of(new TestExecution<>(Day09::part2, 72852L)),

				Arguments.of(new TestExecution<>(Day10::part1, 314L)),
				Arguments.of(new TestExecution<>(Day10::part2, 1513L)),

				Arguments.of(new TestExecution<>(Day11::part1, 1964L)),
				Arguments.of(new TestExecution<>(Day11::part2, "FKEKCFRK")),

				Arguments.of(new TestExecution<>(Day12::part1, 8454L)),

				Arguments.of(new TestExecution<>(Day13::part1, 213L)),
				Arguments.of(new TestExecution<>(Day13::part2, 11441L)),

				Arguments.of(new TestExecution<>(Day15::part1, 214L)),
				Arguments.of(new TestExecution<>(Day15::part2, 344L)),

				Arguments.of(new TestExecution<>(Day16::part1, 61149209L)),

				Arguments.of(new TestExecution<>(Day17::part1, 6520L)),
				Arguments.of(new TestExecution<>(Day17::part2, 1071369L)),

				Arguments.of(new TestExecution<>(Day19::part1, 156L)),
				Arguments.of(new TestExecution<>(Day19::part2, 2610980L)),

				Arguments.of(new TestExecution<>(Day20::part1, 442L)),

				Arguments.of(new TestExecution<>(Day21::part1, 19356971L)),
				Arguments.of(new TestExecution<>(Day21::part2, 1142600034L)),

				Arguments.of(new TestExecution<>(Day22::part1, 8326L)),

				Arguments.of(new TestExecution<>(Day23::part1, 26464L)),
				Arguments.of(new TestExecution<>(Day23::part2, 19544L)),

				Arguments.of(new TestExecution<>(Day24::part1, 30442557L)),
				Arguments.of(new TestExecution<>(Day24::part2, 1987L)),

				Arguments.of(new TestExecution<>(Day25::part1, 2214608912L))

		);
	}

	@ParameterizedTest
	@MethodSource("actualResults")
	void testDays(TestExecution<?> ex) {
		assertEquals(ex.value(), ex.method().get());
	}

}
