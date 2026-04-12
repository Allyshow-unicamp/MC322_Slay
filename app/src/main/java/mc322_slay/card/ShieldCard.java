package mc322_slay.card;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mc322_slay.entity.Entity;
import mc322_slay.serializer.ShieldCardSerializer;

/**
 * Carta que concede escudo (AT Field) ao alvo.
 */
@JsonSerialize(using = ShieldCardSerializer.class)
public class ShieldCard extends Card{

    /**
     * Aumenta o campo AT (escudo) da entidade alvo.
     *
     * @param entity entidade que receberá o escudo.
     * @param amount quantidade de pontos de campo AT concedidos.
     */
    @Override
    public void useCard(Entity entity, int amount) {
        entity.gainATField(amount);
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

    public ShieldCard() {
        super();
    }
}
