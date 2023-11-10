package com.sap.cc.greeting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String createGreetingMessage(String greeting, String name) {
        if (name.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("Name must not contain numbers!");
        }
        MDC.put("path", name);
        logger.debug("created greeting for {}", name);
        MDC.clear();
        return greeting + " " + name;
    }
}