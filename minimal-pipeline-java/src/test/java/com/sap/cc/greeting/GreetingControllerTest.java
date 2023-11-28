package com.sap.cc.greeting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getHello_withoutParameter_returnsHelloWorld() throws Exception {
        this.mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World")));
    }

    @Test
    void getHello_withParameter_returnsCustomGreeting() throws Exception {
        this.mockMvc.perform(get("/hello").param("name", "Neo"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello Neo")));
    }

    @Test
    void getHello_withNumbersInParameter_raisesBadRequest() throws Exception {
        this.mockMvc.perform(get("/hello").param("name", "Ne0"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Name must not contain numbers!"));
    }
}
