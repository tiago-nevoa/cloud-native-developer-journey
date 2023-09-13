package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Kind;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FullHouse extends Hand {

    private static final int FULL_HOUSE_RANK = 7;

    public FullHouse(List<Card> cards) {
        super(cards);
    }

    @Override
    public int getRank() {
        return FULL_HOUSE_RANK;
    }

    @Override
    protected List<Card> findRelevantCards(List<Card> cards) {
        return findFullHouse(cards);
    }

    @Override
    protected int compareRelevantCards(Hand hand) {
        ThreeOfAKind threeOfAKind = new ThreeOfAKind(cards);
        ThreeOfAKind otherThreeOfAKind = new ThreeOfAKind(hand.getRelevantCards());

        return threeOfAKind.compareTo(otherThreeOfAKind);
    }

    private List<Card> findFullHouse(List<Card> cards) {
        Map<Kind, List<Card>> kindGroups = cards.stream().collect(Collectors.groupingBy(Card::getKind));
        List<Card> threeOfAKind =
                findHighestTreeOfAKind(kindGroups)
                        .orElseThrow(() -> new InvalidHandException("FullHouse Not Found"));

        kindGroups.remove(threeOfAKind.get(0).getKind());

        List<Card> pair =
                findHighestPair(kindGroups)
                        .orElseThrow(() -> new InvalidHandException("FullHouse Not Found"));

        threeOfAKind.addAll(pair.subList(0, 2));
        return threeOfAKind;
    }

    private Optional<List<Card>> findHighestPair(Map<Kind, List<Card>> kindGroups) {
        return kindGroups.values().stream().filter(group -> group.size() >= 2)
                .max(Comparator.comparing(list -> list.get(0)));
    }

    private Optional<List<Card>> findHighestTreeOfAKind(Map<Kind, List<Card>> kindGroups) {
        return kindGroups.values().stream().filter(group -> group.size() == 3)
                .max(Comparator.comparing(list -> list.get(0)));
    }
}
