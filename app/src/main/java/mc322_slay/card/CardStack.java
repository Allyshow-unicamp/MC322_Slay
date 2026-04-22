package mc322_slay.card;

import java.util.Collections;
import java.util.Stack;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mc322_slay.serializer.CardStackSerializer;

/**
 * Representa uma pilha de cartas com operações de compra, descarte e embaralhamento.
 */
@JsonSerialize(using = CardStackSerializer.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CardStack {
    /** Estrutura LIFO com as cartas da pilha. */
    @JsonProperty("cards")
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

    /**
     * @return pilha interna de cartas.
     */
    public Stack<Card> getStack() {
        return cardsStack;
    }

    /**
     * Copia shallow: empilha as mesmas instâncias de {@link Card} de outra pilha
     * (ordem preservada conforme iteração sobre a pilha interna).
     *
     * @param stack pilha a copiar (tipicamente o baralho principal do {@link mc322_slay.GameManager}).
     */
    public CardStack(CardStack stack) {
        this.cardsStack = new Stack<>();
        for (Card card : stack.cardsStack) {
            this.cardsStack.add(card);
        }
    }
}
