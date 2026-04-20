package mc322_slay.effect;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mc322_slay.Battle;
import mc322_slay.EventEnum;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;
import mc322_slay.serializer.HighSyncRateSerializer;

/**
 * Efeito que aumenta temporariamente o multiplicador de dano das cartas de dano
 * do herói
 * (via {@link mc322_slay.entity.Entity#getBoost()}), sem alterar dano de
 * efeitos como veneno psíquico.
 */
@JsonSerialize(using = HighSyncRateSerializer.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class HighSyncRate extends Effect {

    /**
     * Multiplicador aplicado ao dano das armas/cartas de dano (ex.: {@code 1.5}
     * para +50%).
     */
    @JsonProperty("boost")
    private double boost;

    /**
     * @return multiplicador atual de dano causado pelo herói em cartas de dano.
     */
    public double getBoost() {
        return this.boost;
    }

    /**
     * @param boost novo multiplicador de dano para cartas de dano.
     */
    public void setBoost(double boost) {
        this.boost = boost;
    }

    /**
     * Retorna descrição textual do efeito.
     *
     * @return texto com nome e turnos restantes.
     */
    @Override
    public String getString() {
        return (name + " (" + points + " turnos restantes) - Dano causado é multiplicado por " + boost
                + " (desconsiderando danos oriundos de efeitos)");
    }

    @Override
    public String getDescription() {
        return "Dano causado é multiplicado por " + boost + " (desconsiderando danos oriundos de efeitos) durante "
                + points + " turnos.";
    }

    /**
     * Decrementa a duração ao fim do turno do jogador quando o dono é o herói.
     *
     * @param event  evento do jogo.
     * @param battle batalha atual.
     * @return {@code true} se o efeito expirou e deve ser removido dos inscritos.
     */
    @Override
    public boolean beNotified(EventEnum event, Battle battle) {
        if (event == EventEnum.playerEndOfTurn && owner.getClass() == Hero.class ||
                event == EventEnum.enemyEndOfTurn && owner.getClass() == Enemy.class) {
            this.points -= 1;

            if (this.points == 0) {
                owner.removeEffect(this);
                return true;
            }
        }
        return false;
    }

    /**
     * Cria um efeito de alta sincronização.
     *
     * @param name  nome do efeito.
     * @param turns duração em turnos do jogador.
     * @param boost multiplicador de dano das cartas de dano (ex.: {@code 1.5}).
     */
    public HighSyncRate(String name, int turns, double boost) {
        this.name = name;
        this.points = turns;
        this.startPoints = turns;
        this.boost = boost;
    }

    public HighSyncRate() {
        super();
    }

    /**
     * Cria uma cópia de outro efeito de alta sincronização (nome, dono e duração).
     *
     * @param effect instância a copiar.
     */
    public HighSyncRate(HighSyncRate effect) {
        this.name = effect.name;
        this.owner = effect.owner;
        this.points = effect.points;
        this.boost = effect.boost;
        this.startPoints = effect.startPoints;
    }

    @Override
    public HighSyncRate cloneEffect() {
        return new HighSyncRate(this);
    }

    @Override
    public void merge(Effect effect) {
        HighSyncRate hE = (HighSyncRate) effect;
        this.setBoost(Math.max(hE.getBoost(), this.getBoost()));
    }
}
