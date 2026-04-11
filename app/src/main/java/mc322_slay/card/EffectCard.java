package mc322_slay.card;

import mc322_slay.effect.Effect;
import mc322_slay.entity.Entity;

/**
 * Carta que aplica um efeito contínuo ao alvo.
 */
public class EffectCard extends Card{

    private Effect effect;
    
    /**
     * Aplica o efeito configurado desta carta em uma entidade.
     *
     * @param entity entidade alvo.
     * @param points duração ou intensidade do efeito.
     */
    @Override
    public void useCard(Entity entity, int points) {
        entity.applyEffect(effect);
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
     * @param name nome da carta.
     * @param energyCost custo de energia para uso.
     * @param cardDescription descrição exibida ao jogador.
     * @param effect efeito aplicado ao usar a carta.
     */
    public EffectCard(String name, int energyCost, String cardDescription, Effect effect) {
        this.name = name;
        this.energyCost = energyCost;
        this.cardDescription = cardDescription;
        this.effect = effect;
    }
}
