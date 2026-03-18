import java.util.ArrayList;
import java.util.List;

public class PlayerHand {
    List<Card> hand;

    public void showHand() {
        System.out.print("""
        ==============
        Mão do Jogador
        ==============
        """);

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);

            System.out.println("\r\n===== Carta " + i + " =====");
            System.out.println("Nome: " + card.getName());
            System.out.println("Custo: " + card.getCost());
            System.out.print("Descrição: " + card.getDescription());
        }
    }
    public void buyCard(CardStack buyPile) {
        hand.add(buyPile.remove());
    }
    public Card useCard(int card) {
        return hand.remove(card);
    }
    public void discardCards(CardStack discardPile) {
        for (int i = 0; i < hand.size(); i++) {
            discardPile.add(hand.remove(0));
        }
    }
    public void restoreCards(CardStack discardPile, CardStack buyPile) {
        discardPile.shuffle();
        while(!discardPile.isEmpty()) {
            buyPile.add(discardPile.remove());
        }
    }
    public int nCards() {
        return hand.size();
    }

    public PlayerHand() {
        this.hand = new ArrayList<>();
    }
}
