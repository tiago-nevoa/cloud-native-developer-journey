package com.sap.ase.poker.smokeTests;

import com.sap.ase.poker.dto.GetTableResponseDto;
import com.sap.ase.poker.dto.PlayerDto;
import com.sap.ase.poker.model.GameState;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//@Disabled
public class FoldSmokeTest extends SmokeTest {

//██╗  ██╗ █████╗ ███╗   ██╗██████╗ ███████╗     ██████╗ ███████╗███████╗██╗██╗██╗
//██║  ██║██╔══██╗████╗  ██║██╔══██╗██╔════╝    ██╔═══██╗██╔════╝██╔════╝██║██║██║
//███████║███████║██╔██╗ ██║██║  ██║███████╗    ██║   ██║█████╗  █████╗  ██║██║██║
//██╔══██║██╔══██║██║╚██╗██║██║  ██║╚════██║    ██║   ██║██╔══╝  ██╔══╝  ╚═╝╚═╝╚═╝
//██║  ██║██║  ██║██║ ╚████║██████╔╝███████║    ╚██████╔╝██║     ██║     ██╗██╗██╗
//╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝     ╚═════╝ ╚═╝     ╚═╝     ╚═╝╚═╝╚═╝
    @Test
    void fold_shouldMarkPlayerAsInactive() {
        addTwoPlayers();
        addPlayerWithID(AL_CAPONE_ID);
        startGame();

        PlayerDto currentPlayerDto = getCurrentPlayerDto();
        performAction(foldActionAsString());

        GetTableResponseDto result = getTableResponseDtoForPlayer(ALICE_ID);
        assertThat(result.getCurrentPlayer().getId()).isNotEqualTo(currentPlayerDto.getId());
        assertThat(getCurrentPlayerState(currentPlayerDto, result).getCash()).isEqualTo(
                currentPlayerDto.getCash());
    }

    @Test
    void fold_shouldEndGame() {
        addTwoPlayers();
        startGame();

        PlayerDto currentPlayerDto = getCurrentPlayerDto();
        performAction(foldActionAsString());

        GetTableResponseDto result = getTableResponseDtoForPlayer(ALICE_ID);
        assertThat(result.getWinner()).isNotNull();
        assertThat(result.getWinnerHand()).isEmpty();
        assertThat(result.getState()).isEqualTo(GameState.ENDED.getValue());
        assertThat(getCurrentPlayerState(currentPlayerDto, result).getCash()).isEqualTo(
                currentPlayerDto.getCash());
    }
}
