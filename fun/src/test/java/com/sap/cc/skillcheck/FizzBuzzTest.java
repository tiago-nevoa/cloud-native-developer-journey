package com.sap.cc.skillcheck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sap.cc.skillcheck.donttouch.FizzBuzz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FizzBuzzTest {

    @Autowired
    private FizzBuzz fizzBuzz;

    @Test
    void whenNumberDivide5_ThenBuzz() {
        int number = 10;
        String result = fizzBuzz.evaluate(number);
        assertEquals("Buzz", result);
    }

    @Test
    void whenNumberDivide3and5_ThenFizzBuzz() {
        int number = 15;
        String result = fizzBuzz.evaluate(number);
        assertEquals("FizzBuzz", result);
    }

    @Test
    void whenNumberNotDivide3and5_TheNumber() {
        int number = 4;
        String result = fizzBuzz.evaluate(number);
        assertEquals(Integer.toString(number), result);
    }

    @Test
    void whenNumberDivide3MultipleTimes_ThenFizzBingo() {
        int number = 6;
        String result = fizzBuzz.evaluate(number);
        number = 9;
        result = fizzBuzz.evaluate(number);
        number = 12;
        result = fizzBuzz.evaluate(number);
        assertEquals(result, "FizzBingo");
    }

    @Test
    void whenNumberDivide5MultipleTimes_ThenBuzzBingo() {
        int number = 10;
        String result = fizzBuzz.evaluate(number);
        number = 20;
        result = fizzBuzz.evaluate(number);
        assertEquals("BuzzBingo", result);
    }

    @Test
    void whenNumberDivide3and5MultipleTimes_ThenFizzBuzzBingo() {
        int number = 15;
        String result = fizzBuzz.evaluate(number);
        number = 30;
        result = fizzBuzz.evaluate(number);
        assertEquals("FizzBuzzBingo", result);
    }

    @Test
    void whenNumberNotDivide3and5MultipleTimesSameNumber_ThenNumberBingo() {
        int number = 4;
        String result = fizzBuzz.evaluate(number);
        number = 4;
        result = fizzBuzz.evaluate(number);
        assertEquals(number + "Bingo", result);
    }

}
