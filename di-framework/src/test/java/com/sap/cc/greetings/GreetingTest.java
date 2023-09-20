package com.sap.cc.greetings;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GreetingTest {

    @Test
    void template() {
        Greeting greeting = new Greeting("Hello %s.");

        assertThat(greeting.forName("World")).isEqualTo("Hello World.");
    }
}
