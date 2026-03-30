package mc322_slay.effect;


import mc322_slay.EventEnum;
import mc322_slay.GameManager;

public class HealthRegeneration extends Effect {

    private int health;

    @Override
    public String getString() {
        return (name + ": Regenera " + health + " de vida por turno por " + points + " turnos.");
    }

    @Override
    public void beNotified(EventEnum event, GameManager gameManager) {
        if (event == EventEnum.playerStartOfTurn || event == EventEnum.enemyStartOfTurn) {
            regenerate(gameManager);
        }
    }

    private void regenerate(GameManager gameManager) {
        this.points -= 1;
        owner.gainHealth(health);
        if (this.points <= 0) {
            gameManager.unsubscribe(this);
            owner.removeEffect(this);
        } else {
            System.out.println(points + " turnos restantes de "+ name + " sobre " + owner.getName() + ".");
        }
    }

    public HealthRegeneration(String name, int amount, int points) {
        this.name = name;
        this.health = amount;
        this.points = points;
    }
}