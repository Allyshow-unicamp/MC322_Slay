package mc322_slay.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import mc322_slay.event.Battle;

/**
 * Serializador Jackson customizado para {@link Battle}.
 * Escreve somente o campo {@code angel} e delega metadados de tipo ao Jackson.
 */
public class BattleSerializer extends StdSerializer<Battle> {

    /**
     * Construtor padrão usado pelo Jackson.
     */
    public BattleSerializer() {
        this(null);
    }

    /**
     * Construtor tipado do serializador.
     *
     * @param t tipo alvo.
     */
    public BattleSerializer(Class<Battle> t) {
        super(t);
    }

    /**
     * Serializa o conteúdo da batalha.
     *
     * @param value batalha de origem.
     * @param gen gerador JSON.
     * @param provider provider do Jackson.
     * @throws IOException em caso de falha de escrita.
     */
    @Override
    public void serialize(Battle value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObjectField("angel", value.getAngel());
    }

    /**
     * Serializa com metadado de tipo para hierarquia polimórfica.
     *
     * @param value batalha de origem.
     * @param gen gerador JSON.
     * @param serializers provider do Jackson.
     * @param typeSer serializador de tipo.
     * @throws IOException em caso de falha de escrita.
     */
    @Override
    public void serializeWithType(Battle value, JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));

        serialize(value, gen, serializers);

        typeSer.writeTypeSuffix(gen, typeIdDef);
    }
}
