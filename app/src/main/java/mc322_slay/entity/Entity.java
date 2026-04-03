package mc322_slay.entity;

import java.util.ArrayList;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.Effect;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.effect.PsychicEffect;

/**
 * Representa uma entidade base no jogo, podendo ser o herói ou um inimigo.
 * Gerencia atributos comuns como vida, escudo (AT Field) e efeitos aplicados.
 */
public abstract class Entity {

    protected double boost = 1;
    protected double deboost = 1;

    /** Nome da entidade. */
    protected String name;

    /** Vida atual da entidade. */
    protected int health;

    /** Vida máxima da entidade. */
    protected int maxHealth;

    /**
     * Valor atual do escudo (AT Field). Funciona de forma idêntica ao atributo de
     * escudo.
     */
    protected int ATField;

    /** Valor máximo do escudo (AT Field). */
    protected int maxATField;

    /** Lista de efeitos aplicados à entidade. */
    protected ArrayList<Effect> effects;

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
        this.health = (health + amount > maxHealth ? maxHealth : health + amount);
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
     * Obtém o valor máximo do escudo (AT Field).
     * 
     * @return O valor máximo do escudo.
     */
    public int getMaxShield() {
        return this.maxATField;
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

    public double getBoost() {
        return boost;
    }

    public void setBoost(double boost) {
        this.boost = boost;
    }

    public double getDeboost() {
        return deboost;
    }

    public void setDeboost(double deboost) {
        this.deboost = deboost;
    }

    /**
     * Verifica se a entidade ainda está viva (vida > 0).
     * 
     * @return True se estiver viva, False caso contrário.
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
     * @param points A quantidade de pontos/intensidade do efeito.
     */
    public void applyEffect(Effect effect, int points) {
        Effect thisEffect = effect;
        boolean contains = false;
        int index = 0;
        for (Effect effectX : effects) {
            if (effectX.getClass() == effect.getClass()) {
                contains = true;
                thisEffect = effectX;
                break;
            }
            index++;
        }
        if (contains) {
            Effect e = effects.get(index);
            e.incrementPoints(points);
            if (e instanceof HealthRegeneration healthRegeneration) {

                HealthRegeneration hE = healthRegeneration;
                HealthRegeneration tE = (HealthRegeneration) thisEffect;
                hE.setHealth(Math.max(hE.getHealth(), tE.getHealth()));

            } else if (e instanceof PsychicEffect psychicEffect) {

                PsychicEffect pE = psychicEffect;
                PsychicEffect tE = (PsychicEffect) thisEffect;
                pE.setDamage(Math.max(pE.getDamage(), tE.getDamage()));

            } else if (e instanceof HighSyncRate highSyncRate) {

                HighSyncRate hE = highSyncRate;
                HighSyncRate tE = (HighSyncRate) thisEffect;
                hE.setBoost(Math.max(hE.getBoost(), tE.getBoost()));
    
            } else if (e instanceof LowSyncRate lowSyncRate) {

                LowSyncRate lE = lowSyncRate;
                LowSyncRate tE = (LowSyncRate) thisEffect;
                lE.setDeboost(Math.min(lE.getDeboost(), tE.getDeboost()));
    
            }
            effects.set(index, e);
        } else {
            Effect newEffect;
            if (effect instanceof ATFieldCorrosion aTFieldCorrosion) {
                newEffect = new ATFieldCorrosion(aTFieldCorrosion);
            } else if (effect instanceof HealthRegeneration healthRegeneration) {
                newEffect = new HealthRegeneration(healthRegeneration);
            } else if (effect instanceof HighSyncRate highSyncRate) {
                newEffect = new HighSyncRate(highSyncRate);
                this.setBoost(highSyncRate.getBoost());
            } else if (effect instanceof LowSyncRate lowSyncRate) {
                newEffect = new LowSyncRate(lowSyncRate);
                this.setDeboost(lowSyncRate.getDeboost());
            } else {
                newEffect = new PsychicEffect((PsychicEffect) effect);
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
            Interface.printMessage(this.name + " está sob efeito de " + effect.getString() + ".", ColorEnum.yellow);
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
