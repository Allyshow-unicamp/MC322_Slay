import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mc322_slay.EventEnum;
import mc322_slay.effect.PsychicEffect;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;
import mc322_slay.event.Battle;

/**
 * Testes de {@link mc322_slay.effect.PsychicEffect}: dano no fim do turno do dono (herói ou anjo).
 */
public class PsychicEffectTest {
    @Test
    public void beNotifiedNotFinishedEffect() {
        Hero hero = new Hero("Herói", 50, 20, null, null);

        PsychicEffect psychicEffect = new PsychicEffect("poison", 20, 3);
        psychicEffect.setOwner(hero);

        Battle battle = battleFor(hero);

        assertFalse(psychicEffect.beNotified(EventEnum.playerEndOfTurn, battle));
    }

    @Test
    public void beNotifiedFinishedEffect() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new PsychicEffect("poison", 20, 1));
        PsychicEffect poison = (PsychicEffect) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertTrue(poison.beNotified(EventEnum.playerEndOfTurn, battle));
        assertEquals(30, hero.getHealth());
        assertFalse(hero.hasEffect(PsychicEffect.class));
    }

    @Test
    public void beNotifiedWrongEvent() {
        Hero hero = new Hero("Herói", 80, 0, null, null);
        hero.applyEffect(new PsychicEffect("poison", 25, 2));
        PsychicEffect poison = (PsychicEffect) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertEquals(80, hero.getHealth());
        assertEquals(2, poison.getPoints());

        assertFalse(poison.beNotified(EventEnum.playerStartOfTurn, battle));
        assertEquals(80, hero.getHealth());
        assertEquals(2, poison.getPoints());
    }

    @Test
    public void beNotifiedPlayerEndOfTurn() {
        Hero hero = new Hero("Herói", 80, 0, null, null);
        hero.applyEffect(new PsychicEffect("poison", 25, 2));
        PsychicEffect poison = (PsychicEffect) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertFalse(poison.beNotified(EventEnum.playerEndOfTurn, battle));
        assertEquals(55, hero.getHealth());
        assertEquals(1, poison.getPoints());
        assertTrue(hero.hasEffect(PsychicEffect.class));
    }

    @Test
    public void beNotifiedEnemyEndOfTurn() {
        Enemy angel = new Enemy("Anjo", 200, 0, 20, 40, null);
        angel.applyEffect(new PsychicEffect("poison", 25, 2));
        PsychicEffect poison = (PsychicEffect) angel.getLastEffect();
        Battle battle = new Battle(angel);

        assertFalse(poison.beNotified(EventEnum.enemyEndOfTurn, battle));
        assertEquals(175, angel.getHealth());
        assertEquals(1, poison.getPoints());
        assertTrue(angel.hasEffect(PsychicEffect.class));
    }

    private static Battle battleFor(Hero hero) {
        return new Battle(new Enemy("Anjo", 200, 100, 20, 40, null));
    }
}
