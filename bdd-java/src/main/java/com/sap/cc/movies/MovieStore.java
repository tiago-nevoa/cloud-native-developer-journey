package com.sap.cc.movies;

import java.util.ArrayList;
import java.util.List;

public class MovieStore {
    private final InMemoryMovieStorage movieStorage;

    public MovieStore(InMemoryMovieStorage movieStorage) {
        this.movieStorage = movieStorage;
    }

    public void addMovie(Movie movie) {
        movieStorage.save(movie);
    }

    public List<Movie> search(String query) {
        var storage = movieStorage.getAll();
        ArrayList<Movie> searchList = new ArrayList<>();
        for (Movie item : storage) {
            if (item.getTitle().contains(query)) {
                searchList.add(item);
            }
        }
        return searchList;
    }
}
