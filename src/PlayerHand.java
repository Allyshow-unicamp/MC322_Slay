import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PlayerHand {
    List<Card> hand;

    public void showHand() {
        System.out.println("""
        ==============\r\n
        Mão do Jogador\r\n
        ==============\r\n
        \r\n""");

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);

            System.out.println("===== Carta " + i + " =====");
            System.out.println("\r\nNome: " + card.getName());
            System.out.println("\r\nCusto: " + card.getCost());
            System.out.println("\r\nDescrição: " + card.getDescription());
            System.out.println("\r\n\r\n");
        }
    }
    public void buyCard(Stack<Card> buyPile) {
        hand.add(buyPile.remove(0));
    }
    public Card useCard(int card) {
        return hand.remove(card);
    }
    public void discardCards(Stack<Card> discardPile) {
        for (int i = 0; i < hand.size(); i++) {
            discardPile.add(hand.remove(0));
        }
    }
    public int nCards() {
        return hand.size();
    }

    public PlayerHand() {
        this.hand = new ArrayList<>();
    }
}
