import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.effect.PsychicEffect;
import mc322_slay.entity.Hero;

/**
 * Testes unitários de {@link Hero} e da lógica herdada de {@link mc322_slay.entity.Entity}:
 * dano, escudo, aplicação/remoção de efeitos e multiplicadores.
 */
public class HeroTest {
    @Test 
    public void maxHealthRespected() {
        Hero hero = new Hero("Herói", 50, 20, null, null);
        hero.takeDamage(10);
        hero.gainHealth(15);
        assertEquals(50, hero.getHealth());
    }
    
    @Test
    public void damageAbsorbedByShield() {
        Hero hero = new Hero("Herói", 50, 20, null, null);
        hero.takeDamage(15);
        assertEquals(50, hero.getHealth());
        assertEquals(5, hero.getShield());
    }

    @Test 
    public void damagePartiallyAbsorbedByShield() {
        Hero hero = new Hero("Herói", 50, 20, null, null);
        hero.takeDamage(30);
        assertEquals(40, hero.getHealth());
        assertEquals(0, hero.getShield());
    }

    @Test 
    public void damageWithNoShield() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.takeDamage(30);
        assertEquals(20, hero.getHealth());
        assertEquals(0, hero.getShield());
    }

    @Test
    public void healthAlwaysNotNegative() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.takeDamage(100);
        assertEquals(0, hero.getHealth());
        assertEquals(0, hero.getShield());
    }

    @Test 
    public void effectApplied() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new ATFieldCorrosion("Corrosão de Campo AT", 3));
        assertTrue(hero.hasEffect(ATFieldCorrosion.class));
        assertEquals("Corrosão de Campo AT", hero.getEffects().get(0).getName());
        assertEquals(3, hero.getEffects().get(0).getPoints());
        assertEquals(3, hero.getEffects().get(0).getStartPoints());
        assertEquals(hero, hero.getEffects().get(0).getOwner());
    }

    @Test 
    public void corrosionEffectPointsIncremented() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new ATFieldCorrosion("Corrosão de Campo AT", 3));
        hero.applyEffect(new ATFieldCorrosion("Corrosão de Campo AT", 3));
        assertEquals(6, hero.getEffects().get(0).getPoints());
    }

    @Test 
    public void healthRegenEffectHealthPointsIncremented() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new HealthRegeneration("Regeneração", 3, 3));
        hero.applyEffect(new HealthRegeneration("Regeneração", 5, 3));
        assertEquals(6, hero.getEffects().get(0).getPoints());
        assertEquals(5, ((HealthRegeneration) hero.getEffects().get(0)).getHealth());
    }

    @Test 
    public void poisonEffectPoisonPointsIncremented() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new PsychicEffect("Dano psicológico", 3, 3));
        hero.applyEffect(new PsychicEffect("Dano psicológico", 5, 3));
        assertEquals(6, hero.getEffects().get(0).getPoints());
        assertEquals(5, ((PsychicEffect) hero.getEffects().get(0)).getDamage());
    }

    @Test 
    public void boostApplied() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new HighSyncRate("Força", 3, 1.5));
        hero.applyEffect(new HighSyncRate("Força", 3, 2));
        assertEquals(2, hero.getBoost());
    }

    @Test 
    public void deboostApplied() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new LowSyncRate("Fraqueza", 3, 0.75));
        hero.applyEffect(new LowSyncRate("Fraqueza", 3, 0.5));
        assertEquals(0.5, hero.getDeboost());
    }

    @Test 
    public void highSyncEffectBoostIncremented() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new HighSyncRate("Força", 3, 1.5));
        hero.applyEffect(new HighSyncRate("Força", 3, 2));
        assertEquals(6, hero.getEffects().get(0).getPoints());
        assertEquals(2, ((HighSyncRate) hero.getEffects().get(0)).getBoost());
    }

    @Test 
    public void lowSyncEffectDeboostIncremented() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new LowSyncRate("Fraqueza", 3, 0.75));
        hero.applyEffect(new LowSyncRate("Fraqueza", 3, 0.5));
        assertEquals(6, hero.getEffects().get(0).getPoints());
        assertEquals(0.5, ((LowSyncRate) hero.getEffects().get(0)).getDeboost());
    }

    @Test 
    public void effectRemoved() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new ATFieldCorrosion("Corrosão de Campo AT", 3));
        hero.removeEffect(hero.getLastEffect());
        assertTrue(hero.getEffects().isEmpty());
    }

    @Test 
    public void allEffectsRemoved() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new ATFieldCorrosion("Corrosão de Campo AT", 3));
        hero.applyEffect(new LowSyncRate("Fraqueza", 3, 0.75));
        hero.applyEffect(new HighSyncRate("Força", 3, 1.5));
        hero.resetEffects();
        assertTrue(hero.getEffects().isEmpty());
    }
}
