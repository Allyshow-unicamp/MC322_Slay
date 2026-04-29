package mc322_slay.relic;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import mc322_slay.EventEnum;
import mc322_slay.entity.Hero;
import mc322_slay.event.Battle;

/**
 * Classe base para as Relíquias utilizando o Padrão Observer.
 * As relíquias "observam" a batalha e reagem a eventos do ciclo de turnos.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.PROPERTY, 
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = BloodVial.class, name = "blood_vial"),
    @JsonSubTypes.Type(value = MotorS2.class, name = "motor_s2"),
    @JsonSubTypes.Type(value = SDATPlayer.class, name = "sdat_player"),
    @JsonSubTypes.Type(value = AngelNucleus.class, name = "angel_nucleus"),
})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Relic {
    protected String name;
    protected String description;
    protected int value;

    /**
     * Retorna o nome da relíquia.
     *
     * @return nome da relíquia.
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna a descrição funcional da relíquia.
     *
     * @return texto descritivo do efeito da relíquia.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retorna o valor numérico associado à relíquia.
     * O significado específico depende do tipo de relíquia (ex: quantidade de cura, escudo gerado, etc.).
     *
     * @return valor numérico da relíquia.
     */
    public int getValue() {
        return value;
    }

    /**
     * Método central do Padrão Observer.
     * É chamado pelo Subject (Batalha) sempre que um evento de turno ocorre.
     *
     * @param event O evento que acabou de acontecer (ex: playerStartOfTurn)
     * @param battle A instância da batalha atual
     * @param hero O herói que possui a relíquia
     */
    public abstract void update(EventEnum event, Battle battle, Hero hero);
}