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

public class ThreeOfAKindTest {
    @Test
    void constructor_shouldThrowException_IfNoThreeOfAKindPresent() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.NINE, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
        assertThatThrownBy(() -> new ThreeOfAKind(cards)).isInstanceOf(InvalidHandException.class);
    }

    @Test
    void constructor_shouldAddThreeOfAKindToCards() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.CLUBS),
                new Card(Kind.FIVE, Suit.SPADES),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.KING, Suit.DIAMONDS)
        );
        ThreeOfAKind result = new ThreeOfAKind(cards);

        assertThat(result.getCards()).contains(
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.CLUBS));
    }

    @Test
    void constructor_shouldAddHighestFillerCardToCards() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.CLUBS),
                new Card(Kind.FIVE, Suit.SPADES),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.KING, Suit.DIAMONDS)
        );
        ThreeOfAKind result = new ThreeOfAKind(cards);

        assertThat(result.getCards()).contains(new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.DIAMONDS));
    }

    @Test
    void threeOfAKindOfAcesIsGreaterThanFourOfAKindOfSevens() {
        ThreeOfAKind threeOfAKindOfSevens = new ThreeOfAKind(HandFixtures.threeOfAKindOfSevens());
        ThreeOfAKind threeOfAKindOfAces = new ThreeOfAKind(HandFixtures.threeOfAKindOfAces());

        assertThat(threeOfAKindOfAces.compareTo(threeOfAKindOfSevens)).isPositive();
        assertThat(threeOfAKindOfSevens.compareTo(threeOfAKindOfAces)).isNegative();
    }

    @Test
    void sameHandsShouldBeEqual() {
        ThreeOfAKind threeOfAKindOfSevens1 = new ThreeOfAKind(HandFixtures.threeOfAKindOfSevens());
        ThreeOfAKind threeOfAKindOfSevens2 = new ThreeOfAKind(HandFixtures.threeOfAKindOfSevens());

        assertThat(threeOfAKindOfSevens1.compareTo(threeOfAKindOfSevens2)).isZero();
    }
}
