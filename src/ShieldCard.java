public class ShieldCard {
    
    private String name = "ATField Regeneration";
    private int energyCost = 1;

    public void useCard(Hero hero, int amount) {
        hero.gainATField(amount);
    }
    public String getName() {
        return this.name;
    }
    public int getCost() {
        return this.energyCost;
    }

    public ShieldCard(String name, int energyCost) {
        this.energyCost = energyCost;
        this.name = name;
    }
}
