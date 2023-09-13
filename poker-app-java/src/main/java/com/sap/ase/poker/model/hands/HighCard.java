package com.sap.ase.poker.model.hands;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.sap.ase.poker.model.deck.Card;

public class HighCard extends Hand {

    public static final int HIGH_CARD_RANK = 1;

    public HighCard(List<Card> cards) {
        super(cards);
    }

    @Override
    public int getRank() {
        return HIGH_CARD_RANK;
    }

    @Override
    protected List<Card> findRelevantCards(List<Card> cards) {
        List<Card> restCards =
                cards.stream().filter(card -> !getCards().contains(card)).sorted(Card::compareTo)
                        .collect(Collectors.toList());
        return restCards.subList(restCards.size() - 5, restCards.size());
    }

    @Override
    protected int compareRelevantCards(Hand hand) {
        List<Card> relevantCards1 = this.getRelevantCards();
        List<Card> relevantCards2 = hand.getRelevantCards();

        //ensure descending ordering of cards (e.g. ACE, KING, ...
        Collections.sort(relevantCards1, Collections.reverseOrder(Card::compareTo));
        Collections.sort(relevantCards2, Collections.reverseOrder(Card::compareTo));

        for (int i = 0; i < relevantCards1.size(); i++) {
            Card card1 = relevantCards1.get(i);
            Card card2 = relevantCards2.get(i);

            int cardComparisonResult = card1.compareTo(card2);
            if (cardComparisonResult != 0) {
                return cardComparisonResult;
            }
        }

        return 0;
    }
}
