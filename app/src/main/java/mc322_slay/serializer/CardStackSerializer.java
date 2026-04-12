package mc322_slay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import mc322_slay.card.Card;
import mc322_slay.card.CardStack;

public class CardStackSerializer extends StdSerializer<CardStack> {
    public CardStackSerializer() {
        this(null);
    }

    public CardStackSerializer(Class<CardStack> t) {
        super(t);
    }

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
