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

public class FullHouseTest {
    @Test
    void constructor_shouldThrowException_IfNoFullHousePresent() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.NINE, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
        assertThatThrownBy(() -> new FullHouse(cards)).isInstanceOf(InvalidHandException.class);
    }

    @Test
    void constructor_shouldAddFullHouseToCards() {
        List<Card> cards = Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.SPADES),
                new Card(Kind.KING, Suit.DIAMONDS)
        );
        FullHouse result = new FullHouse(cards);

        assertThat(result.getCards()).contains(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.CLUBS),
                new Card(Kind.JACK, Suit.SPADES));
    }


    @Test
    void fullHouseWithAcesIsGreaterThanFullHouseWithSevens() {
        FullHouse threeOfAKindOfSevens = new FullHouse(HandFixtures.fullHouseWithSevens());
        FullHouse threeOfAKindOfAces = new FullHouse(HandFixtures.fullHouseWithAces());

        assertThat(threeOfAKindOfAces.compareTo(threeOfAKindOfSevens)).isPositive();
        assertThat(threeOfAKindOfSevens.compareTo(threeOfAKindOfAces)).isNegative();
    }
}
