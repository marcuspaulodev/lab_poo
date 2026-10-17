# Roteiro de Vídeo — Padrão Command

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Command_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **6 a 8 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Command**, ou Comando, o primeiro padrão
> **comportamental** do catálogo GoF que vamos ver."

Frase-resumo (está no slide): *"Command encapsula uma solicitação como um
objeto, permitindo parametrizar clientes com diferentes solicitações,
enfileirá-las, registrá-las e suportar operações que podem ser desfeitas."*

> Dica de fala: "Os criacionais tratam de como criar objetos, os
> estruturais de como montá-los. Os comportamentais tratam de como os
> objetos **se comunicam e dividem responsabilidades**. No Command, a ideia
> central é transformar um pedido — 'insira este texto' — em um objeto."

---

## 1. Problema (slide 2) — ~1min15s

Use o exemplo do slide e do código: um editor de texto.

Pontos a cobrir, na ordem:

1. **A mesma ação pode ser disparada de vários lugares**: botão, menu,
   atalho de teclado.
2. **Quem dispara fica acoplado a quem executa**: cada botão precisa
   conhecer o `EditorTexto` e chamar seus métodos diretamente.
3. **A lógica de cada ação acaba duplicada** entre menu, botão e atalho.
4. **Não há onde guardar o histórico**, então implementar o "Desfazer"
   (Ctrl+Z) fica difícil.

> Dica de fala: "Se a ação é só uma chamada de método, depois que ela
> acontece não sobra nada. Para desfazer, eu precisaria lembrar o que foi
> feito, com quais dados. Esse 'lembrar' é justamente o que falta."

---

## 2. Solução (slide 3) — ~1min15s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Definir uma interface **`Comando`** com `executar()` e `desfazer()`.
2. Criar um **Comando Concreto** para cada ação (`InserirTextoComando`,
   `ApagarTextoComando`), que guarda o **Receiver** (`EditorTexto`) e os
   parâmetros da ação.
3. O **Receiver** continua fazendo o trabalho real; o comando só sabe qual
   método chamar e com quais dados.
4. O **Invoker** (`GerenciadorComandos`) só chama `executar()`, sem saber o
   que o comando faz, e empilha cada comando executado.
5. Para desfazer, o Invoker desempilha o último comando e chama
   `desfazer()`.

> Dica de fala: "Reparem que o `GerenciadorComandos` não sabe o que é
> inserir nem apagar. Ele só sabe que tem na mão um `Comando`. Por isso
> qualquer comando novo funciona com ele, inclusive o desfazer."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min

Aponte para o diagrama enquanto explica de cima para baixo:

- **`Main`** (Cliente) cria os comandos, ligando cada um ao `EditorTexto`,
  e os entrega ao `GerenciadorComandos`.
- **`GerenciadorComandos`** (Invoker) conhece só a interface **`Comando`**.
- **`InserirTextoComando`** e **`ApagarTextoComando`** (Comandos Concretos)
  implementam `Comando` (setas tracejadas) e delegam o trabalho ao
  **`EditorTexto`** (Receiver).

> Dica de fala: "O Invoker de um lado, o Receiver do outro, e no meio os
> comandos fazendo a ponte. Invoker e Receiver nunca se conhecem."

---

## 4. Exemplo de Código (slide 5) — ~2min

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`Comando.java`** — a interface: `executar()`, `desfazer()` e
   `descricao()` (usada só para imprimir o histórico).
2. **`EditorTexto.java`** — o Receiver: `inserir()` e `apagar()`, que
   devolve o trecho apagado.
3. **`InserirTextoComando.java`** — guarda o editor e o texto; desfazer é
   apagar a mesma quantidade de caracteres.
4. **`ApagarTextoComando.java`** — destaque que ele **guarda o trecho
   apagado** ao executar, para conseguir restaurá-lo no `desfazer()`.
5. **`GerenciadorComandos.java`** — o Invoker com a pilha (`Deque`) de
   histórico.
6. **`Main.java`** — insere, apaga e desfaz tudo, passo a passo.

**Demonstração ao vivo (recomendado):**

```bash
cd command/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.command.Main
```

Saída esperada:

```
Executou: inserir "Olá"
  Texto: "Olá"
Executou: inserir " mundo"
  Texto: "Olá mundo"
Executou: apagar 6 caracteres
  Texto: "Olá"
Desfez:   apagar 6 caracteres
  Texto: "Olá mundo"
Desfez:   inserir " mundo"
  Texto: "Olá"
Desfez:   inserir "Olá"
  Texto: ""
Nada para desfazer
```

Comente a saída: os comandos são desfeitos na **ordem inversa** em que
foram executados, porque o histórico é uma pilha. O " mundo" apagado
voltou porque o `ApagarTextoComando` guardou o trecho. A última chamada
mostra o caso de pilha vazia.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- Parametrizar objetos (botões, menus, atalhos) com a ação que eles devem
  executar.
- Suportar desfazer/refazer, guardando um histórico de operações.
- Enfileirar, agendar ou executar solicitações em outro momento ou em
  outra thread.
- Exemplos: `Runnable` e `ExecutorService`, `javax.swing.Action`,
  histórico de editores e filas de tarefas.

> Dica de fala: "Toda vez que vocês passam um `Runnable` para uma thread
> ou para um `ExecutorService`, estão usando a ideia do Command: um pedido
> embrulhado em um objeto, executado depois por outra pessoa."

---

## 6. Prós e Contras (slide 7) — ~1min

- **Prós**: desacopla quem dispara a ação de quem a executa; permite
  desfazer, enfileirar, agendar e registrar operações; segue o
  Open/Closed, já que novos comandos não exigem mudar o Invoker.
- **Contras**: aumenta o número de classes; desfazer exige guardar estado
  em cada comando; adiciona uma camada de indireção que pode ser exagero
  para ações simples.

> Dica de fala: "Se a aplicação tem uma ação só e ninguém precisa
> desfazê-la, um Command é burocracia. O padrão compensa quando há muitas
> ações, vários pontos de disparo ou necessidade de histórico."

---

## 7. Encerramento — ~30s

> "Resumindo: o Command transforma um pedido em um objeto, com tudo o que
> é preciso para executá-lo e desfazê-lo. Isso separa quem pede de quem
> faz e abre espaço para histórico, filas e Ctrl+Z. É o nosso primeiro
> padrão **comportamental**: o foco agora não é criar nem montar objetos,
> mas organizar como eles colaboram. O código completo está disponível no
> repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Reforçar a mudança de categoria: os padrões vistos até aqui eram criacionais e estruturais; o Command abre os comportamentais.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~7min, ajuste o ritmo conforme o tempo definido pelo professor.
