package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Suit;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Flush extends Hand {


    private static final int FLUSH_RANK = 6;

    public Flush(List<Card> cards) {
        super(cards);
    }

    @Override
    public int getRank() {
        return FLUSH_RANK;
    }

    @Override
    protected List<Card> findRelevantCards(List<Card> cards) {
        Map<Suit, List<Card>> suitMap = cards.stream().
                collect(Collectors.groupingBy(Card::getSuit));


        for (List<Card> possibleFlush :
                suitMap.values()) {
            if (possibleFlush.size() < 5) {
                continue;
            }
            return sortCardsDescending(possibleFlush).subList(0, 5);
        }

        throw new InvalidHandException("No Flush Found");
    }

    @Override
    protected int compareRelevantCards(Hand hand) {
        return new HighCard(cards).compareRelevantCards(new HighCard(hand.getRelevantCards()));
    }
}
