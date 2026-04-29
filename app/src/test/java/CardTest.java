import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mc322_slay.card.DamageCard;
import mc322_slay.card.EffectCard;
import mc322_slay.card.ShieldCard;
import mc322_slay.effect.PsychicEffect;
import mc322_slay.entity.Hero;
import mc322_slay.entity.Enemy;

/**
 * Testes unitários das classes de cartas para validação de comportamento,
 * cálculo de dano/escudo e aplicação de efeitos.
 */
public class CardTest {
    
    @Test
    public void damageCardCreationAndGetters() {
        DamageCard card = new DamageCard("Espada Laser", 3);
        
        assertEquals("Espada Laser", card.getName());
        assertEquals(3, card.getCost());
        assertEquals(10, card.getMultiplier()); // valor padrão
        assertNotNull(card.getDescription());
        assertTrue(card.getDescription().contains("30") && card.getDescription().contains("60"));
    }
    
    @Test
    public void damageCardUseCard() {
        DamageCard card = new DamageCard("Espada Laser", 2);
        Hero hero = new Hero("Herói", 50, 0, null, null);
        Enemy enemy = new Enemy("Inimigo", 60, 0, 20, 40, null);
        
        int initialHealth = enemy.getHealth();
        card.useCard(hero, enemy);
        
        // Verifica se o inimigo recebeu dano (deve estar entre 20 e 40)
        assertTrue(enemy.getHealth() < initialHealth);
        assertTrue(enemy.getHealth() >= initialHealth - 40);
    }
    
    @Test
    public void damageCardWithMultiplier() {
        DamageCard card = new DamageCard("Super Ataque", 3);
        card.setMultiplier(15);
        
        assertEquals(15, card.getMultiplier());
        assertTrue(card.getDescription().contains("45") && card.getDescription().contains("90"));
    }
    
    @Test
    public void shieldCardCreationAndGetters() {
        ShieldCard card = new ShieldCard("Barreira AT", 2);
        
        assertEquals("Barreira AT", card.getName());
        assertEquals(2, card.getCost());
        assertEquals(4, card.getMultiplier()); // valor padrão
        assertNotNull(card.getDescription());
        assertTrue(card.getDescription().contains("8") && card.getDescription().contains("16"));
    }
    
    @Test
    public void shieldCardUseCard() {
        ShieldCard card = new ShieldCard("Barreira AT", 1);
        Hero hero = new Hero("Herói", 50, 0, null, null);
        Enemy enemy = new Enemy("Inimigo", 60, 0, 20, 40, null);
        
        int initialShield = hero.getShield();
        card.useCard(hero, enemy);
        
        // Verifica se o herói ganhou escudo (deve estar entre 4 e 8)
        assertTrue(hero.getShield() > initialShield);
        assertTrue(hero.getShield() >= initialShield + 4);
        assertTrue(hero.getShield() <= initialShield + 8);
    }
    
    @Test
    public void shieldCardWithMultiplier() {
        ShieldCard card = new ShieldCard("Super Barreira", 2);
        card.setMultiplier(6);
        
        assertEquals(6, card.getMultiplier());
        assertTrue(card.getDescription().contains("12") && card.getDescription().contains("24"));
    }
    
    @Test
    public void effectCardCreationAndGetters() {
        PsychicEffect effect = new PsychicEffect("Veneno Mental", 5, 3);
        EffectCard card = new EffectCard("Carta Venenosa", 2, effect);
        
        assertEquals("Carta Venenosa", card.getName());
        assertEquals(2, card.getCost());
        assertEquals(effect, card.getEffect());
        assertEquals(effect.getDescription(), card.getDescription());
    }
    
    @Test
    public void effectCardUseCardWithDamageEffect() {
        PsychicEffect effect = new PsychicEffect("Veneno Mental", 5, 3);
        EffectCard card = new EffectCard("Carta Venenosa", 2, effect);
        Hero hero = new Hero("Herói", 50, 0, null, null);
        Enemy enemy = new Enemy("Inimigo", 60, 0, 20, 40, null);
        
        card.useCard(hero, enemy);
        
        // Verifica se o inimigo recebeu o efeito
        assertTrue(enemy.hasEffect(effect.getClass()));
        assertEquals(1, enemy.getEffects().size());
    }
    
    @Test
    public void effectCardUseCardWithBeneficialEffect() {
        mc322_slay.effect.HealthRegeneration effect = new mc322_slay.effect.HealthRegeneration("Cura", 10, 3);
        EffectCard card = new EffectCard("Carta de Cura", 2, effect);
        Hero hero = new Hero("Herói", 30, 0, null, null);
        Enemy enemy = new Enemy("Inimigo", 60, 0, 20, 40, null);
        
        card.useCard(hero, enemy);
        
        // Efeitos benéficos devem ser aplicados ao herói
        assertTrue(hero.hasEffect(effect.getClass()));
        assertEquals(1, hero.getEffects().size());
    }
    
    @Test
    public void damageCardConstructorVazio() {
        DamageCard card = new DamageCard();
        
        assertNotNull(card);
        assertEquals(10, card.getMultiplier()); // valor padrão
    }
    
    @Test
    public void shieldCardConstructorVazio() {
        ShieldCard card = new ShieldCard();
        
        assertNotNull(card);
        assertEquals(4, card.getMultiplier()); // valor padrão
    }
    
    @Test
    public void effectCardConstructorVazio() {
        EffectCard card = new EffectCard();
        
        assertNotNull(card);
    }
}
