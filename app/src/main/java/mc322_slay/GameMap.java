package mc322_slay;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;

import javax.swing.tree.DefaultMutableTreeNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class GameMap {
    private int rows = 0, cols = 0;
    private char[][] map;

    public record pos(int x, int y) {}
    private Hashtable<Integer, pos> mapCoords;

    private Hashtable<Integer, BattleNode> battles;

    private DefaultMutableTreeNode tree;
    private DefaultMutableTreeNode playerNode;

    public void buildMap(String mapFile, String battleFile) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String serialized = Files.readString(Paths.get("..", "data", mapFile));
            this.map = mapper.readValue(serialized, char[][].class);
            this.rows = this.map.length;
            this.cols = this.map[0].length;

            mapCoords = new Hashtable<>();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    char value = map[i][j];
                    if (value != ' ' && value != '\\' && value != '/') {
                        mapCoords.put((int) value, new pos(j, i));
                    }
                }
                System.out.println();
            }

            this.buildTree(battleFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildTree(String battleFile) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String serialized = Files.readString(Paths.get("..", "data", battleFile));
            battles = mapper.readValue(serialized, Hashtable.class);

            tree = new DefaultMutableTreeNode(battles.get(1));
            buildTreeRec(1, tree);

            playerNode = tree;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidPos(int x, int y) {
        return 0 <= x && x < cols && 0 <= y && y < rows;
    }

    public void buildTreeRec(int currenId, DefaultMutableTreeNode currentNode) {
        int[] dirX = {-1, 0, 1};

        pos coord = mapCoords.get(currenId);

        for (int i = 0; i < 3; i++) {
            int newX = coord.x + dirX[i];
            int newY = coord.y - 1;

            if (isValidPos(newX, newY)) {
                char c = map[newY][newX];
                int nextId;
                if (c == '|') { // upper node
                    nextId = (int) map[newY - 1][newX];
                } else if (c == '\\') { // left node
                    nextId = (int) map[newY - 1][newX - 1];
                } else { // right node
                    nextId = (int) map[newY - 1][newX + 1];
                }

                DefaultMutableTreeNode nextNode = new DefaultMutableTreeNode(battles.get(nextId));
                currentNode.add(nextNode);
                buildTreeRec(nextId, nextNode);
            }
        }
    }

    private char charToBePrinted(char c, int counter) {
        if (c == '|' || c == '\\' || c == '/' || c == ' ')    
            return c; // connections or empty spaces
        else {
            BattleNode nodeP = (BattleNode) playerNode.getUserObject();
            BattleNode nodeX = battles.get((int) c);
            if (nodeX.getId() == nodeP.getId())
                return 'P'; // player position
            else if (nodeX.isVisited())
                return 'x'; // already visited position
            else {
                for (int k = 0; k < playerNode.getChildCount(); k++) {
                    DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) playerNode.getChildAt(k);
                    BattleNode nodeC = (BattleNode) childNode.getUserObject();
                    if (nodeX.getId() == nodeC.getId()) 
                        return (char) ('0' + counter); // options to select position
                }
                return 'o'; // normal position 
            }
        }
    }

    public void printMap() {
        int counter = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(charToBePrinted(map[i][j], counter));
            }
            System.out.println();
        }
    }
}
