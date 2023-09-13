package com.sap.ase.poker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sap.ase.poker.model.GameState;
import com.sap.ase.poker.model.IllegalActionException;
import com.sap.ase.poker.model.IllegalAmountException;
import com.sap.ase.poker.model.deck.CardShuffler;
import com.sap.ase.poker.model.deck.Deck;
import com.sap.ase.poker.model.deck.PokerCardsSupplier;
import java.util.Optional;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TableServiceTest {
    private CardShuffler cardShuffler;
    private PokerCardsSupplier pokerCardsSupplier;
    private TableService tableService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pokerCardsSupplier = new PokerCardsSupplier();
        cardShuffler = Mockito.mock(CardShuffler.class);
        Supplier<Deck> deckSupplier = () -> new Deck(pokerCardsSupplier.get(), cardShuffler);
        tableService = new TableService(deckSupplier);
    }

    @Test
    void tableService_getState_shouldBeOpenStateInitially() {
        assertThat(tableService.getState()).isEqualTo(GameState.OPEN);
    }

    @Test
    void tableService_getPlayers_shouldHaveEmptyPlayerListInitially() {
        assertThat(tableService.getPlayers()).isEmpty();
    }

    @Test
    void tableService_addPlayer_shouldInitializeWith100CashAndBeInactive() {
        String playerId = "i-ducati";
        String playerName = "Ducati Opera";
        tableService.addPlayer(playerId, playerName);

        assertThat(tableService.getPlayers()).isNotNull();
        assertThat(tableService.getPlayers()).hasSize(1);
        assertThat(tableService.getPlayers().get(0).getId()).isEqualTo("i-ducati");
        assertThat(tableService.getPlayers().get(0).getName()).isEqualTo("Ducati Opera");
        assertThat(tableService.getPlayers().get(0).getCash()).isEqualTo(100);
        assertThat(tableService.getPlayers().get(0).isActive()).isFalse();
    }

    @Test
    void tableService_start_shouldChangeGameState_toPreFlop() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);

        assertThat(tableService.getPlayers()).isNotNull();
        assertThat(tableService.getPlayers()).hasSize(2);

        tableService.start();

        assertThat(tableService.getState()).isEqualTo(GameState.PRE_FLOP);

        assertThat(tableService.getPlayers().get(0).isActive()).isTrue();
        assertThat(tableService.getPlayers().get(1).isActive()).isTrue();

        assertThat(tableService.getPlayers().get(0).getHandCards()).hasSize(2);
        assertThat(tableService.getPlayers().get(1).getHandCards()).hasSize(2);
    }

    @Test
    void tableService_getCurrentPlayer_shouldBeEmptyIfRoundNotStarted() {
        assertThat(tableService.getCurrentPlayer()).isEmpty();
    }

    @Test
    void tableService_getCurrentPlayer_shouldBeFirstPlayer() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);

        tableService.start();
        assertThat(tableService.getCurrentPlayer()).isEqualTo(Optional.of(tableService.getPlayers().get(0)));
    }

    @Test
    void tableService_getPlayerCards_shouldBeEmptyIfNoCards() {
        assertThat(tableService.getPlayerCards("i-42")).isEmpty();
    }

    @Test
    void tableService_getPlayerCards_shouldBe2Cards() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();
        assertThat(tableService.getPlayerCards(playerId1)).hasSize(2);
        assertThat(tableService.getPlayerCards(playerId2)).hasSize(2);
        assertThat(tableService.getPlayerCards(playerId3)).hasSize(2);
    }

    @Test
    void tableService_getCommunityCards_shouldBeEmptyPreFloat() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();

        assertThat(tableService.getCommunityCards()).isEmpty();
    }

    @Test
    void tableService_getCommunityCards_shouldAddCardByCard() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();
        tableService.performAction("check", 0);
        tableService.performAction("check", 0);
        tableService.performAction("check", 0);
        assertThat(tableService.getCommunityCards()).hasSize(3);
    }

    @Test
    void tableService_performActionCheck_shouldFlopAfterAllCheck() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();

        tableService.performAction("check", 0);
        assertThat(tableService.getState()).isEqualTo(GameState.PRE_FLOP);
        assertThat(tableService.getCurrentPlayer()).isEqualTo(Optional.of(tableService.getPlayers().get(1)));

        tableService.performAction("check", 0);
        tableService.performAction("check", 0);
        assertThat(tableService.getState()).isEqualTo(GameState.FLOP);
        assertThat(tableService.getCurrentPlayer()).isEqualTo(Optional.of(tableService.getPlayers().get(0)));
        assertThat(tableService.getCommunityCards()).hasSize(3);
    }

    @Test
    void tableService_performActionCheck_shouldThrowExceptionIncorrectAmount() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();

        tableService.performAction("raise", 50);
        IllegalActionException exception = assertThrows(IllegalActionException.class, () -> {
            tableService.performAction("check", 0);
        });
        String expectedMessage =
                "Current bet is higher than the player bet, Illegal Check " + tableService.getCurrentPlayer().get().getId();
        assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    void tableService_performActionRaise_shouldThrowExceptionIncorrectAmount() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();

        IllegalAmountException exception1 = assertThrows(IllegalAmountException.class, () -> {
            tableService.performAction("raise", 0);
        });
        String expectedMessage1 = "Current bet is higher or equal than the raise amount: " + tableService.getCurrentPlayer().get().getId();
        assertEquals(expectedMessage1, exception1.getMessage());

        IllegalAmountException exception2 = assertThrows(IllegalAmountException.class, () -> {
            tableService.performAction("raise", 110);
        });
        String expectedMessage2 = "Insufficient funds for raise: " + tableService.getCurrentPlayer().get().getId();
        assertEquals(expectedMessage2, exception2.getMessage());

        tableService.getPlayers().get(2).deductCash(80);

        IllegalAmountException exception3 = assertThrows(IllegalAmountException.class, () -> {
            tableService.performAction("raise", 50);
        });
        String expectedMessage3 =
                "Player " + tableService.getPlayers().get(2).getId() + " only has " + tableService.getPlayers().get(2).getCash() + " left.";
        assertEquals(expectedMessage3, exception3.getMessage());
    }

    @Test
    void tableService_performActionRaise_shouldPlaceBetDeduceCash() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();

        tableService.performAction("raise", 25);
        assertThat(tableService.getPlayers().get(0).getBet()).isEqualTo(25);
        assertThat(tableService.getPlayers().get(0).getCash()).isEqualTo(75);

    }

    @Test
    void tableService_performActionFold_shouldInactivePlayer() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();

        tableService.performAction("raise", 25);
        tableService.performAction("raise", 35);
        tableService.performAction("raise", 45);
        tableService.performAction("fold", 0);
        tableService.performAction("raise", 55);
        tableService.performAction("raise", 65);
        tableService.performAction("raise", 75);

        assertThat(tableService.getPlayers().get(0).getBet()).isZero();
        assertThat(tableService.getPlayers().get(0).getCash()).isEqualTo(75);
        assertThat(tableService.getPlayers().get(0).isActive()).isFalse();
    }

    @Test
    void tableService_performActionFold_shouldWinLastInactive() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();
        tableService.performAction("fold", 0);
        tableService.performAction("check", 0);
        tableService.performAction("fold", 0);
        assertThat(tableService.getWinner().get().getId()).isEqualTo(playerId2);
        assertThat(tableService.getState()).isEqualTo(GameState.ENDED);
    }
    @Test
    void tableService_performActionCall_shouldThrowIllegalActionException() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();

        IllegalActionException exception = assertThrows(IllegalActionException.class, () -> {
            tableService.performAction("call", 42);
        });
        String expectedMessage = "Current bet is lower or equal than the player bet, Illegal Check " + tableService.getCurrentPlayer().get().getId();
        assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    void tableService_performActionUnsupported_shouldThrowUnsupportedOperationException() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();

        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> {
            tableService.performAction("cheat", 42);
        });
        String expectedMessage = "Unsupported action: cheat";
        assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    void tableService_getWinnerHand_shouldReturnWinnerHand() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();
        tableService.performAction("fold", 0);
        tableService.performAction("check", 0);
        tableService.performAction("fold", 0);
        assertThat(tableService.getWinner().get().getId()).isEqualTo(playerId2);
        assertThat(tableService.getWinnerHand()).isEqualTo(tableService.getPlayers().get(1).getHandCards());
    }

    @Test
    void tableService_performActionCall_shouldDeductsCash() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();

        tableService.performAction("raise", 25);
        tableService.performAction("raise", 50);
        tableService.performAction("raise", 75);

        tableService.performAction("call", 0);
        tableService.performAction("call", 0);

        assertThat(tableService.getPlayers().get(0).getBet()).isEqualTo(75);
        assertThat(tableService.getPlayers().get(1).getBet()).isEqualTo(75);
        assertThat(tableService.getPlayers().get(2).getBet()).isEqualTo(75);
        assertThat(tableService.getPlayers().get(0).getCash()).isEqualTo(25);
        assertThat(tableService.getPlayers().get(1).getCash()).isEqualTo(25);
        assertThat(tableService.getPlayers().get(2).getCash()).isEqualTo(25);
    }

    @Test
    void tableService_endOfRound_shouldUpdatePot() {
        String playerId1 = "i-ducati";
        String playerName1 = "Ducati Opera";
        String playerId2 = "i-honda";
        String playerName2 = "Honda Blade";
        String playerId3 = "i-mv";
        String playerName3 = "MV Augusta";
        tableService.addPlayer(playerId1, playerName1);
        tableService.addPlayer(playerId2, playerName2);
        tableService.addPlayer(playerId3, playerName3);

        tableService.start();

        tableService.performAction("raise", 25);
        tableService.performAction("raise", 50);
        tableService.performAction("raise", 75);

        tableService.performAction("call", 0);
        tableService.performAction("call", 0);
        tableService.performAction("check", 0);

        assertThat(tableService.getState()).isEqualTo(GameState.FLOP);
        assertThat(tableService.getPot()).isEqualTo(75 * 3);
        assertThat(tableService.getBets()).isEmpty();
        assertThat(tableService.getPlayers().get(0).getBet()).isZero();
        assertThat(tableService.getPlayers().get(1).getBet()).isZero();
        assertThat(tableService.getPlayers().get(2).getBet()).isZero();
        assertThat(tableService.getPlayers().get(0).getCash()).isEqualTo(25);
        assertThat(tableService.getPlayers().get(1).getCash()).isEqualTo(25);
        assertThat(tableService.getPlayers().get(2).getCash()).isEqualTo(25);
    }
}
