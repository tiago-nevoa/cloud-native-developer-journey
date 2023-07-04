package com.sap.cc.videostore;

public abstract class Movie {
    private final String title;

    public Movie(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    abstract int determineFrequentRenterPoints(int daysRented);

    abstract double determineAmount(int daysRented);
}
