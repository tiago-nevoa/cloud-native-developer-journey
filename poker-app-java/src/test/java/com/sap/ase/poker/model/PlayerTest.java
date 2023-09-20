package com.sap.ase.poker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    public static final int CASH = 100;
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final int BET = 10;
    private Player underTest;

    @BeforeEach
    void setUp() {
        underTest = new Player(ID, NAME, CASH);
    }

    @Test
    void bet_shouldAddToBetAndDeductCash() {
        underTest.bet(BET);
        assertThat(underTest.getCash()).isEqualTo(CASH- BET);
        assertThat(underTest.getBet()).isEqualTo(BET);
    }

    @Test
    void clearBet_shouldSetBetToZero() {
        underTest.bet(BET);
        underTest.clearBet();
        assertThat(underTest.getBet()).isEqualTo(0);
    }

    @Test
    void addCash_shouldIncreasePlayerCash() {
        underTest.addCash(BET);
        assertThat(underTest.getCash()).isEqualTo(CASH+BET);
    }

    @Test
    void deductCash_shouldDecreasePlayerCash() {
        underTest.deductCash(BET);
        assertThat(underTest.getCash()).isEqualTo(CASH-BET);
    }

    @Test
    void setInactive_shouldSetPayerInActive() {
        underTest.setInactive();
        assertThat(underTest.isActive()).isFalse();

    }
}