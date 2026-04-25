package mc322_slay.visitor;

import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.effect.PsychicEffect;

public interface EffectVisitor {
    public void visit(ATFieldCorrosion effect);
    public void visit(HealthRegeneration effect);
    public void visit(HighSyncRate effect);
    public void visit(LowSyncRate effect);
    public void visit(PsychicEffect effect);
}
