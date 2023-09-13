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

class StraightTest {
    @Test
    void constructor_shouldThrowException_IfNoStraightPresent() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.NINE, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
        assertThatThrownBy(() -> new Straight(cards)).isInstanceOf(InvalidHandException.class);
    }

    @Test
    void constructor_shouldAddStraightToCards() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.SIX, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FOUR, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
        Straight result = new Straight(cards);

        assertThat(result.getCards()).contains(
                new Card(Kind.SIX, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.HEARTS),
                new Card(Kind.FOUR, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS));
    }


    @Test
    void StraightWithAceIsGreaterThanFourOfAKindWithSeven() {
        Straight straightWithSeven = new Straight(HandFixtures.straightWithSeven());
        Straight straightWithAce = new Straight(HandFixtures.straightWithAce());

        assertThat(straightWithAce.compareTo(straightWithSeven)).isPositive();
        assertThat(straightWithSeven.compareTo(straightWithAce)).isNegative();
    }
}