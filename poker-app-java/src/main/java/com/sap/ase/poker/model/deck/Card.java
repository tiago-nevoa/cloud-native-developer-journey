package com.sap.ase.poker.model.deck;

public class Card implements Comparable<Card> {

    private final Kind kind;

    private final Suit suit;

    public Card(Kind kind, Suit suit) {
        this.kind = kind;
        this.suit = suit;
    }

    @Override
    public int compareTo(Card c) {
        return this.kind.rank - c.kind.rank;
    }

    @Override
    public String toString() {
        return suit + " " + kind;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((kind == null) ? 0 : kind.hashCode());
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Card other = (Card) obj;
        if (kind != other.kind) {
            return false;
        }
        if (suit != other.suit) {
            return false;
        }
        return true;
    }

    public Kind getKind() {
        return kind;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean notEquals(Card card) {
        return !this.equals(card);
    }
}