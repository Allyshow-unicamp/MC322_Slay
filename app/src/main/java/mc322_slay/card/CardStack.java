package mc322_slay.card;

import java.util.Collections;
import java.util.Stack;

public class CardStack {
    
    private Stack<Card> cardsStack;

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

    public CardStack() {
        this.cardsStack = new Stack<>();
    }
}
