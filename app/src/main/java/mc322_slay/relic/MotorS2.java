package mc322_slay.relic;

import mc322_slay.ColorEnum;
import mc322_slay.EventEnum;
import mc322_slay.Interface;
import mc322_slay.entity.Hero;
import mc322_slay.event.Battle;

public class MotorS2 extends Relic {

    public MotorS2(int value) {
        this.name = "Motor S2";
        this.value = value;
        this.description = "Fonte de energia infinita. No início do seu turno, recupera "+ value +" pontos de Vida e ganha "+ value +" pontos de AT Field.";
    }

    @Override
    public void update(EventEnum event, Battle battle, Hero hero) {
        if (event == EventEnum.playerStartOfTurn) {
            Interface.printMessage("O [" + name + "] pulsa no peito do seu EVA! Você regenera vida e escudo.", ColorEnum.red);
            hero.gainHealth(value);
            hero.gainATField(value);
        }
    }
}