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

class TwoPairsTest {
    @Test
    void constructor_shouldThrowException_IfNoTwoPairsPresent() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.NINE, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
        assertThatThrownBy(() -> new TwoPairs(cards)).isInstanceOf(InvalidHandException.class);
    }

    @Test
    void constructor_shouldAddTwoPairsToCards() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.SPADES)
        );
        TwoPairs result = new TwoPairs(cards);

        assertThat(result.getCards()).contains(
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.SPADES));
    }

    @Test
    void constructor_shouldAddHighestFillerCardToCards() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.SPADES)
        );
        TwoPairs result = new TwoPairs(cards);

        assertThat(result.getCards()).contains(new Card(Kind.JACK, Suit.DIAMONDS));
    }

    @Test
    void twoPairsOfSevensAndAcesShouldBeGreaterThanTwoPairsOfSevensAndKings() {
        TwoPairs twoPairsOfSevensAndAces = new TwoPairs(HandFixtures.twoPairsOfSevensAndAces());
        TwoPairs twoPairsOfSevensAndKings = new TwoPairs(HandFixtures.twoPairsOfSevensAndKings());

        assertThat(twoPairsOfSevensAndAces.compareTo(twoPairsOfSevensAndKings)).isPositive();
        assertThat(twoPairsOfSevensAndKings.compareTo(twoPairsOfSevensAndAces)).isNegative();
    }

    @Test
    void twoPairsOfAcesAndSevensShouldBeGreaterThanTwoPairsOfAcesAndTwos() {
        TwoPairs twoPairsOfAcesAndSevens = new TwoPairs(HandFixtures.twoPairsOfSevensAndAces());
        TwoPairs twoPairsOfAcesAndTwos = new TwoPairs(HandFixtures.twoPairsOfAcesAndTwos());

        assertThat(twoPairsOfAcesAndSevens.compareTo(twoPairsOfAcesAndTwos)).isPositive();
        assertThat(twoPairsOfAcesAndTwos.compareTo(twoPairsOfAcesAndSevens)).isNegative();
    }
}

