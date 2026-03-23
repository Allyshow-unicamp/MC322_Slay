package mc322_slay;
import java.util.ArrayList;
import java.util.List;

public class PlayerHand {
    List<Card> hand;

    public void showHand() {

        // sotrs cards by cost, using a compareTo like function to do so
        // given that the sort function sorts in ascending order, -1 is placed at the beginning so that is simulates a desc order
        hand.sort((card1, card2) -> { return -1 * (card1.getCost() > card2.getCost() ? 1 : card1.getCost() == card2.getCost() ? 0 : -1); });

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);

            System.out.println("\r\n===== Carta " + i + " =====");
            System.out.println("Nome: " + card.getName() + " (Custo: " + card.getCost() + "); Descrição: " + card.getDescription());
        }
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
