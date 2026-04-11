import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mc322_slay.card.CardStack;
import mc322_slay.card.DamageCard;
import mc322_slay.card.EffectCard;
import mc322_slay.card.PlayerHand;
import mc322_slay.card.ShieldCard;
import mc322_slay.effect.PsychicEffect;

/**
 * Testes de {@link mc322_slay.card.PlayerHand}: compra, descarte e restauração
 * do baralho (reservado).
 */
public class PlayerHandTest {
    @Test
    public void buyCardFromNotEmptyBuyPile() {
        PlayerHand playerHand = new PlayerHand();
        CardStack buyPile = new CardStack();
        CardStack discardPile = new CardStack();

        buyPile.add(new DamageCard("Carta de dano", 3, "Uma carta de dano"));
        buyPile.add(new ShieldCard("Carta de escudo", 3, "Uma carta de escudo"));
        EffectCard card = new EffectCard("Carta de efeito", 3, "Uma carta de efeito",
                new PsychicEffect("Veneno", 20, 3));
        buyPile.add(card);

        playerHand.buyCard(buyPile, discardPile);
        assertEquals(card, playerHand.getHand().get(0));
        assertTrue(!buyPile.getStack().contains(card));
    }

    @Test 
    public void restoreCardsCorrecly() {
        PlayerHand playerHand = new PlayerHand();
        CardStack buyPile = new CardStack();
        CardStack discardPile = new CardStack();
        
        DamageCard card1 = new DamageCard("Carta de dano", 3, "Uma carta de dano");
        discardPile.add(card1);
        ShieldCard card2 = new ShieldCard("Carta de escudo", 3, "Uma carta de escudo");
        discardPile.add(card2);
        EffectCard card3 = new EffectCard("Carta de efeito", 3, "Uma carta de efeito",
                new PsychicEffect("Veneno", 20, 3));
        discardPile.add(card3);

        playerHand.restoreCards(discardPile, buyPile);
        assertTrue(discardPile.isEmpty());
        assertTrue(buyPile.getStack().contains(card1));
        assertTrue(buyPile.getStack().contains(card2));
        assertTrue(buyPile.getStack().contains(card3));
    }

    @Test
    public void buyCardFromEmptyBuyPile() {
        PlayerHand playerHand = new PlayerHand();
        CardStack buyPile = new CardStack();
        CardStack discardPile = new CardStack();
        
        DamageCard card1 = new DamageCard("Carta de dano", 3, "Uma carta de dano");
        discardPile.add(card1);
        ShieldCard card2 = new ShieldCard("Carta de escudo", 3, "Uma carta de escudo");
        discardPile.add(card2);
        EffectCard card3 = new EffectCard("Carta de efeito", 3, "Uma carta de efeito",
                new PsychicEffect("Veneno", 20, 3));
        discardPile.add(card3);

        playerHand.buyCard(buyPile, discardPile);
        assertTrue(discardPile.isEmpty());
        assertTrue(playerHand.nCards() == 1);
        assertTrue(buyPile.getStack().size() == 2);
    }

    @Test 
    public void discardCardsCorrectly() {
        PlayerHand playerHand = new PlayerHand();
        CardStack buyPile = new CardStack();
        CardStack discardPile = new CardStack();

        DamageCard card1 = new DamageCard("Carta de dano", 3, "Uma carta de dano");
        buyPile.add(card1);
        ShieldCard card2 = new ShieldCard("Carta de escudo", 3, "Uma carta de escudo");
        buyPile.add(card2);
        EffectCard card3 = new EffectCard("Carta de efeito", 3, "Uma carta de efeito",
                new PsychicEffect("Veneno", 20, 3));
        buyPile.add(card3);

        playerHand.buyCard(buyPile, discardPile);
        playerHand.buyCard(buyPile, discardPile);
        playerHand.buyCard(buyPile, discardPile);

        playerHand.discardCards(discardPile);
        assertTrue(playerHand.nCards() == 0);
        assertTrue(discardPile.getStack().contains(card1));
        assertTrue(discardPile.getStack().contains(card2));
        assertTrue(discardPile.getStack().contains(card3));
    }
}
