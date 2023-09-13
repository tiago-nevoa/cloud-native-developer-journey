package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Kind;
import com.sap.ase.poker.model.deck.Suit;

import java.util.Arrays;
import java.util.List;

public class RoyalFlush extends Hand {
    private static final int ROYAL_FLUSH_RANK = 10;

    public RoyalFlush(List<Card> cards) {
        super(cards);
    }

    @Override
    public int getRank() {
        return ROYAL_FLUSH_RANK;
    }

    @Override
    protected List<Card> findRelevantCards(List<Card> cards) {
        StraightFlush straightFlush;
        try {
            straightFlush = new StraightFlush(cards);
        } catch (InvalidHandException exception) {
            throw new InvalidHandException("No royal flush present");
        }
        List<Card> relevantCards = straightFlush.getRelevantCards();
        if (cardsContainOnlyRoyalKinds(relevantCards)) {
            return relevantCards;
        }
        throw new InvalidHandException("No royal flush present");
    }

    private boolean cardsContainOnlyRoyalKinds(List<Card> relevantCards) {
        Suit suit = relevantCards.get(0).getSuit();

        return relevantCards.containsAll(Arrays.asList(
                new Card(Kind.ACE, suit),
                new Card(Kind.KING, suit),
                new Card(Kind.QUEEN, suit),
                new Card(Kind.JACK, suit),
                new Card(Kind.TEN, suit)
        ));
    }

    @Override
    protected int compareRelevantCards(Hand hand) {
        return 0;
    }
}
