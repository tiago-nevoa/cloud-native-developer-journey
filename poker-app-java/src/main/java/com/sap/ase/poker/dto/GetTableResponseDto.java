package com.sap.ase.poker.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetTableResponseDto {

	private List<PlayerDto> players = new ArrayList<>();
	private List<CardDto> playerCards = new ArrayList<>();
	private PlayerDto currentPlayer;
	private List<CardDto> communityCards = new ArrayList<>();
	private int pot;
	private Map<String, Integer> bets = new HashMap<>();
	private int state;
	private PlayerDto winner;
	private List<CardDto> winnerHand;

	public GetTableResponseDto() {
	}

	public GetTableResponseDto(String uiPlayerName) {
	}

	public List<PlayerDto> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerDto> players) {
		this.players = players;
	}

	public List<CardDto> getPlayerCards() {
		return playerCards;
	}

	public void setPlayerCards(List<CardDto> playerCards) {
		this.playerCards = playerCards;
	}

	public PlayerDto getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(PlayerDto currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public List<CardDto> getCommunityCards() {
		return communityCards;
	}

	public void setCommunityCards(List<CardDto> communityCards) {
		this.communityCards = communityCards;
	}

	public int getPot() {
		return pot;
	}

	public void setPot(int pot) {
		this.pot = pot;
	}

	public Map<String, Integer> getBets() {
		return bets;
	}

	public void setBets(Map<String, Integer> bets) {
		this.bets = bets;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public PlayerDto getWinner() {
		return winner;
	}

	public void setWinner(PlayerDto winner) {
		this.winner = winner;
	}

	public List<CardDto> getWinnerHand() {
		return winnerHand;
	}

	public void setWinnerHand(List<CardDto> winnerHand) {
		this.winnerHand = winnerHand;
	}
}
