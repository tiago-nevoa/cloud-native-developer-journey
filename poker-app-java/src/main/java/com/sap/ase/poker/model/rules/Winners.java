package com.sap.ase.poker.model.rules;

import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.model.hands.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Winners {

    List<Player> winners = new ArrayList<>();
    Hand winningHand;

    public Winners(List<Player> winners, Hand winningHand) {
        this.winners.addAll(winners);
        this.winningHand = winningHand;
    }

    public Optional<Hand> getWinningHand() {
        return Optional.ofNullable(winningHand);
    }

    public List<Player> getWinners() {
        return winners;
    }
}
