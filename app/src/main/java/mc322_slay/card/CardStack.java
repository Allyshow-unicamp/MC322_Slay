package mc322_slay.card;

import java.util.Collections;
import java.util.Stack;

/**
 * Representa uma pilha de cartas com operações de compra, descarte e embaralhamento.
 */
public class CardStack {
    
    private Stack<Card> cardsStack;

    /**
     * Adiciona uma carta ao topo da pilha.
     *
     * @param card carta a ser adicionada.
     */
    public void add(Card card) {
        cardsStack.push(card);
    }
    /**
     * Remove e retorna a carta do topo da pilha.
     *
     * @return carta removida.
     */
    public Card remove() {
        Card removedCard = cardsStack.pop();
        return removedCard;
    }
    /**
     * Embaralha as cartas da pilha.
     */
    public void shuffle() {
        Collections.shuffle(cardsStack);
    }
    /**
     * Indica se a pilha está vazia.
     *
     * @return {@code true} se não houver cartas.
     */
    public boolean isEmpty() {
        return cardsStack.empty();
    }

    /**
     * Cria uma pilha de cartas vazia.
     */
    public CardStack() {
        this.cardsStack = new Stack<>();
    }

    public CardStack(CardStack stack) {
        this.cardsStack = new Stack<>();
        for (Card card : stack.cardsStack) {
            this.cardsStack.add(card);
        }
    }
}
