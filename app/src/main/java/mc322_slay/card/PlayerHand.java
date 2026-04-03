package mc322_slay.card;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia a mão de cartas do jogador.
 */
public class PlayerHand {
    private List<Card> hand;

    /**
     * Retorna as cartas atualmente na mão.
     *
     * @return lista de cartas na mão.
     */
    public List<Card> getHand() {
        return hand;
    }
    /**
     * Compra uma carta da pilha de compra, restaurando-a quando necessário.
     *
     * @param buyPile pilha principal de compra.
     * @param discardPile pilha de descarte para restauração.
     */
    public void buyCard(CardStack buyPile, CardStack discardPile) {
        if (buyPile.isEmpty()) {
            restoreCards(discardPile, buyPile);
        }
        hand.add(buyPile.remove());
    }
    /**
     * Remove da mão a carta escolhida para uso.
     *
     * @param card índice da carta.
     * @return carta removida da mão.
     */
    public Card useCard(int card) {
        return hand.remove(card);
    }
    /**
     * Descarta todas as cartas da mão.
     *
     * @param discardPile pilha que receberá as cartas descartadas.
     */
    public void discardCards(CardStack discardPile) {
        while (!hand.isEmpty()) {
            discardPile.add(hand.remove(0));
        }
    }
    /**
     * Move cartas do descarte para a pilha de compra após embaralhar.
     *
     * @param discardPile pilha de descarte.
     * @param buyPile pilha de compra.
     */
    public void restoreCards(CardStack discardPile, CardStack buyPile) {
        discardPile.shuffle();
        while(!discardPile.isEmpty()) {
            buyPile.add(discardPile.remove());
        }
    }
    /**
     * Consulta o custo de energia de uma carta da mão.
     *
     * @param card índice da carta.
     * @return custo de energia da carta.
     */
    public int seeCardCost(int card) {
        Card Card = hand.get(card);
        int cost = Card.getCost();
        return cost;
    }
    /**
     * Retorna a quantidade atual de cartas na mão.
     *
     * @return número de cartas disponíveis.
     */
    public int nCards() {
        return hand.size();
    }

    /**
     * Cria uma mão vazia para o jogador.
     */
    public PlayerHand() {
        this.hand = new ArrayList<>();
    }
}
