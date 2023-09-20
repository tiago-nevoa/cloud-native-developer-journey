package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Kind;
import com.sap.ase.poker.model.deck.Suit;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoyalFlushTest {

    private final List<Card> royalFlushOne = Arrays.asList(
            new Card(Kind.KING, Suit.DIAMONDS),
            new Card(Kind.SIX, Suit.DIAMONDS),
            new Card(Kind.QUEEN, Suit.DIAMONDS),
            new Card(Kind.SEVEN, Suit.HEARTS),
            new Card(Kind.JACK, Suit.DIAMONDS),
            new Card(Kind.TEN, Suit.DIAMONDS),
            new Card(Kind.ACE, Suit.DIAMONDS)
    );
    private final List<Card> royalFlushTwo = Arrays.asList(
            new Card(Kind.KING, Suit.SPADES),
            new Card(Kind.SIX, Suit.SPADES),
            new Card(Kind.QUEEN, Suit.SPADES),
            new Card(Kind.SEVEN, Suit.HEARTS),
            new Card(Kind.JACK, Suit.SPADES),
            new Card(Kind.TEN, Suit.SPADES),
            new Card(Kind.ACE, Suit.SPADES)
    );

    @Test
    void constructor_shouldThrowException_IfNoRoyalFlushPresent() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.SIX, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FOUR, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
        assertThatThrownBy(() -> new RoyalFlush(cards)).isInstanceOf(InvalidHandException.class);
    }

    @Test
    void constructor_shouldAddRoyalFlushToCards() {
        RoyalFlush result = new RoyalFlush(royalFlushOne);

        assertThat(result.getCards()).contains(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.QUEEN, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.TEN, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.DIAMONDS));
    }

    @Test
    void compareRelevantCards_alwaysReturnsZero() {
        int result = new RoyalFlush(royalFlushOne).compareTo(new RoyalFlush(royalFlushTwo));
        assertThat(result).isZero();
    }
}