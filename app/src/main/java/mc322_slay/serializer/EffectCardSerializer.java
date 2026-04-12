package mc322_slay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import mc322_slay.card.EffectCard;

public class EffectCardSerializer extends StdSerializer<EffectCard> {
    public EffectCardSerializer() {
        this(null);
    }

    public EffectCardSerializer(Class<EffectCard> t) {
        super(t);
    }

    @Override
    public void serialize(EffectCard value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStringField("name", value.getName());
        gen.writeNumberField("cost", value.getCost());
        gen.writeStringField("description", value.getDescription());
        gen.writeObjectField("effect", value.getEffect());
    }

    @Override
    public void serializeWithType(EffectCard value, JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));

        serialize(value, gen, serializers);

        typeSer.writeTypeSuffix(gen, typeIdDef);
    }
}
