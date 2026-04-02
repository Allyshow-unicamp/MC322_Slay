package mc322_slay.entity;

import java.util.ArrayList;

/**
 * Representa o personagem controlado pelo jogador.
 */
public class Hero extends Entity {

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
    public void resetShield(){
        this.ATField = 0;
    }
    /**
     * Cria um herói com atributos iniciais.
     *
     * @param name nome inicial.
     * @param health vida inicial.
     * @param ATField escudo inicial.
     * @param imageAsset arte associada ao herói.
     */
    public Hero(String name, int health, int ATField, String imageAsset) {
        this.name = name;
        this.health = health;
        this.ATField = ATField;
        this.effects = new ArrayList<>();
        this.imageAsset = imageAsset;
        this.maxHealth = health;
    }
}
