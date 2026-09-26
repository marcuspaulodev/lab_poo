# Roteiro de Vídeo — Padrão Flyweight

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Flyweight_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **7 a 9 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Flyweight**, mais um padrão **estrutural** do
> catálogo GoF."

Frase-resumo (está no slide): *"Flyweight usa compartilhamento para
suportar eficientemente grandes quantidades de objetos, mantendo em comum a
parte do estado que se repete em vez de duplicá-la em cada objeto."*

> Dica de fala: "O nome vem do boxe: 'peso-mosca', a categoria mais leve.
> A ideia é deixar cada objeto o mais leve possível, tirando dele tudo o que
> pode ser compartilhado com os outros."

---

## 1. Problema (slide 2) — ~1min30s

Use o exemplo do slide e do código: uma floresta em um jogo ou mapa, com
centenas de milhares de árvores.

Pontos a cobrir, na ordem:

1. **A aplicação precisa de uma quantidade enorme de objetos parecidos** —
   uma `Floresta` com centenas de milhares de `Arvore`s.
2. **Cada árvore guarda tudo sozinha**: a posição (x, y), que é só dela, e
   também nome, cor e textura, que são **idênticos** entre árvores da mesma
   espécie.
3. **Esses dados repetidos são duplicados em memória** milhares de vezes, e
   o programa pode ficar lento ou até esgotar a RAM.
4. **É preciso separar o que é comum** (e pode ser compartilhado) do que é
   único de cada objeto.

> Dica de fala: "Pensem numa textura de imagem: se cada uma das 100 mil
> árvores carregar a sua própria cópia de `cajueiro.png`, estamos guardando
> o mesmo arquivo 100 mil vezes na memória."

---

## 2. Solução (slide 3) — ~1min30s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Dividir o estado em duas partes:
   - **Intrínseco**: compartilhável, igual para muitos objetos (nome, cor,
     textura).
   - **Extrínseco**: único de cada objeto (posição x, y).
2. Mover o estado intrínseco para um objeto **Flyweight imutável**
   (`TipoArvore`), que passa a ser compartilhado.
3. O objeto de **contexto** (`Arvore`) guarda só o estado extrínseco e uma
   referência ao Flyweight.
4. Uma **Fábrica de Flyweights** (`FabricaTiposArvore`) mantém um cache e
   devolve um Flyweight já existente sempre que possível.
5. As operações recebem o estado extrínseco como **parâmetro**:
   `tipo.desenhar(x, y)`.

> Dica de fala: "O Flyweight precisa ser imutável. Como o mesmo objeto é
> usado por milhares de árvores, se alguém mudasse a cor de um `TipoArvore`,
> todas as árvores daquela espécie mudariam juntas."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min30s

Aponte para o diagrama enquanto explica de cima para baixo:

- **`Floresta`** (Cliente) planta árvores e pede os tipos à fábrica.
- **`Arvore`** (Contexto) guarda o estado extrínseco (`x`, `y`) e uma
  referência ao `TipoArvore`.
- **`TipoArvore`** (Flyweight) guarda o estado intrínseco (`nome`, `cor`,
  `textura`) e oferece `desenhar(x, y)`.
- **`FabricaTiposArvore`** mantém o cache (`Map<String, TipoArvore>`) e
  garante que cada tipo seja criado uma única vez.

> Dica de fala: "Reparem que muitas `Arvore`s apontam para o **mesmo**
> `TipoArvore`. Temos muitos objetos pequenos (contextos) e poucos objetos
> pesados (flyweights)."

---

## 4. Exemplo de Código (slide 5) — ~2min30s

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`TipoArvore.java`** — o Flyweight: todos os campos são `final`
   (imutável), e `desenhar(x, y)` recebe a posição por parâmetro.
2. **`Arvore.java`** — o contexto: só `x`, `y` e a referência `tipo`;
   `desenhar()` apenas repassa a posição para `tipo.desenhar(x, y)`.
3. **`FabricaTiposArvore.java`** — o cache: destaque o `computeIfAbsent`,
   que só cria um `TipoArvore` novo se a chave ainda não existir no `Map`.
4. **`Floresta.java`** — `plantar()` pede o tipo à fábrica em vez de usar
   `new TipoArvore(...)` diretamente.
5. **`Main.java`** — planta 4 árvores e as desenha; depois planta mais
   100 mil cajueiros e mostra quantos tipos foram realmente criados.

**Demonstração ao vivo (recomendado):**

```bash
cd flyweight/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.flyweight.Main
```

Saída esperada:

```
Desenhando Ipê (Amarelo, ipe.png) em (1, 2)
Desenhando Cajueiro (Verde, cajueiro.png) em (5, 3)
Desenhando Ipê (Amarelo, ipe.png) em (8, 7)
Desenhando Pau-brasil (Vermelho, paubrasil.png) em (4, 9)
Árvores plantadas: 100004
Tipos de árvore criados: 3
```

Comente a saída: as duas árvores de Ipê usam **o mesmo** objeto
`TipoArvore`, só com posições diferentes. E, mesmo com mais de 100 mil
árvores, só existem **3** objetos com nome, cor e textura na memória. Sem o
padrão, seriam 100.004.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- A aplicação usa uma quantidade muito grande de objetos, e o custo de
  memória para mantê-los é alto.
- Boa parte do estado pode se tornar extrínseca, e o que sobra se repete
  entre muitos objetos.
- A identidade dos objetos não importa: vários "objetos" podem ser, na
  verdade, a mesma instância.
- Exemplos: caracteres em editores de texto, partículas e árvores em jogos,
  ícones em mapas.

> Dica de fala: "O próprio Java usa Flyweight: `Integer.valueOf(10)` devolve
> sempre o mesmo objeto para valores entre -128 e 127, e literais `String`
> iguais apontam para a mesma instância no pool de Strings."

---

## 6. Prós e Contras (slide 7) — ~1min

- **Prós**: economiza muita memória quando há uma enorme quantidade de
  objetos semelhantes; o estado compartilhado fica em objetos imutáveis,
  seguros para uso por vários contextos; a fábrica garante que cada
  combinação de estado intrínseco é instanciada uma única vez.
- **Contras**: pode trocar memória por processamento, já que o estado
  extrínseco precisa ser repassado ou recalculado a cada chamada; o código
  fica mais complicado, com o estado de um objeto dividido em dois lugares;
  só compensa quando há realmente muitos objetos repetidos.

> Dica de fala: "Flyweight é uma otimização. Se o programa tem só algumas
> dezenas de objetos, não há memória a economizar e o padrão só adiciona
> complexidade. Meça antes de aplicar."

---

## 7. Encerramento — ~30s

> "Resumindo: o Flyweight separa o estado intrínseco, que é compartilhado,
> do estado extrínseco, que é único de cada objeto. Assim, milhares de
> objetos podem reaproveitar poucas instâncias pesadas, e uma fábrica
> garante esse compartilhamento. O código completo está disponível no
> repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Se a turma já viu o Singleton, vale comentar a semelhança: ambos reaproveitam instâncias, mas o Singleton garante **uma** instância da classe, enquanto o Flyweight mantém **uma instância por combinação** de estado intrínseco.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~8min, ajuste o ritmo conforme o tempo definido pelo professor.
