package com.sap.ase.poker.model.deck;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RandomCardShuffler implements CardShuffler {

    @Override
    public List<Card> shuffle(List<Card> cards) {
        ArrayList<Card> shuffled = new ArrayList<>(cards);
        Collections.shuffle(shuffled);
        return new ArrayList<>(shuffled);
    }
}
