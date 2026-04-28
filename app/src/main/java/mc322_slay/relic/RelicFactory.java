package mc322_slay.relic;

import java.util.Random;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;

public class RelicFactory {
    
    static Random rand = new Random();

    static Relic loot;

    static public Relic createRandomRelic() {
        int sorteio = rand.nextInt(4);

                if (sorteio == 0) {
                    loot = new AngelNucleus(rand.nextInt(4));
                } else if (sorteio == 1) {
                    loot = new BloodVial(rand.nextInt(5, 11));
                } else if (sorteio == 2) {
                    loot = new SDATPlayer(rand.nextInt(5, 31));
                } else {
                    loot = new MotorS2(rand.nextInt(5, 16));
                } 
        
        return loot;
    }

    static public boolean worthyOfRelic(int enemyDifficulty) {
        // --- LÓGICA DE DROP DE RELÍQUIA ---
        int prob = rand.nextInt(299, 400); // Representando a probabilidade como um inteiro
        int thisprob = enemyDifficulty;
        // quanto maior a dificuldade do inimigo maiores as chances de drop de uma relíquia.
        if (thisprob >= prob) {
            Interface.printMessage("\nO inimigo era formidável! Ele deixou um artefato para trás...", ColorEnum.purple);
            
            return true;
        }
        return false;
    }
}
