package com.sap.cc.books;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/books")
public class BookController  {

    private final BookStorage bookStorage;

    private BookController(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

    @GetMapping
    List<Book> getAllBooks() {
        return bookStorage.getAll();
    }

    @PostMapping
    ResponseEntity<Book> addBook(
            @RequestBody
            Book book) throws URISyntaxException {
        Book createdBook = book;
        bookStorage.save(createdBook);
        UriComponents uriComponents = UriComponentsBuilder.fromPath("/api/v1/books" + "/{id}").buildAndExpand(book.getId());
        URI locationHeaderUri = new URI(uriComponents.getPath());

        return ResponseEntity.created(locationHeaderUri).body(createdBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> getSingle(
            @PathVariable("id")
            Long id) {
        if (id < 1) throw new IllegalArgumentException("Id must not be less than 1");
        Optional<Book> book = bookStorage.get(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book);
        } else {
            throw new NotFoundException();
        }
    }
}
