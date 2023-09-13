package com.sap.ase.poker.smokeTests;

import com.sap.ase.poker.dto.GetTableResponseDto;
import com.sap.ase.poker.dto.PlayerDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//@Disabled
public class RaiseSmokeTest extends SmokeTest {

//██╗  ██╗ █████╗ ███╗   ██╗██████╗ ███████╗     ██████╗ ███████╗███████╗██╗██╗██╗
//██║  ██║██╔══██╗████╗  ██║██╔══██╗██╔════╝    ██╔═══██╗██╔════╝██╔════╝██║██║██║
//███████║███████║██╔██╗ ██║██║  ██║███████╗    ██║   ██║█████╗  █████╗  ██║██║██║
//██╔══██║██╔══██║██║╚██╗██║██║  ██║╚════██║    ██║   ██║██╔══╝  ██╔══╝  ╚═╝╚═╝╚═╝
//██║  ██║██║  ██║██║ ╚████║██████╔╝███████║    ╚██████╔╝██║     ██║     ██╗██╗██╗
//╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝     ╚═════╝ ╚═╝     ╚═╝     ╚═╝╚═╝╚═╝

    public static final int BET = 10;


    @Test
    void raise_shouldRaiseBetOfPlayer() {
        addTwoPlayers();
        startGame();


        PlayerDto currentPlayerDto = getCurrentPlayerDto();
        performAction(raiseActionAsString(BET));

        GetTableResponseDto result = getTableResponseDtoForPlayer(currentPlayerDto.getId());
        assertThat(result.getBets().get(currentPlayerDto.getId())).isEqualTo(BET);
        assertThat(getCurrentPlayerState(currentPlayerDto, result).getCash()).isEqualTo(
                STARTING_CASH - BET);
    }


    @Test
    void raise_shouldRaiseBetOfPlayer_IfHigherThanPreviousRaise() {
        addTwoPlayers();
        startGame();

        performAction(raiseActionAsString(BET - 1));

        PlayerDto currentPlayerDto = getCurrentPlayerDto();
        performAction(raiseActionAsString(BET));

        GetTableResponseDto result = getTableResponseDtoForPlayer(currentPlayerDto.getId());
        assertThat(result.getBets().get(currentPlayerDto.getId())).isEqualTo(BET);
    }

    @Test
    void raise_shouldFail_ExceedsPlayersCash() {
        addTwoPlayers();
        startGame();
        performAction(raiseActionAsString(BET));
        performAction(callActionAsString());
        performAction(foldActionAsString());
        startGame();
        assertIllegalAmountException(
                () -> performAction(raiseActionAsString(getCurrentPlayerDto().getCash() + 1)));
    }

    @Test
    void raise_shouldFail_ifExceedsOtherPlayersCash() {
        addTwoPlayers();
        startGame();
        performAction(raiseActionAsString(BET));
        performAction(callActionAsString());
        performAction(foldActionAsString());
        startGame();
        performAction(checkActionAsString());
        assertIllegalAmountException(() -> performAction(raiseActionAsString(STARTING_CASH)));
    }
}
