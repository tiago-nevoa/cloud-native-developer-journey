package com.sap.cc.greeting;

import com.github.lalyos.jfiglet.FigletFont;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GreetingServiceTest {
    @Test
    void createGreetingMessage() {
        UserServiceClient usersMock = mock(UserServiceClient.class);
        FigletFontConverter figletFontMock = mock(FigletFontConverter.class);
        GreetingService service = new GreetingService(usersMock, figletFontMock);
        when(usersMock.findById(1L)).thenReturn(new User("Jane", "en-US"));
        String expectedGreeting = "Expected greeting";
        when(figletFontMock.convertOneLine("Hello Jane!")).thenReturn(expectedGreeting);

        String greeting = service.createGreetingMessage(1L);

        assertThat(greeting).isEqualTo(expectedGreeting);
    }

}