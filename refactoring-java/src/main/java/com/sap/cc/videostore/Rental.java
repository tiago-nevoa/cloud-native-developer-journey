package com.sap.cc.videostore;

class Rental {
    private final Movie movie;
    private final int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public Movie getMovie() {
        return movie;
    }

    public double determineAmount() {
        return movie.determineAmount(daysRented);
    }

    public int determineFrequentRenterPoints() {
        return movie.determineFrequentRenterPoints(daysRented);
    }
}