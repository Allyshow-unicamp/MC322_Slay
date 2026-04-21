package mc322_slay.entity;

import java.util.ArrayList;

import mc322_slay.card.CardStack;

/**
 * Representa o personagem controlado pelo jogador.
 */
public class Hero extends Entity {

    private CardStack deck;

    public CardStack getDeck() {
        return deck;
    }

    public void setDeck(CardStack deck) {
        this.deck = deck;
    }

    /**
     * Define o nome do herói.
     *
     * @param newName novo nome do personagem.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Reseta o escudo do herói para zero ao fim do turno.
     */
    public void resetShield() {
        this.ATField = 0;
    }

    /**
     * Remove todos os efeitos ativos do herói (usado entre batalhas na campanha).
     */
    public void resetEffects() {
        this.effects.removeAll(effects);
    }

    /**
     * Cria um herói com atributos iniciais.
     *
     * @param name       nome inicial.
     * @param health     vida inicial.
     * @param ATField    valor inicial do campo AT (escudo).
     * @param imageAsset arte associada ao herói.
     */
    public Hero(String name, int health, int ATField, CardStack deck, String imageAsset) {
        this.name = name;
        this.health = health;
        this.ATField = ATField;
        this.effects = new ArrayList<>();
        this.deck = deck;
        this.imageAsset = imageAsset;
        this.maxHealth = health;
    }
}
