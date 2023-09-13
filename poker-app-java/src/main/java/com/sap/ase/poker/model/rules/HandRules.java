package com.sap.ase.poker.model.rules;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.hands.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Component
public class HandRules {

    public static final List<Function<List<Card>, Hand>> CREATION_FUNCTIONS =
            Arrays.asList(
                    RoyalFlush::new,
                    StraightFlush::new,
                    FourOfAKind::new,
                    FullHouse::new,
                    Flush::new,
                    Straight::new,
                    ThreeOfAKind::new,
                    TwoPairs::new,
                    Pair::new);

    public Hand findBestHand(List<Card> handCards) {
        if (handCards.size() != 7) {
            throw new InvalidAmountOfCardsException("7 cards needed");
        }

        return findHandCombinations(handCards);
    }

    private Hand findHandCombinations(List<Card> cards) {

        for (Function<List<Card>, Hand> createHand : CREATION_FUNCTIONS) {
            try {
                return createHand.apply(cards);
            } catch (InvalidHandException ignored) {
            }
        }
        return new HighCard(cards);
    }
}
