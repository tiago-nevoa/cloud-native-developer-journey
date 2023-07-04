package com.sap.cc.videostore;

public class RegularMovie extends Movie {
    public RegularMovie(String name) {
        super(name);
    }

    @Override
    int determineFrequentRenterPoints(int daysRented) {
        return 1;
    }

    @Override
    double determineAmount(int daysRented) {
        //determine amounts for rental line
        double rentalAmount = 0;
        rentalAmount += 2;
        if (daysRented > 2) {
            rentalAmount += (daysRented - 2) * 1.5;
        }
        return rentalAmount;
    }
}
