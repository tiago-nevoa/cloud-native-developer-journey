package com.sap.ase.poker.service;

import com.sap.ase.poker.model.GameState;
import com.sap.ase.poker.model.IllegalActionException;
import com.sap.ase.poker.model.IllegalAmountException;
import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Deck;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
public class TableService {

    private static final int START_CASH = 100;
    private static final int MIN_PLAYERS_TO_START = 2;
    private static final int NUM_COMMUNITY_CARDS = 5;
    private static final int NUM_FLOP_CARDS = 3;
    private final Supplier<Deck> deckSupplier;
    private final List<Player> players = new ArrayList<>();
    private final List<Card> communityCards = new ArrayList<>();
    private final Map<String, Integer> bets = new HashMap<>();
    private Deck deck;
    private GameState gameState = GameState.OPEN;
    private Player currentPlayer;
    private int currentBet = 0;
    private Player winner;
    private int pot = 0;

    public TableService(Supplier<Deck> deckSupplier) {
        this.deckSupplier = deckSupplier;
    }

    public GameState getState() {
        return gameState;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Card> getPlayerCards(String playerId) {
        return players.stream().filter(player -> player.getId().equals(playerId)).findFirst().map(Player::getHandCards)
                .orElse(Collections.emptyList());
    }

    public List<Card> getCommunityCards() {
        if (gameState == GameState.PRE_FLOP) {
            return Collections.emptyList();
        }
        return communityCards;
    }

    public Optional<Player> getCurrentPlayer() {
        if (gameState == GameState.OPEN) {
            return Optional.empty();
        }
        return Optional.of(currentPlayer);
    }

    public Map<String, Integer> getBets() {
        return bets;
    }

    private void setBets(String playerId) {
        bets.put(playerId, currentBet);
    }

    public int getPot() {
        return pot;
    }

    public Optional<Player> getWinner() {
        return Optional.ofNullable(winner);
    }

    public List<Card> getWinnerHand() {
        return winner.getHandCards();
    }

    public void start() {
        // maybe the players size condition need to be checked before call start
        if (players.size() < MIN_PLAYERS_TO_START) {
            return;
        }

        deck = deckSupplier.get();
        //deck.shuffle();
        gameState = GameState.PRE_FLOP;
        players.forEach(player -> player.setHandCards(List.of(deck.draw(), deck.draw())));
        players.forEach(Player::setActive);
        currentPlayer = players.get(0);
        winner = currentPlayer;
    }

    public void addPlayer(String playerId, String playerName) {
        Player player = new Player(playerId, playerName, START_CASH);
        players.add(player);
        System.out.printf("Player joined the table: %s%n", playerId);
    }

    public void performAction(String action, int amount) throws IllegalAmountException, IllegalActionException {
        if (gameState == GameState.OPEN) {
            return;
        }
        if (!currentPlayer.isActive()) {
            setNextPlayer();
        }

        switch (action) {
            case "check" -> handleCheckAction();
            case "raise" -> handleRaiseAction(amount);
            case "fold" -> handleFoldAction();
            case "call" -> handleCallAction();
            default -> throw new UnsupportedOperationException("Unsupported action: " + action);
        }
        System.out.printf("Action performed: %s, amount: %d%n", action, amount);
        setNextPlayer();
        if (gameState != GameState.ENDED) {
            endOfRound();
        }
    }

    private void handleCheckAction() throws IllegalActionException {
        validateCheck();
    }

    private void validateCheck() throws IllegalActionException {
        if (currentPlayer.getBet() < currentBet) {
            throw new IllegalActionException("Current bet is higher than the player bet, Illegal Check " + currentPlayer.getId());
        }
    }

    private void handleRaiseAction(int amount) throws IllegalAmountException {
        validateRaiseAmount(amount);
        currentBet = amount;
        currentPlayer.bet(currentBet - currentPlayer.getBet());
        setBets(currentPlayer.getId());
    }

    private void validateRaiseAmount(int amount) throws IllegalAmountException {
        if (currentBet >= amount) {
            throw new IllegalAmountException("Current bet is higher or equal than the raise amount: " + currentPlayer.getId());
        }

        if (currentPlayer.getCash() + currentPlayer.getBet() < amount) {
            throw new IllegalAmountException("Insufficient funds for raise: " + currentPlayer.getId());
        }

        for (Player player : players) {
            if (player.getBet() + player.getCash() < amount) {
                throw new IllegalAmountException("Player " + player.getId() + " only has " + player.getCash() + " left.");
            }
        }
    }

    private void handleFoldAction() {
        currentPlayer.setInactive();
        currentPlayer.clearBet();
        checkTheresWinner();
    }

    private void checkTheresWinner() {
        long activePlayerCount = players.stream().filter(Player::isActive).count();
        if (activePlayerCount == 1) {
            winner = players.stream().filter(Player::isActive).findFirst().orElse(null);
            winner.setHandCards(Collections.emptyList());
            endGame();
        }
    }

    private void handleCallAction() throws IllegalActionException {
        validateCall();
        currentPlayer.bet(currentBet - currentPlayer.getBet());
        setBets(currentPlayer.getId());
    }

    private void validateCall() throws IllegalActionException {
        if (currentPlayer.getBet() >= currentBet) {
            throw new IllegalActionException("Current bet is lower or equal than the player bet, Illegal Check " + currentPlayer.getId());
        }
    }

    private void setNextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        currentPlayer = players.get(nextIndex);
    }

    private void endOfRound() {
        if (players.indexOf(currentPlayer) == 0 && allPlayersHaveEqualBets()) {
            while (communityCards.size() < NUM_FLOP_CARDS) {
                communityCards.add(deck.draw());
            }
            gameState = GameState.FLOP;
            updatePot();
        }
    }

    private boolean allPlayersHaveEqualBets() {
        int firstPlayerBet = players.get(0).getBet();
        return players.stream().allMatch(player -> player.getBet() == firstPlayerBet);
    }

    private void updatePot() {
        for (int value : bets.values()) {
            pot += value;
        }
        players.forEach(Player::clearBet);
        bets.clear();
    }

    private void endGame() {
        currentBet = 0;
        gameState = GameState.ENDED;
    }
}