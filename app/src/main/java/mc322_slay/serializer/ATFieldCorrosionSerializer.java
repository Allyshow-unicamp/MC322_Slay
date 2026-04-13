package mc322_slay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import mc322_slay.effect.ATFieldCorrosion;

/**
 * Serializador customizado de {@link ATFieldCorrosion}.
 */
public class ATFieldCorrosionSerializer extends StdSerializer<ATFieldCorrosion> {
    /**
     * Cria o serializador padrão para uso pelo Jackson.
     */
    public ATFieldCorrosionSerializer() {
        this(null);
    }

    /**
     * @param t tipo concreto serializado.
     */
    public ATFieldCorrosionSerializer(Class<ATFieldCorrosion> t) {
        super(t);
    }

    /**
     * Serializa nome e duração do efeito de corrosão.
     */
    @Override
    public void serialize(ATFieldCorrosion value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStringField("name", value.getName());
        gen.writeNumberField("turns", value.getPoints());
        gen.writeNumberField("startTurns", value.getStartPoints());
    }

    /**
     * Serializa o efeito incluindo metadados de tipo polimórfico.
     */
    @Override
    public void serializeWithType(ATFieldCorrosion value, JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));

        serialize(value, gen, serializers);

        typeSer.writeTypeSuffix(gen, typeIdDef);
    }
}
