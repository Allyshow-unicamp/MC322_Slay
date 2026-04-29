import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mc322_slay.card.DamageCard;
import mc322_slay.card.EffectCard;
import mc322_slay.card.ShieldCard;
import mc322_slay.effect.PsychicEffect;
import mc322_slay.visitor.EffectVisitor;
import mc322_slay.visitor.UpgradeEffectVisitor;
import mc322_slay.visitor.UpgradeVisitor;
import mc322_slay.visitor.Visitor;

/**
 * Testes unitários das classes do padrão Visitor para validação de
 * comportamento de melhoria em cartas e efeitos.
 */
public class VisitorTest {
    
    @Test
    public void upgradeVisitorDamageCard() {
        UpgradeVisitor visitor = new UpgradeVisitor();
        DamageCard card = new DamageCard("Espada", 2);
        
        int initialMultiplier = card.getMultiplier();
        visitor.visit(card);
        
        // Verifica se o multiplicador foi aumentado
        assertTrue(card.getMultiplier() > initialMultiplier);
        assertEquals(initialMultiplier * 2, card.getMultiplier());
    }
    
    @Test
    public void upgradeVisitorShieldCard() {
        UpgradeVisitor visitor = new UpgradeVisitor();
        ShieldCard card = new ShieldCard("Escudo", 1);
        
        int initialMultiplier = card.getMultiplier();
        visitor.visit(card);
        
        // Verifica se o multiplicador foi aumentado
        assertTrue(card.getMultiplier() > initialMultiplier);
    }
    
    @Test
    public void upgradeEffectVisitorPsychicEffect() {
        UpgradeEffectVisitor visitor = new UpgradeEffectVisitor();
        PsychicEffect effect = new PsychicEffect("Veneno", 5, 3);
        
        int initialDamage = effect.getDamage();
        visitor.visit(effect);
        
        // Verifica se o dano foi aumentado
        assertTrue(effect.getDamage() > initialDamage);
        assertEquals(initialDamage * 2, effect.getDamage());
    }
    
    @Test
    public void upgradeEffectVisitorHealthRegeneration() {
        UpgradeEffectVisitor visitor = new UpgradeEffectVisitor();
        mc322_slay.effect.HealthRegeneration effect = new mc322_slay.effect.HealthRegeneration("Cura", 8, 3);
        
        int initialHealth = effect.getHealth();
        visitor.visit(effect);
        
        // Verifica se a regeneração foi aumentada
        assertTrue(effect.getHealth() > initialHealth);
        assertEquals(initialHealth * 2, effect.getHealth());
    }
    
    @Test
    public void upgradeEffectVisitorLowSyncRate() {
        UpgradeEffectVisitor visitor = new UpgradeEffectVisitor();
        mc322_slay.effect.LowSyncRate effect = new mc322_slay.effect.LowSyncRate("Fraqueza", 3, 0.75);
        
        double initialDeboost = effect.getDeboost();
        visitor.visit(effect);
        
        // Verifica se o deboost foi aumentado (torna mais fraco)
        assertTrue(effect.getDeboost() < initialDeboost);
    }
    
    @Test
    public void cardAcceptVisitor() {
        UpgradeVisitor visitor = new UpgradeVisitor();
        
        // Testa DamageCard
        DamageCard damageCard = new DamageCard("Espada", 2);
        int initialMultiplier = damageCard.getMultiplier();
        damageCard.accept(visitor);
        assertTrue(damageCard.getMultiplier() > initialMultiplier);
        
        // Testa ShieldCard
        ShieldCard shieldCard = new ShieldCard("Escudo", 1);
        initialMultiplier = shieldCard.getMultiplier();
        shieldCard.accept(visitor);
        assertTrue(shieldCard.getMultiplier() > initialMultiplier);
        
        // Testa EffectCard
        PsychicEffect effect = new PsychicEffect("Veneno", 5, 3);
        EffectCard effectCard = new EffectCard("Carta Venenosa", 2, effect);
        int initialDamage = ((PsychicEffect) effectCard.getEffect()).getDamage();
        effectCard.accept(visitor);
        assertTrue(((PsychicEffect) effectCard.getEffect()).getDamage() > initialDamage);
    }
    
    @Test
    public void effectAcceptEffectVisitor() {
        UpgradeEffectVisitor visitor = new UpgradeEffectVisitor();
        
        // Testa PsychicEffect
        PsychicEffect psychicEffect = new PsychicEffect("Veneno", 5, 3);
        int initialDamage = psychicEffect.getDamage();
        psychicEffect.accept(visitor);
        assertTrue(psychicEffect.getDamage() > initialDamage);
        
        // Testa HealthRegeneration
        mc322_slay.effect.HealthRegeneration healthEffect = new mc322_slay.effect.HealthRegeneration("Cura", 8, 3);
        int initialHealth = healthEffect.getHealth();
        healthEffect.accept(visitor);
        assertTrue(healthEffect.getHealth() > initialHealth);
        
        // Testa HighSyncRate
        mc322_slay.effect.HighSyncRate highSyncEffect = new mc322_slay.effect.HighSyncRate("Força", 3, 1.5);
        double initialBoost = highSyncEffect.getBoost();
        highSyncEffect.accept(visitor);
        assertTrue(highSyncEffect.getBoost() > initialBoost);
        
        // Testa LowSyncRate
        mc322_slay.effect.LowSyncRate lowSyncEffect = new mc322_slay.effect.LowSyncRate("Fraqueza", 3, 0.75);
        double initialDeboost = lowSyncEffect.getDeboost();
        lowSyncEffect.accept(visitor);
        assertTrue(lowSyncEffect.getDeboost() < initialDeboost);
    }
    
    @Test
    public void visitorPatternConsistency() {
        UpgradeVisitor cardVisitor = new UpgradeVisitor();
        UpgradeEffectVisitor effectVisitor = new UpgradeEffectVisitor();
        
        // Verifica se os visitantes são instâncias corretas
        assertNotNull(cardVisitor);
        assertNotNull(effectVisitor);
        assertTrue(cardVisitor instanceof Visitor);
        assertTrue(effectVisitor instanceof EffectVisitor);
    }
}
