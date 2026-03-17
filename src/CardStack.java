import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;

public class CardStack {
    
    Stack<Card> cardsStack;

    public void add(Card card) {
        cardsStack.push(card);
    }
    public Card remove() {
        Card removedCard = cardsStack.pop();
        return removedCard;
    }
    public void shuffle() {
        Collections.shuffle(cardsStack);
    }
    public boolean isEmpty() {
        return cardsStack.empty();
    }
    public CardStack(ArrayList<Card> cardsList) {
        this.cardsStack = new Stack<Card>();
        for (int i = 0; i < cardsList.size(); i++) {
            cardsStack.push(cardsList.removeFirst());
        };
    }
}
