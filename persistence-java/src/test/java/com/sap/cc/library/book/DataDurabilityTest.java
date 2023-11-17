package com.sap.cc.library.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DataDurabilityTest {

    @Autowired
    private BookRepository repository;

    @Test
    void populateDb(){
        Book book1 = BookFixtures.designPatterns();
        repository.save(book1);
        Book book2 = BookFixtures.domainDrivenDesign();
        repository.save(book2);
        Book book3 = BookFixtures.refactoring();
        repository.save(book3);
        Book book4 = BookFixtures.cleanCode();
        repository.save(book4);
        Book book5 = BookFixtures.modernOperatingSystems();
        repository.save(book5);

        List<Book> books = repository.findAll();

        assertThat(books).hasSizeGreaterThanOrEqualTo(5);
    }

    @Test
    void isDbPopulated(){
        List<Book> books = repository.findAll();
        assertThat(books).hasSizeGreaterThanOrEqualTo(5);
    }
}
