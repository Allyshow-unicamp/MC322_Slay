package mc322_slay.event;

import java.util.HashMap;
import java.util.Scanner;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.card.CardStack;
import mc322_slay.entity.Hero;
import mc322_slay.event.command.Command;
import mc322_slay.event.command.DeleteCommand;
import mc322_slay.event.command.HealCommand;
import mc322_slay.event.command.UpgradeCommand;

/**
 * Evento de descanso (fogueira/base) com comandos de cura, upgrade e deleção.
 */
public class RestSite extends Event {
    /** Scanner dedicado à interação textual deste evento. */
    private Scanner scanner;

    /**
     * Executa o fluxo de descanso e aplica o comando selecionado.
     */
    @Override
    public boolean init(Hero hero, CardStack possibleNewCards) {
        scanner = new Scanner(System.in);

        Interface.clearScreen();

        Interface.printInline("\r\n== BASE DA NERV ==\r\n", ColorEnum.purple);
        Interface.printFile("nerv.txt", ColorEnum.red);
        Interface.printMessage("Após muita luta, vem um momento de descanso. Escolha como pretende passar seu tempo na NERV: ",
                ColorEnum.purple);

        Interface.printInline("\r\n0) ", ColorEnum.yellow);
        Interface.printInline("Descansar e recuperar 30% da vida.", ColorEnum.reset);

        Interface.printInline("\r\n1) ", ColorEnum.yellow);
        Interface.printInline("Melhorar 1 entre 3 cartas.", ColorEnum.reset);

        Interface.printInline("\r\n2) ", ColorEnum.yellow);
        Interface.printInline("Deletar 1 entre 3 cartas.", ColorEnum.reset);

        Interface.printInline("\r\n=============\r\n", ColorEnum.purple);

        int option = 0;
        while (true) {
            try {
                System.out.print("\r\nSelecione sua escolha (D para ver deck e vida): ");
                char response = scanner.next().charAt(0);
                if (response == 'D') {
                    Interface.printHeroInfo(hero);
                } else {
                    option = Integer.parseInt(response + "");
                    if (0 <= option && option <= 2) {
                        
                        HashMap<Integer,Command> map = new HashMap<>();
                        map.put(0, new HealCommand());
                        map.put(1, new UpgradeCommand());
                        map.put(2, new DeleteCommand());

                        map.get(option).execute(hero);

                        break;
                    } else {
                        Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
                    }
                }
            } catch (Exception e) {
                Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
            }
        }

        return true;
    }

    /**
     * @return descrição curta do evento para o mapa.
     */
    @Override
    public String getDescription() {
        return "Base da NERV (fogueira)";
    }

    /**
     * Imprime marcador de fogueira usado na renderização do mapa.
     */
    public void printChar() {
        Interface.printInline("f", ColorEnum.purple);
    }
}
