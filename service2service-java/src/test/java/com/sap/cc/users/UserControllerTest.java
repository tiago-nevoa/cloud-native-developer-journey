package com.sap.cc.users;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    private static final String API_V1_USERS_PATH = "/api/v1/users/pretty";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserStorage userStorage;

    @MockBean
    private PrettyUserPageCreator prettyUserPageCreator;

    @Test
    void printPrettyPage_returnsUserAttributes() throws Exception {
        Optional<User> optionalUser = Optional.of(new User("someName", "someNumber", "2"));

        when(userStorage.retrieveUserById(2L)).thenReturn(optionalUser);
        when(prettyUserPageCreator.getPrettyPage(optionalUser.get())).thenReturn("somePrettyName" + System.lineSeparator() + "somePrettyNumber");

        MockHttpServletResponse response = this.mockMvc.perform(get(API_V1_USERS_PATH + "/2")).andReturn().getResponse();

        assertThat(response.getContentAsString(), containsString("somePrettyName"));
        assertThat(response.getContentAsString(), containsString("somePrettyNumber"));
    }

    @Test
    void printPrettyPageThrowsIAE_whenIdIsLessThanOne() {
        ServletException nestedServletException = assertThrows(ServletException.class,
                () -> this.mockMvc.perform(get(API_V1_USERS_PATH + "/0")).andReturn().getResponse());

        Throwable cause = nestedServletException.getCause();
        assertThat(cause, instanceOf(IllegalArgumentException.class));
    }
}
