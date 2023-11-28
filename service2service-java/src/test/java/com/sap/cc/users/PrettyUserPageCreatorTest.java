package com.sap.cc.users;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.cc.ascii.AsciiArtServiceClient;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PrettyUserPageCreatorTest {

    @InjectMocks
    private PrettyUserPageCreator prettyUserPageCreator;

    @Mock
    private AsciiArtServiceClient asciiArtServiceClient;

    @Test
    void shouldCreateAPrettyUserPage() {
        User user = new User("someName", "somePhoneNumber", "1");
        String prettifiedName = "prettifiedName";

        Mockito.when(asciiArtServiceClient.getAsciiString(ArgumentMatchers.any())).thenReturn(prettifiedName);

        assertThat(prettyUserPageCreator.getPrettyPage(user)).isEqualTo(prettifiedName + "\r\n" + user.getPhoneNumber());
    }
}