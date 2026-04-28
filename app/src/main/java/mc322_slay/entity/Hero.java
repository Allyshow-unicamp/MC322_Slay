package mc322_slay.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.card.CardStack;
import mc322_slay.relic.Relic;

/**
 * Representa o personagem controlado pelo jogador.
 */
public class Hero extends Entity {

    /** Relíquias passivas do herói na campanha. */
    @JsonProperty("relics")
    private List<Relic> relics = new ArrayList<>();

    /** Baralho permanente do herói na campanha. */
    private CardStack deck;

    /**
     * @return baralho atual do herói.
     */
    public CardStack getDeck() {
        return deck;
    }

    /**
     * @param deck novo baralho do herói.
     */
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
     * @return lista de relíquias do herói.
     */
    public List<Relic> getRelics() {
        return relics;
    }

    /**
     * Adiciona uma nova relíquia ao herói.
     * @param relic relíquia adquirida.
     */
    public void addRelic(Relic relic) {
        this.relics.add(relic);
        Interface.printMessage("Você obteve a relíquia: " + relic.getName() + " - " + relic.getDescription(), ColorEnum.green);
    }

    public void replaceRelic(int index, Relic newRelic) {
        if (index >= 0 && index < relics.size()) {
            relics.set(index, newRelic);
        } 
    }

    /**
     * Cria um herói com atributos iniciais.
     *
     * @param name       nome inicial.
     * @param health     vida inicial.
     * @param ATField    valor inicial do campo AT (escudo).
     * @param deck       baralho inicial do herói.
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
