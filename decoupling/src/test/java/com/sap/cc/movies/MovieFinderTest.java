package com.sap.cc.movies;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import org.mockito.Mockito;

import static com.sap.cc.movies.MovieFixtures.MOVIES;
import static org.assertj.core.api.Assertions.assertThat;

class MovieFinderTest {

    MovieStorage movieStorageMock = Mockito.mock(MovieStorage.class);
    MovieFinder movieFinder = new MovieFinder(movieStorageMock);

    @BeforeEach
    void setUp() {
        Mockito.when(movieFinder.getAllMovies()).thenReturn(MOVIES);
        //movieFinder.setMovieStorage(new MovieStorageStub());
        //MOVIES.forEach(movieFinder.getMovieStorage()::save);
    }

    @Test
    void getAllMoviesShouldReturnAllMovies() {
        List<Movie> allMovies = movieFinder.getAllMovies();

        assertThat(allMovies).hasSameSizeAs(MOVIES);
    }

    @Test
    void findMoviesByDirector_calledWithFrancis_shouldReturnTheGodfatherMovies() {
        List<Movie> francisMovies = movieFinder.findMoviesByDirector("Francis Ford Coppola");

        assertThat(francisMovies).hasSize(2);
        for(Movie movie: francisMovies){
            assertThat(movie.getDirector()).isEqualTo("Francis Ford Coppola");
        }
    }

    @Test
    void findMoviesByDirector_calledWithFrancisAllLowerCase_shouldReturnTheGodfatherMovies() {
        List<Movie> francisMovies = movieFinder.findMoviesByDirector("francis ford coppola");

        assertThat(francisMovies).hasSize(2);
        for(Movie movie: francisMovies){
            assertThat(movie.getDirector()).isEqualTo("Francis Ford Coppola");
        }
    }

    @Test
    void findMoviesByTitle_calledWithThe_ReturnsEveryMovieContainingThe() {
        List<Movie> moviesWithThe = movieFinder.findMoviesByTitle("The");

        assertThat(moviesWithThe).hasSameSizeAs(MOVIES);
    }

    @Test
    void findMoviesByTitle_calledWithGodfather_ReturnsGodfatherMovies() {
        List<Movie> moviesWithGodfather = movieFinder.findMoviesByTitle("Godfather");

        assertThat(moviesWithGodfather).hasSize(2);
        for (Movie movie: moviesWithGodfather){
            assertThat(movie.getTitle().toUpperCase()).contains("Godfather".toUpperCase());
        }
    }
}