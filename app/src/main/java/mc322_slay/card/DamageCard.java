package mc322_slay.card;

import java.util.Random;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.entity.Entity;
import mc322_slay.serializer.DamageCardSerializer;
import mc322_slay.visitor.Visitor;

/**
 * Carta que causa dano direto ao alvo.
 */
@JsonSerialize(using = DamageCardSerializer.class)
public class DamageCard extends Card {

    /** Multiplicador base aplicado sobre o custo para calcular dano. */
    private int multiplier = 10;

    /**
     * @return multiplicador de dano atual da carta.
     */
    public int getMultiplier() {
        return multiplier;
    }

    /**
     * @param multiplier novo multiplicador base de dano da carta.
     */
    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Aplica dano na entidade alvo.
     *
     * @param player entidade que joga a carta.
     * @param enemy entidade alvo que receberá o dano.
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

    /**
     * Construtor vazio para desserialização.
     */
    public DamageCard() {
        super();
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
