package com.sap.cc.movies;

import java.util.List;
import java.util.Optional;

public interface MovieStorage {
    Movie save(Movie movie);

    Optional<Movie> get(Long id);

    List<Movie> getAll();

    void deleteAll();

    void delete(Long id);
}
