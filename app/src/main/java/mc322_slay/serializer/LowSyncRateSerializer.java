package mc322_slay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import mc322_slay.effect.LowSyncRate;

/**
 * Serializador customizado de {@link LowSyncRate}.
 */
public class LowSyncRateSerializer extends StdSerializer<LowSyncRate> {
    /**
     * Cria o serializador padrão para uso pelo Jackson.
     */
    public LowSyncRateSerializer() {
        this(null);
    }

    /**
     * @param t tipo concreto serializado.
     */
    public LowSyncRateSerializer(Class<LowSyncRate> t) {
        super(t);
    }

    /**
     * Serializa nome, duração e fator de deboost do efeito.
     */
    @Override
    public void serialize(LowSyncRate value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStringField("name", value.getName());
        gen.writeNumberField("turns", value.getPoints());
        gen.writeNumberField("startTurns", value.getStartPoints());
        gen.writeNumberField("deboost", value.getDeboost());
    }

    /**
     * Serializa o efeito incluindo metadados de tipo polimórfico.
     */
    @Override
    public void serializeWithType(LowSyncRate value, JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));

        serialize(value, gen, serializers);

        typeSer.writeTypeSuffix(gen, typeIdDef);
    }
}
