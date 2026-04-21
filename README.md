# Jogo de cartas com temática de Neon Genesis Evangelion

<img src="assets/image.png" width="150" height="250" alt="Capa">

## O jogo

### Temática

O projeto é um jogo de cartas jogado **no terminal**, inspirado no universo de *Neon Genesis Evangelion*. O jogador enfrenta **anjos** em sequência através de um mapa. Após a abertura e a escolha do piloto, o programa carrega o mapa e os dados das lutas a partir de arquivos JSON, exibe o mapa com a posição atual, caminhos já vencidos e opções numeradas, e inicia cada combate quando o jogador escolhe um caminho. O objetivo da campanha é percorrer os nós até o fim da árvore mantendo o herói vivo; o **baralho** é o mesmo em todas as batalhas (pilhas de compra e descarte compartilhadas), enquanto **efeitos ativos no herói** são limpos entre uma luta e a outra.

### Personagens

No início da partida o jogador escolhe controlar **Shinji Ikari**, **Rei Ayanami** ou **Asuka Langley Soryu** (piloto de EVA). A cada **turno do jogador** dentro de uma batalha, o herói recupera a **sincronização** (energia disponível para jogar cartas) e o **campo AT** (escudo) é **zerado** no começo de cada turno.

### Mapa e progressão

O mapa é uma matriz de caracteres (`data/map.json`) interpretada pelo `GameMap`: letras e símbolos representam nós e conexões. Os dados de cada nó (qual anjo, vida, dano, escudo, arquivo de arte) vêm de `data/battles.json`. Ao vencer uma luta, o jogador avança para o nó escolhido; ao perder, a campanha termina. Quando não há mais filhos no nó atual, a campanha chegou ao fim e o jogo exibe o resultado final.

### Compra e uso de cartas

O conteúdo inicial do baralho está em `data/deck.json` e é carregado na forma de uma `CardStack`, depois embaralhado. Em cada turno do jogador, ele **compra quatro cartas** da pilha de compra para formar a mão e pode usar, respeitando o custo de sincronização:

- uma **arma** (carta de dano contra o anjo);
- uma carta que **recupera o campo AT** (escudo);
- uma carta que **aplica um efeito** ao herói ou ao inimigo, conforme o tipo de efeito.

Cada carta tem **custo de sincronização**; o jogador pode jogar várias cartas no mesmo turno enquanto a energia não se esgotar. Quando a energia chega a **zero**, o turno do jogador encerra. Também é possível digitar **`-1`** na escolha da carta para **passar o turno**.

As cartas usadas vão para a **pilha de descarte**; ao fim do turno do jogador, as cartas **não utilizadas** na mão seguem para o descarte. Se a pilha de **compra** acabar, a pilha de **descarte** é embaralhada e passa a ser a nova compra (fluxo interno do jogo).

Para dar variabilidade ao combate, tanto o **dano** das armas quanto o valor do **escudo** obtido por cartas de campo AT combinam um valor base derivado do custo da carta com um **incremento aleatório** (conforme constantes e sorteios na classe `Battle`).

### Inimigos

Cada batalha é contra um **anjo** (filho de Lilith, no enredo do anime), com nome, estatísticas e ilustração em texto definidos nos dados (`battles.json` + arquivos em `assets/`). No turno do jogador, o anjo **anuncia** no começo qual tipo de ação pretende realizar **ao final** daquele turno (atacar, recuperar campo AT ou usar efeito). No turno do inimigo, essa ação é executada. Valores como quantidade de escudo ganho ou parâmetros de efeito são **aleatórias**, conforme a implementação em `Enemy` e `Battle`.

### Efeitos

Tanto o jogador (via cartas de efeito) quanto o inimigo podem aplicar efeitos sobre si ou sobre o oponente. Os tipos são:

- **Dano psicológico** (`PsychicEffect`, “veneno”): inflige dano por turno a quem está sob o efeito, durante um número determinado de turnos.
- **Regeneração de vida** (`HealthRegeneration`): no início do turno de quem está sob o efeito, restaura uma quantidade de vida.
- **Corrosão de campo AT** (`ATFieldCorrosion`): anula a proteção do campo AT para quem sofre o efeito, de modo que dano recebido vai direto à vida.
- **Alta taxa de sincronização** (`HighSyncRate`): aumenta o dano causado por **ataques** do detentor — em particular o dano das **cartas de dano** do herói; **não** altera dano vindo de efeitos como o dano psicológico.
- **Baixa taxa de sincronização** (`LowSyncRate`): reduz o dano das **cartas de dano** do herói enquanto ativo; o foco da regra no combate é o dano de armas, não o de efeitos como veneno.

**Sobre duração e intensidade:** se a mesma entidade receber **o mesmo tipo de efeito** mais de uma vez, a **duração em turnos tende a se acumular**. Se houver **intensidades diferentes** para o mesmo tipo de efeito, prevalece a **maior intensidade** e o efeito permanece ativo pela **maior duração** entre as instâncias envolvidas (conforme a lógica em `Entity` e subclasses).

### Fim do jogo

Dentro de cada batalha, o combate segue até a morte de **uma** das entidades. **Vence** quem deixar o oponente com vida zero. Se **ambos** morrerem no mesmo desfecho, o jogo ainda trata como **derrota do jogador**.

Na **campanha**, ao concluir o **último deslocamento** possível no mapa sem ter sido derrotado, o jogador **vence** a campanha. Caso ele **perca** qualquer **batalha**, a campanha é **perdida**.

---

## Requisitos para desenvolver e executar

