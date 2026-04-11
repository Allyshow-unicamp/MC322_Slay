package mc322_slay.effect;

import mc322_slay.Battle;
import mc322_slay.ColorEnum;
import mc322_slay.EventEnum;
import mc322_slay.Interface;
import mc322_slay.entity.Hero;

/**
 * Efeito que aumenta temporariamente o multiplicador de dano das cartas de dano do herói
 * (via {@link mc322_slay.entity.Entity#getBoost()}), sem alterar dano de efeitos como veneno psíquico.
 */
public class HighSyncRate extends Effect {

    /** Multiplicador aplicado ao dano das armas/cartas de dano (ex.: {@code 1.5} para +50%). */
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

    /**
     * Decrementa a duração ao fim do turno do jogador quando o dono é o herói.
     *
     * @param event  evento do jogo.
     * @param battle batalha atual.
     * @return {@code true} se o efeito expirou e deve ser removido dos inscritos.
     */
    @Override
    public boolean beNotified(EventEnum event, Battle battle) {
        if (event == EventEnum.playerEndOfTurn && owner.getClass() == Hero.class) {
            this.points -= 1;
            if (this.points > 0) {
                // Interface.printMessage(points + " turnos restantes de " + name + " sobre " +
                // owner.getName() + ".",
                // ColorEnum.reset);
            }

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
