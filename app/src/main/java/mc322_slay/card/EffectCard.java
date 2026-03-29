package mc322_slay.card;

import mc322_slay.effect.Effect;
import mc322_slay.entity.Entity;

public class EffectCard extends Card{

    private Effect effect;
    
    @Override
    public void useCard(Entity angel, int points) {
        angel.applyEffect(effect, points);
    }
    public Effect getEffect() {
        return effect;
    }

    public EffectCard(String name, int energyCost, String cardDescription, Effect effect) {
        this.name = name;
        this.energyCost = energyCost;
        this.cardDescription = cardDescription;
        this.effect = effect;
    }
}
