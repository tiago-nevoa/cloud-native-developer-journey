package com.sap.cc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
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

class ClassesCreatedTests {

	private static final String DEVELOPER_CLASS_ERROR_MSG = "Developer class does not exist or is not a subclass of CodeCreator";
	private static final String HACKATHON_CLASS_ERROR_MSG = "Hackathon class does not exist or is not an implementation of DeveloperEvent";
	private static final String DEVELOPER_CLASS_ACCESSOR_ERROR_MSG = "Developer class does not have accessor (get/set) methods implemented or they are behaving wrong";

	private Reflections reflections = new Reflections("com.sap.cc");

	@Test
	@DisplayName("A class named Developer should exist which is a subclass of CodeCreator")
	void testThatDeveloperClassCreated() throws NoSuchMethodException, SecurityException {
		Set<Class<? extends CodeCreator>> classes = reflections.getSubTypesOf(CodeCreator.class);
		assertThat(classes.size()).withFailMessage(DEVELOPER_CLASS_ERROR_MSG).isEqualTo(1);
		Class<? extends CodeCreator> developerClass = classes.iterator().next();
		assertThat(developerClass.getSimpleName()).withFailMessage(DEVELOPER_CLASS_ERROR_MSG).isEqualTo("Developer");
		assertThat(classes.iterator().next().getProtectionDomain().getCodeSource().getLocation().getPath()).withFailMessage("The Developer class is in the src/test/java folder, not in src/main/java where it belongs.").contains("target/classes");
	}

	@Test
	@DisplayName("Developer class should have specified fields and set/get methods")
	void testThatDeveloperClassHasExpectedFieldsAndAccessors()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Set<Class<? extends CodeCreator>> classes = reflections.getSubTypesOf(CodeCreator.class);
		Assumptions.assumeTrue(classes.size() == 1, "Aborting Test since Developer class seems to be missing");
		Class<? extends CodeCreator> developerClass = classes.iterator().next();
		try {
			assertThat(developerClass.getDeclaredField("name").getType()).isEqualTo(String.class);
			assertThat(developerClass.getDeclaredField("language").getType()).isEqualTo(String.class);
		} catch (NoSuchFieldException e) {
			fail("expected field is missing", e);
		}

		try {
			Method setName = developerClass.getMethod("setName", String.class);
			Method getName = developerClass.getMethod("getName");
			Method getLanguage = developerClass.getMethod("getLanguage");
			Method setLanguage = developerClass.getMethod("setLanguage", String.class);

			assertThat(getName.getReturnType()).withFailMessage(DEVELOPER_CLASS_ACCESSOR_ERROR_MSG)
					.isEqualTo(String.class);
			assertThat(setName.getReturnType()).withFailMessage(DEVELOPER_CLASS_ACCESSOR_ERROR_MSG)
					.isEqualTo(void.class);
			assertThat(getLanguage.getReturnType()).withFailMessage(DEVELOPER_CLASS_ACCESSOR_ERROR_MSG)
					.isEqualTo(String.class);
			assertThat(setLanguage.getReturnType()).withFailMessage(DEVELOPER_CLASS_ACCESSOR_ERROR_MSG)
					.isEqualTo(void.class);

			CodeCreator instance;
			try {
				Constructor<? extends CodeCreator> constructor = developerClass.getConstructor(String.class,
						String.class);
				instance = constructor.newInstance("Jan", "Java");
			} catch (Exception e) {
				e.printStackTrace();
				Assumptions.assumeTrue(false, "Developer Instance initialization failed");
				return;
			}

			setName.invoke(instance, "Ada");
			assertThat(getName.invoke(instance)).withFailMessage(DEVELOPER_CLASS_ACCESSOR_ERROR_MSG).isEqualTo("Ada");
			setLanguage.invoke(instance, "ABAP");
			assertThat(getLanguage.invoke(instance)).withFailMessage(DEVELOPER_CLASS_ACCESSOR_ERROR_MSG)
					.isEqualTo("ABAP");

		} catch (NoSuchMethodException e) {
			fail("expected accessor method is missing", e);
		}

	}

	@Test
	@DisplayName("Developer class should have specified constructor")
	void testThatDeveloperClassHasExpectedConstructor()
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Set<Class<? extends CodeCreator>> classes = reflections.getSubTypesOf(CodeCreator.class);
		Assumptions.assumeTrue(classes.size() == 1, "Aborting Test since Developer class seems to be missing");
		Class<? extends CodeCreator> developerClass = classes.iterator().next();
		Constructor<? extends CodeCreator> constructor;
		try {
			constructor = developerClass.getConstructor(String.class, String.class);
		} catch (NoSuchMethodException e) {
			fail("Constructor with expected parameters for Developer is missing", e);
			return;
		}

		CodeCreator instance = constructor.newInstance("Jan", "Java");
		Field nameField = developerClass.getDeclaredField("name");
		nameField.setAccessible(true);
		assertThat(nameField.get(instance)).isEqualTo("Jan");
		Field languageField = developerClass.getDeclaredField("language");
		languageField.setAccessible(true);
		assertThat(languageField.get(instance)).isEqualTo("Java");

	}

	@Test
	@DisplayName("A class named Hackathon should exist which is an implementation of DeveloperEvent")
	void testThatHackathonClassCreated() {
		Set<Class<? extends DeveloperEvent>> classes = reflections.getSubTypesOf(DeveloperEvent.class);

		assertThat(classes.size()).withFailMessage(HACKATHON_CLASS_ERROR_MSG).isEqualTo(1);
		assertThat(classes.iterator().next().getSimpleName()).withFailMessage(HACKATHON_CLASS_ERROR_MSG)
				.isEqualTo("Hackathon");
		
		assertThat(classes.iterator().next().getProtectionDomain().getCodeSource().getLocation().getPath()).withFailMessage("The Hackathon class is in the src/test/java folder, not in src/main/java where it belongs.").contains("target/classes");
	}

}
