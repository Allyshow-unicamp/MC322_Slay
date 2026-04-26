package mc322_slay.event.command;

import java.util.Scanner;

import mc322_slay.entity.Hero;

/**
 * Comando abstrato para ações disponíveis no evento de descanso.
 */
public abstract class Command {
    /** Scanner para interação textual dos comandos. */
    protected Scanner scanner;

    /**
     * Executa o comando sobre o herói atual.
     *
     * @param hero herói alvo do comando.
     */
    public abstract void execute(Hero hero);
}
