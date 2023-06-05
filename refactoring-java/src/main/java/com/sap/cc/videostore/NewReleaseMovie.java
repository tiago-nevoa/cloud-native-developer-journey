package com.sap.cc.videostore;

public class NewReleaseMovie extends Movie {
    public NewReleaseMovie(String name) {
        super(name);
    }

    @Override
    int determineFrequentRenterPoints(int daysRented) {
        // add frequent renter points
        int frequentPoints = 1;
        // add bonus for a two day new release rental
        if (daysRented > 1) {
            frequentPoints++;
        }
        return frequentPoints;
    }

    @Override
    double determineAmount(int daysRented) {
        //determine amounts for rental line
        double rentalAmount = 0;
        rentalAmount += daysRented * 3;
        return rentalAmount;
    }
}
