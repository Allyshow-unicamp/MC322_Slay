package mc322_slay.card;

import java.util.Random;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.entity.Entity;
import mc322_slay.serializer.DamageCardSerializer;

/**
 * Carta que causa dano direto ao alvo.
 */
@JsonSerialize(using = DamageCardSerializer.class)
public class DamageCard extends Card {

    private int multiplier = 10;

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Aplica dano na entidade alvo.
     *
     * @param entity entidade que receberá o dano.
     * @param damage valor de dano aplicado.
     */
    @Override
    public void useCard(Entity player, Entity enemy) {
        Random random = new Random();
        int damage = getCost() * multiplier + random.nextInt(getCost() * multiplier);
        if (player.hasEffect(HighSyncRate.class)) {
            damage = (int) (player.getBoost() * damage);
        }
        if (player.hasEffect(LowSyncRate.class)) {
            damage = (int) (player.getDeboost() * damage);
        }

        Interface.printMessage(player.getName() + " usa " + getName() + " contra " + enemy.getName() + ".", ColorEnum.green);

        enemy.takeDamage(damage);
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
