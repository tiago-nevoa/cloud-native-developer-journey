package com.sap.ase.poker.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ase.poker.data.PlayerNamesRepository;
import com.sap.ase.poker.dto.BetRequestDto;
import com.sap.ase.poker.dto.GetTableResponseDto;
import com.sap.ase.poker.model.GameState;
import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.service.TableService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
public class TableControllerTest {

    private static final String PATH = "/api/v1";
    public static final int BET_AMOUNT = 10;
    public static final String RAISE = "raise";
    public static final String CHECK = "check";
    public static final String ALICE_ID = "alice";
    public static final String ALICE_NAME = "aliceName";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TableService tableService;

    @MockBean
    PlayerNamesRepository playerNamesRepository;

    @Test
    void getTable_returnsGetTableResponseDtoWithTableStatus() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(ALICE_ID);

        Mockito.when(tableService.getPlayers()).thenReturn(Arrays.asList(
                new Player(ALICE_ID, "Alice", 100),
                new Player("bob", "Bob", 100)));
        Mockito.when(tableService.getState()).thenReturn(GameState.FLOP);

        MockHttpServletResponse response = mockMvc.perform(get(PATH).principal(mockPrincipal))
                .andExpect(status().isOk()).andReturn().getResponse();

        GetTableResponseDto result = objectMapper.readValue(response.getContentAsString(), GetTableResponseDto.class);

        assertThat(result.getPlayers()).hasSize(2);
        assertThat(result.getState()).isEqualTo(GameState.FLOP.getValue());
    }
    @Test
    void joinTable_adsValidPlayerToTable() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(ALICE_ID);

        Mockito.when(playerNamesRepository.getNameForId(ALICE_ID)).thenReturn(ALICE_NAME);

        mockMvc.perform(post(PATH+"/players").principal(mockPrincipal))
                .andExpect(status().isNoContent());
        Mockito.verify(tableService,Mockito.times(1)).addPlayer(ALICE_ID, ALICE_NAME);
    }

    @Test
    void start_startsGameWithTableService() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(ALICE_ID);

        mockMvc.perform(post(PATH+"/start").principal(mockPrincipal))
                .andExpect(status().isNoContent()).andReturn().getResponse();

        Mockito.verify(tableService,Mockito.times(1)).start();
    }

    @Test
    void placeBet_withRaise_callsTableServiceWithCorrectAmount() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(ALICE_ID);

        BetRequestDto betRequest = new BetRequestDto();
        betRequest.setType(RAISE);
        betRequest.setArgs(new int[]{BET_AMOUNT});

        String raiseJson = objectMapper.writeValueAsString(betRequest);

        mockMvc.perform(post(PATH+"/actions").
                        principal(mockPrincipal).
                        content(raiseJson).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        Mockito.verify(tableService,Mockito.times(1)).performAction(RAISE,BET_AMOUNT);
    }

    @Test
    void placeBet_withCheck_callsTableServiceAmountZero() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(ALICE_ID);

        BetRequestDto betRequest = new BetRequestDto();
        betRequest.setType(CHECK);
        betRequest.setArgs(new int[]{});


        String raiseJson = objectMapper.writeValueAsString(betRequest);

        mockMvc.perform(post(PATH+"/actions").
                        principal(mockPrincipal).
                        content(raiseJson).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        Mockito.verify(tableService,Mockito.times(1)).performAction(CHECK,0);
    }

}
