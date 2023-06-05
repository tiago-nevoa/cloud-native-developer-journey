package com.sap.cc;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import com.sap.cc.handsoff.CodeCreator;
import com.sap.cc.handsoff.DeveloperEvent;

//██╗  ██╗ █████╗ ███╗   ██╗██████╗ ███████╗     ██████╗ ███████╗███████╗██╗██╗██╗
//██║  ██║██╔══██╗████╗  ██║██╔══██╗██╔════╝    ██╔═══██╗██╔════╝██╔════╝██║██║██║
//███████║███████║██╔██╗ ██║██║  ██║███████╗    ██║   ██║█████╗  █████╗  ██║██║██║
//██╔══██║██╔══██║██║╚██╗██║██║  ██║╚════██║    ██║   ██║██╔══╝  ██╔══╝  ╚═╝╚═╝╚═╝
//██║  ██║██║  ██║██║ ╚████║██████╔╝███████║    ╚██████╔╝██║     ██║     ██╗██╗██╗
//╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝     ╚═════╝ ╚═╝     ╚═╝     ╚═╝╚═╝╚═╝

class HackathonTests {

	private static final String SEPARATOR = "\n";
	private static Reflections reflections = new Reflections("com.sap.cc");
	private static Constructor<? extends DeveloperEvent> hackathonConstructor;
	private static Constructor<? extends CodeCreator> developerConstructor;

	@BeforeAll
	public static void beforeClass() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Set<Class<? extends DeveloperEvent>> developerEventclasses = reflections.getSubTypesOf(DeveloperEvent.class);
		Assumptions.assumeTrue(developerEventclasses.size() == 1, "Not executing since Hackathon class missing");
		Class<? extends DeveloperEvent> hackathonClass = developerEventclasses.iterator().next();
		Assumptions.assumeTrue(hackathonClass.getSimpleName().equals("Hackathon"),
				"Not executing since Hackathon class missing");
		hackathonConstructor = hackathonClass.getConstructor();

		Set<Class<? extends CodeCreator>> codeCreatorclasses = reflections.getSubTypesOf(CodeCreator.class);
		Assumptions.assumeTrue(codeCreatorclasses.size() == 1, "Not executing since Developer class missing");
		Class<? extends CodeCreator> developerClass = codeCreatorclasses.iterator().next();
		Assumptions.assumeTrue(developerClass.getSimpleName().equals("Developer"),
				"Not executing since Developer class missing");
		developerConstructor = developerClass.getConstructor(String.class, String.class);
	}

	@Test
	void testCodeTogetherMethodEmptyList()
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		DeveloperEvent instance;
		try {

			instance = hackathonConstructor.newInstance();
			assertThat(instance.codeTogether(Collections.emptyList())).isEqualTo("");

		} catch (Exception e) {
			e.printStackTrace();
			Assumptions.assumeTrue(false, "Hackaton instance initialization failed");
			return;
		}

	}

	@Test
	void testCodeTogetherMethodListOneDeveloper()
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		DeveloperEvent hackathonInstance;
		try {

			hackathonInstance = hackathonConstructor.newInstance();
			List<CodeCreator> list = new ArrayList<>();
			list.add(createDeveloper(DeveloperTests.GO_NAME, DeveloperTests.GO_LANGUAGE));
			assertThat(hackathonInstance.codeTogether(list)).isEqualTo(DeveloperTests.GO_STRING + SEPARATOR);

		} catch (Exception e) {
			e.printStackTrace();
			Assumptions.assumeTrue(false, "Instance initialization failed");
			return;
		}

	}

	@Test
	void testCodeTogetherMethodListThreeDevelopers()
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		DeveloperEvent hackathonInstance;
		try {

			hackathonInstance = hackathonConstructor.newInstance();
			List<CodeCreator> list = new ArrayList<>();
			list.add(createDeveloper(DeveloperTests.GO_NAME, DeveloperTests.GO_LANGUAGE));
			list.add(createDeveloper(DeveloperTests.NODE_NAME, DeveloperTests.NODE_LANGUAGE));
			list.add(createDeveloper(DeveloperTests.PYTHON_NAME, DeveloperTests.PYTHON_LANGUAGE));
			assertThat(hackathonInstance.codeTogether(list)).isEqualTo(DeveloperTests.GO_STRING + SEPARATOR
					+ DeveloperTests.NODE_STRING + SEPARATOR + DeveloperTests.PYTHON_STRING + SEPARATOR);

		} catch (Exception e) {
			e.printStackTrace();
			Assumptions.assumeTrue(false, "Instance initialization failed");
			return;
		}

	}

	@Test
	void testCodeTogetherMethodListThreeDevelopersOneUnsupportedLanguage()
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		DeveloperEvent hackathonInstance;
		try {

			hackathonInstance = hackathonConstructor.newInstance();
			List<CodeCreator> list = new ArrayList<>();
			list.add(createDeveloper(DeveloperTests.GO_NAME, DeveloperTests.GO_LANGUAGE));
			list.add(createDeveloper(DeveloperTests.NODE_NAME, DeveloperTests.NODE_LANGUAGE));
			list.add(createDeveloper("Ada", DeveloperTests.ABAP_LANGUAGE));
			assertThat(hackathonInstance.codeTogether(list)).isEqualTo(DeveloperTests.GO_STRING + SEPARATOR
					+ DeveloperTests.NODE_STRING + SEPARATOR + DeveloperTests.ABAP_STRING + SEPARATOR);

		} catch (Exception e) {
			e.printStackTrace();
			Assumptions.assumeTrue(false, "Instance initialization failed");
			return;
		}

	}

	private CodeCreator createDeveloper(String name, String language)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		return developerConstructor.newInstance(name, language);

	}

}
