package com.sap.cc.videostore;

import java.util.ArrayList;
import java.util.List;

class Statement {
    private final String _name;
    private final List<Rental> _rentals = new ArrayList<>();
    private double totalAmount;
    private int frequentRenterPoints;

    public Statement(String name) {
        _name = name;
    }

    public void addRental(Rental arg) {
        _rentals.add(arg);
    }

    public String getName() {
        return _name;
    }

    public String generate() {
        clearTotals();
        String statementText = header();
        statementText += rentalLines();
        statementText += footer();
        return statementText;
    }

    private void clearTotals() {
        totalAmount = 0;
        frequentRenterPoints = 0;
    }

    private String header() {
        return "Rental Record for " + getName() + "\n";
    }

    private String rentalLines() {
        String statementText = "";
        for (Rental rental : _rentals) {
            statementText += rentalLine(rental);
        }
        return statementText;
    }

    private String footer() {
        return "Amount owed is " + totalAmount + "\n" + "You earned " + frequentRenterPoints + " frequent renter points";
    }

    private String rentalLine(Rental rental) {
        frequentRenterPoints += rental.determineFrequentRenterPoints();
        double rentalAmount = rental.determineAmount();
        //show figures for this rental
        totalAmount += rentalAmount;
        return formatRentalLine(rental, rentalAmount);
    }

    private String formatRentalLine(Rental rental, double rentalAmount) {
        return ("\t") + (rental.getMovie().getTitle()) + ("\t") + (rentalAmount) + ("\n");
    }
}
