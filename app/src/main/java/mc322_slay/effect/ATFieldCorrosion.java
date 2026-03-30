package mc322_slay.effect;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;

public class ATFieldCorrosion extends Effect {    
    
    @Override
    public String getString() {
        return (name + ": Anula a redução de dano do campo AT de "+ owner.getName() + " pela duração de " + points + " turnos.");
    }

    @Override
    public void beNotified(EventEnum event, GameManager gameManager) {
        if (event == EventEnum.playerEndOfTurn || event == EventEnum.enemyEndOfTurn) {
            // at the beginning of the turn the entity has their shield temporarely reduced to 0
            applyErosion(gameManager);
        } 
    }

    private void applyErosion(GameManager gameManager) {
        this.points -= 1;
        if (this.points <= 0) {
            gameManager.unsubscribe(this);
            owner.removeEffect(this);
        } else {
            System.out.println(points + " turnos restantes de "+ name + " sobre " + owner.getName() + ".");
        }
    }

    public ATFieldCorrosion(String name, int turns) {
        this.name = name;
        this.points = turns;
    }
}
