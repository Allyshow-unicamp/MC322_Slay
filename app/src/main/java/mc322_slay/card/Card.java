package mc322_slay.card;

import mc322_slay.entity.Entity;

/**
 * Classe base para cartas jogáveis.
 */
public abstract class Card {

    /** Nome exibido na mão e nas mensagens de uso. */
    protected String name;
    /** Custo em sincronização (energia) para jogar a carta no turno atual. */
    protected int energyCost;
    /** Texto de ajuda exibido ao jogador. */
    protected String cardDescription;

    /**
     * Aplica o efeito da carta em uma entidade.
     *
     * @param entity entidade alvo da carta.
     * @param amount magnitude associada ao uso da carta.
     */
    public abstract void useCard(Entity entity, int amount);

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
