package mc322_slay.entity;

import java.util.ArrayList;
import java.util.Random;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.PsychicEffect;
import mc322_slay.effect.LowSyncRate;

/**
 * Representa um inimigo (Anjo) no jogo.
 * Estende a classe Entity e define comportamentos específicos de ataque e uso
 * de efeitos.
 */
public class Enemy extends Entity {

    /** Gerador de números aleatórios para as ações do inimigo. */
    public Random random = new Random();

    /** Quantidade de dano que o inimigo causará em seu próximo ataque. */
    private int damage;
    private PsychicEffect p = new PsychicEffect("Dano psicológico", 20, 3);
    private ATFieldCorrosion c = new ATFieldCorrosion("Corrosão de campo AT", 3);
    private HealthRegeneration h = new HealthRegeneration("Regeneração de vida", 50, 3);
    private LowSyncRate w = new LowSyncRate("Baixa taxa de sincronização", 3, 0.75);
    private HighSyncRate hs = new HighSyncRate("Alta taxa de sincronização", 3, 1.5);

    /**
     * Realiza um ataque contra o herói.
     * 
     * @param hero O herói que receberá o dano.
     * @return A quantidade de dano causada.
     */
    public int attack(Hero hero) {
        Interface.printMessage("O inimigo " + name + " ataca " + hero.getName() + ".", ColorEnum.red);
        hero.takeDamage((int) (this.damage * boost * deboost));
        return (int) (this.damage * boost * deboost);
    }

    /**
     * Retorna o dano do inimigo.
     * 
     * @return O dano do inimigo.
     */
    public int getDamage() {
        return (int) (this.damage * boost * deboost);
    }

    /**
     * Atualiza o dano do inimigo.
     * 
     * @param damage O novo dano.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Escolhe aleatoriamente um efeito e o aplica: dano psicológico ou corrosão de
     * AT Field ao herói,
     * ou regeneração de vida sobre o próprio inimigo.
     *
     * @param hero herói alvo quando o efeito sorteado afeta o jogador.
     * @return {@code true} se o efeito foi aplicado no inimigo (regeneração);
     *         {@code false} caso contrário.
     */
    public boolean useEffect(Hero hero) {
        int effect = random.nextInt(5);

        boolean selfInflicted = false;
        switch (effect) {
            case 0:
                Interface.printMessage("O inimigo " + this.name + " utilizou o efeito " + p.getName()
                        + " com duração de " + p.getPoints() + " turnos.", ColorEnum.red);
                hero.applyEffect(p);
                break;
            case 1:
                Interface.printMessage("O inimigo " + this.name + " utilizou o efeito " + c.getName()
                        + " com duração de " + c.getPoints() + " turnos.", ColorEnum.red);
                hero.applyEffect(c);
                break;
            case 2:
                Interface.printMessage("O inimigo " + this.name + " utilizou o efeito " + h.getName()
                        + " com duração de " + h.getPoints() + " turnos.", ColorEnum.red);
                this.applyEffect(h);
                selfInflicted = true;
                break;
            case 3:
                Interface.printMessage("O inimigo " + this.name + " utilizou o efeito " + w.getName()
                        + " com duração de " + w.getPoints() + " turnos.", ColorEnum.red);
                hero.applyEffect(w);
                break;
            case 4:
                Interface.printMessage("O inimigo " + this.name + " utilizou o efeito " + hs.getName()
                        + " com duração de " + hs.getPoints() + " turnos.", ColorEnum.red);
                this.applyEffect(hs);
                selfInflicted = true;
                break;
        }
        return selfInflicted;
    }

    /**
     * Define a próxima ação do inimigo aleatoriamente.
     * 
     * @return O valor correspondente à ação sorteada.
     */
    public int nextAction() {
        this.damage = (random.nextInt(40) + 1);
        int action = random.nextInt(3);
        return action;
    }

    /**
     * Construtor da classe Enemy.
     * 
     * @param name       Nome do inimigo.
     * @param health     Vida inicial.
     * @param ATField    Valor inicial do escudo (AT Field).
     * @param imageAsset Caminho do recurso de imagem.
     */
    public Enemy(String name, int health, int ATField, String imageAsset) {
        this.name = name;
        this.health = health;
        this.ATField = ATField;
        this.damage = 0;
        this.effects = new ArrayList<>();
        this.imageAsset = imageAsset;
        this.maxHealth = health;
    }
}
