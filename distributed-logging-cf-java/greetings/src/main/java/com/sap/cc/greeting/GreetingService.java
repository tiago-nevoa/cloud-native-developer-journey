package com.sap.cc.greeting;

import com.github.lalyos.jfiglet.FigletFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GreetingService {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final UserServiceClient userServiceClient;
    private final FigletFontConverter figletFontConverter;

    public GreetingService(UserServiceClient userServiceClient, FigletFontConverter figletFontConverter) {
        this.userServiceClient = userServiceClient;
        this.figletFontConverter = figletFontConverter;
    }

    public String createGreetingMessage(Long id) {
        User user = userServiceClient.findById(id);
        logger.info("Creating greeting message for user: {}", user.toString());

        String languageSpecificGreeting = createLanguageSpecificGreeting(user.getLocale(), user.getName());

        return figletFontConverter.convertOneLine(languageSpecificGreeting);
    }

    private String createLanguageSpecificGreeting(String locale, String name) {
        return String.format(getLanguageSpecificFormatString(locale), name);
    }

    private String getLanguageSpecificFormatString(String locale) {
        switch (locale) {
            case "en-US":
                return "Hello %s!";
            case "de-DE":
                return "Hallo %s!";
            case "es-ES":
                return "¡Hola %s!";
            default:
                throw new IllegalArgumentException("unknown locale");
        }
    }

    public String getWelcomePage() {
        List<User> users = userServiceClient.getAllUsers();

        List<String> response = new ArrayList<>(getWelcomeText());

        for (int i = 0; i < users.size(); i++) {
            response.add(users.get(i).getName() + ", with id " + i);
        }

        return String.join("\n", response);
    }

    private List<String> getWelcomeText() {
        return Arrays.asList("Welcome to the",
                FigletFont.convertOneLine("GreetingsService"),
                "Choose one of the below listed users and create a greeting by appending the ID as parameter in the URL.",
                "");
    }
}