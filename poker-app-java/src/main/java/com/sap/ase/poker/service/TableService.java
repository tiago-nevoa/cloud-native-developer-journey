package com.sap.ase.poker.service;

import com.sap.ase.poker.model.GameState;
import com.sap.ase.poker.model.IllegalActionException;
import com.sap.ase.poker.model.IllegalAmountException;
import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.CardShuffler;
import com.sap.ase.poker.model.deck.Deck;
import com.sap.ase.poker.model.deck.PokerCardsSupplier;
import com.sap.ase.poker.model.deck.RandomCardShuffler;
import com.sap.ase.poker.model.rules.HandRules;
import com.sap.ase.poker.model.rules.WinnerRules;
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
    private static final int NUM_FLOP_CARDS = 3;
    private static final int NUM_TURN_CARDS = 4;
    private static final int NUM_RIVER_CARDS = 5;
    private final List<Player> players = new ArrayList<>();
    private final List<Card> communityCards = new ArrayList<>();
    private final Map<String, Integer> bets = new HashMap<>();
    private final WinnerRules winnerRules = new WinnerRules(new HandRules());
    private final Deck deck;
    private GameState gameState = GameState.OPEN;
    private Player currentPlayer;
    private int currentBet = 0;
    private int pot = 0;

    public TableService(final Supplier<Deck> deckSupplier) {
        List<Card> pokerCards = new PokerCardsSupplier().get();
        CardShuffler cardShuffler = new RandomCardShuffler();
        this.deck = new Deck(pokerCards, cardShuffler);
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
        if (gameState != GameState.ENDED) {
            return Optional.empty();
        }

        final var activePlayers = getActivePlayers();
        if (activePlayers.size() == 1) {
            return Optional.of(activePlayers.get(0));
        }

        final var winners = winnerRules.findWinners(getCommunityCards(), activePlayers);
        return Optional.of(winners.getWinners().get(0));
    }

    public List<Card> getWinnerHand() {
        if (getWinner().isEmpty() || getActivePlayers().size() == 1) {
            return Collections.emptyList();
        }

        final var winners = winnerRules.findWinners(getCommunityCards(), getActivePlayers());
        return winners.getWinningHand().get().getCards();
    }

    public void start() {
        // maybe the players size condition need to be checked before call start
        if (players.size() < MIN_PLAYERS_TO_START) {
            return;
        }
        deck.shuffle();
        gameState = GameState.PRE_FLOP;
        players.forEach(player -> player.setHandCards(List.of(deck.draw(), deck.draw())));
        players.forEach(Player::setActive);
        currentPlayer = players.get(0);
    }

    public void addPlayer(String playerId, String playerName) {
        Player player = new Player(playerId, playerName, START_CASH);
        players.add(player);
        setBets(playerId);
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
        if (gameState == GameState.ENDED) {
            checkTheresWinner();
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
        if (getActivePlayers().size() == 1) {
            endGame();
        } else if (gameState == GameState.ENDED) {
            winnerReward();
        }
    }

    private void winnerReward() {
        getWinner().get().addCash(pot);
        pot = 0;
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

    private List<Player> getActivePlayers() {
        return players.stream().filter(Player::isActive).toList();
    }

    private void endOfRound() {
        if (players.indexOf(currentPlayer) == 0 && allPlayersHaveEqualBets()) {
            switch (gameState) {
                case PRE_FLOP:
                    drawCommunityCards(NUM_FLOP_CARDS);
                    gameState = GameState.FLOP;
                    break;
                case FLOP:
                    drawCommunityCards(NUM_TURN_CARDS);
                    gameState = GameState.TURN;
                    break;
                case TURN:
                    drawCommunityCards(NUM_RIVER_CARDS);
                    gameState = GameState.RIVER;
                    break;
                case RIVER:
                    gameState = GameState.ENDED;
                    break;
            }
            updatePot();
        }
    }

    private boolean allPlayersHaveEqualBets() {
        int firstPlayerBet = getActivePlayers().get(0).getBet();
        return getActivePlayers().stream().allMatch(player -> player.getBet() == firstPlayerBet);
    }

    private void drawCommunityCards(int numCards) {
        while (communityCards.size() < numCards) {
            communityCards.add(deck.draw());
        }
    }

    private void updatePot() {
        for (int value : bets.values()) {
            pot += value;
        }
        players.forEach(Player::clearBet);
        bets.clear();
        currentBet = 0;
    }

    private void endGame() {
        currentBet = 0;
        gameState = GameState.ENDED;
    }
}