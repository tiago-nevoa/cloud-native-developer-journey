package com.sap.cc.greeting;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GreetingController {

	private GreetingService service;

	public GreetingController(GreetingService service) {
		this.service = service;
	}

	@GetMapping("/hello")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return getGreeting("Hello", name);
	}

	@GetMapping("/howdy")
	public String deprecatedGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		System.err.println("Deprecated endpoint used.");
		return getGreeting("Howdy", name);
	}

	private String getGreeting(String greeting, String name) {
		try {
			return service.createGreetingMessage(greeting, name);
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		} finally {
		}
	}

}
