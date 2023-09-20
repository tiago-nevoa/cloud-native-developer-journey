package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Kind;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FourOfAKind extends Hand {

    public static final int FOUR_OF_A_KIND_RANK = 8;

    public FourOfAKind(List<Card> cards) {
        super(cards);
    }

    @Override
    public int getRank() {
        return FOUR_OF_A_KIND_RANK;
    }

    @Override
    protected List<Card> findRelevantCards(List<Card> cards) {
        return findFourOfAKind(cards);
    }

    @Override
    protected int compareRelevantCards(Hand hand) {
        int rank = this.getRelevantCards().get(0).getKind().getRank();
        int otherRank = hand.getRelevantCards().get(0).getKind().getRank();
        return rank - otherRank;
    }

    private List<Card> findFourOfAKind(List<Card> cards) {
        Map<Kind, List<Card>> kindGroups = cards.stream().collect(Collectors.groupingBy(Card::getKind));
        Optional<List<Card>> quadruple =
                kindGroups.values().stream().filter(group -> group.size() == 4).findFirst();

        return quadruple.orElseThrow(() -> new InvalidHandException("FourOfAKind Not Found"));
    }
}
