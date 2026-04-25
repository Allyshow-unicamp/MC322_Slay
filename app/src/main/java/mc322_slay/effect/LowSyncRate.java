package mc322_slay.effect;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mc322_slay.EventEnum;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;
import mc322_slay.event.Battle;
import mc322_slay.serializer.LowSyncRateSerializer;
import mc322_slay.visitor.EffectVisitor;

/**
 * Efeito que reduz (ou altera) o multiplicador {@link mc322_slay.entity.Entity#getDeboost()} do herói,
 * diminuindo o dano das cartas de dano enquanto ativo (vide {@link mc322_slay.event.Battle}).
 * A duração é decrementada ao fim do turno do jogador quando o dono é o herói.
 */
@JsonSerialize(using = LowSyncRateSerializer.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LowSyncRate extends Effect {

    @JsonProperty("deboost")
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

    @Override
    public String getDescription() {
        return "Dano causado é multiplicado por " + deboost + " (desconsiderando danos oriundos de efeitos) durante "
                + points + " turnos.";
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
     * Construtor vazio para desserialização.
     */
    public LowSyncRate() {
        super();
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

    /**
     * Mescla baixa sincronização mantendo o menor fator (maior penalidade).
     *
     * @param effect outro efeito do mesmo tipo.
     */
    @Override
    public void merge(Effect effect) {
        LowSyncRate tE = (LowSyncRate) effect;
        this.setDeboost(Math.min(tE.getDeboost(), this.getDeboost()));
    }
    
    public void accept(EffectVisitor visitor) {
        visitor.visit(this);
    }
}
