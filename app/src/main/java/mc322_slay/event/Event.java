package mc322_slay.event;

import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import mc322_slay.card.CardStack;
import mc322_slay.entity.Hero;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Battle.class, name = "battle")
})
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
/**
 * Tipo base de eventos do mapa (ex.: batalhas), com inicialização própria e
 * descrição para exibição na interface.
 */
public abstract class Event {
    /** Leitor de entrada de ações do jogador no terminal. */
    protected Scanner scanner;

    /**
     * Executa o evento.
     *
     * @param hero herói da partida.
     * @param possibleNewCards pilha de cartas possíveis para recompensas/efeitos do evento.
     * @return {@code true} quando o herói sobrevive ao evento.
     */
    public abstract boolean init(Hero hero, CardStack possibleNewCards);

    /**
     * Retorna descrição curta do evento para listagem no mapa.
     *
     * @return texto de descrição do evento.
     */
    public abstract String getDescription();

    public abstract void printChar();
}
