package com.sap.ase.poker.model.deck;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards;
    private final CardShuffler cardShuffler;
    private final List<Card> pokerCardsSupply;

    public Deck(List<Card> pokerCardsSupply, CardShuffler cardShuffler) {
        this.pokerCardsSupply = new ArrayList<>(pokerCardsSupply);
        this.cards = new ArrayList<>(pokerCardsSupply);
        this.cardShuffler = cardShuffler;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card draw() {
        if (cards.isEmpty()) {
            throw new OutOfCardsException("No cards left to draw.");
        }
        return cards.remove(0);
    }

    public void shuffle() {
        cards = cardShuffler.shuffle(pokerCardsSupply);
    }
}