- **Java 25**: o `app/build.gradle` fixa a toolchain; o Gradle pode baixar o JDK compatível conforme plugins e ambiente.
- **Gradle** via wrapper (`gradlew` / `gradlew.bat`) já versionado no repositório.
- **Internet** na primeira execução, para baixar dependências (Maven Central): Jackson, Guava, JUnit 5, etc.

---

## Dados externos (JSON e arte)

| Arquivo / pasta | Função |
|-----------------|--------|
| `data/map.json` | Matriz de caracteres do mapa; posições e ligações entre nós de batalha. |
| `data/battles.json` | Dicionário com dados das batalhas, contendo os nós `BattleNode`. |
| `data/deck.json` | Lista de cartas do baralho inicial. |
| `assets/*.txt` | Arte ASCII e textos de interface (abertura, seleção de personagem, inimigos, vitória, derrota, etc.). |

A leitura dos JSON usa **Jackson** (`jackson-databind`). Tipos polimórficos (por exemplo cartas com campo `type`, inimigos, efeitos dentro de cartas de efeito) são desserializados com **serializers** em `mc322_slay.serializer`.

---

## Estrutura do projeto

O código é **Java**, organizado com **Gradle** no `app`. Testes usam **JUnit 5** (Jupiter). O plugin **JaCoCo** mede cobertura; a tarefa `check` falha se a cobertura ficar **abaixo de 40%**.

Pastas principais:

- `app/src/main/java` — código-fonte principal, pacote raiz `mc322_slay` e subpacotes.
- `app/src/test/java` — testes unitários (JUnit 5).
- `app/build` — saída de compilação e relatórios gerados (incluindo JaCoCo após `test`).
- `gradle` — arquivos do Gradle Wrapper.
- `data/` — JSON de mapa, batalhas e baralho.
- `assets/` — arquivos de texto e arte para o terminal.

### Sobre as classes e pacotes

**Raiz `mc322_slay`**

- `App` — configura saída **UTF-8**; instancia `GameManager`; é responsável pela tela inicial, seleção de piloto de EVA, montagem do baralho e mecânica do mapa e batalhas até o fim da campanha.
- `GameManager` — representa o estado do jogo; possui herói, `Scanner`, `CardStack` do baralho e `GameMap`; é responsável pelo fluxo de telas, escolha de caminho no mapa, criação de `Battle`, limpeza de efeitos do herói entre lutas e tela de resultado.
- `GameMap` — lê `map.json` e `battles.json`, monta a árvore de nós (usa `javax.swing.tree.DefaultMutableTreeNode`), mantém o nó atual do jogador e imprime o mapa com cores e legenda.
- `BattleNode` — possui id do nó, referência ao `Enemy` daquela luta e se o nó já foi visitado.
- `Battle` — representa o ciclo completo de **um** combate, sendo responsável pela compra de cartas, energia, mão, pilhas, ações do jogador e do inimigo e resultado da luta.
- `Interface` — cores (`ColorEnum`), mensagens e leitura de arquivos em `assets/` via `Files` / `Path`.
- `ColorEnum` — cores disponíveis no terminal.
- `EventEnum` — tipos de evento notificados aos efeitos durante o turno.

**Pacote `mc322_slay.card`**

- `CardStack` — pilha de cartas com embaralhamento; também usada como tipo raiz na desserialização do `deck.json`.
- `PlayerHand` — mão do jogador; interação com compra e descarte.
- `Card` — classe abstrata base das cartas.
- `DamageCard`, `ShieldCard`, `EffectCard` — cartas de dano, escudo e efeito.

**Pacote `mc322_slay.entity`**

- `Entity` — classe abstrata com atributos comuns (vida, escudo, efeitos, etc.).
- `Hero` — herói; inclui reset de escudo ao turno.
- `Enemy` — anjo; planejamento de ação e interação com o herói.
- `EnemyActions` — enum das ações do inimigo (atacar, ganhar escudo, usar efeito).

**Pacote `mc322_slay.effect`**

- `Effect` — base para efeitos observadores de eventos.
- `PsychicEffect` — dano psicológico por turnos.
- `HealthRegeneration` — regeneração por turno.
- `ATFieldCorrosion` — corrosão do campo AT.
- `HighSyncRate` / `LowSyncRate` — modificadores de dano em contexto de cartas de dano, conforme documentado acima.

**Pacote `mc322_slay.serializer`**

- Serializers Jackson que complementam a leitura dos JSON para `CardStack`, cartas concretas, `Enemy` e subclasses de `Effect`.

---

## Execução dos testes unitários

Certifique-se que está na **raiz do repositório** (onde está `settings.gradle`):

**Linux / macOS**

```bash
./gradlew test
```

Para rodar também a verificação de cobertura (e falhar se estiver abaixo de 40%):

```bash
./gradlew check
```

---

## Compilação e execução

Certifique-se que está na **raiz do repositório** (onde está `settings.gradle`):

**Linux / macOS** — na primeira vez, se o script não for executável:

```bash
chmod +x gradlew
```

Compilar **sem** executar os testes:

```bash
./gradlew build -x test
```

Executar o jogo:

```bash
./gradlew :app:run
```

---

## Contribuição de IA generativa

A documentação **Javadoc** dos arquivos Java, trechos de texto deste **README** e descrições detalhadas de efeitos foram elaborados com auxílio de **inteligências artificiais generativas** (por exemplo **Gemini**, Google DeepMind, e **Cursor AI**, Anysphere, Inc.). Esses modelos também apoiaram o uso das bibliotecas **`Files`**, **`Path`** e **`Paths`** para impressão de arquivos `.txt`, parte dos **testes unitários com JUnit 5**, e a integração com **Jackson** (leitura de JSON e serializers) para mapa, batalhas e baralho.
