package com.sap.ase.poker.model.deck;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ShuffledDeckSupplierTest {
    public static final Card CARD = new Card(Kind.SEVEN, Suit.HEARTS);
    @Mock
    private PokerCardsSupplier mockCardSupplier;
    @Mock
    private CardShuffler mockCardShuffler;
    @Captor
    private ArgumentCaptor<List<Card>> captor;

    @InjectMocks
    private ShuffledDeckSupplier underTest;


    @Test
    void get_shufflesDeck() {
        Mockito.when(mockCardSupplier.get()).thenReturn(Collections.singletonList(CARD));

        underTest.get();

        Mockito.verify(mockCardShuffler, times(1)).shuffle(captor.capture());
        assertThat(captor.getValue()).containsOnly(CARD);
    }
}