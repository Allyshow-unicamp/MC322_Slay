import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mc322_slay.Battle;
import mc322_slay.EventEnum;
import mc322_slay.card.CardStack;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

/**
 * Testes de {@link mc322_slay.effect.HealthRegeneration}: cura no início do turno do dono (herói ou
 * anjo) e expiração.
 */
public class HealthRegenerationTest {
    @Test
    public void beNotifiedNotFinishedEffect() {
        Hero hero = new Hero("Herói", 50, 20, null);

        HealthRegeneration healthRegeneration = new HealthRegeneration("Regeneração", 20, 3);
        healthRegeneration.setOwner(hero);

        Battle battle = battleFor(hero);

        assertFalse(healthRegeneration.beNotified(EventEnum.playerStartOfTurn, battle));
    }

    @Test
    public void beNotifiedFinishedEffect() {
        Hero hero = new Hero("Herói", 50, 20, null);
        hero.takeDamage(55);
        hero.applyEffect(new HealthRegeneration("Regeneração", 20, 1));
        HealthRegeneration regen = (HealthRegeneration) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertTrue(regen.beNotified(EventEnum.playerStartOfTurn, battle));
        assertEquals(35, hero.getHealth());
        assertFalse(hero.hasEffect(HealthRegeneration.class));
    }

    @Test
    public void beNotifiedWrongEvent() {
        Hero hero = new Hero("Herói", 50, 0, null);
        hero.takeDamage(35);
        hero.applyEffect(new HealthRegeneration("Regeneração", 20, 2));
        HealthRegeneration regen = (HealthRegeneration) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertEquals(15, hero.getHealth());
        assertEquals(2, regen.getPoints());

        assertFalse(regen.beNotified(EventEnum.playerEndOfTurn, battle));
        assertEquals(15, hero.getHealth());
        assertEquals(2, regen.getPoints());
    }

    @Test
    public void beNotifiedPlayerStartOfTurn() {
        Hero hero = new Hero("Herói", 50, 0, null);
        hero.takeDamage(35);
        hero.applyEffect(new HealthRegeneration("Regeneração", 20, 2));
        HealthRegeneration regen = (HealthRegeneration) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertFalse(regen.beNotified(EventEnum.playerStartOfTurn, battle));
        assertEquals(35, hero.getHealth());
        assertEquals(1, regen.getPoints());
        assertTrue(hero.hasEffect(HealthRegeneration.class));
    }

    @Test
    public void beNotifiedEnemyStartOfTurn() {
        Hero hero = new Hero("Herói", 50, 0, null);
        Enemy angel = new Enemy("Anjo", 200, 100, 20, 40, null);
        angel.takeDamage(200);
        angel.applyEffect(new HealthRegeneration("Regeneração", 20, 2));
        HealthRegeneration regen = (HealthRegeneration) angel.getLastEffect();
        Battle battle = new Battle(hero, angel, new CardStack());

        assertFalse(regen.beNotified(EventEnum.enemyStartOfTurn, battle));
        assertEquals(120, angel.getHealth());
        assertEquals(1, regen.getPoints());
        assertTrue(angel.hasEffect(HealthRegeneration.class));
    }

    private static Battle battleFor(Hero hero) {
        return new Battle(hero, new Enemy("Anjo", 200, 100, 20, 40, null), new CardStack());
    }
}
