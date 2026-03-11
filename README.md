# Jogo de cartas com temática de Neon Genesis Evangelion

<img src="image.png" width="150" height="250" alt="Capa">

## O jogo

O jogo consiste em um sistema de batalhas via terminal onde o jogador enfrenta um inimigo usando cartas. No início da batalha, ele pode escolher entre ser Shinji, Rei ou Asuka (os 3 pilotos de EVA dentro do universo de Evangelion). A cada turno, ele escolhe entre uma arma (carta de dano) ou uma carta que recupera seu campo AT (escudo), ao custo de sua sincronização (energia). Para dar dinamicidade ao jogo, tanto o dano quanto o escudo são aleatórios. Seu inimigo é um anjo, que ataca ao fim de cada turno e também possui seu próprio campo AT. Vence quem eliminar o outro primeiro.

## Estrutura do projeto

- `src`: pasta com as classes .java do projeto, incluindo o App
- `bin`: pasta com os arquivos compilados
- `lib`: pasta de dependências

## Compilação e execução do projeto (em linux)

Para compilar o projeto, basta executar o código abaixo no prompt de comandos:
```
javac -d bin $(find src -name "*.java")
```

Já para executar, use o comando:
```
java -cp bin App
```
