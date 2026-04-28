package mc322_slay.relic;

import mc322_slay.ColorEnum;
import mc322_slay.EventEnum;
import mc322_slay.Interface;
import mc322_slay.entity.Hero;
import mc322_slay.event.Battle;

public class AngelNucleus extends Relic {

    public AngelNucleus(int value) {
        this.name = "Núcleo de Anjo";
        this.value = value;
        this.description = "Poder corrompido. No final do teu turno, sacrifica "+value+" de Vida para gerar "+5*value+" de AT Field.";
    }

    @Override
    public void update(EventEnum event, Battle battle, Hero hero) {
        if (event == EventEnum.playerEndOfTurn) {
            // Verifica se o herói tem mais de 1 de vida para evitar que a relíquia o mate acidentalmente
            if (hero.getHealth() > value) {
                Interface.printMessage("O [" + name + "] pulsa de forma sinistra... Consome 1 de HP para gerar 5 de AT Field!", ColorEnum.purple);
                
                // Aplica o dano e o escudo
                hero.takeDamage(value); 
                hero.gainATField(5*value);
            }
        }
    }
}