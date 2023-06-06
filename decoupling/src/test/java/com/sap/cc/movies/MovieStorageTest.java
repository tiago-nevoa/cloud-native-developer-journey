package com.sap.cc.movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MovieStorageTest {

    private InMemoryMovieStorage movieStorage = new InMemoryMovieStorage();

    private final Movie SHAWSHANK_REDEMPTION = new Movie("The Shawshank Redemption", "Frank Darabont");
    private final Movie FIGHT_CLUB = new Movie("Fight Club", "David Fincher");

    @BeforeEach
    public void beforeEach() {
        movieStorage.deleteAll();
    }

    @Test
    public void getNonExistingMovie_movieNotPresent() {
        Optional<Movie> returnedMovie = movieStorage.get(1L);
        assertThat(returnedMovie.isPresent()).isEqualTo(false);
    }

    @Test
    public void saveOneMovie_returnsTheMovie() {
        Movie returnedMovie = movieStorage.save(SHAWSHANK_REDEMPTION);

        assertThat(returnedMovie.getTitle()).isEqualTo(SHAWSHANK_REDEMPTION.getTitle());
        assertThat(returnedMovie.getDirector()).isEqualTo(SHAWSHANK_REDEMPTION.getDirector());
        assertThat(returnedMovie.getId()).isEqualTo(1L);
    }

    @Test
    public void saveTwoMovies_returnsTheSecondMovie() {
        movieStorage.save(SHAWSHANK_REDEMPTION);

        Movie returnedMovie = movieStorage.save(FIGHT_CLUB);

        assertThat(returnedMovie.getTitle()).isEqualTo(FIGHT_CLUB.getTitle());
        assertThat(returnedMovie.getDirector()).isEqualTo(FIGHT_CLUB.getDirector());
        assertThat(returnedMovie.getId()).isEqualTo(2L);
    }

    @Test
    public void saveOneMovieTryToForceId_ignoreTheForcedId() {
        Movie movie = SHAWSHANK_REDEMPTION;
        movie.setId(10L);
        Movie returnedMovie = movieStorage.save(movie);

        assertThat(returnedMovie.getId()).isEqualTo(1L);
    }

    @Test
    public void oneMovie_getById_returnsTheMovie() {
        movieStorage.save(SHAWSHANK_REDEMPTION);

        Optional<Movie> returnedMovie = movieStorage.get(1L);

        assertThat(returnedMovie.isPresent()).isEqualTo(true);
        assertThat(returnedMovie.get().getId()).isEqualTo(1L);
        assertThat(returnedMovie.get().getTitle()).isEqualTo(SHAWSHANK_REDEMPTION.getTitle());
        assertThat(returnedMovie.get().getDirector()).isEqualTo(SHAWSHANK_REDEMPTION.getDirector());
    }

    @Test
    public void oneMovie_updateTitle_returnsUpdatedMovie() {
        Movie returnedMovie = movieStorage.save(SHAWSHANK_REDEMPTION);
        assertThat(returnedMovie.getId()).isEqualTo(1L);

        final String newTitle = "Die Verurteilten";
        returnedMovie.setTitle(newTitle);

        returnedMovie = movieStorage.save(returnedMovie);

        assertThat(returnedMovie.getTitle()).isEqualTo(newTitle);
        assertThat(returnedMovie.getId()).isEqualTo(1L);
    }

    @Test
    public void noMovies_getAll_returnsEmptyList() {
        List<Movie> returnedMovies = movieStorage.getAll();
        assertThat(returnedMovies.size()).isEqualTo(0);
    }

    @Test
    public void oneMovie_getAll_returnsTheMovie() {
        movieStorage.save(SHAWSHANK_REDEMPTION);

        List<Movie> returnedMovies = movieStorage.getAll();
        assertThat(returnedMovies.size()).isEqualTo(1);
        assertThat(returnedMovies.iterator().next().getTitle()).isEqualTo(SHAWSHANK_REDEMPTION.getTitle());
        assertThat(returnedMovies.iterator().next().getId()).isEqualTo(1L);
    }

    @Test
    public void twoMovies_getAll_returnsTwoMovies() {
        movieStorage.save(SHAWSHANK_REDEMPTION);
        movieStorage.save(FIGHT_CLUB);

        List<Movie> returnedMovies = movieStorage.getAll();
        assertThat(returnedMovies.size()).isEqualTo(2);
    }

    @Test
    public void twoMovies_deleteFirst_returnsTheSecond() {
        movieStorage.save(SHAWSHANK_REDEMPTION);
        movieStorage.save(FIGHT_CLUB);
        movieStorage.delete(1L);

        List<Movie> returnedMovies = movieStorage.getAll();
        assertThat(returnedMovies.size()).isEqualTo(1);
        assertThat(returnedMovies.iterator().next().getTitle()).isEqualTo(FIGHT_CLUB.getTitle());
        assertThat(returnedMovies.iterator().next().getDirector()).isEqualTo(FIGHT_CLUB.getDirector());
        assertThat(returnedMovies.iterator().next().getId()).isEqualTo(2L);
    }

    @Test
    public void twoMovies_deleteAll_returnsEmptyList() {
        movieStorage.save(SHAWSHANK_REDEMPTION);
        movieStorage.save(FIGHT_CLUB);
        movieStorage.deleteAll();

        List<Movie> returnedMovies = movieStorage.getAll();
        assertThat(returnedMovies.size()).isEqualTo(0);
    }
}
