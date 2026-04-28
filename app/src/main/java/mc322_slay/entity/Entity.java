package mc322_slay.entity;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.Effect;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.LowSyncRate;

/**
 * Representa uma entidade base no jogo, podendo ser o herói ou um inimigo.
 * Gerencia atributos comuns como vida, escudo (AT Field) e efeitos aplicados.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Enemy.class, name = "enemy"),
        @JsonSubTypes.Type(value = Hero.class, name = "hero"),
})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Entity {
    /** Multiplicador ofensivo aplicado no cálculo de dano da entidade. */
    protected double boost = 1;
    /** Multiplicador secundário aplicado junto ao boost (debuff/buff adicional). */
    protected double deboost = 1;

    @JsonProperty("name")
    /** Nome da entidade. */
    protected String name;

    @JsonProperty("health")
    /** Vida atual da entidade. */
    protected int health;

    /** Vida máxima da entidade. */
    @JsonProperty("maxHealth")
    protected int maxHealth;

    @JsonProperty("shield")
    /** Valor atual do campo AT (escudo) que absorve dano antes da vida. */
    protected int ATField;

    @JsonProperty("effects")
    /** Lista de efeitos aplicados à entidade. */
    protected ArrayList<Effect> effects;

    @JsonProperty("image")
    /** Caminho ou nome do recurso de imagem associado à entidade. */
    protected String imageAsset;

    /**
     * Obtém o nome da entidade.
     * 
     * @return O nome da entidade.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Obtém a vida atual da entidade.
     * 
     * @return A vida atual.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Aumenta a vida da entidade, respeitando o limite máximo de vida.
     * 
     * @param amount A quantidade de vida a ser ganha.
     */
    public void gainHealth(int amount) {
        int increment = (health + amount > maxHealth ? maxHealth - health : amount);
        if (increment <= 0) {
            return;
        }
        Interface.printMessage(this.name + " recuperou " + increment  + " de vida.", ColorEnum.yellow);
        this.health += increment;
    }

    /**
     * Obtém a vida máxima da entidade.
     * 
     * @return A vida máxima.
     */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Obtém o valor atual do escudo (AT Field).
     * 
     * @return O valor do escudo.
     */
    public int getShield() {
        return this.ATField;
    }

    /**
     * Obtém o caminho ou nome do recurso de imagem associado à entidade.
     * 
     * @return O recurso de imagem.
     */
    public String getImage() {
        return this.imageAsset;
    }

    /**
     * Obtém a lista de efeitos aplicados à entidade.
     * 
     * @return A lista de efeitos.
     */
    public ArrayList<Effect> getEffects() {
        return this.effects;
    }

    /**
     * Obtém o último efeito adicionado à entidade.
     * 
     * @return O último efeito.
     */
    public Effect getLastEffect() {
        return this.effects.getLast();
    }

    /**
     * Multiplicador de dano ao causar dano no combate: no herói, aplicado ao dano das
     * cartas de dano; no anjo, combinado com {@link #getDeboost()} em {@link Enemy#attack(Hero)}.
     *
     * @return fator multiplicativo; padrão {@code 1.0}.
     */
    public double getBoost() {
        return boost;
    }

    /**
     * @param boost novo multiplicador de dano ofensivo desta entidade.
     */
    public void setBoost(double boost) {
        this.boost = boost;
    }

    /**
     * Segundo multiplicador usado no cálculo de dano: no herói, aplicado junto com
     * {@link #getBoost()} ao resolver cartas de dano; no anjo, aplicado em {@link Enemy#attack(Hero)}.
     * Valores menores que {@code 1.0} reduzem o dano final.
     *
     * @return fator multiplicativo; padrão {@code 1.0}.
     */
    public double getDeboost() {
        return deboost;
    }

    /**
     * @param deboost novo fator multiplicativo (ex.: efeitos de baixa sincronização).
     */
    public void setDeboost(double deboost) {
        this.deboost = deboost;
    }

    /**
     * Verifica se a entidade ainda está viva ({@code vida > 0}).
     *
     * @return {@code true} se ainda houver vida; caso contrário {@code false}.
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    /**
     * Aumenta o valor do escudo (AT Field) da entidade.
     * 
     * @param amount A quantidade de escudo a ser ganha.
     */
    public void gainATField(int amount) {
        if (amount <= 0) {
            return;
        }
        Interface.printMessage(this.getName() + " recuperou " + amount + " de campo AT.", ColorEnum.yellow);
        this.ATField = ATField + amount;
    }

    /**
     * Processa o dano recebido pela entidade, considerando o escudo (AT Field)
     * e possíveis efeitos como corrosão de AT Field.
     * 
     * @param damage A quantidade de dano a ser processada.
     */
    public void takeDamage(int damage) {
        if (this.ATField > 0 && !hasEffect(ATFieldCorrosion.class)) {
            if (damage > ATField) {
                int newHealth = (health - (damage - ATField)) >= 0 ? health - (damage - ATField) : 0;
                Interface.printMessage(this.name + " leva " + (health - newHealth)
                        + " de dano.", ColorEnum.yellow);
                this.health = newHealth;
            }
            int newField = (ATField - damage) >= 0 ? ATField - damage : 0;
            Interface.printMessage("O campo AT (escudo) de " + this.name + " absorve " + (ATField - newField)
                    + " de dano.", ColorEnum.yellow);
            this.ATField = newField;
        } else {
            int newHealth = (health - damage) >= 0 ? health - damage : 0;
            Interface.printMessage(this.name + " leva " + (health - newHealth)
                    + " de dano.", ColorEnum.yellow);
            this.health = newHealth;
        }
    }

    /**
     * Aplica um efeito à entidade ou incrementa os pontos se o efeito já existir.
     * 
     * @param effect O efeito a ser aplicado.
     */
    public void applyEffect(Effect effect) {
        boolean contains = false;
        int index = 0;
        for (Effect effectX : effects) {
            if (effectX.getClass() == effect.getClass()) {
                contains = true;
                break;
            }
            index++;
        }
        if (contains) {
            Effect e = effects.get(index);
            e.incrementPoints(e.getStartPoints());
            e.merge(effect);
            if (e instanceof HighSyncRate highSyncRate) {
                this.setBoost(highSyncRate.getBoost());
            } else if (e instanceof LowSyncRate lowSyncRate) {
                this.setDeboost(lowSyncRate.getDeboost());
            }
            effects.set(index, e);
        } else {
            Effect newEffect = effect.cloneEffect();
            if (newEffect instanceof HighSyncRate highSyncRate) {
                this.setBoost(highSyncRate.getBoost());
            } else if (newEffect instanceof LowSyncRate lowSyncRate) {
                this.setDeboost(lowSyncRate.getDeboost());
            }
            newEffect.setOwner(this);
            effects.add(newEffect);
        }

        Interface.printMessage(this.name + " está sob efeito de " + effect.getString() + ".", ColorEnum.yellow);
    }

    /**
     * Remove um efeito da entidade.
     * 
     * @param effect O efeito a ser removido.
     */
    public void removeEffect(Effect effect) {
        boolean result = effects.remove(effect);
        effect.setOwner(null);
        if (result)
            Interface.printMessage(this.name + " não está mais sob o efeito de " + effect.getName() + ".",
                    ColorEnum.yellow);
    }

    /**
     * Verifica se a entidade possui um efeito de uma determinada classe.
     * 
     * @param effectX A classe do efeito a ser verificado.
     * @return True se possuir o efeito, False caso contrário.
     */
    public boolean hasEffect(Class<? extends Effect> effectX) {
        for (Effect effect : this.effects) {
            if (effectX.isInstance(effect)) {
                return true;
            }
        }
        return false;
    }
}
