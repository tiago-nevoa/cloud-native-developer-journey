package com.sap.cc.movies;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieStoreTest {

    private MovieStore movieStore;

    @BeforeEach
    void setUp() {
        this.movieStore = new MovieStore(new InMemoryMovieStorage());
    }

    @Test
    void exactMatch() {
        givenoneOrMoreMoviesTitled("Forrest Gump", "Titanic");
        var list = whenIsSearched("Forrest Gump");
        var result = thenTheResultsListConsistsOf(list);
        Assertions.assertNotNull(list, "The object should not be null");
        Assertions.assertEquals("Forrest Gump ", result, "Values are not equal");

    }

    @Test
    void partialMatch() {
        givenoneOrMoreMoviesTitled("The Godfather", "City of God");
        var list = whenIsSearched("God");
        var result = thenTheResultsListConsistsOf(list);
        Assertions.assertEquals("The Godfather City of God ", result, "Values are not equal");
    }

    private String thenTheResultsListConsistsOf(List<Movie> list) {
        StringBuilder resultBuilder = new StringBuilder();
        list.forEach(item -> resultBuilder.append(item.getTitle()).append(" "));

        return resultBuilder.toString();
    }

    private List<Movie> whenIsSearched(String movie) {
        return movieStore.search(movie);
    }

    private void givenoneOrMoreMoviesTitled(String... movies) {
        var count = 1;
        for (String movie : movies) {
            Movie m = new Movie(movie, "director" + count++);
            movieStore.addMovie(m);
        }
    }
}