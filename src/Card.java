public abstract class Card {
    
    protected String name;
    protected int energyCost;
    protected String cardDescription;

    public abstract void useCard(Entity entity, int amount);

    public String getName() {
        return this.name;
    }
    public int getCost() {
        return this.energyCost;
    }
    public String getDescription() {
        return this.cardDescription;
    }
}
