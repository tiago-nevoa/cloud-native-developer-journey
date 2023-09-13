package com.sap.ase.poker.smokeTests;

import com.sap.ase.poker.dto.GetTableResponseDto;
import com.sap.ase.poker.dto.PlayerDto;
import com.sap.ase.poker.model.GameState;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//@Disabled
public class CallSmokeTest extends SmokeTest {
//██╗  ██╗ █████╗ ███╗   ██╗██████╗ ███████╗     ██████╗ ███████╗███████╗██╗██╗██╗
//██║  ██║██╔══██╗████╗  ██║██╔══██╗██╔════╝    ██╔═══██╗██╔════╝██╔════╝██║██║██║
//███████║███████║██╔██╗ ██║██║  ██║███████╗    ██║   ██║█████╗  █████╗  ██║██║██║
//██╔══██║██╔══██║██║╚██╗██║██║  ██║╚════██║    ██║   ██║██╔══╝  ██╔══╝  ╚═╝╚═╝╚═╝
//██║  ██║██║  ██║██║ ╚████║██████╔╝███████║    ╚██████╔╝██║     ██║     ██╗██╗██╗
//╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝     ╚═════╝ ╚═╝     ╚═╝     ╚═╝╚═╝╚═╝

    @Test
    void call_shouldRaiseBetOfPlayer() {
        addTwoPlayers();
        addPlayerWithID(AL_CAPONE_ID);
        startGame();

        performAction(raiseActionAsString(BET));

        PlayerDto currentPlayerDto = getCurrentPlayerDto();
        performAction(callActionAsString());

        GetTableResponseDto result = getTableResponseDtoForPlayer(currentPlayerDto.getId());
        assertThat(result.getBets().get(currentPlayerDto.getId())).isEqualTo(BET);
        assertThat(getCurrentPlayerState(currentPlayerDto, result).getCash()).isEqualTo(
                STARTING_CASH - BET);
    }


    @Test
    void call_shouldAdvanceState() {
        addTwoPlayers();
        startGame();

        performAction(raiseActionAsString(BET));

        PlayerDto currentPlayerDto = getCurrentPlayerDto();
        performAction(callActionAsString());

        GetTableResponseDto result = getTableResponseDtoForPlayer(currentPlayerDto.getId());
        assertThat(result.getState()).isEqualTo(GameState.FLOP.getValue());
    }


    @Test
    void call_shouldReRaiseBetOfPlayer() {
        addTwoPlayers();
        addPlayerWithID(AL_CAPONE_ID);
        startGame();

        performAction(raiseActionAsString(BET));
        performAction(callActionAsString());
        performAction(raiseActionAsString(2 * BET));


        PlayerDto currentPlayerDto = getCurrentPlayerDto();
        performAction(callActionAsString());

        GetTableResponseDto result = getTableResponseDtoForPlayer(currentPlayerDto.getId());
        assertThat(result.getBets().get(currentPlayerDto.getId())).isEqualTo(2 * BET);
        assertThat(getCurrentPlayerState(currentPlayerDto, result).getCash()).isEqualTo(
                STARTING_CASH - 2 * BET);
    }


    @Test
    void call_shouldFail_ifNoRaisePerformedBefore() {
        addTwoPlayers();
        startGame();
        assertIllegalActionException(() -> performAction(callActionAsString()));
    }
}
