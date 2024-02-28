package com.sap.cc.greeting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GreetingServiceTest {

    GreetingService cut = new GreetingService();

    @Test
    void createGreetingMessage_withValidParameters_createsGreeting() {
        String greetingMessage = cut.createGreetingMessage("Wake up", "Neo");

        assertThat(greetingMessage).isEqualTo("Wake up Neo");
    }

    @Test
    void createGreetingMessage_withNumberInNameParameter_throwsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> cut.createGreetingMessage("Wake up", "N30"));

        assertTrue(exception.getMessage().contains("Name must not contain numbers!"));
    }
}
