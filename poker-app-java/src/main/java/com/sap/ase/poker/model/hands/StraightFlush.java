package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Suit;

import java.util.List;
import java.util.stream.Collectors;

public class StraightFlush extends Hand {

    private static final int STRAIGHT_FLUSH_RANK = 9;

    public StraightFlush(List<Card> cards) {
        super(cards);
    }

    @Override
    public int getRank() {
        return STRAIGHT_FLUSH_RANK;
    }

    @Override
    protected List<Card> findRelevantCards(List<Card> cards) {
        try {
            Flush flush = new Flush(cards);
            Suit suit = flush.getRelevantCards().get(0).getSuit();

            List<Card> cardsOfSameSuit =
                    cards.stream().filter(card -> card.getSuit().equals(suit)).collect(Collectors.toList());

            Straight straight = new Straight(cardsOfSameSuit);
            return straight.getRelevantCards();
        } catch (InvalidHandException e) {
            throw new InvalidHandException("No straight flush present");
        }
    }

    @Override
    protected int compareRelevantCards(Hand hand) {
        Card highestCard = hand.getRelevantCards().get(0);
        return this.getRelevantCards().get(0).compareTo(highestCard);
    }
}
