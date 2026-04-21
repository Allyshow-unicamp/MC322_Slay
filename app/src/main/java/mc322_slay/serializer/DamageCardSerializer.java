package mc322_slay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import mc322_slay.card.DamageCard;

/**
 * Serializador customizado de {@link DamageCard}.
 */
public class DamageCardSerializer extends StdSerializer<DamageCard> {
    /**
     * Cria o serializador padrão para uso pelo Jackson.
     */
    public DamageCardSerializer() {
        this(null);
    }

    /**
     * @param t tipo concreto serializado.
     */
    public DamageCardSerializer(Class<DamageCard> t) {
        super(t);
    }

    /**
     * Serializa os campos estáveis da carta de dano.
     */
    @Override
    public void serialize(DamageCard value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStringField("name", value.getName());
        gen.writeNumberField("cost", value.getCost());
        gen.writeNumberField("multiplier", value.getCost());
    }

    /**
     * Serializa a carta incluindo metadados de tipo polimórfico.
     */
    @Override
    public void serializeWithType(DamageCard value, JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));

        serialize(value, gen, serializers);

        typeSer.writeTypeSuffix(gen, typeIdDef);
    }
}
