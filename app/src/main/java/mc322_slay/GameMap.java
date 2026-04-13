package mc322_slay;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;

import javax.swing.tree.DefaultMutableTreeNode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Constrói e renderiza o mapa de progressão entre batalhas.
 * Também mantém o nó atual do jogador e as opções de avanço na árvore.
 */
public class GameMap {
    private int rows = 0, cols = 0;
    private char[][] map;

    /**
     * Coordenada de um identificador de batalha dentro da malha textual do mapa.
     */
    public record pos(int x, int y) {}
    private Hashtable<Integer, pos> mapCoords;

    private Hashtable<Integer, BattleNode> battles;

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
     * @param mapFile arquivo JSON da matriz de caracteres do mapa.
     * @param battleFile arquivo JSON com metadados dos nós de batalha.
     */
    public void buildMap(String mapFile, String battleFile) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String serialized = Files.readString(Path.of("..","data", mapFile));
            this.map = mapper.readValue(serialized, char[][].class);
            this.rows = this.map.length;
            this.cols = this.map[0].length;

            mapCoords = new Hashtable<>();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    char value = map[i][j];
                    if (value != ' ' && value != '|' && value != '\\' && value != '/') {
                        mapCoords.put(value - '0', new pos(j, i));
                    }
                }
                System.out.println();
            }

            this.buildTree(battleFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega os nós de batalha e cria a árvore conectada a partir do nó inicial.
     *
     * @param battleFile arquivo JSON com o dicionário id -> {@link BattleNode}.
     */
    public void buildTree(String battleFile) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String serialized = Files.readString(Path.of("..","data", battleFile));
            battles = mapper.readValue(serialized, new TypeReference<Hashtable<Integer, BattleNode>>() {
            });

            tree = new DefaultMutableTreeNode(battles.get(1));
            buildTreeRec(1, tree);

            playerNode = tree;

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
     * @param currenId id do nó atual em expansão.
     * @param currentNode referência de árvore correspondente ao nó atual.
     */
    public void buildTreeRec(int currenId, DefaultMutableTreeNode currentNode) {
        int[] dirX = { -1, 0, 1 };

        pos coord = mapCoords.get(currenId);

        for (int i = 0; i < 3; i++) {
            int newX = coord.x + dirX[i];
            int newY = coord.y - 1;

            if (isValidPos(newX, newY)) {
                char c = map[newY][newX];
                if (c != ' ') {
                    int nextId;
                    if (c == '|') { // upper node
                        nextId = map[newY - 1][newX] - '0';
                    } else if (c == '\\') { // left node
                        nextId = map[newY - 1][newX - 1] - '0';
                    } else { // right node
                        nextId = map[newY - 1][newX + 1] - '0';
                    }

                    DefaultMutableTreeNode nextNode = new DefaultMutableTreeNode(battles.get(nextId));
                    currentNode.add(nextNode);
                    buildTreeRec(nextId, nextNode);
                }
            }
        }
    }

    /**
     * Imprime o estado atual do mapa para o terminal.
     * Marca posição do jogador, caminhos já visitados e opções disponíveis.
     */
    public void printMap() {
        int counter = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = map[i][j];
                if (c == '|' || c == '\\' || c == '/' || c == ' ') {
                    System.out.print(c);
                } // connections or empty spaces
                else {
                    BattleNode nodeP = (BattleNode) playerNode.getUserObject();
                    BattleNode nodeX = battles.get(c - '0');
                    if (nodeX.getId() == nodeP.getId()) {
                        c = 'P'; // player position
                        Interface.printInline(c + "", ColorEnum.yellow);
                    }
                    else if (nodeX.isVisited()) {
                        c = 'x'; // already visited position
                        Interface.printInline(c + "", ColorEnum.red);
                    }
                    else {
                        boolean nextOption = false;
                        for (int k = 0; k < playerNode.getChildCount(); k++) {
                            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) playerNode.getChildAt(k);
                            BattleNode nodeC = (BattleNode) childNode.getUserObject();
                            if (nodeX.getId() == nodeC.getId()) {
                                c = (char) ('0' + counter); // options to select position
                                counter++;
                                nextOption = true;
                                Interface.printInline(c + "", ColorEnum.yellow);
                                break;
                            }
                        }
                        if (!nextOption) {
                            c = 'o'; // normal position
                            Interface.printInline(c + "", ColorEnum.blue);
                        }
                    }
                }
            }
            System.out.println();
        }

        Interface.printInline("\r\n========\r\n", ColorEnum.purple);
        System.out.println("Legenda: ");
        System.out.println("P: Sua posição (player)");
        System.out.println("o: Batalhas ainda não travadas");
        System.out.println("x: Batalhas já vencidas");
        System.out.println("1,...,n: Caminhos disponíveis");
        Interface.printInline("\r\n========\r\n", ColorEnum.purple);
    }
}
