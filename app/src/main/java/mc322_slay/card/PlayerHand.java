package mc322_slay.card;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand {
    private List<Card> hand;

    public List<Card> getHand() {
        return hand;
    }
    public void buyCard(CardStack buyPile, CardStack discardPile) {
        if (buyPile.isEmpty()) {
            restoreCards(discardPile, buyPile);
        }
        hand.add(buyPile.remove());
    }
    public Card useCard(int card) {
        return hand.remove(card);
    }
    public void discardCards(CardStack discardPile) {
        while (!hand.isEmpty()) {
            discardPile.add(hand.remove(0));
        }
    }
    public void restoreCards(CardStack discardPile, CardStack buyPile) {
        discardPile.shuffle();
        while(!discardPile.isEmpty()) {
            buyPile.add(discardPile.remove());
        }
    }
    public int seeCardCost(int card) {
        Card Card = hand.get(card);
        int cost = Card.getCost();
        return cost;
    }
    public int nCards() {
        return hand.size();
    }

    public PlayerHand() {
        this.hand = new ArrayList<>();
    }
}
