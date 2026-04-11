package mc322_slay.effect;

import mc322_slay.Battle;
import mc322_slay.EventEnum;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

/**
 * Efeito que reduz (ou altera) o multiplicador {@link mc322_slay.entity.Entity#getDeboost()} do herói,
 * diminuindo o dano das cartas de dano enquanto ativo (vide {@link mc322_slay.Battle}).
 * A duração é decrementada ao fim do turno do jogador quando o dono é o herói.
 */
public class LowSyncRate extends Effect {

    private double deboost;

    /**
     * Fator pelo qual o dano de ataque direto contra o dono é multiplicado enquanto o efeito estiver ativo.
     */
    public double getDeboost() {
        return this.deboost;
    }

    /**
     * @param deboost novo fator de redução/ampliação do dano recebido em ataques diretos.
     */
    public void setDeboost(double deboost) {
        this.deboost = deboost;
    }

    @Override
    public String getString() {
        return (name + " (" + points + " turnos restantes) - Dano causado é multiplicado por " + deboost
                + " (desconsiderando danos oriundos de efeitos)");
    }

    /**
     * Decrementa a duração ao fim do turno do jogador quando o dono é o herói.
     *
     * @param event  evento do jogo.
     * @param battle batalha atual.
     * @return {@code true} se o efeito expirou e deve ser removido da lista de inscritos da {@link Battle}.
     */
    @Override
    public boolean beNotified(EventEnum event, Battle battle) {
        if (event == EventEnum.playerEndOfTurn && owner.getClass() == Hero.class || 
            event == EventEnum.enemyEndOfTurn && owner.getClass() == Enemy.class) {
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
     * @param name    nome exibido do efeito.
     * @param turns   duração em turnos do jogador.
     * @param deboost multiplicador de dano recebido em ataques diretos (ex.: {@code 0.75} para 25% a menos).
     */
    public LowSyncRate(String name, int turns, double deboost) {
        this.name = name;
        this.points = turns;
        this.startPoints = turns;
        this.deboost = deboost;
    }

    /**
     * Cópia usada ao empilhar o mesmo tipo de efeito na entidade.
     *
     * @param effect protótipo copiado.
     */
    public LowSyncRate(LowSyncRate effect) {
        this.name = effect.name;
        this.owner = effect.owner;
        this.points = effect.points;
        this.deboost = effect.deboost;
        this.startPoints = effect.startPoints;
    }

    @Override
    public LowSyncRate cloneEffect() {
        return new LowSyncRate(this);
    }

    @Override
    public void merge(Effect effect) {
        LowSyncRate tE = (LowSyncRate) effect;
        this.setDeboost(Math.min(tE.getDeboost(), this.getDeboost()));
    }
}
