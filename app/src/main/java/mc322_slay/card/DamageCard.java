package mc322_slay.card;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mc322_slay.entity.Entity;
import mc322_slay.serializer.DamageCardSerializer;

/**
 * Carta que causa dano direto ao alvo.
 */
@JsonSerialize(using = DamageCardSerializer.class)
public class DamageCard extends Card {

    public static final int multiplier = 10;

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
     * @param name            nome da carta.
     * @param energyCost      custo de energia para uso.
     * @param cardDescription descrição exibida ao jogador.
     */
    public DamageCard(String name, int energyCost) {
        this.name = name;
        this.energyCost = energyCost;
        this.cardDescription = "Use-a para dar entre " + energyCost * multiplier + " e " + 2 * energyCost * multiplier
                + " de dano.";
    }

    @Override
    public String getDescription() {
        return "Use-a para dar entre " + energyCost * multiplier + " e " + 2 * energyCost * multiplier + " de dano.";
    }

    public DamageCard() {
        super();
    }
}
