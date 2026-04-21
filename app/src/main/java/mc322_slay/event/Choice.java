package mc322_slay.event;

import java.util.Scanner;

import mc322_slay.entity.Hero;

public class Choice extends Event {

    Scanner scanner;

    @Override
    public boolean init(Hero hero) {
        System.out.println("Escolha");

        scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();

        scanner.close();

        return true;
    }

    @Override
    public String getDescription() {
        return "Escolha";
    }
}
