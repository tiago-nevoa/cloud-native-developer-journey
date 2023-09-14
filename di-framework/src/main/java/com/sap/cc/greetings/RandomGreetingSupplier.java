package com.sap.cc.greetings;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@Component
public class RandomGreetingSupplier implements Supplier<Greeting> {

    private final List<Greeting> greetings = Arrays.asList(
            new Greeting("Hello %s."),
            new Greeting("Hi %s!"),
            new Greeting("Welcome %s."),
            new Greeting("Good morning %s."),
            new Greeting("Good afternoon %s."),
            new Greeting("Good evening %s."),
            new Greeting("Nice to meet you, %s.")
    );
    private final Random random = new Random();

    @Override
    public Greeting get() {
        return greetings.get(random.nextInt(greetings.size()));
    }
}
