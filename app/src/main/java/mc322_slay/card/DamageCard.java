package mc322_slay.card;

import mc322_slay.entity.Entity;

/**
 * Carta que causa dano direto ao alvo.
 */
public class DamageCard extends Card{
    
    /**
     * Aplica dano na entidade alvo.
     *
     * @param angel entidade que receberá dano.
     * @param damage valor de dano aplicado.
     */
    @Override
    public void useCard(Entity angel, int damage) {
        angel.takeDamage(damage);
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
