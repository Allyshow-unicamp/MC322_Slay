package mc322_slay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import mc322_slay.card.ShieldCard;

/**
 * Serializador customizado de {@link ShieldCard}.
 */
public class ShieldCardSerializer extends StdSerializer<ShieldCard> {
    /**
     * Cria o serializador padrão para uso pelo Jackson.
     */
    public ShieldCardSerializer() {
        this(null);
    }

    /**
     * @param t tipo concreto serializado.
     */
    public ShieldCardSerializer(Class<ShieldCard> t) {
        super(t);
    }

    /**
     * Serializa os campos estáveis da carta de escudo.
     */
    @Override
    public void serialize(ShieldCard value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStringField("name", value.getName());
        gen.writeNumberField("cost", value.getCost());
        gen.writeStringField("description", value.getDescription());
    }

    /**
     * Serializa a carta incluindo metadados de tipo polimórfico.
     */
    @Override
    public void serializeWithType(ShieldCard value, JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));

        serialize(value, gen, serializers);

        typeSer.writeTypeSuffix(gen, typeIdDef);
    }
}
