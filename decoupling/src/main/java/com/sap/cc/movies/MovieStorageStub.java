package com.sap.cc.movies;

import java.util.List;
import java.util.Optional;

import static com.sap.cc.movies.MovieFixtures.MOVIES;
public class MovieStorageStub implements MovieStorage{

    List<Movie> movies = MOVIES;
    @Override
    public Movie save(Movie movie) {
        return null;
    }

    @Override
    public Optional<Movie> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Movie> getAll() {
        return movies;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void delete(Long id) {

    }
}
