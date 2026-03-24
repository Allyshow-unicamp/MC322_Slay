public abstract class Effect {
    protected String name;
    protected Entity owner;
    protected int points;

    public int getPoints() {
        return this.points;    
    }
    public int incrementPoints(int points) {
        this.points += points;
    } 

    public abstract String getString();

    public abstract void beNotified(EventEnum event, GameManager gameManager);
}
