import java.util.Stack;
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
    public CardStack() {
        this.cardsStack = new Stack<Card>();
    }
}
