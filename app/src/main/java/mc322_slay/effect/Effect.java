package mc322_slay.effect;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.entity.Entity;

/**
 * Classe base para efeitos temporários aplicados em entidades.
 */
public abstract class Effect {
    protected String name;
    protected Entity owner;
    protected int points;
    protected int startPoints;

    /**
     * Retorna os pontos restantes de duração/intensidade do efeito.
     *
     * @return pontos atuais.
     */
    public int getPoints() {
        return this.points;    
    }
    /**
     * Retorna a quantidade inicial de pontos do efeito.
     *
     * @return pontos iniciais.
     */
    public int getStartPoints() {
        return this.startPoints;
    }
    /**
     * Incrementa os pontos restantes do efeito.
     *
     * @param points quantidade a ser adicionada.
     */
    public void incrementPoints(int points) {
        this.points += points;
    } 
    /**
     * Retorna o nome do efeito.
     *
     * @return nome do efeito.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Retorna a entidade dona do efeito.
     *
     * @return entidade proprietária.
     */
    public Entity getOwner() {
        return owner;
    }
    /**
     * Define a entidade dona do efeito.
     *
     * @param owner nova entidade proprietária.
     */
    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    /**
     * Retorna representação textual amigável do efeito.
     *
     * @return texto resumido do efeito.
     */
    public abstract String getString();

    /**
     * Reage a um evento do jogo.
     *
     * @param event evento recebido.
     * @param gameManager estado atual do jogo.
     * @return {@code true} quando o efeito deve ser removido dos inscritos.
     */
    public abstract boolean beNotified(EventEnum event, GameManager gameManager);
}
