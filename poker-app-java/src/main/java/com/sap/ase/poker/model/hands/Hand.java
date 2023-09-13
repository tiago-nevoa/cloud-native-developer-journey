package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.deck.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Hand implements Comparable<Hand> {
    public static final int MAX_HAND_CARD_AMOUNT = 5;

    protected List<Card> cards = new ArrayList<>();
    protected List<Card> relevantCards = new ArrayList<>();
    protected List<Card> kickerCards = new ArrayList<>();

    public Hand(List<Card> cards) {
        List<Card> relevantCards = findRelevantCards(cards);
        this.cards.addAll(relevantCards);
        List<Card> kickerCards = findKickerCards(cards);

        this.cards.addAll(kickerCards);
        this.relevantCards.addAll(relevantCards);
        this.kickerCards.addAll(kickerCards);
    }

    public abstract int getRank();

    abstract protected List<Card> findRelevantCards(List<Card> cards);

    protected List<Card> findKickerCards(List<Card> cards) {
        List<Card> potentialKickerCards = findUnusedCards(cards);
        potentialKickerCards.sort(Card::compareTo);

        int amountOfNeededKickerCards = MAX_HAND_CARD_AMOUNT - this.cards.size();
        return potentialKickerCards.subList(potentialKickerCards.size() - amountOfNeededKickerCards,
                potentialKickerCards.size());
    }

    private List<Card> findUnusedCards(List<Card> cards) {
        return cards.stream().filter(card -> !this.cards.contains(card)).collect(Collectors.toList());
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public int compareTo(Hand hand) {
        int rankComparisonResult = this.getRank() - hand.getRank();

        if (rankComparisonResult != 0) {
            return rankComparisonResult;
        } else {
            int relevantCardsComparisonResult = compareRelevantCards(hand);

            if (relevantCardsComparisonResult != 0) {
                return relevantCardsComparisonResult;
            } else {
                return compareKickerCards(hand);
            }
        }
    }

    protected List<Card> sortCardsDescending(List<Card> cards) {
        List<Card> sortedAscending =
                sortCardsAscending(cards);
        Collections.reverse(sortedAscending);
        return sortedAscending;
    }

    protected List<Card> sortCardsAscending(List<Card> cards) {
        return cards.stream().sorted(Card::compareTo).collect(Collectors.toList());
    }

    protected abstract int compareRelevantCards(Hand hand);

    private int compareKickerCards(Hand hand) {

        List<Card> kickerCardsOfHand1 = this.getKickerCards();
        List<Card> kickerCardsOfHand2 = hand.getKickerCards();

        if (kickerCardsOfHand1.isEmpty() && kickerCardsOfHand2.isEmpty()) {
            return 0;
        }

        kickerCardsOfHand1.sort(Collections.reverseOrder(Card::compareTo));
        kickerCardsOfHand2.sort(Collections.reverseOrder(Card::compareTo));

        int kickerCardsComparison;

        int i = 0;
        do {
            kickerCardsComparison = kickerCardsOfHand1.get(i).compareTo(kickerCardsOfHand2.get(i));
            i++;
        } while (kickerCardsComparison == 0 && i < kickerCardsOfHand1.size());

        return kickerCardsComparison;
    }

    public List<Card> getKickerCards() {
        return kickerCards;
    }

    public List<Card> getRelevantCards() {
        return relevantCards;
    }
}
