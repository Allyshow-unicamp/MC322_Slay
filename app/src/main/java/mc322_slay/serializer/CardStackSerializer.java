package mc322_slay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import mc322_slay.card.Card;
import mc322_slay.card.CardStack;

/**
 * Serializador customizado para {@link CardStack}.
 * Escreve o objeto no formato {@code {"cards":[...]}}.
 */
public class CardStackSerializer extends StdSerializer<CardStack> {
    /**
     * Cria o serializador padrão para uso pelo Jackson.
     */
    public CardStackSerializer() {
        this(null);
    }

    /**
     * @param t tipo concreto serializado.
     */
    public CardStackSerializer(Class<CardStack> t) {
        super(t);
    }

    /**
     * Serializa a pilha como objeto com array de cartas na ordem interna da pilha.
     */
    @Override
    public void serialize(CardStack value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeArrayFieldStart("cards");
        for (Card card : value.getStack()) {
            gen.writeObject(card);
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}
