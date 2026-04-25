package mc322_slay.event.command;

import java.util.Scanner;

import mc322_slay.entity.Hero;

public abstract class Command {
    protected Scanner scanner;

    public abstract void execute(Hero hero);
}
