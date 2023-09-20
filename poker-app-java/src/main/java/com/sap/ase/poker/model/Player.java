package com.sap.ase.poker.model;

import com.sap.ase.poker.model.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String id;
    private final String name;
    private int cash;

    private int bet = 0;
    private boolean isActive = false;
    private List<Card> handCards = new ArrayList<>();

    public Player(String id, String name, int cash) {
        this.id = id;
        this.name = name;
        this.cash = cash;
    }

    public String getName() {
        return name;
    }

    public void bet(int bet) {
        this.bet+=bet;
        deductCash(bet);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBet() {
        return bet;
    }

    public void clearBet() {
        bet = 0;
    }

    public int getCash() {
        return cash;
    }

    public void addCash(int amount) {
        cash += amount;
    }

    public void deductCash(int amount) {
        cash -= amount;
    }

    public void setActive() {
        this.isActive = true;
    }

    public void setInactive() {
        this.isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<Card> getHandCards() {
        return this.handCards;
    }

    public void setHandCards(List<Card> handCards) {
        this.handCards = handCards;
    }
}
