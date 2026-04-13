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

    private DefaultMutableTreeNode tree;

    public void buildMap(String mapString, String battleString) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String serialized = Files.readString(Paths.get("..", "data", mapString));
            this.map = mapper.readValue(serialized, char[][].class);
            this.rows = this.map.length;
            this.cols = this.map[0].length;

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    char value = map[i][j];
                    if (value != ' ' && value != '\\' && value != '/') {
                        mapCoords.put((int) value, new pos(i, j));
                    }
                }
                System.out.println();
            }

            this.buildTree(battleString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildTree(String battleString) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String serialized = Files.readString(Paths.get("..", "data", battleString));
            Hashtable<Integer, BattleNode> battles = mapper.readValue(serialized, Hashtable.class);

            tree = new DefaultMutableTreeNode(battles.get(1));

            buildTreeRec(tree, battles);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildTreeRec(DefaultMutableTreeNode currentNode, Hashtable<Integer, BattleNode> battles) {
        
    }

    public void printMap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(map[i][j] + "");
            }
            System.out.println();
        }
    }
}
