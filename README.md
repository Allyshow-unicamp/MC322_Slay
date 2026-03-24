# Jogo de cartas com temática de Neon Genesis Evangelion

<img src="assets/image.png" width="150" height="250" alt="Capa">

## O jogo

O jogo consiste em um sistema de batalhas com baralho completo via terminal, baseado no anime popular Neon Genesis Evangelion, onde o jogador enfrenta um inimigo usando suas cartas. 

No início da batalha, ele pode escolher entre ser Shinji, Rei ou Asuka (os 3 pilotos de EVA dentro do universo de Evangelion). A cada turno o héroi recupera sua sincronização (energia) e tem seu campo AT (escudo) zerado.

Então, ele compra 4 cartas de uma baralho de compras para formar sua mão e escolhe dentre as cartas em sua mão usar uma arma (carta de dano) ou usar uma carta que recupera seu campo AT (carta de escudo), ao custo de sua sincronização (energia). Após as cartas serem utilizadas estas vão para uma pilha de descarte, e ao fim do turno as cartas não utilizadas tem o mesmo destino. 

Para dar dinamicidade ao jogo, tanto o dano das armas quanto o escudo contém um valor base e um incremento aleatório. 

Seu inimigo é um anjo, filho de Lilith, que anuncia sua ação no começo de cada turno e ataca ao fim de cada turno e também possui seu próprio campo AT. O combate continua até a morte de uma das entidades. Vence quem eliminar o outro primeiro.

## Estrutura do projeto

O projeto Java foi criado com a build tool gradle. Assim, a estrutura de pastas consiste em:

- `app/src/main/java`: pasta com as classes .java do projeto, incluindo o App
- `app/build`: pasta com os arquivos compilados e outros arquivos gerados
- `gradle`: contém arquivos específicos de versionamento do gradle

## Sobre as Classes:

- `App`: classe responsável pelas chamadas de metódos do GameManager responsável pelo fluxo do jogo
- `GameManager`:  classe que contém os atributos estáticos do jogo e responsável por toda instanciação das classes, impressão dos menus, seleção de ações, delay entre a execução de ações e novos turnos, lógica de seleção de cartas e combate
- `CardStack`: classe que herda as propriedades de um Stack e acrescenta um método de embaralhamento
- `Entity`: classe abstrata que contém os atributos encapsulados presentes nas classes filhas
- `Hero`: subclasse de Entity que possui método específico de resetar escudo
- `Enemy`: subclasse de Entity que possui método específico de atacar um héroi
- `Card`: classe abstrata que contém os atributos encapsulados, e o método abstrato para usá-la, presente nas classes filhas 
- `ShieldCard`: subclasse de Card que possui método para usá-la que sobrescreve o método de sua superclasse
- `DamageCard`: subclasse de Card que possui método para usá-la que sobrescreve o método de sua superclasse

## Compilação e execução do projeto (em linux)

Para compilar o projeto, basta executar o código abaixo no prompt de comandos, estando na pasta raiz do mesmo:
```
./gradlew build
```

Já para executar, use o comando:
```
./gradlew run
```
