package com.sap.ase.poker.model.deck;

import java.util.List;

public interface CardShuffler {
    List<Card> shuffle(List<Card> cards);
}
