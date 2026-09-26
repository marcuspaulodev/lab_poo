# Roteiro de Vídeo — Padrão Composite

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Composite_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **7 a 9 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Composite**, mais um padrão **estrutural** do catálogo
> GoF — depois do Adapter e do Bridge, que já vimos antes."

Frase-resumo (está no slide): *"Composite compõe objetos em estruturas de
árvore para representar hierarquias parte-todo, permitindo que o cliente
trate objetos individuais e composições de maneira uniforme."*

> Dica de fala: "A palavra-chave aqui é **uniforme**: a ideia do Composite
> é que o código cliente não precise saber se está lidando com um único
> objeto ou com uma árvore inteira de objetos."

---

## 1. Problema (slide 2) — ~1min30s

Use o exemplo do slide e do código: um sistema de arquivos, onde uma pasta
contém arquivos e também outras pastas.

Pontos a cobrir, na ordem:

1. **Muitos domínios são hierárquicos**: uma `Pasta` contém `Arquivo`s e
   outras `Pasta`s, que contêm mais itens — formando uma árvore.
2. **Se `Arquivo` e `Pasta` não têm nada em comum**, o cliente precisa
   testar o tipo de cada item (`instanceof`, `if/else`) para saber como
   tratá-lo.
3. **Operações sobre a árvore inteira** (ex.: calcular o tamanho total)
   ficam cheias de condicionais e precisam descer manualmente cada nível.
4. **Adicionar um novo tipo de item** (ex.: `Atalho`) obriga a alterar
   todo código cliente que percorre a estrutura.

> Dica de fala: "Imaginem calcular o tamanho de uma pasta sem o padrão:
> 'se for arquivo, soma o tamanho; se for pasta, entra nela e repete tudo
> de novo'. Esse `if` se espalha por cada lugar que percorre a árvore."

---

## 2. Solução (slide 3) — ~1min30s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Definir uma **interface comum**, o Component (`ItemSistemaArquivos`),
   com as operações que fazem sentido para itens simples e compostos
   (`nome()`, `tamanho()`, `exibir()`).
2. A **Folha** (`Arquivo`) implementa a interface e faz o trabalho real,
   sem ter filhos.
3. O **Composto** (`Pasta`) também implementa a interface, mas guarda uma
   **lista de filhos do tipo Component**.
4. O Composto **delega** o trabalho aos filhos e combina os resultados —
   `tamanho()` da pasta é a soma dos `tamanho()` dos filhos — de forma
   **recursiva**.
5. O cliente trabalha apenas com a interface, sem saber se tem uma folha
   ou uma árvore inteira nas mãos.

> Dica de fala: "O truque é que a `Pasta` guarda filhos do tipo da
> interface, e não do tipo `Arquivo`. Assim, um filho pode ser outra
> `Pasta`, e a recursão acontece naturalmente pelo polimorfismo."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min30s

Aponte para o diagrama enquanto explica de cima para baixo:

- **`Main`** (Cliente) conhece apenas a interface `ItemSistemaArquivos`.
- **`ItemSistemaArquivos`** é o Component: a interface comum a folhas e
  compostos.
- **`Arquivo`** é a Folha: implementa as operações diretamente.
- **`Pasta`** é o Composto: implementa a interface **e** guarda uma lista
  de `ItemSistemaArquivos` (a seta tracejada "filhos 0..*"), além de
  oferecer `adicionar()` e `remover()`.

> Dica de fala: "Reparem que a `Pasta` aparece duas vezes ligada à
> interface: uma vez porque **é** um `ItemSistemaArquivos` (implementa) e
> outra porque **contém** vários `ItemSistemaArquivos` (composição). Essa
> dupla relação é a assinatura do Composite."

---

## 4. Exemplo de Código (slide 5) — ~2min30s

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`ItemSistemaArquivos.java`** — a interface Component, com `nome()`,
   `tamanho()` e `exibir(indentacao)`.
2. **`Arquivo.java`** — a Folha: guarda nome e tamanho fixo, e `exibir()`
   só imprime a própria linha.
3. **`Pasta.java`** — o Composto: destaque o campo
   `List<ItemSistemaArquivos> filhos`, os métodos `adicionar`/`remover`, e
   como `tamanho()` soma os filhos e `exibir()` chama `exibir()` de cada
   filho com mais indentação.
4. **`Main.java`** — monta a árvore (`projeto/` contendo `src/`, `docs/` e
   `README.md`) e chama `exibir()` e `tamanho()` só na raiz.

**Demonstração ao vivo (recomendado):**

```bash
cd composite/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.composite.Main
```

Saída esperada:

```
projeto/ (132 KB)
  src/ (10 KB)
    Main.java (4 KB)
    Pasta.java (6 KB)
  docs/ (120 KB)
    manual.pdf (120 KB)
  README.md (2 KB)
Tamanho total: 132 KB
```

Comente a saída: uma única chamada `projeto.exibir("")` percorreu a árvore
inteira, e cada pasta mostra o tamanho somado dos seus filhos (`src/` =
4 + 6 = 10 KB; `projeto/` = 10 + 120 + 2 = 132 KB). O `Main` nunca
precisou perguntar se um item era `Arquivo` ou `Pasta`.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- O domínio forma uma hierarquia parte-todo representável como árvore →
  pastas e arquivos, organogramas, menus e submenus.
- O cliente deve tratar objetos simples e composições da mesma forma, sem
  condicionais por tipo.
- Interfaces gráficas → um painel contém botões, campos e outros painéis,
  e todos sabem se desenhar.
- Expressões e documentos → uma expressão contém subexpressões; um
  documento contém seções, parágrafos e imagens.

> Dica de fala: "Em Java, o próprio Swing/AWT usa Composite: um
> `Container` é um `Component` que contém outros `Component`s."

---

## 6. Prós e Contras (slide 7) — ~1min

- **Prós**: simplifica o trabalho com árvores complexas usando
  polimorfismo e recursão; respeita o Princípio Aberto/Fechado (novos
  tipos de folha ou composto sem quebrar o cliente); o mesmo código
  funciona para um item isolado ou para a árvore inteira.
- **Contras**: a interface comum pode ficar genérica demais quando as
  classes são muito diferentes; é difícil restringir quais tipos de filhos
  um composto aceita; operações como `adicionar()`/`remover()` não fazem
  sentido para folhas — colocá-las na interface sacrifica segurança de
  tipos.

> Dica de fala: "No nosso código, optamos por deixar `adicionar()` e
> `remover()` só na `Pasta`, priorizando a segurança de tipos. A
> alternativa — colocar esses métodos na interface — deixa o cliente ainda
> mais uniforme, mas obriga o `Arquivo` a implementar métodos que não fazem
> sentido para ele. É um trade-off clássico do padrão."

---

## 7. Encerramento — ~30s

> "Resumindo: o Composite organiza objetos em árvore e faz com que folhas
> e compostos compartilhem a mesma interface, para que o cliente trate
> tudo de forma uniforme. Comparando com os padrões estruturais que já
> vimos: o Adapter conecta interfaces incompatíveis, o Bridge separa
> abstração de implementação, e o Composite monta hierarquias parte-todo.
> O código completo está disponível no repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Se a turma já viu Adapter e Bridge, aproveite para contrastar os três padrões estruturais no encerramento.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~8min, ajuste o ritmo conforme o tempo definido pelo professor.
