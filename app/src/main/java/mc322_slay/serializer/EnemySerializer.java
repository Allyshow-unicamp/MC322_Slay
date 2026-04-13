package mc322_slay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import mc322_slay.entity.Enemy;

public class EnemySerializer extends StdSerializer<Enemy> {
    public EnemySerializer() {
        this(null);
    }

    public EnemySerializer(Class<Enemy> t) {
        super(t);
    }

    @Override
    public void serialize(Enemy value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStringField("name", value.getName());
        gen.writeNumberField("health", value.getHealth());
        gen.writeNumberField("shield", value.getShield());
        gen.writeStringField("image", value.getImage());
    }

    @Override
    public void serializeWithType(Enemy value, JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));

        serialize(value, gen, serializers);

        typeSer.writeTypeSuffix(gen, typeIdDef);
    }
}
