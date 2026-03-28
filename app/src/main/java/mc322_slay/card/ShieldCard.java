package mc322_slay.card;

import mc322_slay.entity.Entity;

public class ShieldCard extends Card{

    @Override
    public void useCard(Entity hero, int amount) {
        hero.gainATField(amount);
    }

    public ShieldCard(String name, int energyCost, String cardDescription) {
        this.energyCost = energyCost;
        this.name = name;
        this.cardDescription = cardDescription;
    }
}
