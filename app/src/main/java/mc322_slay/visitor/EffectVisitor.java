package mc322_slay.visitor;

import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.effect.PsychicEffect;

/**
 * Visitor para operações sobre tipos concretos de efeito.
 */
public interface EffectVisitor {
    /**
     * Visita efeito de corrosão de AT Field.
     *
     * @param effect efeito visitado.
     */
    public void visit(ATFieldCorrosion effect);
    /**
     * Visita efeito de regeneração de vida.
     *
     * @param effect efeito visitado.
     */
    public void visit(HealthRegeneration effect);
    /**
     * Visita efeito de alta sincronização.
     *
     * @param effect efeito visitado.
     */
    public void visit(HighSyncRate effect);
    /**
     * Visita efeito de baixa sincronização.
     *
     * @param effect efeito visitado.
     */
    public void visit(LowSyncRate effect);
    /**
     * Visita efeito de dano psicológico.
     *
     * @param effect efeito visitado.
     */
    public void visit(PsychicEffect effect);
}
