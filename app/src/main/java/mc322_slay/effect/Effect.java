package mc322_slay.effect;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import mc322_slay.EventEnum;
import mc322_slay.entity.Entity;
import mc322_slay.event.Battle;
import mc322_slay.visitor.EffectVisitor;

/**
 * Classe base para efeitos temporários aplicados a {@link Entity}.
 * Os efeitos podem reagir a {@link mc322_slay.EventEnum eventos} da batalha e são
 * clonados ao serem aplicados pela primeira vez.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ATFieldCorrosion.class, name = "corrosion"),
    @JsonSubTypes.Type(value = HealthRegeneration.class, name = "regeneration"),
    @JsonSubTypes.Type(value = HighSyncRate.class, name = "strength"),
    @JsonSubTypes.Type(value = LowSyncRate.class, name = "weakness"),
    @JsonSubTypes.Type(value = PsychicEffect.class, name = "poison"),
})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Effect {
    /** Nome exibido na interface e nas mensagens de status. */
    @JsonProperty("name")
    protected String name;
    /** Entidade sobre a qual o efeito está ativo; pode ser {@code null} antes de {@link Entity#applyEffect(Effect)}. */
    protected Entity owner;
    /**
     * Pontos restantes (em geral turnos ou cargas); o significado exato depende da subclasse
     * (ex.: duração em turnos para dano psicológico).
     */
    @JsonProperty("turns")
    protected int points;
    /** Valor inicial de {@link #points} quando o efeito foi aplicado (útil para empilhar e mesclar). */
    @JsonProperty("startTurns")
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

    public void setStartPoints(int points) {
        this.startPoints += points;
    }

    public void setPoints(int points) {
        this.points += points;
    }

    /**
     * Retorna representação textual amigável do efeito.
     *
     * @return texto resumido do efeito.
     */
    public abstract String getString();

    /**
     * Retorna descrição funcional do efeito para telas de inspeção/cartas.
     *
     * @return texto descritivo do efeito.
     */
    public abstract String getDescription();

    /**
     * Reage a um evento do jogo.
     *
     * @param event   evento recebido.
     * @param battle  contexto da batalha atual (para efeitos que precisem consultar o estado).
     * @return {@code true} quando o efeito expirou e deve ser removido da lista de inscritos da {@link Battle}.
     */
    public abstract boolean beNotified(EventEnum event, Battle battle);

    /**
     * Cria uma cópia do efeito para nova aplicação em uma entidade (sem compartilhar estado mutável indevido).
     *
     * @return nova instância configurada como este protótipo.
     */
    public abstract Effect cloneEffect();

    /**
     * Mescla valores de outro efeito do mesmo tipo quando {@link Entity#applyEffect(Effect)} empilha duplicatas.
     *
     * @param effect outra instância do mesmo tipo lógico (ex.: maior cura, maior boost).
     */
    public abstract void merge(Effect effect);

    public abstract void accept(EffectVisitor visitor);
}
