# Roteiro de Vídeo — Padrão Interpreter

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Interpreter_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **6 a 8 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Interpreter**, ou Interpretador, um padrão
> **comportamental** do catálogo GoF."

Frase-resumo (está no slide): *"Interpreter, dada uma linguagem, define
uma representação para sua gramática junto com um interpretador que usa
essa representação para interpretar sentenças dessa linguagem."*

> Dica de fala: "Parece coisa de compilador, mas a ideia é bem mais
> simples: quando um problema pode ser escrito como uma 'frase' de uma
> linguagem pequena, cada regra dessa linguagem vira uma classe."

---

## 1. Problema (slide 2) — ~1min15s

Use o exemplo do slide e do código: expressões aritméticas com variáveis.

Pontos a cobrir, na ordem:

1. **Alguns problemas se repetem** e podem ser escritos como sentenças de
   uma linguagem simples: fórmulas, regras de negócio, filtros de busca.
2. **Exemplo**: avaliar `(x + 5) * (y - 2)` para diferentes valores de `x`
   e `y`.
3. **Um bloco gigante de `if`/`switch`** sobre strings deixa o código
   difícil de ler, testar e estender.
4. **Adicionar um novo operador** obriga a mexer nesse código central.

> Dica de fala: "Imaginem uma planilha. O usuário digita uma fórmula e o
> sistema precisa calcular o resultado, e recalcular sempre que uma
> célula mudar. Escrever isso com `if`s vira um pesadelo rapidinho."

---

## 2. Solução (slide 3) — ~1min15s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Descrever a linguagem por uma **gramática simples** e criar **uma
   classe para cada regra**.
2. **Expressões Terminais** (`Numero`, `Variavel`) são as folhas: produzem
   um valor diretamente.
3. **Expressões Não-terminais** (`Soma`, `Subtracao`, `Multiplicacao`)
   guardam outras expressões e combinam os resultados delas.
4. Todas implementam **`interpretar(Contexto)`**; o **Contexto** guarda as
   informações externas, como os valores das variáveis.
5. Cada sentença vira uma **árvore sintática**, avaliada recursivamente a
   partir da raiz.

> Dica de fala: "Quem viu o Composite vai reconhecer a árvore: os
> terminais são as folhas, os não-terminais são os nós compostos. O
> Interpreter é praticamente um Composite com uma operação
> `interpretar()`."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min

Aponte para o diagrama enquanto explica de cima para baixo:

- **`Main`** (Cliente) monta a árvore e chama `interpretar()` passando um
  **`Contexto`**.
- **`Expressao`** é a interface comum a todas as regras.
- **`Numero` e `Variavel`** (Terminais) e **`Soma`, `Subtracao`,
  `Multiplicacao`** (Não-terminais) implementam `Expressao` (setas
  tracejadas).
- Os não-terminais **guardam duas `Expressao`** (`esquerda` e `direita`):
  é o losango ligando de volta à interface, o que permite aninhar
  expressões em qualquer profundidade.

> Dica de fala: "Esse losango é o segredo da recursão: uma `Soma` não sabe
> se está somando dois números ou duas multiplicações inteiras. Ela só
> chama `interpretar()` dos dois lados."

---

## 4. Exemplo de Código (slide 5) — ~2min

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`Expressao.java`** e **`Contexto.java`** — a interface e o mapa de
   variáveis.
2. **`Numero.java`** e **`Variavel.java`** — terminais; `Variavel` busca o
   próprio valor no `Contexto`.
3. **`Soma.java`** (e `Subtracao`, `Multiplicacao`) — interpretam os dois
   lados e combinam os resultados.
4. **`AnalisadorPosfixo.java`** — transforma a sentença em notação
   pós-fixa `"x 5 + y 2 - *"` na árvore, usando uma pilha. Lembre que a
   análise sintática **não faz parte do padrão**: ele só define como a
   árvore é representada e interpretada.
5. **`Main.java`** — interpreta a mesma árvore com dois contextos e monta
   uma segunda árvore à mão.

**Demonstração ao vivo (recomendado):**

```bash
cd interpreter/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.interpreter.Main
```

Saída esperada:

```
Sentença: x 5 + y 2 - *
Árvore:   ((x + 5) * (y - 2))
Com x = 3 e y = 10: 64
Com x = 1 e y = 4:  12
Árvore montada à mão: (2 + (x * 4)) = 14
```

Comente a saída: a árvore foi montada **uma vez** e interpretada duas
vezes, com contextos diferentes. `(3 + 5) * (10 - 2) = 64` e
`(1 + 5) * (4 - 2) = 12`. A última linha mostra que dá para montar a
árvore direto com `new`, sem o analisador.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- Há uma linguagem simples a interpretar e suas sentenças podem ser
  representadas como árvores.
- A gramática é pequena e estável; para gramáticas grandes, geradores de
  parser (como ANTLR) são mais adequados.
- Eficiência não é a principal preocupação.
- Exemplos: `java.util.regex.Pattern`, expressões SpEL/EL, regras de
  negócio configuráveis, calculadoras e consultas simples.

> Dica de fala: "Quando vocês escrevem `#{usuario.nome}` em uma página JSF,
> ou uma expressão SpEL em uma anotação do Spring, por trás existe uma
> árvore de expressões sendo interpretada."

---

## 6. Prós e Contras (slide 7) — ~1min

- **Prós**: gramática fácil de estender (nova regra = nova classe); cada
  regra isolada e simples de testar; a mesma árvore pode ser interpretada
  em contextos diferentes.
- **Contras**: gramáticas grandes geram muitas classes; desempenho
  inferior ao de parsers dedicados; o padrão não resolve a análise
  sintática.

> Dica de fala: "Querem adicionar divisão? É criar uma classe `Divisao` e
> um `case` no analisador. Nenhuma das classes existentes muda."

---

## 7. Encerramento — ~30s

> "Resumindo: o Interpreter representa cada regra de uma linguagem simples
> como uma classe e cada sentença como uma árvore dessas classes, que se
> avalia recursivamente. É um padrão **comportamental** muito próximo do
> Composite: a estrutura é a mesma árvore, mas o foco aqui é o
> comportamento de interpretar. O código completo está disponível no
> repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Se a turma já viu o Composite, reforce a ligação: a árvore de expressões é um Composite.
- [ ] Ter pronta uma explicação curta da notação pós-fixa (operador depois dos operandos), caso alguém pergunte.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~7min, ajuste o ritmo conforme o tempo definido pelo professor.
