package com.sap.ase.poker.fixtures;

import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Kind;
import com.sap.ase.poker.model.deck.Suit;

import java.util.Arrays;
import java.util.List;

public class HandFixtures {
    public static List<Card> highCardOfAce() {
        return Arrays.asList(
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.NINE, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
    }

    public static List<Card> highCardOfKing() {
        return Arrays.asList(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.NINE, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.FOUR, Suit.DIAMONDS),
                new Card(Kind.THREE, Suit.SPADES)
        );
    }

    public static List<Card> highCardOfJack() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.TEN, Suit.SPADES),
                new Card(Kind.NINE, Suit.HEARTS),
                new Card(Kind.EIGHT, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.FOUR, Suit.DIAMONDS)
        );
    }

    public static List<Card> pairOfNines() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.NINE, Suit.DIAMONDS),
                new Card(Kind.NINE, Suit.HEARTS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
    }

    public static List<Card> pairOfSevens() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
    }

    public static List<Card> invalidHandWith6Cards() {
        return Arrays.asList(
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.NINE, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.TWO, Suit.SPADES)
        );
    }

    public static List<Card> twoPairsOfSevensAndAces() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.SPADES)
        );
    }

    public static List<Card> twoPairsOfSevensAndKings() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.KING, Suit.SPADES)
        );
    }

    public static List<Card> twoPairsOfAcesAndTwos() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.HEARTS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.SPADES)
        );
    }

    public static List<Card> pairOfRedJacks() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
    }

    public static List<Card> pairOfBlackJacks() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.SPADES),
                new Card(Kind.JACK, Suit.CLUBS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.CLUBS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
    }

    public static List<Card> threeOfAKindOfAces() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.HEARTS),
                new Card(Kind.ACE, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES)
        );
    }

    public static List<Card> threeOfAKindOfSevens() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.SPADES)
        );
    }

    public static List<Card> fourOfAKindOfAces() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.HEARTS),
                new Card(Kind.ACE, Suit.CLUBS),
                new Card(Kind.ACE, Suit.SPADES),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.KING, Suit.DIAMONDS)
        );
    }

    public static List<Card> fourOfAKindOfSevens() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.CLUBS),
                new Card(Kind.SEVEN, Suit.SPADES),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.KING, Suit.DIAMONDS)
        );
    }

    public static List<Card> straightWithSeven() {
        return Arrays.asList(
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SIX, Suit.HEARTS),
                new Card(Kind.SIX, Suit.CLUBS),
                new Card(Kind.FIVE, Suit.SPADES),
                new Card(Kind.FOUR, Suit.DIAMONDS),
                new Card(Kind.KING, Suit.DIAMONDS));
    }

    public static List<Card> straightWithAce() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.HEARTS),
                new Card(Kind.KING, Suit.CLUBS),
                new Card(Kind.TEN, Suit.SPADES),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.QUEEN, Suit.DIAMONDS)
        );
    }

    public static List<Card> straightFlushWithSeven() {
        return Arrays.asList(
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SIX, Suit.HEARTS),
                new Card(Kind.SIX, Suit.DIAMONDS),
                new Card(Kind.FIVE, Suit.DIAMONDS),
                new Card(Kind.FOUR, Suit.DIAMONDS),
                new Card(Kind.KING, Suit.DIAMONDS));
    }

    public static List<Card> straightFlushWithKing() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.SPADES),
                new Card(Kind.ACE, Suit.HEARTS),
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.TEN, Suit.DIAMONDS),
                new Card(Kind.NINE, Suit.DIAMONDS),
                new Card(Kind.QUEEN, Suit.DIAMONDS)
        );
    }

    public static List<Card> royalFlush() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.HEARTS),
                new Card(Kind.KING, Suit.DIAMONDS),
                new Card(Kind.TEN, Suit.DIAMONDS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.QUEEN, Suit.DIAMONDS)
        );
    }

    public static List<Card> FlushWithSeven() {
        return Arrays.asList(
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SIX, Suit.HEARTS),
                new Card(Kind.SIX, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.DIAMONDS),
                new Card(Kind.FOUR, Suit.DIAMONDS),
                new Card(Kind.KING, Suit.HEARTS));
    }

    public static List<Card> FlushWithKing() {
        return Arrays.asList(
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SIX, Suit.HEARTS),
                new Card(Kind.SIX, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.DIAMONDS),
                new Card(Kind.FOUR, Suit.DIAMONDS),
                new Card(Kind.KING, Suit.DIAMONDS));
    }

    public static List<Card> fullHouseWithAces() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.ACE, Suit.HEARTS),
                new Card(Kind.ACE, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.SPADES),
                new Card(Kind.KING, Suit.DIAMONDS)
        );
    }

    public static List<Card> fullHouseWithSevens() {
        return Arrays.asList(
                new Card(Kind.JACK, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.DIAMONDS),
                new Card(Kind.SEVEN, Suit.HEARTS),
                new Card(Kind.SEVEN, Suit.CLUBS),
                new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.SPADES),
                new Card(Kind.KING, Suit.DIAMONDS)
        );
    }

    public static List<Card> handWithAce() {
        return Arrays.asList(
                new Card(Kind.ACE, Suit.DIAMONDS),
                new Card(Kind.FOUR, Suit.CLUBS)
        );
    }

    public static List<Card> handWithLowCards() {
        return Arrays.asList(
                new Card(Kind.SEVEN, Suit.CLUBS),
                new Card(Kind.EIGHT, Suit.DIAMONDS)
        );
    }

    public static List<Card> handWithTenAndLowCard() {
        return Arrays.asList(
                new Card(Kind.TEN, Suit.SPADES),
                new Card(Kind.FOUR, Suit.CLUBS)
        );
    }

    public static List<Card> handWithTenAndHighCard() {
        return Arrays.asList(
                new Card(Kind.TEN, Suit.HEARTS),
                new Card(Kind.ACE, Suit.CLUBS)
        );
    }

    public static List<Card> communityCardsWithPairOfTens() {
        return Arrays.asList(
                new Card(Kind.TEN, Suit.CLUBS),
                new Card(Kind.TWO, Suit.CLUBS),
                new Card(Kind.TEN, Suit.DIAMONDS),
                new Card(Kind.KING, Suit.SPADES),
                new Card(Kind.FIVE, Suit.DIAMONDS)
        );
    }
}
