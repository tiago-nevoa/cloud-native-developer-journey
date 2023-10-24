package com.sap.cc.books;

import java.util.List;
import java.util.Optional;

public interface BookStorage {

    public Book save(Book book);

    public Optional<Book> get(Long id);

    public List<Book> getAll();

    public void delete(Long id);

    public void deleteAll();

}
