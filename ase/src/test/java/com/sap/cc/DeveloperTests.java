package com.sap.cc;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import com.sap.cc.handsoff.CodeCreator;
import com.sap.cc.handsoff.UnsupportedDevelopmentLanguageException;

//██╗  ██╗ █████╗ ███╗   ██╗██████╗ ███████╗     ██████╗ ███████╗███████╗██╗██╗██╗
//██║  ██║██╔══██╗████╗  ██║██╔══██╗██╔════╝    ██╔═══██╗██╔════╝██╔════╝██║██║██║
//███████║███████║██╔██╗ ██║██║  ██║███████╗    ██║   ██║█████╗  █████╗  ██║██║██║
//██╔══██║██╔══██║██║╚██╗██║██║  ██║╚════██║    ██║   ██║██╔══╝  ██╔══╝  ╚═╝╚═╝╚═╝
//██║  ██║██║  ██║██║ ╚████║██████╔╝███████║    ╚██████╔╝██║     ██║     ██╗██╗██╗
//╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝     ╚═════╝ ╚═╝     ╚═╝     ╚═╝╚═╝╚═╝

class DeveloperTests {

	public static final String GO_NAME = "Gopher";
	public static final String GO_LANGUAGE = "go";
	public static final String GO_STRING = "fmt.Println(\"Hello, Gopher!\")";
	public static final String NODE_NAME = "Nabil";
	public static final String NODE_LANGUAGE = "nodejs";
	public static final String NODE_STRING = "console.log(\"Hello, Nabil!\")";
	public static final String PYTHON_NAME = "Pranjal";
	public static final String PYTHON_LANGUAGE = "python";
	public static final String PYTHON_STRING = "print(\"Hello, Pranjal!\")";
	public static final String ABAP_NAME = "Anja";
	public static final String ABAP_LANGUAGE = "abap";
	public static final String ABAP_STRING = "Unsupported language: abap";
	
	
	private static Reflections reflections = new Reflections("com.sap.cc");
	private static Constructor<? extends CodeCreator> constructor;

	@BeforeAll
	public static void beforeClass() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Set<Class<? extends CodeCreator>> classes = reflections.getSubTypesOf(CodeCreator.class);
		Assumptions.assumeTrue(classes.size() == 1, "Not executing since Developer class missing");
		Class<? extends CodeCreator> developerClass = classes.iterator().next();
		Assumptions.assumeTrue(developerClass.getSimpleName().equals("Developer"),
				"Not executing since Developer class missing");
		constructor = developerClass.getConstructor(String.class, String.class);

	}

	@Test
	void testCodeMethod()
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		CodeCreator instance;
		try {
			
			instance = constructor.newInstance(GO_NAME, GO_LANGUAGE);
			assertThat(instance.code()).isEqualTo(GO_STRING);
			instance = constructor.newInstance(NODE_NAME, NODE_LANGUAGE);
			assertThat(instance.code()).isEqualTo(NODE_STRING);
			instance = constructor.newInstance(PYTHON_NAME, PYTHON_LANGUAGE);
			assertThat(instance.code()).isEqualTo(PYTHON_STRING);
			instance = constructor.newInstance(ABAP_NAME, ABAP_LANGUAGE);
			Assertions.assertThrows(UnsupportedDevelopmentLanguageException.class, instance::code);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assumptions.assumeTrue(false, "Developer instance initialization failed");
			return;
		}
	}
}
