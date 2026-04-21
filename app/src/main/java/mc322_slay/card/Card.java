package mc322_slay.card;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import mc322_slay.entity.Entity;

/**
 * Classe base para cartas jogáveis.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DamageCard.class, name = "damage"),
        @JsonSubTypes.Type(value = ShieldCard.class, name = "shield"),
        @JsonSubTypes.Type(value = EffectCard.class, name = "effect")
})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Card {

    /** Nome exibido na mão e nas mensagens de uso. */
    @JsonProperty("name")
    protected String name;
    /** Custo em sincronização (energia) para jogar a carta no turno atual. */
    @JsonProperty("cost")
    protected int energyCost;
    /** Texto de ajuda exibido ao jogador. */
    protected String cardDescription;

    /**
     * Aplica o efeito da carta em uma entidade.
     *
     * @param entity entidade alvo da carta.
     * @param amount magnitude associada ao uso da carta.
     */
    public abstract void useCard(Entity player, Entity enemy);

    /**
     * Retorna o nome da carta.
     *
     * @return nome da carta.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retorna o custo de energia da carta.
     *
     * @return custo em energia.
     */
    public int getCost() {
        return this.energyCost;
    }

    /**
     * Retorna a descrição da carta.
     *
     * @return texto descritivo da carta.
     */
    public String getDescription() {
        return this.cardDescription;
    }
}
