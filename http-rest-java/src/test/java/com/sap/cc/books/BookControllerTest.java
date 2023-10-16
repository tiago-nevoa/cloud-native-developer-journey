package com.sap.cc.books;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookStorage storage;

    @BeforeEach
    public void beforeEach() {
        storage.deleteAll();
    }

    @Test
    void getAll_noBooks_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/api/v1/books")).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    void addBook_returnsCreatedBook() throws Exception {
        var response = createBook("author_value");

        Book myBook = objectMapper.readValue(response.getContentAsString(), Book.class);

        assertThat(response.getHeader("location"), is("/api/v1/books/" + myBook.getId()));
    }

    @Test
    void addBookAndGetSingle_returnsBook() throws Exception {
        var response = createBook("author_2");
        System.out.println(response.getHeader("location"));
        System.out.println(response.getContentAsString());

        mockMvc.perform(get(response.getHeader("location"))).andExpect(status().isOk())
                .andExpect(content().json(response.getContentAsString()));
    }

    MockHttpServletResponse createBook(String author) throws Exception {
        Book book = new Book();
        book.setAuthor(author);

        String jsonBody = objectMapper.writeValueAsString(book);

        var response =
                mockMvc.perform(post("/api/v1/books").contentType(MediaType.APPLICATION_JSON).content(jsonBody)).andExpect(status().is(201))
                        .andExpect(jsonPath("$.author", is(author))).andReturn().getResponse();
        return response;
    }

    @Test
    void getSingle_noBooks_returnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/books/1")).andExpect(status().isNotFound());
    }

    @Test
    void addMultipleAndGetAll_returnsAddedBooks() throws Exception {

        createBook("SpringBoot");
        var response = mockMvc.perform(get("/api/v1/books")).andExpect(status().isOk()).andReturn().getResponse();
        List<Book> books = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Book>>() {
        });
        assertEquals(1, books.size());
        assertEquals("SpringBoot", books.get(0).getAuthor());

        createBook("Java17");
        response = mockMvc.perform(get("/api/v1/books")).andExpect(status().isOk()).andReturn().getResponse();
        books = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Book>>() {
        });
        assertEquals(2, books.size());
    }

    @Test
    void getSingle_idLessThanOne_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/books/0")).andExpect(status().isBadRequest());
    }
}
