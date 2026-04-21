package mc322_slay.card;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.effect.Effect;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.entity.Entity;
import mc322_slay.serializer.EffectCardSerializer;

/**
 * Carta que aplica um efeito contínuo ao alvo.
 */
@JsonSerialize(using = EffectCardSerializer.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class EffectCard extends Card {

    /**
     * Efeito persistente aplicado ao usar a carta (duração/intensidade conforme a
     * subclasse).
     */
    @JsonProperty("effect")
    private Effect effect;

    /**
     * Aplica o efeito configurado desta carta em uma entidade.
     *
     * @param entity entidade alvo.
     * @param points duração ou intensidade do efeito.
     */
    @Override
    public void useCard(Entity player, Entity enemy) {
        if (effect instanceof HealthRegeneration || effect instanceof HighSyncRate) {
            Interface.printMessage(player.getName() + " usa " + getName() + " em si mesm*.", ColorEnum.green);
            player.applyEffect(effect);
        } else {
            Interface.printMessage(player.getName() + " usa " + getName() + " contra " + enemy.getName() + ".", ColorEnum.green);
            enemy.applyEffect(effect);
        }
    }

    /**
     * Retorna o efeito associado à carta.
     *
     * @return efeito encapsulado.
     */
    public Effect getEffect() {
        return effect;
    }

    /**
     * Cria uma carta de efeito.
     *
     * @param name            nome da carta.
     * @param energyCost      custo de energia para uso.
     * @param cardDescription descrição exibida ao jogador.
     * @param effect          efeito aplicado ao usar a carta.
     */
    public EffectCard(String name, int energyCost, Effect effect) {
        this.name = name;
        this.energyCost = energyCost;
        this.effect = effect;
        this.cardDescription = effect.getDescription();
    }

    @Override
    public String getDescription() {
        return effect.getDescription();
    }

    public EffectCard() {
        super();
    }
}
