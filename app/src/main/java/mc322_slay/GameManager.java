package mc322_slay;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

import javax.swing.tree.DefaultMutableTreeNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import mc322_slay.card.CardStack;
import mc322_slay.entity.Hero;
import mc322_slay.event.Battle;
import mc322_slay.event.Event;
import mc322_slay.event.EventNode;
import mc322_slay.event.Reward;

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
    /** Mapa de progressão e posição atual do jogador. */
    private GameMap map;
    /** Cartas candidatas para recompensas após vitórias. */
    private CardStack possibleNewCards;
    /** Sinaliza término da campanha ao alcançar folha do mapa. */
    private boolean end = false;

    /**
     * Carrega do arquivo a pilha de cartas possíveis de recompensa.
     */
    private void readPosssibleCards() {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String serialized = Files.readString(Paths.get("..", "data", "cards.json"));
            possibleNewCards = mapper.readValue(serialized, CardStack.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Preenche o baralho de compra com cartas iniciais da partida.
     */
    private void populateDeck(String deckName) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String serialized = Files.readString(Paths.get("..", "data", deckName + ".json"));
            hero.setDeck(mapper.readValue(serialized, CardStack.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        hero.getDeck().shuffle();

        Interface.printMessage("O baralho foi embaralhado!", ColorEnum.blue);
    }

    /**
     * Inicializa os objetos principais e variáveis de estado da partida.
     */
    public void start() {
        this.hero = new Hero("", 50, 0, new CardStack(), "eva.txt");
        this.readPosssibleCards();
        this.scanner = new Scanner(System.in);
        this.map = new GameMap();
        Random random = new Random();
        int n = random.nextInt(1, 5);
        this.map.buildMap("map"+n+".json", "battles.json");
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
        String deckName = "deck";
        switch (option) {
            case 1:
                name = "Shinji Ikari";
                deckName += "1";
                break;
            case 2:
                name = "Rei Ayanami";
                deckName += "2";
                break;
            case 3:
                name = "Asuka Soryu";
                deckName += "3";
                break;
        }
        hero.setName(name);
        this.populateDeck(deckName);

        Interface.clearScreen();

        Interface.printMessage("\r\n" + hero.getName() + " selecionad*.\r\n", ColorEnum.reset);
    }

    /**
     * Limpa efeitos persistentes do herói entre uma batalha e outra.
     */
    private void resetEffects() {
        this.hero.resetEffects();
    }

    /**
     * @return {@code true} enquanto o herói estiver vivo e a campanha não tiver terminado.
     */
    public boolean isRunning() {
        return hero.isAlive() && !end;
    }

    /**
     * Mostra o mapa e solicita a próxima rota do jogador.
     *
     * @return índice da opção escolhida (1..N) ou {@code -1} quando já não há nós seguintes.
     */
    public int selectPathOnMap() {
        this.map.printMap();

        DefaultMutableTreeNode playerNode = map.getPlayerNode();
        int nOptions = playerNode.getChildCount();
        if (nOptions == 0) { // reached end
            end = true;
            return -1;
        }

        int option;
        while (true) {
            try {
                System.out.print("Selecione o caminho que deseja seguir no mapa (D para ver deck e vida): ");
                char response = scanner.next().charAt(0);
                if (response == 'D') {
                    Interface.printHeroInfo(hero);
                } else {
                    option = Integer.parseInt(response + "");
                    if (0 < option && option <= nOptions) {
                        break;
                    } else {
                        Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
                    }
                }
            } catch (Exception e) {
                Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
            }
        }

        return option;
    }

    /**
     * Executa o evento correspondente ao caminho escolhido e avança o nó atual.
     *
     * @param option índice da opção escolhida no mapa.
     * @return {@code true} quando o herói permanece vivo após o evento.
     */
    public boolean performEvent(int option) {
        if (option == -1)
            return true;

        DefaultMutableTreeNode playerNode = map.getPlayerNode();
        EventNode playerEventNode = (EventNode) playerNode.getUserObject();

        DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) playerNode.getChildAt(option - 1);
        EventNode childEventNode = (EventNode) childNode.getUserObject();

        Event event = childEventNode.getEvent();
        boolean alive = event.init(hero, possibleNewCards);
        if (alive) {
            if (event instanceof Battle) {
                Reward reward = new Reward();
                reward.init(hero, possibleNewCards);
            }

            playerEventNode.setVisited(true);
            playerNode.setUserObject(playerEventNode);
            map.setPlayerNode(childNode);
            resetEffects();
        }
        return alive;
    }

    /**
     * Exibe tela final de vitória/derrota e encerra leitura do teclado.
     *
     * @param won indica se o jogador venceu a campanha.
     */
    public void printResults(boolean won) {
        scanner.close();

        Interface.clearScreen();

        if (won)
            Interface.printFile("youWin.txt", ColorEnum.purple);
        else
            Interface.printFile("gameOver.txt", ColorEnum.red);
    }

    /**
     * Retorna o herói controlado pelo jogador.
     *
     * @return herói atual da partida.
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * Retorna o mapa de progressão atual.
     *
     * @return mapa do jogo.
     */
    public GameMap getMap() {
        return map;
    }
}
