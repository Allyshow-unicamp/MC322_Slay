package mc322_slay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import mc322_slay.entity.Enemy;

/**
 * Serializador customizado de {@link Enemy}.
 */
public class EnemySerializer extends StdSerializer<Enemy> {
    /**
     * Cria o serializador padrão para uso pelo Jackson.
     */
    public EnemySerializer() {
        this(null);
    }

    /**
     * @param t tipo concreto serializado.
     */
    public EnemySerializer(Class<Enemy> t) {
        super(t);
    }

    /**
     * Serializa os dados persistidos do inimigo para salvamento/carregamento.
     */
    @Override
    public void serialize(Enemy value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStringField("name", value.getName());
        gen.writeNumberField("health", value.getHealth());
        gen.writeNumberField("shield", value.getShield());
        gen.writeStringField("image", value.getImage());
    }

    /**
     * Serializa o inimigo incluindo metadados de tipo polimórfico.
     */
    @Override
    public void serializeWithType(Enemy value, JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));

        serialize(value, gen, serializers);

        typeSer.writeTypeSuffix(gen, typeIdDef);
    }
}
