package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Kind;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Pair extends Hand {

    public static final int PAIR_RANK = 2;

    public Pair(List<Card> cards) {
        super(cards);
    }

    @Override
    public int getRank() {
        return PAIR_RANK;
    }

    @Override
    protected List<Card> findRelevantCards(List<Card> cards) {
        return findPair(cards);
    }

    @Override
    protected int compareRelevantCards(Hand hand) {
        Integer pairRank = this.getRelevantCards().stream().map(card -> card.getKind().getRank())
                .max(Integer::compareTo).get();
        Integer otherPairRank = hand.getRelevantCards().stream().map(card -> card.getKind().getRank())
                .max(Integer::compareTo).get();

        return pairRank - otherPairRank;
    }

    private List<Card> findPair(List<Card> cards) {
        Map<Kind, List<Card>> kindGroups = cards.stream().collect(Collectors.groupingBy(Card::getKind));
        Optional<List<Card>> pair =
                kindGroups.values().stream().filter(group -> group.size() == 2).findFirst();


        return pair.orElseThrow(() -> new InvalidHandException("Pair Not found"));
    }
}
