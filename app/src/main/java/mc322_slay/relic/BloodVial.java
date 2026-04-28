package mc322_slay.relic;

import mc322_slay.ColorEnum;
import mc322_slay.EventEnum;
import mc322_slay.Interface;
import mc322_slay.entity.Hero;
import mc322_slay.event.Battle;

public class BloodVial extends Relic {

    public BloodVial(int value) {
        this.name = "Frasco de Sangue";
        this.value = value;
        this.description = "No início do seu turno, recupera "+ value +" de vida.";
    }

    @Override
    public void update(EventEnum event, Battle battle, Hero hero) {
        // A relíquia filtra o evento que importa para ela
        if (event == EventEnum.playerStartOfTurn) {
            Interface.printMessage("A relíquia [" + name + "] brilhou! Você recupera HP.", ColorEnum.green);
            hero.gainHealth(10);
        }
    }
}