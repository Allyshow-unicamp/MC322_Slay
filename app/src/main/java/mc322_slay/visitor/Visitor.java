package mc322_slay.visitor;

import mc322_slay.card.DamageCard;
import mc322_slay.card.EffectCard;
import mc322_slay.card.ShieldCard;

/**
 * Visitor para operações sobre tipos concretos de carta.
 */
public interface Visitor {
    /**
     * Visita carta de dano.
     *
     * @param card carta visitada.
     */
    public void visit(DamageCard card);
    /**
     * Visita carta de escudo.
     *
     * @param card carta visitada.
     */
    public void visit(ShieldCard card);
    /**
     * Visita carta de efeito.
     *
     * @param card carta visitada.
     */
    public void visit(EffectCard card);
}
