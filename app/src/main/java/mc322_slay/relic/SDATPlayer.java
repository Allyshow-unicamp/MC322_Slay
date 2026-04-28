package mc322_slay.relic;

import mc322_slay.ColorEnum;
import mc322_slay.EventEnum;
import mc322_slay.Interface;
import mc322_slay.entity.Hero;
import mc322_slay.event.Battle;

public class SDATPlayer extends Relic {

    public SDATPlayer(int value) {
        this.name = "Toca-Fitas SDAT";
        this.value = value;
        this.description = "Música para isolar a mente. No final do seu turno, se você estiver sem escudo, ganha até "+value+" de AT Field.";
    }

    @Override
    public void update(EventEnum event, Battle battle, Hero hero) {
        if (event == EventEnum.playerEndOfTurn) {
            if (hero.getShield() == 0) {
                Interface.printMessage("Ouvindo a faixa 26 no [" + name + "]... sua tolerância psicológica aumenta (AT Field).", ColorEnum.blue);
                hero.gainATField(value);
            }
        }
    }
}