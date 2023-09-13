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

class FlushTest {
    @Test
    void constructor_shouldThrowException_IfNoFlushPresent() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.NINE, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
        assertThatThrownBy(() -> new Flush(cards)).isInstanceOf(InvalidHandException.class);
    }

    @Test
    void constructor_shouldAddFlushToCards() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.SIX, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FOUR, Suit.DIAMONDS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.DIAMONDS)
        );
        Flush result = new Flush(cards);

        assertThat(result.getCards()).contains(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.SIX, Suit.DIAMONDS),
                new Card(Kind.FOUR, Suit.DIAMONDS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.DIAMONDS));
    }


    @Test
    void FlushWithAceIsGreaterThanFourOfAKindWithSeven() {
        Flush FlushWithSeven = new Flush(HandFixtures.FlushWithSeven());
        Flush FlushWithAce = new Flush(HandFixtures.FlushWithKing());

        assertThat(FlushWithAce.compareTo(FlushWithSeven)).isPositive();
        assertThat(FlushWithSeven.compareTo(FlushWithAce)).isNegative();
    }
}