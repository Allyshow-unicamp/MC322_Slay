package mc322_slay.card;

import mc322_slay.entity.Entity;

/**
 * Carta que concede escudo (AT Field) ao alvo.
 */
public class ShieldCard extends Card{

    /**
     * Aumenta o escudo da entidade alvo.
     *
     * @param hero entidade que receberá escudo.
     * @param amount quantidade de escudo aplicada.
     */
    @Override
    public void useCard(Entity hero, int amount) {
        hero.gainATField(amount);
    }

    /**
     * Cria uma carta de escudo.
     *
     * @param name nome da carta.
     * @param energyCost custo de energia para uso.
     * @param cardDescription descrição exibida ao jogador.
     */
    public ShieldCard(String name, int energyCost, String cardDescription) {
        this.energyCost = energyCost;
        this.name = name;
        this.cardDescription = cardDescription;
    }
}
