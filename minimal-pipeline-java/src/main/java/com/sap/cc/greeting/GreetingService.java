package com.sap.cc.greeting;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {
	public String createGreetingMessage(String greeting, String name) {
		if (name.matches(".*[0-9].*")) {
			throw new IllegalArgumentException("Name must not contain numbers!");
		}
		System.out.println("created greeting.");
		return greeting + " " + name;
	}
}