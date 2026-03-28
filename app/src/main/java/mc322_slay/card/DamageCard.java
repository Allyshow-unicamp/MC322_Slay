package mc322_slay.card;

import mc322_slay.entity.Entity;

public class DamageCard extends Card{
    
    @Override
    public void useCard(Entity angel, int damage) {
        angel.takeDamage(damage);
    }

    public DamageCard(String name, int energyCost, String cardDescription) {
        this.name = name;
        this.energyCost = energyCost;
        this.cardDescription = cardDescription;
    }
}
