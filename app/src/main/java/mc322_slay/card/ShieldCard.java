package mc322_slay.card;

import java.util.Random;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.entity.Entity;
import mc322_slay.serializer.ShieldCardSerializer;
import mc322_slay.visitor.Visitor;

/**
 * Carta que concede escudo (AT Field) ao alvo.
 */
@JsonSerialize(using = ShieldCardSerializer.class)
public class ShieldCard extends Card {

    /** Multiplicador base aplicado sobre o custo para calcular escudo ganho. */
    private int multiplier = 4;

    /**
     * @return multiplicador de escudo atual da carta.
     */
    public int getMultiplier() {
        return multiplier;
    }

    /**
     * @param multiplier novo multiplicador base de escudo da carta.
     */
    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Aumenta o campo AT (escudo) da entidade alvo.
     *
     * @param player entidade que joga/recebe o escudo.
     * @param enemy parâmetro não utilizado por cartas defensivas.
     */
    @Override
    public void useCard(Entity player, Entity enemy) {
        Random random = new Random();
        int shield = getCost() * multiplier + random.nextInt(getCost() * multiplier);
        Interface.printMessage(player.getName() + " usa " + getName() + " em si mesm*.", ColorEnum.green);
        player.gainATField(shield);
    }

    /**
     * Cria uma carta de escudo.
     *
     * @param name            nome da carta.
     * @param energyCost      custo de energia para uso.
     * @param cardDescription descrição exibida ao jogador.
     */
    public ShieldCard(String name, int energyCost) {
        this.energyCost = energyCost;
        this.name = name;
        this.cardDescription = "Use-a para restaurar entre " + this.energyCost * multiplier + " e "
                + 2 * this.energyCost * multiplier + " do seu campo AT (escudo).";
    }

    @Override 
    public String getDescription() {
        return "Use-a para restaurar entre " + this.energyCost * multiplier + " e "
                + 2 * this.energyCost * multiplier + " do seu campo AT (escudo).";
    }

    /**
     * Construtor vazio para desserialização.
     */
    public ShieldCard() {
        super();
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
