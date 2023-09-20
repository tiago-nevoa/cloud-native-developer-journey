package com.sap.ase.poker.service;

import com.sap.ase.poker.model.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Players {
    private static final int START_CASH = 100;
    private final List<Player> players = new ArrayList<>();
    private Player winner;

    private Player currentPlayer;
    public Players(){
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Optional<Player> getCurrentPlayer() {
        return Optional.of(currentPlayer);
    }

    public void addPlayer(String playerId, String playerName) {
        Player player = new Player(playerId, playerName, START_CASH);
        players.add(player);
        System.out.printf("Player joined the table: %s%n", playerId);
    }

    private void setNextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        currentPlayer = players.get(nextIndex);
    }

    private boolean allPlayersHaveEqualBets() {
        int firstPlayerBet = players.get(0).getBet();
        return players.stream().allMatch(player -> player.getBet() == firstPlayerBet);
    }

    public void setCurrentPlayer() {
        currentPlayer = players.get(0);
    }
    public Optional<Player> getWinner() {
        return Optional.ofNullable(winner);
    }
}
