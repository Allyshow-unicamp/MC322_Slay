public class ShieldCard {
    
    String name = "ATField Regeneration";
    int energyCost = 1;

    public void useCard(Hero EVA, int amount) {
        EVA.ATField += amount;
    }

    public ShieldCard(String name, int energyCost) {
        this.energyCost = energyCost;
        this.name = name;
    }
}
