package com.sap.ase.poker.smokeTests;

import com.sap.ase.poker.dto.GetTableResponseDto;
import com.sap.ase.poker.dto.PlayerDto;
import com.sap.ase.poker.model.GameState;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//@Disabled
public class WinningSmokeTest extends SmokeTest {

//██╗  ██╗ █████╗ ███╗   ██╗██████╗ ███████╗     ██████╗ ███████╗███████╗██╗██╗██╗
//██║  ██║██╔══██╗████╗  ██║██╔══██╗██╔════╝    ██╔═══██╗██╔════╝██╔════╝██║██║██║
//███████║███████║██╔██╗ ██║██║  ██║███████╗    ██║   ██║█████╗  █████╗  ██║██║██║
//██╔══██║██╔══██║██║╚██╗██║██║  ██║╚════██║    ██║   ██║██╔══╝  ██╔══╝  ╚═╝╚═╝╚═╝
//██║  ██║██║  ██║██║ ╚████║██████╔╝███████║    ╚██████╔╝██║     ██║     ██╗██╗██╗
//╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝     ╚═════╝ ╚═╝     ╚═╝     ╚═╝╚═╝╚═╝

    @Test
    void determineWinner_shouldDetermineWinner_withHighestCards() {
        addTwoPlayers();

        startGame();
        checkGameTillRiver();
        performFinalBettingRound();

        GetTableResponseDto result = getTableResponseDtoForPlayer(ALICE_ID);
        assertThat(result.getState()).isEqualTo(GameState.ENDED.getValue());
        String winnerId = result.getWinner().getId();
        assertThat(winnerId).isNotNull();

        PlayerDto winner =
                result.getPlayers().stream().filter(playerDto -> playerDto.getId().equals(winnerId))
                        .findFirst().get();


        assertThat(winner.getCash()).isEqualTo(STARTING_CASH + BET);
    }


    private void playTwoPlayerGameWithBet() {
        checkGameTillRiver();
        performFinalBettingRound();
    }

    private void performFinalBettingRound() {
        performAction(raiseActionAsString(BET));
        performAction(callActionAsString());
    }

    private void checkGameTillRiver() {
        performAction(checkActionAsString());
        performAction(checkActionAsString());
        performAction(checkActionAsString());
        performAction(checkActionAsString());
        performAction(checkActionAsString());
        performAction(checkActionAsString());
    }
}
