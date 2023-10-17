package com.sap.cc.library.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    void clearDb() {
        repository.deleteAll();
    }

    @Test
    void findAllBooks_should_be_empty() {
        List<Book> books = repository.findAll();
        assertThat(books).isEmpty();
    }

    @Test
    void whenSaveBook_books_should_not_be_empty() {
        Book book = BookFixtures.cleanCode();
        //book.setId(1L);
        repository.save(book);
        List<Book> books = repository.findAll();
        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getAuthor()).isNotNull();
    }

    @Test
    void whenFindAllBooksNotEmpty_should_retrieveAllBooks() {
        Book book1 = BookFixtures.cleanCode();
        //book1.setId(1L);
        repository.save(book1);

        Book book2 = BookFixtures.refactoring();
        //book2.setId(2L);
        repository.save(book2);

        List<Book> books = repository.findAll();
        assertThat(books).hasSize(2);
    }

    @Test
    void whenRetrieveById_should_retrieve_only_one() {

        Book book2 = BookFixtures.refactoring();
        //book2.setId(2L);
        repository.save(book2);

        List<Book> books = repository.findAll();
        assertThat(books).hasSize(1);
    }

    @Test
    void whenRetrieveById_should_had_correct_title() {
        Book book2 = BookFixtures.refactoring();
        //book2.setId(2L);
        repository.save(book2);

        List<Book> books = repository.findAll();
        assertThat(books.get(0).getTitle()).isEqualTo("Refactoring");
    }

    @Test
    void findBookByTitle() {
        Book book1 = BookFixtures.designPatterns();
        repository.save(book1);
        Book book2 = BookFixtures.domainDrivenDesign();
        repository.save(book2);

        Book source = repository.findByTitle("Design Patterns");

        assertThat(source.getTitle()).isEqualTo(book1.getTitle());
    }

}
