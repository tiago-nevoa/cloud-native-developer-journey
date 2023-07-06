package com.sap.cc.movies;

import java.util.*;


public class InMemoryMovieStorage implements MovieStorage {

    private final Map<Long, Movie> movies = new HashMap<>();

    public InMemoryMovieStorage() {
    }

    @Override
    public Movie save(final Movie movie) {
        boolean isIdEmptyOrNonexistent = movie.getId() == null || !movies.containsKey(movie.getId());

        if (isIdEmptyOrNonexistent) {
            Long id = movies.size() + 1L;
            movie.setId(id);
        }

        movies.put(movie.getId(), movie);
        return movie;
    }

    @Override
    public Optional<Movie> get(Long id) {
        return Optional.ofNullable(movies.get(id));
    }

    @Override
    public List<Movie> getAll() {
        return new ArrayList<>(movies.values());
    }

    @Override
    public void deleteAll() {
        movies.clear();
    }

    @Override
    public void delete(Long id) {
        movies.remove(id);
    }
}