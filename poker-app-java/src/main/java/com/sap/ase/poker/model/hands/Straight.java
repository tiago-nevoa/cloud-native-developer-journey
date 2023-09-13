package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;

import java.util.List;
import java.util.stream.Collectors;

public class Straight extends Hand {
    private final static int STRAIGHT_RANK = 5;

    public Straight(List<Card> cards) {
        super(cards);
    }

    @Override
    public int getRank() {
        return STRAIGHT_RANK;
    }

    @Override
    protected List<Card> findRelevantCards(List<Card> cards) {
        List<Card> sorted = sortCardsAscending(cards);
        if (isFiveCardStraight(cards)) {
            return cards;
        }
        for (Card card : sorted) {
            List<Card> oneCardRemoved =
                    sorted.stream().filter(card::notEquals).collect(Collectors.toList());

            if (isFiveCardStraight(oneCardRemoved)) {
                return oneCardRemoved;
            }
            for (Card card2 : oneCardRemoved) {
                List<Card> possibleHand =
                        oneCardRemoved.stream().filter(card2::notEquals).collect(Collectors.toList());

                if (isFiveCardStraight(possibleHand)) {
                    return possibleHand;
                }
            }
        }
        throw new InvalidHandException("No straight present");
    }

    private boolean isFiveCardStraight(List<Card> cards) {
        return cards.size() == 5 && isStraight(cards);
    }


    private boolean isStraight(List<Card> possibleHand) {
        List<Card> cards = sortCardsDescending(possibleHand);

        int previousRank = -1;
        for (Card card : cards) {
            int rank = card.getKind().getRank();
            if (previousRank == -1) {
                previousRank = rank;
                continue;
            }
            int difference = previousRank - rank;

            if (difference != 1) {
                return false;
            }
            previousRank = rank;
        }
        return true;
    }

    @Override
    protected int compareRelevantCards(Hand hand) {
        return new HighCard(cards).compareRelevantCards(new HighCard(hand.getRelevantCards()));
    }
}
