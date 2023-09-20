package com.sap.ase.poker.model.deck;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static com.sap.ase.poker.model.deck.Kind.*;
import static com.sap.ase.poker.model.deck.Suit.*;

@Component
public class PokerCardsSupplier implements Supplier<List<Card>> {
    public List<Card> get() {
        List<Card> pokerCards = new ArrayList<>();
        List<Suit> suits = Arrays.asList(DIAMONDS, SPADES, CLUBS, HEARTS);
        List<Kind> kinds = Arrays.asList(TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE);

        for (Suit suit : suits) {
            for (Kind kind : kinds) {
                pokerCards.add(new Card(kind, suit));
            }
        }

        return pokerCards;
    }
}
