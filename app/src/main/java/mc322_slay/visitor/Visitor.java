package mc322_slay.visitor;

import mc322_slay.card.DamageCard;
import mc322_slay.card.EffectCard;
import mc322_slay.card.ShieldCard;

public interface Visitor {
    public void visit(DamageCard card);
    public void visit(ShieldCard card);
    public void visit(EffectCard card);
}
