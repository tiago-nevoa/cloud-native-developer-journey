package com.sap.cc.greeting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> greetUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(greetingService.createGreetingMessage(id));
    }

    @GetMapping
    public ResponseEntity<String> getAllUsers() {
        String joinedResponse = greetingService.getWelcomePage();
        return ResponseEntity.ok().contentType(MediaType.valueOf("text/plain;charset=UTF-8")).body(joinedResponse);
    }
}
