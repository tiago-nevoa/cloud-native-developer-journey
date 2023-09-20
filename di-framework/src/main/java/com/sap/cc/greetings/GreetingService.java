package com.sap.cc.greetings;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;

@Component
public class GreetingService {

    private final Supplier<Greeting> greetingSupplier;

    public GreetingService(Supplier<Greeting> greetingSupplier) {
        this.greetingSupplier = greetingSupplier;
    }

    public String greet(String name) {
        return greetingSupplier.get().forName(name);
    }
}
