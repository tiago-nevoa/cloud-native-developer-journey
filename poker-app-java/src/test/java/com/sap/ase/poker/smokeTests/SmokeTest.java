package com.sap.ase.poker.smokeTests;

import com.sap.ase.poker.dto.BetRequestDto;
import com.sap.ase.poker.dto.GetTableResponseDto;
import com.sap.ase.poker.dto.PlayerDto;
import com.sap.ase.poker.model.IllegalActionException;
import com.sap.ase.poker.model.IllegalAmountException;
import com.sap.ase.poker.rest.TableController;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


// @Disabled
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SmokeTest {

//██╗  ██╗ █████╗ ███╗   ██╗██████╗ ███████╗     ██████╗ ███████╗███████╗██╗██╗██╗
//██║  ██║██╔══██╗████╗  ██║██╔══██╗██╔════╝    ██╔═══██╗██╔════╝██╔════╝██║██║██║
//███████║███████║██╔██╗ ██║██║  ██║███████╗    ██║   ██║█████╗  █████╗  ██║██║██║
//██╔══██║██╔══██║██║╚██╗██║██║  ██║╚════██║    ██║   ██║██╔══╝  ██╔══╝  ╚═╝╚═╝╚═╝
//██║  ██║██║  ██║██║ ╚████║██████╔╝███████║    ╚██████╔╝██║     ██║     ██╗██╗██╗
//╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝     ╚═════╝ ╚═╝     ╚═╝     ╚═╝╚═╝╚═╝

    protected static final String PATH = "/api/v1/";
    protected static final int STARTING_CASH = 100;
    protected static final String ALICE_ID = "poker-alice";
    protected static final String BILL_ID = "wild-bill";
    protected static final String AL_CAPONE_ID = "al-capone";
    protected static final int BET = 10;
    protected static final String ALICE_NAME = "Poker Alice";
    protected static final String BILL_NAME = "Wild Bill";

    @Autowired
    protected TableController underTest;


    void addTwoPlayers() {
        addPlayerWithID(ALICE_ID);
        addPlayerWithID(BILL_ID);
    }


    void startGame() {
        underTest.start();
    }

    PlayerDto getCurrentPlayerDto() {
        GetTableResponseDto tableResponseDtoAtStart = getTableResponseDtoForPlayer(ALICE_ID);
        return tableResponseDtoAtStart.getCurrentPlayer();
    }

    protected PlayerDto getCurrentPlayerState(PlayerDto currentPlayerDto,
                                              GetTableResponseDto result) {
        return result.getPlayers().stream()
                .filter(playerDto -> playerDto.getId().equals(currentPlayerDto.getId())).findFirst().get();
    }

    protected BetRequestDto checkActionAsString() {
        return getActionAsString("check", new int[]{});
    }

    protected BetRequestDto foldActionAsString() {
        return getActionAsString("fold", new int[]{});
    }

    protected BetRequestDto raiseActionAsString(int bet) {
        return getActionAsString("raise", new int[]{bet});
    }

    protected BetRequestDto callActionAsString() {
        return getActionAsString("call", new int[]{});
    }

    protected BetRequestDto getActionAsString(String type, int[] args) {
        BetRequestDto checkRequest = new BetRequestDto();
        checkRequest.setType(type);
        checkRequest.setArgs(args);
        return (checkRequest);
    }

    void performAction(BetRequestDto checkString) {
        underTest.placeBet(checkString);
    }

    GetTableResponseDto getTableResponseDtoForPlayer(String id) {
        return underTest.getTable(createMockPrincipalWithId(id));
    }


    void addPlayerWithID(String id) {
        Principal mockPrincipal = createMockPrincipalWithId(id);

        underTest.joinTable(mockPrincipal);
    }

    private Principal createMockPrincipalWithId(String id) {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn(id);
        return mockPrincipal;
    }

    protected void assertIllegalAmountException(ThrowableAssert.ThrowingCallable throwingCallable) {
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalAmountException.class);
    }

    void assertIllegalActionException(ThrowableAssert.ThrowingCallable throwingCallable) {
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalActionException.class);
    }
}
