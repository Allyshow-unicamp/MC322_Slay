package mc322_slay.event;

/**
 * Nó lógico de um caminho do mapa.
 * Cada nó representa uma batalha contra um inimigo e mantém o estado de visita.
 */
public class EventNode {
    /** Identificador único do nó na malha do mapa. */
    private char id;
    /** Evento associado ao nó (batalha, escolha, etc.). */
    private Event event;
    /** Flag de controle para indicar se o jogador já passou por este nó. */
    private boolean visited;

    /**
     * @return evento associado ao nó.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @param event novo evento associado ao nó.
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * @return identificador único do nó no mapa.
     */
    public char getId() {
        return id;
    }

    /**
     * @param id novo identificador único do nó.
     */
    public void setId(char id) {
        this.id = id;
    }

    /**
     * @return {@code true} quando o nó já foi visitado pelo jogador.
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * @param visited marca se o nó já foi visitado.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Constrói um nó vazio para desserialização.
     */
    public EventNode() {
        super();
    }

    /**
     * Cria um nó de batalha completo.
     *
     * @param id identificador do nó.
     * @param event evento associado ao nó.
     * @param visited estado inicial de visita.
     */
    public EventNode(char id, Event event, boolean visited) {
        this.id = id;
        this.event = event;
        this.visited = visited;
    }
}
