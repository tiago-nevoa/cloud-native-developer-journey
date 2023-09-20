# Poker application

This is the reference application for the poker game that is being developed as part of the agile software engineering training.

## Initial setup
- This repository is supposed to be forked for your training so that you can share code changes among your training team
- The code requires at least Java 8 and Maven 3 to compile and package
- If you use Eclipse as an IDE, import the project as "Existing Maven project"
- The project is a self-contained Spring Boot project
- You can start the application either:
	- using `mvn spring-boot:run`, from the command line or within your IDE
	- if you are using [Spring Tool Suite](https://spring.io/tools): using "Run As" -> "Spring Boot App" or from the "Boot Dashboard" view
- The application will be available at http://localhost:8080
- If you would like to use Gradle instead of Maven:
  - Use `gradle init` to generate Gradle artifacts from the Maven pom.xml
  - Add `mavenCentral()` to the `repositories`
  - [Add the Spring Boot plugin for Gradle](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/), otherwise the `bootRun` Gradle task will not work
  - Add the following to enable JUnit5: 
    ```groovy
    test {
        useJUnitPlatform()
    }
    ```
  - For PITest add the plugin `id 'info.solidsoft.pitest' version '1.5.1'` and the following to support JUnit 5 tests:
    ```groovy
    pitest {
        junit5PluginVersion = '0.12'
    }
    ```

## Test support tools
- To continuously execute your tests you can use [Infinitest](http://infinitest.github.io/) which has plugins for Eclipse and IntelliJ
- To get code coverage statistics you can use:
	- IntelliJ's built-in [code coverage runner](https://www.jetbrains.com/help/idea/code-coverage.html)
	- [EclEmma](https://www.eclemma.org/) plugin for Eclipse
- Mutation tests are available using the PIT maven plugin.
	- Simply run `mvn org.pitest:pitest-maven:mutationCoverage` the report will be available at **target/pit-reports/{timestamp}/index.html**
	- NOTE: If pitest reports any problems you can try to run `mvn install` and then retry.
