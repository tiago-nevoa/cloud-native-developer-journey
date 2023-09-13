package com.sap.ase.poker.model.deck;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeckTest {

    public static final int EXPECTED_DECK_SIZE = 52;

    CardShuffler shuffler = Mockito.mock(CardShuffler.class);

    Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck(new PokerCardsSupplier().get(), shuffler);
    }

    @Test
    void has52Cards() {
        List<Card> cards = deck.getCards();

        assertThat(cards).hasSize(EXPECTED_DECK_SIZE);
    }

    @Test
    void drawShouldReduceAmountOfCards() {
        int amountOfCards = deck.getCards().size();

        deck.draw();

        assertThat(deck.getCards().size()).isEqualTo(amountOfCards - 1);
    }

    @Test
    void drawing53TimesShouldThrowAnError() {
        IntStream.range(0, 52).forEach(drawAmounts -> deck.draw());
        assertThatThrownBy(() -> deck.draw()).isInstanceOf(OutOfCardsException.class);
    }

    @Test
    void collectionsShuffleGetsCalledWhenShuffling() {

        Mockito.when(shuffler.shuffle(deck.getCards())).thenReturn(deck.getCards());
        deck.shuffle();
        Mockito.verify(shuffler, Mockito.times(1)).shuffle(deck.getCards());
    }

    @Test
    void shuffleResetsDeck() {
        deck = new Deck(new PokerCardsSupplier().get(), new RandomCardShuffler());

        IntStream.range(0,52).forEach(drawAmounts -> deck.draw());

        deck.shuffle();
        assertThat(deck.getCards().size()).isEqualTo(EXPECTED_DECK_SIZE);
    }
}