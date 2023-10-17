package com.sap.cc.library.book;

import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String title);
}
