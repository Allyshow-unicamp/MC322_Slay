package mc322_slay.entity;

import java.util.ArrayList;
import java.util.Random;

import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.PsychicEffect;
import mc322_slay.effect.LowSyncRate;

/**
 * Representa um inimigo (Anjo) no jogo.
 * Estende a classe Entity e define comportamentos específicos de ataque e uso de efeitos.
 */
public class Enemy extends Entity {

    /** Gerador de números aleatórios para as ações do inimigo. */
    public Random random = new Random();
    
    /** Quantidade de dano que o inimigo causará em seu próximo ataque. */
    private int damage;
    private PsychicEffect p = new PsychicEffect("Dano psicológico", 20, 3);
    private ATFieldCorrosion c = new ATFieldCorrosion("Corrosão de campo AT", 3);
    private HealthRegeneration h = new HealthRegeneration("Regeneração de vida", 50, 3);
    private LowSyncRate w = new LowSyncRate("Baixa taxa de sincronização", 3);
    private HighSyncRate hs = new HighSyncRate("Alta taxa de sincronização", 3);

    /**
     * Realiza um ataque contra o herói.
     * @param hero O herói que receberá o dano.
     * @return A quantidade de dano causada.
     */
    public int attack(Hero hero) {
        hero.takeDamage(this.damage);
        return this.damage;
    }
  
    /**
     * Retorna o dano do inimigo.
     * @return O dano do inimigo.
     */
    public int getDamage() {
        return this.damage;
    }
    
    /**
     * Atualiza o dano do inimigo.
     * @param damage O novo dano.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Escolhe aleatoriamente um efeito e o aplica: dano psicológico ou corrosão de AT Field ao herói,
     * ou regeneração de vida sobre o próprio inimigo.
     *
     * @param hero herói alvo quando o efeito sorteado afeta o jogador.
     * @return {@code true} se o efeito foi aplicado no inimigo (regeneração); {@code false} caso contrário.
     */
    public boolean useEffect(Hero hero) {

        int effect = random.nextInt(5);
        boolean selfInflicted = false;
        switch (effect) { // utilizarei outra branch para criar novos efeitos
            case 0: // caso sorteado efeito de dano psicológico
                hero.applyEffect(p, p.getPoints());
                break;
            case 1:
                hero.applyEffect(c, c.getPoints());
                break;
            case 2:
                this.applyEffect(h, h.getPoints());
                selfInflicted = true;
                break;
            case 3:
                hero.applyEffect(w, w.getPoints());
                break;
            case 4:
                this.applyEffect(hs, hs.getPoints());
                selfInflicted = true;
                break;
        }
        return selfInflicted;
    }
    
    /**
     * Define a próxima ação do inimigo aleatoriamente.
     * @return O valor correspondente à ação sorteada.
     */
    public int nextAction() {
        this.damage = (random.nextInt(40) +1);
        int action = random.nextInt(3);
        return action;
    }

    /**
     * Construtor da classe Enemy.
     * @param name Nome do inimigo.
     * @param health Vida inicial.
     * @param ATField Valor inicial do escudo (AT Field).
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
