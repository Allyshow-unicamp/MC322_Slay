package mc322_slay;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.Random;

import javax.swing.tree.DefaultMutableTreeNode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import mc322_slay.event.Battle;
import mc322_slay.event.Choice;
import mc322_slay.event.Event;
import mc322_slay.event.EventNode;

/**
 * Constrói e renderiza o mapa de progressão entre batalhas.
 * Também mantém o nó atual do jogador e as opções de avanço na árvore.
 */
public class GameMap {
    private int rows = 0, cols = 0;
    private char[][] map;

    private char cur_random_id = 'a';

    /**
     * Coordenada de um identificador de batalha dentro da malha textual do mapa.
     */
    public record pos(int x, int y) {
    }

    private Hashtable<Character, pos> mapCoords;

    private Hashtable<Character, EventNode> events;

    private DefaultMutableTreeNode tree;
    private DefaultMutableTreeNode playerNode;

    public DefaultMutableTreeNode getPlayerNode() {
        return playerNode;
    }

    public void setPlayerNode(DefaultMutableTreeNode playerNode) {
        this.playerNode = playerNode;
    }

    /**
     * Carrega matriz visual do mapa e monta a árvore de batalhas.
     *
     * @param mapFile   arquivo JSON da matriz de caracteres do mapa.
     * @param eventFile arquivo JSON com metadados dos nós de batalha.
     */
    public void buildMap(String mapFile, String eventFile) {
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            String serialized = Files.readString(Path.of("..", "data", mapFile));
            this.map = mapper.readValue(serialized, char[][].class);
            this.rows = this.map.length;
            this.cols = this.map[0].length;

            mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            serialized = Files.readString(Path.of("..", "data", eventFile));
            this.events = mapper.readValue(serialized, new TypeReference<Hashtable<Character, EventNode>>() {
            });

            this.events.put('P', new EventNode('P', null, false));

            this.mapCoords = new Hashtable<>();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    char value = map[i][j];
                    if (value != ' ' && value != '|' && value != '\\' && value != '/' && value != 'R') {
                        mapCoords.put(value, new pos(j, i));
                    }
                    else if (value == 'R') {
                        EventNode eventNode = generateRandomEvent();
                        map[i][j] = eventNode.getId();
                        events.put(eventNode.getId(), eventNode);
                        mapCoords.put(eventNode.getId(), new pos(j, i));
                    }
                }
            }

            tree = new DefaultMutableTreeNode(events.get('P'));
            buildTreeRec('P', tree);

            this.playerNode = tree;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica se uma coordenada pertence aos limites do mapa.
     *
     * @param x coordenada horizontal.
     * @param y coordenada vertical.
     * @return {@code true} quando a posição é válida.
     */
    private boolean isValidPos(int x, int y) {
        return 0 <= x && x < cols && 0 <= y && y < rows;
    }

    /**
     * Percorre recursivamente a matriz e conecta os próximos nós válidos na árvore.
     *
     * @param currenId    id do nó atual em expansão.
     * @param currentNode referência de árvore correspondente ao nó atual.
     */
    public void buildTreeRec(char currenId, DefaultMutableTreeNode currentNode) {
        int[] dirX = { -1, 0, 1 };

        pos coord = mapCoords.get(currenId);

        for (int i = 0; i < 3; i++) {
            int newX = coord.x + dirX[i];
            int newY = coord.y - 1;

            if (isValidPos(newX, newY)) {
                char c = map[newY][newX];
                if (c != ' ') {
                    char nextId = '*';
                    if (c == '|' && i == 1) { // upper node
                        nextId = map[newY - 1][newX];
                    } else if (c == '\\' && i == 0) { // left node
                        nextId = map[newY - 1][newX - 1];
                    } else if (c == '/' && i == 2) { // right node
                        nextId = map[newY - 1][newX + 1];
                    }

                    if (nextId != '*') {
                        DefaultMutableTreeNode nextNode = new DefaultMutableTreeNode(events.get(nextId));
                        currentNode.add(nextNode);
                        buildTreeRec(nextId, nextNode);
                    }
                }
            }
        }
    }

    public EventNode generateRandomEvent() {
        Random random = new Random();
        int n = random.nextInt(3);

        Event event = new Choice();
        if (n == 0) {
            // loja
        } else if (n == 1) {
            // fogueira
        } else if (n == 2) {
            // escolha
        }

        EventNode node = new EventNode(cur_random_id, event, false);
        cur_random_id++;
        return node;
    }

    /**
     * Imprime o estado atual do mapa para o terminal.
     * Marca posição do jogador, caminhos já visitados e opções disponíveis.
     */
    public void printMap() {
        Interface.printInline("\r\n= MAPA =\r\n", ColorEnum.purple);
        int counter = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = map[i][j];
                if (c == '|' || c == '\\' || c == '/' || c == ' ') {
                    System.out.print(c);
                } // connections or empty spaces
                else {
                    EventNode nodeP = (EventNode) playerNode.getUserObject();
                    EventNode nodeX = events.get(c);
                    if (nodeX.getId() == nodeP.getId()) {
                        c = 'P'; // player position
                        Interface.printInline(c + "", ColorEnum.yellow);
                    } else if (nodeX.isVisited()) {
                        c = 'x'; // already visited position
                        Interface.printInline(c + "", ColorEnum.red);
                    } else {
                        boolean nextOption = false;
                        for (int k = 0; k < playerNode.getChildCount(); k++) {
                            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) playerNode.getChildAt(k);
                            EventNode nodeC = (EventNode) childNode.getUserObject();
                            if (nodeX.getId() == nodeC.getId()) {
                                c = (char) ('0' + counter); // options to select position
                                counter++;
                                nextOption = true;
                                Interface.printInline(c + "", ColorEnum.yellow);
                                break;
                            }
                        }
                        if (!nextOption) {
                            if (nodeX.getEvent() instanceof Battle) {
                                c = 'o';
                                Interface.printInline(c + "", ColorEnum.blue);
                            } else if (nodeX.getEvent() instanceof Choice) {
                                c = 'c';
                                Interface.printInline(c + "", ColorEnum.green);
                            }
                            // outros eventos
                        }
                    }
                }
            }
            System.out.println();
        }

        Interface.printInline("========\r\n", ColorEnum.purple);

        Interface.printInline("Legenda: \r\n", ColorEnum.purple);
        Interface.printInline("P", ColorEnum.yellow);
        System.out.println(": Sua posição (player)");
        Interface.printInline("o", ColorEnum.blue);
        System.out.println(": Batalhas ainda não travadas");
        Interface.printInline("s", ColorEnum.purple);
        System.out.println(": NERV HQ (loja)");
        Interface.printInline("f", ColorEnum.yellow);
        System.out.println(": Divisões da NERV (fogueiras)");
        Interface.printInline("c", ColorEnum.green);
        System.out.println(": Escolhas");
        Interface.printInline("x", ColorEnum.red);
        System.out.println(": Salas já visitadas");

        Interface.printInline("========\r\n", ColorEnum.purple);

        Interface.printInline("Escolhas disponíveis: \r\n", ColorEnum.purple);
        for (int k = 0; k < playerNode.getChildCount(); k++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) playerNode.getChildAt(k);
            EventNode nodeC = (EventNode) childNode.getUserObject();
            Interface.printInline(k + 1 + "", ColorEnum.yellow);
            System.out.println(": " + nodeC.getEvent().getDescription());
        }

        Interface.printInline("========\r\n\r\n", ColorEnum.purple);
    }
}
