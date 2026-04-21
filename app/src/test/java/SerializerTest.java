import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import mc322_slay.card.CardStack;
import mc322_slay.card.DamageCard;
import mc322_slay.card.EffectCard;
import mc322_slay.card.ShieldCard;
import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.effect.PsychicEffect;
import mc322_slay.entity.Enemy;

/**
 * Testes de serialização JSON das classes em {@link mc322_slay.serializer}: o {@link ObjectMapper}
 * aplica os serializers registrados via {@code @JsonSerialize}, como no carregamento do baralho em
 * {@link mc322_slay.GameManager}.
 */
public class SerializerTest {

    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Test
    public void psychicEffectSerializerWritesNameTurnsAndDamage() throws Exception {
        PsychicEffect effect = new PsychicEffect("poison", 20, 3);
        JsonNode root = mapper.readTree(mapper.writeValueAsString(effect));

        assertEquals("poison", root.get("type").asText());
        assertEquals("poison", root.get("name").asText());
        assertEquals(3, root.get("turns").asInt());
        assertEquals(20, root.get("damage").asInt());
    }

    @Test
    public void healthRegenerationSerializerWritesNameTurnsAndHealth() throws Exception {
        HealthRegeneration effect = new HealthRegeneration("Regeneração", 20, 2);
        JsonNode root = mapper.readTree(mapper.writeValueAsString(effect));

        assertEquals("regeneration", root.get("type").asText());
        assertEquals("Regeneração", root.get("name").asText());
        assertEquals(2, root.get("turns").asInt());
        assertEquals(20, root.get("health").asInt());
    }

    @Test
    public void highSyncRateSerializerWritesNameTurnsAndBoost() throws Exception {
        HighSyncRate effect = new HighSyncRate("Força", 2, 1.5);
        JsonNode root = mapper.readTree(mapper.writeValueAsString(effect));

        assertEquals("strength", root.get("type").asText());
        assertEquals("Força", root.get("name").asText());
        assertEquals(2, root.get("turns").asInt());
        assertEquals(1.5, root.get("boost").asDouble(), 1e-9);
    }

    @Test
    public void lowSyncRateSerializerWritesNameTurnsAndDeboost() throws Exception {
        LowSyncRate effect = new LowSyncRate("Fraqueza", 2, 0.75);
        JsonNode root = mapper.readTree(mapper.writeValueAsString(effect));

        assertEquals("weakness", root.get("type").asText());
        assertEquals("Fraqueza", root.get("name").asText());
        assertEquals(2, root.get("turns").asInt());
        assertEquals(0.75, root.get("deboost").asDouble(), 1e-9);
    }

    @Test
    public void atFieldCorrosionSerializerWritesNameAndTurns() throws Exception {
        ATFieldCorrosion effect = new ATFieldCorrosion("Corrosão", 4);
        JsonNode root = mapper.readTree(mapper.writeValueAsString(effect));

        assertEquals("corrosion", root.get("type").asText());
        assertEquals("Corrosão", root.get("name").asText());
        assertEquals(4, root.get("turns").asInt());
    }

    @Test
    public void damageCardSerializerWritesNameCost() throws Exception {
        DamageCard card = new DamageCard("Ataque", 2);
        JsonNode root = mapper.readTree(mapper.writeValueAsString(card));

        assertEquals("damage", root.get("type").asText());
        assertEquals("Ataque", root.get("name").asText());
        assertEquals(2, root.get("cost").asInt());
    }

    @Test
    public void shieldCardSerializerWritesNameCost() throws Exception {
        ShieldCard card = new ShieldCard("Escudo", 1);
        JsonNode root = mapper.readTree(mapper.writeValueAsString(card));

        assertEquals("shield", root.get("type").asText());
        assertEquals("Escudo", root.get("name").asText());
        assertEquals(1, root.get("cost").asInt());
    }

    @Test
    public void effectCardSerializerWritesNestedEffect() throws Exception {
        PsychicEffect inner = new PsychicEffect("veneno", 15, 2);
        EffectCard card = new EffectCard("Carta tóxica", 3, inner);
        JsonNode root = mapper.readTree(mapper.writeValueAsString(card));

        assertEquals("effect", root.get("type").asText());
        assertEquals("Carta tóxica", root.get("name").asText());
        assertEquals(3, root.get("cost").asInt());

        JsonNode effectNode = root.get("effect");
        assertEquals("poison", effectNode.get("type").asText());
        assertEquals("veneno", effectNode.get("name").asText());
        assertEquals(2, effectNode.get("turns").asInt());
        assertEquals(15, effectNode.get("damage").asInt());
    }

    @Test
    public void cardStackSerializerWritesCardsArray() throws Exception {
        CardStack stack = new CardStack();
        stack.add(new DamageCard("A", 1));
        stack.add(new ShieldCard("B", 2));

        JsonNode root = mapper.readTree(mapper.writeValueAsString(stack));
        assertTrue(root.has("cards"));
        assertEquals(2, root.get("cards").size());

        JsonNode first = root.get("cards").get(0);
        assertEquals("damage", first.get("type").asText());
        assertEquals("A", first.get("name").asText());

        JsonNode second = root.get("cards").get(1);
        assertEquals("shield", second.get("type").asText());
        assertEquals("B", second.get("name").asText());
    }

    @Test
    public void cardStackSerializerEmptyDeck() throws Exception {
        CardStack stack = new CardStack();
        JsonNode root = mapper.readTree(mapper.writeValueAsString(stack));

        assertTrue(root.has("cards"));
        assertEquals(0, root.get("cards").size());
    }

    @Test
    public void enemySerializerWritesNameHealthShieldAndImage() throws Exception {
        Enemy enemy = new Enemy("Sachiel", 180, 60, 20, 40, "sachiel.txt");
        JsonNode root = mapper.readTree(mapper.writeValueAsString(enemy));

        assertEquals("Sachiel", root.get("name").asText());
        assertEquals(180, root.get("health").asInt());
        assertEquals(60, root.get("shield").asInt());
        assertEquals("sachiel.txt", root.get("image").asText());
    }
}
