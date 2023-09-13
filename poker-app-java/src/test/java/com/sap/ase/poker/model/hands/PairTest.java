package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.fixtures.HandFixtures;
import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Kind;
import com.sap.ase.poker.model.deck.Suit;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PairTest {
    @Test
    void constructor_shouldThrowException_IfNoPairPresent() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.NINE, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
        assertThatThrownBy(() -> new Pair(cards)).isInstanceOf(InvalidHandException.class);
    }

    @Test
    void constructor_shouldAddPairToCards() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SIX, Suit.HEARTS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.SPADES)
        );
        Pair result = new Pair(cards);

        assertThat(result.getCards()).contains(
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.SPADES));
    }

    @Test
    void constructor_shouldAddHighestFillerCardToCards() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.HEARTS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.SPADES)
        );
        Pair result = new Pair(cards);

        assertThat(result.getCards()).contains(new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS), new Card(Kind.FIVE, Suit.CLUBS));
    }

    @Test
    void pairOfNinesShouldBeGreaterThanPairOfTwos() {
        Pair pairOfNines = new Pair(HandFixtures.pairOfNines());
        Pair pairOfSevens = new Pair(HandFixtures.pairOfSevens());

        assertThat(pairOfNines.compareTo(pairOfSevens)).isPositive();
        assertThat(pairOfSevens.compareTo(pairOfNines)).isNegative();
    }

    @Test
    void samePairsShouldBeEqual() {
        Pair pairOfNines1 = new Pair(HandFixtures.pairOfNines());
        Pair pairOfNines2 = new Pair(HandFixtures.pairOfNines());

        assertThat(pairOfNines1.compareTo(pairOfNines2)).isZero();
    }
}

