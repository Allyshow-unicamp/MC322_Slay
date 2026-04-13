package mc322_slay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import mc322_slay.effect.PsychicEffect;

/**
 * Serializador customizado de {@link PsychicEffect}.
 */
public class PsychicEffectSerializer extends StdSerializer<PsychicEffect> {
    /**
     * Cria o serializador padrão para uso pelo Jackson.
     */
    public PsychicEffectSerializer() {
        this(null);
    }

    /**
     * @param t tipo concreto serializado.
     */
    public PsychicEffectSerializer(Class<PsychicEffect> t) {
        super(t);
    }

    /**
     * Serializa nome, duração e dano periódico do efeito.
     */
    @Override
    public void serialize(PsychicEffect value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStringField("name", value.getName());
        gen.writeNumberField("turns", value.getPoints());
        gen.writeNumberField("damage", value.getDamage());
    }

    /**
     * Serializa o efeito incluindo metadados de tipo polimórfico.
     */
    @Override
    public void serializeWithType(PsychicEffect value, JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));

        serialize(value, gen, serializers);

        typeSer.writeTypeSuffix(gen, typeIdDef);
    }
}
