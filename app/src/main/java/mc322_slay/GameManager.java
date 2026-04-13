package mc322_slay;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.tree.DefaultMutableTreeNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import mc322_slay.card.CardStack;
import mc322_slay.entity.Hero;

/**
 * Orquestra o estado do jogo: inicialização, seleção de personagem, montagem do
 * baralho
 * e sequência de batalhas até a vitória ou derrota.
 */
public class GameManager {
    /** Herói controlado pelo jogador na partida atual. */
    private Hero hero;
    /** Leitura de entradas do teclado. */
    private Scanner scanner;
    /** Baralho principal de compra (pilha de cartas). */
    private CardStack deck;
    private GameMap map;
    private boolean end;

    /**
     * Preenche o baralho de compra com cartas iniciais da partida.
     */
    void populateDeck() {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String serialized = Files.readString(Paths.get("..","data", "deck.json"));
            deck = mapper.readValue(serialized, CardStack.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        deck.shuffle();

        Interface.printMessage("O baralho foi embaralhado!", ColorEnum.blue);
    }

    /**
     * Inicializa os objetos principais e variáveis de estado da partida.
     */
    public void start() {
        this.hero = new Hero("", 50, 0, "eva.txt");
        this.deck = new CardStack();
        this.scanner = new Scanner(System.in);
        this.map = new GameMap();
        this.map.buildMap("map.json", "battles.json");
    }

    /**
     * Exibe a tela inicial e aguarda confirmação para começar.
     */
    public void initialScreen() {
        Interface.clearScreen();
        Interface.printFile("initialScreenArt.txt", ColorEnum.reset);
        System.out.println("Digite [Enter] para começar");
        scanner.nextLine();
    }

    /**
     * Permite ao jogador escolher o personagem controlado.
     */
    public void selectCharacter() {
        Interface.printFile("selectCharacter.txt", ColorEnum.reset);
        int option;
        String name = "";
        while (true) {
            try {
                System.out.print("Selecione o piloto de EVA: ");
                option = Integer.parseInt(scanner.nextLine());
                if (0 < option && option < 4) {
                    break;
                } else {
                    Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
                }
            } catch (Exception e) {
                Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
            }
        }
        switch (option) {
            case 1:
                name = "Shinji Ikari";
                break;
            case 2:
                name = "Rei Ayanami";
                break;
            case 3:
                name = "Asuka Langley Soryu";
                break;
        }
        hero.setName(name);

        Interface.clearScreen();

        Interface.printMessage("\r\n" + hero.getName() + " selecionad*.\r\n", ColorEnum.reset);
    }

    /**
     * Limpa efeitos persistentes do herói entre uma batalha e outra.
     */
    private void resetBattle() {
        this.hero.resetEffects();
    }

    public boolean isRunning() {
        return hero.isAlive() && !end;
    }

    public int selectPathOnMap() {
        Interface.printInline("\r\n\r\n=== MAPA ===\r\n", ColorEnum.purple);
        this.map.printMap();

        DefaultMutableTreeNode playerNode = map.getPlayerNode();
        int nOptions = playerNode.getChildCount();
        if (nOptions == 0) // reached end
            return -1;

        int option;
        while (true) {
            try {
                System.out.print("\r\nSelecione o caminho que deseja seguir no mapa: ");
                option = Integer.parseInt(scanner.nextLine());
                if (0 < option && option <= nOptions) {
                    break;
                } else {
                    Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
                }
            } catch (Exception e) {
                Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
            }
        }
        
        return option;
    }

    public boolean performBattle(int option) {
        if (option == -1) 
            return true;
        
        DefaultMutableTreeNode playerNode = map.getPlayerNode();
        BattleNode playerBattleNode = (BattleNode) playerNode.getUserObject();

        DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) playerNode.getChildAt(option - 1);
        BattleNode childBattleNode = (BattleNode) childNode.getUserObject();

        Battle battle = new Battle(hero, childBattleNode.getEnemy(), deck);
        boolean won = battle.performFight();
        if (won) {
            playerBattleNode.setVisited(true);
            playerNode.setUserObject(playerBattleNode);
            map.setPlayerNode(childNode);
            resetBattle();
        }
        return won;
    }

    public void printResults(boolean won) {
        Interface.clearScreen();

        if (won)
            Interface.printFile("youWin.txt", ColorEnum.purple);
        else
            Interface.printFile("gameOver.txt", ColorEnum.red);
    }
}
