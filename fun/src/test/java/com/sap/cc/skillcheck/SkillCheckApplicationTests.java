package com.sap.cc.skillcheck;

import static org.assertj.core.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sap.cc.skillcheck.donttouch.FizzBuzz;

@SpringBootTest
class SkillCheckApplicationTests {

	@Autowired(required = false)
	private FizzBuzz fizzBuzz;

	@Test
	void contextLoads() {
		if (fizzBuzz == null) {
			fail("Make FizzBuzzImpl a managed Bean!");
		}
	}

}
