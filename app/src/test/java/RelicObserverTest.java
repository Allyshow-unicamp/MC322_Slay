import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import mc322_slay.EventEnum;
import mc322_slay.entity.Hero;
import mc322_slay.relic.AngelNucleus;
import mc322_slay.relic.BloodVial;
import mc322_slay.relic.MotorS2;
import mc322_slay.relic.SDATPlayer;

/**
 * Testes das relíquias baseadas em Observer:
 * cada relíquia deve reagir apenas aos eventos relevantes.
 */
public class RelicObserverTest {

    @Test
    public void bloodVialOnlyTriggersOnPlayerStart() {
        Hero hero = new Hero("Shinji", 50, 0, null, null);
        hero.takeDamage(20); // vida atual = 30
        BloodVial bloodVial = new BloodVial(6);

        bloodVial.update(EventEnum.playerEndOfTurn, null, hero);
        assertEquals(30, hero.getHealth());

        bloodVial.update(EventEnum.playerStartOfTurn, null, hero);
        // Implementação atual cura 10 fixo, independente do value.
        assertEquals(40, hero.getHealth());
    }

    @Test
    public void motorS2RestoresHealthAndShieldOnPlayerStart() {
        Hero hero = new Hero("Asuka", 60, 0, null, null);
        hero.takeDamage(20); // vida atual = 40
        MotorS2 motorS2 = new MotorS2(7);

        motorS2.update(EventEnum.playerStartOfTurn, null, hero);

        assertEquals(47, hero.getHealth());
        assertEquals(7, hero.getShield());
    }

    @Test
    public void sdatPlayerOnlyAddsShieldWhenHeroHasNoShield() {
        Hero hero = new Hero("Rei", 45, 5, null, null);
        SDATPlayer sdatPlayer = new SDATPlayer(9);

        sdatPlayer.update(EventEnum.playerEndOfTurn, null, hero);
        assertEquals(5, hero.getShield());

        hero.resetShield();
        sdatPlayer.update(EventEnum.playerEndOfTurn, null, hero);
        assertEquals(9, hero.getShield());
    }

    @Test
    public void angelNucleusConsumesHealthAndGeneratesShieldWhenEnoughHealth() {
        Hero hero = new Hero("Mari", 40, 0, null, null);
        AngelNucleus angelNucleus = new AngelNucleus(3);

        angelNucleus.update(EventEnum.playerEndOfTurn, null, hero);

        assertEquals(37, hero.getHealth());
        assertEquals(15, hero.getShield());
    }

    @Test
    public void angelNucleusDoesNothingWhenHealthIsNotGreaterThanValue() {
        Hero hero = new Hero("Kaworu", 3, 0, null, null);
        AngelNucleus angelNucleus = new AngelNucleus(3);

        angelNucleus.update(EventEnum.playerEndOfTurn, null, hero);

        assertEquals(3, hero.getHealth());
        assertEquals(0, hero.getShield());
    }
}
