package mc322_slay.card;

import mc322_slay.entity.Entity;

/**
 * Carta que causa dano direto ao alvo.
 */
public class DamageCard extends Card{
    
    /**
     * Aplica dano na entidade alvo.
     *
     * @param entity entidade que receberá o dano.
     * @param damage valor de dano aplicado.
     */
    @Override
    public void useCard(Entity entity, int damage) {
        entity.takeDamage(damage);
    }

    /**
     * Cria uma carta de dano.
     *
     * @param name nome da carta.
     * @param energyCost custo de energia para uso.
     * @param cardDescription descrição exibida ao jogador.
     */
    public DamageCard(String name, int energyCost, String cardDescription) {
        this.name = name;
        this.energyCost = energyCost;
        this.cardDescription = cardDescription;
    }
}
