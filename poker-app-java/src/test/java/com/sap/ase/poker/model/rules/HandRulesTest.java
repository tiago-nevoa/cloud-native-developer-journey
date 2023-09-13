package com.sap.ase.poker.model.rules;

import com.sap.ase.poker.fixtures.HandFixtures;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Kind;
import com.sap.ase.poker.model.deck.Suit;
import com.sap.ase.poker.model.hands.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HandRulesTest {

    private final HandRules underTest = new HandRules();

    @Test
    void whenOnlySixCardsPresent_findBestHand_shouldReturnException() {

        List<Card> cards = HandFixtures.invalidHandWith6Cards();

        assertThatThrownBy(() -> underTest.findBestHand(cards)).isInstanceOf(
                InvalidAmountOfCardsException.class);
    }

    @Test
    void whenOnlyHighCardKingPresent_findBestHand_shouldReturnHighCardWithKing() {
        List<Card> cards = HandFixtures.highCardOfKing();

        Hand hand = underTest.findBestHand(cards);

        assertThat(hand.getCards()).hasSize(5);
        assertThat(hand.getCards()).doesNotContain(new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES));
        assertThat(hand).isInstanceOf(HighCard.class);
    }

    @Test
    void whenOnlyHighCardAcePresent_findBestHand_shouldReturnHighCardWithAce() {
        List<Card> cards = HandFixtures.highCardOfAce();

        Hand hand = underTest.findBestHand(cards);

        assertThat(hand.getCards()).hasSize(5);
        assertThat(hand.getCards()).doesNotContain(new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES));
        assertThat(hand).isInstanceOf(HighCard.class);
    }

    @Test
    void whenOnlyTwoSevensPresent_findBestHand_shouldReturnPairOfSeven() {
        List<Card> cards = HandFixtures.pairOfSevens();

        Hand hand = underTest.findBestHand(cards);

        assertThat(hand.getCards()).hasSize(5);
        assertThat(hand.getCards()).doesNotContain(new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES));
        assertThat(hand).isInstanceOf(Pair.class);
    }

    @Test
    void whenTwoSevensAndTwoAcesPresent_findBestHand_shouldReturnTwoPairsOfSevenAndAce() {
        List<Card> cards = HandFixtures.twoPairsOfSevensAndAces();

        Hand hand = underTest.findBestHand(cards);

        assertThat(hand.getCards()).hasSize(5);
        assertThat(hand.getCards()).doesNotContain(new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.TWO, Suit.SPADES));
        assertThat(hand).isInstanceOf(TwoPairs.class);
    }

    @Test
    void whenThreeSevensPresent_findBestHand_shouldReturnThreeOfAKindOfSevens() {
        List<Card> cards = HandFixtures.threeOfAKindOfSevens();

        Hand hand = underTest.findBestHand(cards);

        assertThat(hand.getCards()).hasSize(5);
        assertThat(hand.getCards()).doesNotContain(new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.DIAMONDS));
        assertThat(hand).isInstanceOf(ThreeOfAKind.class);
    }

    @Test
    void whenFourSevensPresent_findBestHand_shouldReturnFourOfAKindOfSevens() {
        List<Card> cards = HandFixtures.fourOfAKindOfSevens();

        Hand hand = underTest.findBestHand(cards);

        assertThat(hand.getCards()).hasSize(5);
        assertThat(hand.getCards()).doesNotContain(new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.JACK, Suit.DIAMONDS));
        assertThat(hand).isInstanceOf(FourOfAKind.class);
    }

    @Test
    void whenStraightPresent_findBestHand_shouldReturnStraight() {
        List<Card> cards = HandFixtures.straightWithAce();

        Hand hand = underTest.findBestHand(cards);

        assertThat(hand.getCards()).hasSize(5);
        assertThat(hand.getCards()).doesNotContain(new Card(Kind.THREE, Suit.DIAMONDS));
        assertThat(hand).isInstanceOf(Straight.class);
    }

    @Test
    void whenFlushPresent_findBestHand_shouldReturnFlush() {
        List<Card> cards = HandFixtures.FlushWithKing();

        Hand hand = underTest.findBestHand(cards);

        assertThat(hand.getCards()).hasSize(5);
        assertThat(hand.getCards()).doesNotContain(new Card(Kind.SIX, Suit.HEARTS),
                new Card(Kind.TWO, Suit.DIAMONDS));
        assertThat(hand).isInstanceOf(Flush.class);
    }

    @Test
    void whenFullHousePresent_findBestHand_shouldReturnFullHouse() {
        List<Card> cards = HandFixtures.fullHouseWithSevens();

        Hand hand = underTest.findBestHand(cards);

        assertThat(hand.getCards()).hasSize(5);
        assertThat(hand.getCards()).doesNotContain(new Card(Kind.THREE, Suit.DIAMONDS),
                new Card(Kind.KING, Suit.DIAMONDS));
        assertThat(hand).isInstanceOf(FullHouse.class);
    }

    @Test
    void whenStraightFlushPresent_findBestHand_shouldReturnStraightFlush() {
        List<Card> cards = HandFixtures.straightFlushWithKing();

        Hand hand = underTest.findBestHand(cards);

        assertThat(hand.getCards()).hasSize(5);
        assertThat(hand.getCards()).doesNotContain(new Card(Kind.ACE, Suit.SPADES),
                new Card(Kind.ACE, Suit.HEARTS));
        assertThat(hand).isInstanceOf(StraightFlush.class);
    }

    @Test
    void whenRoyalFlushPresent_findBestHand_shouldReturnRoyalFlush() {
        List<Card> cards = HandFixtures.royalFlush();

        Hand hand = underTest.findBestHand(cards);

        assertThat(hand.getCards()).hasSize(5);
        assertThat(hand.getCards()).doesNotContain(new Card(Kind.ACE, Suit.HEARTS),
                new Card(Kind.THREE, Suit.DIAMONDS));
        assertThat(hand).isInstanceOf(RoyalFlush.class);
    }
}
