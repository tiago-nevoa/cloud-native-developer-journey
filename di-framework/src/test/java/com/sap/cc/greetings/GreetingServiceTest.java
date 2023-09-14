package com.sap.cc.greetings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class GreetingServiceTest {

    @Autowired
    GreetingService greetingService;

    @MockBean
    Supplier<Greeting> greetingSupplier;

    @Test
    void getGreetingForPerson() {
        String name = "Angela";

        Greeting mockGreeting = new Greeting("Hello %s.");

        when(greetingSupplier.get()).thenReturn(mockGreeting);

        String greeting = greetingService.greet(name);

        assertThat(greeting).contains(name);
    }
}
