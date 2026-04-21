package mc322_slay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import mc322_slay.event.Battle;

public class BattleSerializer extends StdSerializer<Battle> {

    public BattleSerializer() {
        this(null);
    }

    public BattleSerializer(Class<Battle> t) {
        super(t);
    }

    @Override
    public void serialize(Battle value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObjectField("angel", value.getAngel());
    }

    @Override
    public void serializeWithType(Battle value, JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));

        serialize(value, gen, serializers);

        typeSer.writeTypeSuffix(gen, typeIdDef);
    }
}
