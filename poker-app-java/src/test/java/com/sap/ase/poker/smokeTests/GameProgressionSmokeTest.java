package com.sap.ase.poker.smokeTests;

import com.sap.ase.poker.dto.GetTableResponseDto;
import com.sap.ase.poker.dto.PlayerDto;
import com.sap.ase.poker.model.GameState;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class GameProgressionSmokeTest extends SmokeTest {

//██╗  ██╗ █████╗ ███╗   ██╗██████╗ ███████╗     ██████╗ ███████╗███████╗██╗██╗██╗
//██║  ██║██╔══██╗████╗  ██║██╔══██╗██╔════╝    ██╔═══██╗██╔════╝██╔════╝██║██║██║
//███████║███████║██╔██╗ ██║██║  ██║███████╗    ██║   ██║█████╗  █████╗  ██║██║██║
//██╔══██║██╔══██║██║╚██╗██║██║  ██║╚════██║    ██║   ██║██╔══╝  ██╔══╝  ╚═╝╚═╝╚═╝
//██║  ██║██║  ██║██║ ╚████║██████╔╝███████║    ╚██████╔╝██║     ██║     ██╗██╗██╗
//╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝     ╚═════╝ ╚═╝     ╚═╝     ╚═╝╚═╝╚═╝

    @Test
    void gameShouldBeOpenInitially() {
        GetTableResponseDto result = getTableResponseDtoForPlayer(ALICE_ID);

        assertThat(result.getState()).isEqualTo(GameState.OPEN.getValue());
        assertGameNotStarted(result);
    }

    @Test
    void addPlayer_shouldAddNewPlayer() {
        super.addTwoPlayers();

        GetTableResponseDto result = getTableResponseDtoForPlayer(ALICE_ID);

        assertGameNotStarted(result);
        assertAllBetsAreZero(result);

        assertThat(result.getPlayers()).hasSize(2);
        assertThat(result.getPlayers().stream().map(PlayerDto::getId)).containsOnly(ALICE_ID, BILL_ID);
        assertThat(result.getPlayers().stream().map(PlayerDto::getName)).containsOnly(BILL_NAME,
                ALICE_NAME);
        assertThat(result.getPlayers().stream().map(PlayerDto::getCash)).containsOnly(STARTING_CASH);
    }

    @Test
    void startGame_shouldStartGame() {
        addTwoPlayers();

        startGame();

        GetTableResponseDto result = getTableResponseDtoForPlayer(ALICE_ID);

        assertThat(result.getState()).isEqualTo(GameState.PRE_FLOP.getValue());
        assertThat(result.getPlayerCards()).hasSize(2);
        assertThat(result.getPot()).isEqualTo(0);
        assertThat(result.getCurrentPlayer().getId()).isEqualTo(ALICE_ID);
        assertThat(result.getPot()).isEqualTo(0);
        assertAllBetsAreZero(result);
    }

    @Test
    void playerActionsShouldProgressGameState() {
        assertThat(getTableResponseDtoForPlayer(ALICE_ID).getState()).isEqualTo(
                GameState.OPEN.getValue());

        addTwoPlayers();
        startGame();

        assertThat(getTableResponseDtoForPlayer(ALICE_ID).getState()).isEqualTo(
                GameState.PRE_FLOP.getValue());
        assertThat(getTableResponseDtoForPlayer(ALICE_ID).getCommunityCards()).hasSize(0);


        performAction(checkActionAsString());
        performAction(checkActionAsString());

        assertThat(getTableResponseDtoForPlayer(ALICE_ID).getState()).isEqualTo(
                GameState.FLOP.getValue());
        assertThat(getTableResponseDtoForPlayer(ALICE_ID).getCommunityCards()).hasSize(3);

        performAction(checkActionAsString());
        performAction(checkActionAsString());

        assertThat(getTableResponseDtoForPlayer(ALICE_ID).getState()).isEqualTo(
                GameState.TURN.getValue());
        assertThat(getTableResponseDtoForPlayer(ALICE_ID).getCommunityCards()).hasSize(4);

        performAction(checkActionAsString());
        performAction(checkActionAsString());

        assertThat(getTableResponseDtoForPlayer(ALICE_ID).getState()).isEqualTo(
                GameState.RIVER.getValue());
        assertThat(getTableResponseDtoForPlayer(ALICE_ID).getCommunityCards()).hasSize(5);

        performAction(checkActionAsString());
        performAction(checkActionAsString());

        assertThat(getTableResponseDtoForPlayer(ALICE_ID).getState()).isEqualTo(
                GameState.ENDED.getValue());
    }

    private void assertGameNotStarted(GetTableResponseDto result) {
        assertThat(result.getState()).isEqualTo(GameState.OPEN.getValue());
        assertThat(result.getCurrentPlayer()).isNull();
        assertThat(result.getWinner()).isNull();
        assertThat(result.getPlayerCards()).isEmpty();
        assertThat(result.getPot()).isEqualTo(0);
    }

    private void assertAllBetsAreZero(GetTableResponseDto result) {
        assertThat(result.getBets().values()).containsOnly(0);
    }
}
