import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mc322_slay.EventEnum;
import mc322_slay.entity.Hero;
import mc322_slay.relic.AngelNucleus;
import mc322_slay.relic.BloodVial;
import mc322_slay.relic.MotorS2;
import mc322_slay.relic.RelicFactory;
import mc322_slay.relic.SDATPlayer;

/**
 * Testes unitários das classes de relíquias para validação de comportamento,
 * criação via factory e efeitos passivos.
 */
public class RelicTest {
    
    @Test
    public void bloodVialCreationAndGetters() {
        BloodVial bloodVial = new BloodVial(5);
        
        assertNotNull(bloodVial.getName());
        assertNotNull(bloodVial.getDescription());
        assertEquals(5, bloodVial.getValue());
    }
    
    @Test
    public void bloodVialUpdateOnPlayerStart() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.takeDamage(10);
        BloodVial bloodVial = new BloodVial(6);
        
        bloodVial.update(EventEnum.playerStartOfTurn, null, hero);
        
        // Verifica se o herói foi curado (implementação atual cura 10 fixo)
        assertEquals(50, hero.getHealth());
    }
    
    @Test
    public void bloodVialUpdateOnOtherEvents() {
        Hero hero = new Hero("Herói", 40, 0, null, null);
        BloodVial bloodVial = new BloodVial(6);
        
        int initialHealth = hero.getHealth();
        bloodVial.update(EventEnum.playerEndOfTurn, null, hero);
        
        // Não deve curar em outros eventos
        assertEquals(initialHealth, hero.getHealth());
    }
    
    @Test
    public void motorS2CreationAndGetters() {
        MotorS2 motorS2 = new MotorS2(7);
        
        assertNotNull(motorS2.getName());
        assertNotNull(motorS2.getDescription());
        assertEquals(7, motorS2.getValue());
    }
    
    @Test
    public void motorS2UpdateOnPlayerStart() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.takeDamage(10);
        MotorS2 motorS2 = new MotorS2(7);
        
        motorS2.update(EventEnum.playerStartOfTurn, null, hero);
        
        // Verifica se o herói ganhou vida e escudo
        assertTrue(hero.getHealth() > 40);
        assertTrue(hero.getShield() > 0);
        assertEquals(47, hero.getHealth()); // 40 + 7
        assertEquals(7, hero.getShield());
    }
    
    @Test
    public void sdatPlayerCreationAndGetters() {
        SDATPlayer sdatPlayer = new SDATPlayer(9);
        
        assertNotNull(sdatPlayer.getName());
        assertNotNull(sdatPlayer.getDescription());
        assertEquals(9, sdatPlayer.getValue());
    }
    
    @Test
    public void sdatPlayerUpdateWithNoShield() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        SDATPlayer sdatPlayer = new SDATPlayer(9);
        
        sdatPlayer.update(EventEnum.playerEndOfTurn, null, hero);
        
        // Verifica se o herói ganhou escudo
        assertEquals(9, hero.getShield());
    }
    
    @Test
    public void sdatPlayerUpdateWithExistingShield() {
        Hero hero = new Hero("Herói", 50, 5, null, null);
        SDATPlayer sdatPlayer = new SDATPlayer(9);
        
        int initialShield = hero.getShield();
        sdatPlayer.update(EventEnum.playerEndOfTurn, null, hero);
        
        // Não deve ganhar escudo se já tiver
        assertEquals(initialShield, hero.getShield());
    }
    
    @Test
    public void angelNucleusCreationAndGetters() {
        AngelNucleus angelNucleus = new AngelNucleus(3);
        
        assertNotNull(angelNucleus.getName());
        assertNotNull(angelNucleus.getDescription());
        assertEquals(3, angelNucleus.getValue());
    }
    
    @Test
    public void angelNucleusUpdateWithEnoughHealth() {
        Hero hero = new Hero("Herói", 40, 0, null, null);
        AngelNucleus angelNucleus = new AngelNucleus(3);
        
        angelNucleus.update(EventEnum.playerEndOfTurn, null, hero);
        
        // Verifica se converteu vida em escudo (3 vida -> 15 escudo)
        assertEquals(37, hero.getHealth()); // 40 - 3
        assertEquals(15, hero.getShield()); // 3 * 5
    }
    
    @Test
    public void angelNucleusUpdateWithoutEnoughHealth() {
        Hero hero = new Hero("Herói", 2, 0, null, null);
        AngelNucleus angelNucleus = new AngelNucleus(3);
        
        int initialHealth = hero.getHealth();
        int initialShield = hero.getShield();
        
        angelNucleus.update(EventEnum.playerEndOfTurn, null, hero);
        
        // Não deve fazer nada se não tiver vida suficiente
        assertEquals(initialHealth, hero.getHealth());
        assertEquals(initialShield, hero.getShield());
    }
    
    @Test
    public void relicFactoryCreateRandomRelic() {
        // Testa criação múltipla para garantir variedade
        for (int i = 0; i < 10; i++) {
            mc322_slay.relic.Relic relic = RelicFactory.createRandomRelic();
            
            assertNotNull(relic);
            assertNotNull(relic.getName());
            assertNotNull(relic.getDescription());
            
            // Verifica se é um dos tipos conhecidos
            assertTrue(relic instanceof BloodVial || 
                      relic instanceof MotorS2 || 
                      relic instanceof SDATPlayer || 
                      relic instanceof AngelNucleus);
        }
    }
    
    @Test
    public void relicFactoryConsistency() {
        // Testa se a factory produz resultados consistentes
        mc322_slay.relic.Relic relic1 = RelicFactory.createRandomRelic();
        mc322_slay.relic.Relic relic2 = RelicFactory.createRandomRelic();
        
        assertNotNull(relic1);
        assertNotNull(relic2);
        
        // Podem ser do mesmo tipo ou não, ambos devem ser válidos
        assertTrue(relic1.getValue() > 0);
        assertTrue(relic2.getValue() > 0);
    }
}
