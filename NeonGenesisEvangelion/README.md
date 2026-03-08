### Jogo de cartas com temática de Neon Genesis Evangelion

<img src="image.png" width="150" height="250" alt="Capa">

## O jogo

O jogo consiste em um sistema de batalhas via terminal onde o jogador enfrenta um inimigo usando cartas. A cada turno, ele escolhe diferntes armas (cartas de dano) ou cartas que recuperam seu campo AT (escudo), ao custo de sua sincronização (energia). Seu inimigo é um anjo, que ataca ao fim de cada turno e também possui seu próprio campo AT. Vence quem eliminar o outro primeiro.

## Estrutura do projeto

- `src`: pasta com as classes .java do projeto, incluindo o App
- `bin`: pasta com os arquivos compilados
- `lib`: pasta de dependências

## Compilação e execução do projeto

Para compilar o projeto, basta executar o código abaixo no prompt de comandos:
```
javac -d bin $(find src -name "*.java")
```

Já para executar, use o comando:
```
java -cp bin App
```
