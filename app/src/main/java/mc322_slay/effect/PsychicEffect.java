package mc322_slay.effect;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mc322_slay.Battle;
import mc322_slay.ColorEnum;
import mc322_slay.EventEnum;
import mc322_slay.Interface;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;
import mc322_slay.serializer.PsychicEffectSerializer;

/**
 * Representa um efeito de dano psicológico aplicado a uma entidade.
 * Este efeito causa dano ao dono no final de seu turno.
 */
@JsonSerialize(using = PsychicEffectSerializer.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PsychicEffect extends Effect {

    /** Quantidade de dano psicológico causado por turno. */
    @JsonProperty("damage")
    private int damage;

    /**
     * Retorna descrição textual do efeito.
     *
     * @return texto com nome e turnos restantes.
     */
    @Override
    public String getString() {
        return (name + " (" + points + " turnos restantes) - Causa " + damage + " de dano por turno");
    }

    /**
     * Define o dano psicológico aplicado por turno.
     *
     * @param damage valor do dano por ativação.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Retorna o dano psicológico aplicado por turno.
     *
     * @return valor do dano por ativação.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Causa dano ao dono no fim do turno correspondente: fim do turno do jogador se o dono for {@link Hero},
     * ou fim do turno do inimigo se o dono for {@link Enemy}.
     *
     * @param event  evento do jogo.
     * @param battle batalha atual.
     * @return {@code true} se o efeito expirou e deve ser removido dos inscritos.
     */
    @Override
    public boolean beNotified(EventEnum event, Battle battle) {
        if (event == EventEnum.playerEndOfTurn && this.owner.getClass() == Hero.class ||
                event == EventEnum.enemyEndOfTurn && this.owner.getClass() == Enemy.class) {
            // damage inflicted to entity at the end of his turn, if it is under this effect
            Interface.printMessage(owner.getName() + " sofre dano psicológico.", ColorEnum.yellow);
            this.owner.takeDamage(damage);

            this.points -= 1;
            
            if (this.points == 0) { // effect is over
                owner.removeEffect(this);
                return true;
            }
        }
        return false;
    }

    /**
     * Construtor da classe PsychicEffect.
     * 
     * @param name   Nome do efeito.
     * @param damage Dano causado por turno.
     * @param turns  Duração em turnos.
     */
    public PsychicEffect(String name, int damage, int turns) {
        this.name = name;
        this.damage = damage;
        this.points = turns;
        this.startPoints = turns;
    }

    /**
     * Cria uma cópia superficial de outro efeito psicológico (nome, dano e
     * duração).
     *
     * @param effect instância a copiar.
     */
    public PsychicEffect(PsychicEffect effect) {
        this.name = effect.name;
        this.damage = effect.damage;
        this.points = effect.points;
        this.startPoints = effect.startPoints;
    }

    public PsychicEffect() {
        super();
    }

    @Override
    public PsychicEffect cloneEffect() {
        return new PsychicEffect(this);
    }

    @Override
    public void merge(Effect effect) {
        PsychicEffect tE = (PsychicEffect) effect;
        this.setDamage(Math.max(tE.getDamage(), this.getDamage()));
    }
}
