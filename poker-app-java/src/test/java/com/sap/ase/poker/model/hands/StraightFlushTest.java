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

class StraightFlushTest {
    @Test
    void constructor_shouldThrowException_IfNoStraightFlushPresent() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.SIX, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FOUR, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
        assertThatThrownBy(() -> new StraightFlush(cards)).isInstanceOf(InvalidHandException.class);
    }

    @Test
    void constructor_shouldAddStraightFlushToCards() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.SIX, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FOUR, Suit.DIAMONDS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.DIAMONDS)
        );
        StraightFlush result = new StraightFlush(cards);

        assertThat(result.getCards()).contains(
                new Card(Kind.FOUR, Suit.DIAMONDS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.DIAMONDS),
                new Card(Kind.SIX, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.DIAMONDS));
    }


    @Test
    void StraightWithAceIsGreaterThanFourOfAKindWithSeven() {
        StraightFlush straightFlushWithSeven = new StraightFlush(HandFixtures.straightFlushWithSeven());
        StraightFlush straightFlushWithAce = new StraightFlush(HandFixtures.straightFlushWithKing());

        assertThat(straightFlushWithAce.compareTo(straightFlushWithSeven)).isPositive();
        assertThat(straightFlushWithSeven.compareTo(straightFlushWithAce)).isNegative();
    }
}