package com.sap.cc.tdd;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FizzBuzzTest {

	private FizzBuzz fizzBuzz;

	@BeforeEach
	void setUp() {
		fizzBuzz = new FizzBuzz();
	}

	@Test
	void oneShouldGiveOne() {
		assertThat(fizzBuzz.print(1)).isEqualTo("1");
	}
	@Test
	void seventeenShouldGiveSeventeen() {
		assertThat(fizzBuzz.print(17)).isEqualTo("17");
	}
	@Test
	void fiftyNineShouldGiveFiftyNine() {
		assertThat(fizzBuzz.print(59)).isEqualTo("59");
	}
	@Test
	void twoShouldGiveTwo() {
		assertThat(fizzBuzz.print(2)).isEqualTo("2");
	}
	@Test
	void threeShouldGiveFizz() {
		assertThat(fizzBuzz.print(3)).isEqualTo("Fizz");
	}
	@Test
	void thirtyThreeShouldGiveBuzz() {
		assertThat(fizzBuzz.print(33)).isEqualTo("Fizz");
	}
	@Test
	void fiveShouldGiveBuzz() {
		assertThat(fizzBuzz.print(5)).isEqualTo("Buzz");
	}
	@Test
	void twentyFiveShouldGiveBuzz() {
		assertThat(fizzBuzz.print(25)).isEqualTo("Buzz");
	}
	@Test
	void fifteenShouldGiveFizzBuzz() {
		assertThat(fizzBuzz.print(15)).isEqualTo("FizzBuzz");
	}
	@Test
	void sixtyShouldGiveFizzBuzz() {
		assertThat(fizzBuzz.print(60)).isEqualTo("FizzBuzz");
	}

}
