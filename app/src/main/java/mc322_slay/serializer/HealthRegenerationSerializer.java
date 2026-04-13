package mc322_slay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import mc322_slay.effect.HealthRegeneration;

/**
 * Serializador customizado de {@link HealthRegeneration}.
 */
public class HealthRegenerationSerializer extends StdSerializer<HealthRegeneration> {
    /**
     * Cria o serializador padrão para uso pelo Jackson.
     */
    public HealthRegenerationSerializer() {
        this(null);
    }

    /**
     * @param t tipo concreto serializado.
     */
    public HealthRegenerationSerializer(Class<HealthRegeneration> t) {
        super(t);
    }

    /**
     * Serializa nome, duração e valor de cura do efeito.
     */
    @Override
    public void serialize(HealthRegeneration value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStringField("name", value.getName());
        gen.writeNumberField("turns", value.getPoints());
        gen.writeNumberField("health", value.getHealth());
    }

    /**
     * Serializa o efeito incluindo metadados de tipo polimórfico.
     */
    @Override
    public void serializeWithType(HealthRegeneration value, JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));

        serialize(value, gen, serializers);

        typeSer.writeTypeSuffix(gen, typeIdDef);
    }
}
