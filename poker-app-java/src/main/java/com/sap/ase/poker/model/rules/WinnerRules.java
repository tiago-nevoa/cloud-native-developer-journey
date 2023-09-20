package com.sap.ase.poker.model.rules;

import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.hands.Hand;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class WinnerRules {

    private final HandRules handRules;

    public WinnerRules(HandRules handRules) {
        this.handRules = handRules;
    }


    public Winners findWinners(List<Card> communityCards, List<Player> activePlayers) {
        Map.Entry<Hand, List<Player>> bestHand = mapPlayersToBestHand(activePlayers, communityCards);

        return new Winners(bestHand.getValue(), bestHand.getKey());
    }

    private Map.Entry<Hand, List<Player>> mapPlayersToBestHand(List<Player> players,
                                                               List<Card> communityCards) {
        TreeMap<Hand, List<Player>> playerHands = new TreeMap<>();
        for (Player player : players) {
            Hand bestHand = handRules.findBestHand(combineCards(communityCards, player.getHandCards()));
            if (!playerHands.containsKey(bestHand)) {
                playerHands.put(bestHand, new ArrayList<>());
            }
            playerHands.get(bestHand).add(player);
        }
        return playerHands.pollLastEntry();
    }

    private List<Card> combineCards(List<Card> communityCards, List<Card> handCards) {
        List<Card> availableCards = new ArrayList<>(handCards);
        availableCards.addAll(communityCards);
        return availableCards;
    }
}
