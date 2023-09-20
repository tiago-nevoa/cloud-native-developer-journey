package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Kind;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TwoPairs extends Hand {

    public static final int TWO_PAIRS_RANK = 3;

    public TwoPairs(List<Card> cards) {
        super(cards);
    }

    @Override
    public int getRank() {
        return TWO_PAIRS_RANK;
    }

    @Override
    protected List<Card> findRelevantCards(List<Card> cards) {
        return findTwoPairs(cards);
    }

    @Override
    protected int compareRelevantCards(Hand hand) {
        Integer highestPairRank = this.getRelevantCards().stream().map(card -> card.getKind().getRank())
                .max(Integer::compareTo).get();
        Integer otherHighestPairRank =
                hand.getRelevantCards().stream().map(card -> card.getKind().getRank())
                        .max(Integer::compareTo).get();

        int highestPairComparison = highestPairRank - otherHighestPairRank;
        if (highestPairComparison != 0) {
            return highestPairComparison;
        } else {
            Integer lowestPairRank =
                    this.getRelevantCards().stream().map(card -> card.getKind().getRank())
                            .min(Integer::compareTo).get();
            Integer otherLowestPairRank =
                    hand.getRelevantCards().stream().map(card -> card.getKind().getRank())
                            .min(Integer::compareTo).get();

            return lowestPairRank - otherLowestPairRank;
        }
    }

    private List<Card> findTwoPairs(List<Card> cards) {
        Map<Kind, List<Card>> kindGroups = cards.stream().collect(Collectors.groupingBy(Card::getKind));
        List<Card> twoPairs =
                kindGroups.values().stream().filter(group -> group.size() == 2).flatMap(Collection::stream)
                        .collect(Collectors.toList());

        if (twoPairs.size() != 4) {
            throw new InvalidHandException("Two PairsNotFound");
        }

        return twoPairs;
    }
}
