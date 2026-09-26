# Roteiro de Vídeo — Padrão Decorator

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Decorator_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **7 a 9 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Decorator**, mais um padrão **estrutural** do
> catálogo GoF."

Frase-resumo (está no slide): *"Decorator anexa responsabilidades
adicionais a um objeto dinamicamente, envolvendo-o em objetos decoradores;
oferece uma alternativa flexível à herança para estender funcionalidades."*

> Dica de fala: "Pensem em embrulhar um presente: cada camada de papel
> acrescenta algo, mas o que está dentro continua sendo o mesmo presente. O
> Decorator faz isso com objetos."

---

## 1. Problema (slide 2) — ~1min30s

Use o exemplo do slide e do código: uma cafeteria com bebidas e
adicionais.

Pontos a cobrir, na ordem:

1. **Uma cafeteria vende bebidas** (ex.: `CafeExpresso`) que podem receber
   vários adicionais — leite, chocolate, chantilly — em **qualquer
   combinação**.
2. **Uma subclasse para cada combinação** (`CafeComLeite`,
   `CafeComLeiteEChocolate`, ...) faz o número de classes explodir.
3. **Herança é estática**: não dá para adicionar ou remover um
   comportamento de um objeto em tempo de execução.
4. **Colocar todos os adicionais como flags** na classe base (`temLeite`,
   `temChocolate`...) a deixa inchada e obriga a alterá-la a cada novo
   adicional.

> Dica de fala: "Com 3 adicionais já temos 8 combinações possíveis. Com 5,
> são 32. Criar uma classe para cada uma não é viável."

---

## 2. Solução (slide 3) — ~1min30s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Definir uma **interface comum**, o Componente (`Bebida`), com
   `descricao()` e `preco()`.
2. O **Componente Concreto** (`CafeExpresso`) implementa o comportamento
   básico.
3. Criar um **Decorador abstrato** (`DecoradorBebida`) que também
   implementa `Bebida` e **guarda uma referência a outra `Bebida`**,
   repassando as chamadas para ela.
4. Cada **Decorador Concreto** (`ComLeite`, `ComChocolate`, ...) sobrescreve
   as operações: chama a bebida envolvida e acrescenta seu próprio
   comportamento.
5. Como decoradores também são `Bebida`s, eles **podem ser empilhados**:
   `new ComChocolate(new ComLeite(new CafeExpresso()))`.

> Dica de fala: "O segredo é que o decorador **é** uma `Bebida` e **tem**
> uma `Bebida`. Por isso o cliente não percebe a diferença entre um café
> puro e um café decorado várias vezes."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min30s

Aponte para o diagrama enquanto explica de cima para baixo:

- **`Bebida`** é o Componente: a interface comum.
- **`CafeExpresso`** é o Componente Concreto: implementa `Bebida`
  diretamente.
- **`DecoradorBebida`** implementa `Bebida` **e** guarda uma `Bebida` (a
  seta tracejada "envolve 1"), delegando a ela por padrão.
- **`ComLeite`, `ComChocolate`, `ComChantilly`** estendem o decorador e
  acrescentam descrição e preço.

> Dica de fala: "Reparem que a estrutura lembra a do Composite: o decorador
> implementa a interface e também aponta para ela. A diferença é que o
> Composite tem **vários** filhos e serve para montar árvores; o Decorator
> envolve **um** objeto e serve para acrescentar comportamento."

---

## 4. Exemplo de Código (slide 5) — ~2min30s

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`Bebida.java`** — a interface Componente, com `descricao()` e
   `preco()`.
2. **`CafeExpresso.java`** — o Componente Concreto: "Café expresso",
   R$ 5,00.
3. **`DecoradorBebida.java`** — destaque o campo `protected final Bebida
   bebida` e como, por padrão, os métodos só repassam a chamada.
4. **`ComLeite.java` / `ComChocolate.java` / `ComChantilly.java`** — cada
   um chama `bebida.preco()` e soma o seu valor; `descricao()` acrescenta o
   nome do adicional.
5. **`Main.java`** — monta um café simples, um com leite e um completo,
   empilhando os decoradores.

**Demonstração ao vivo (recomendado):**

```bash
cd decorator/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.decorator.Main
```

Saída esperada:

```
Café expresso: R$ 5,00
Café expresso, leite: R$ 6,50
Café expresso, leite, chocolate, chantilly: R$ 11,00
```

Comente a saída: o método `imprimir()` do `Main` recebe sempre uma
`Bebida` e não sabe quantas camadas existem. No café completo, a chamada a
`preco()` atravessa chantilly → chocolate → leite → expresso, e cada camada
soma o seu valor: 5,00 + 1,50 + 2,00 + 2,50 = 11,00.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- Adicionar responsabilidades a objetos individuais de forma dinâmica e
  transparente, sem afetar outros objetos da mesma classe.
- Quando estender por herança é impraticável → muitas combinações
  independentes geram uma explosão de subclasses.
- Quando a classe não pode ser estendida por herança (é `final`) ou não se
  tem acesso ao seu código.
- Exemplos: streams de I/O do Java, `Collections.unmodifiableList()`, bordas
  e barras de rolagem em GUIs.

> Dica de fala: "Vocês já usaram Decorator sem saber:
> `new BufferedReader(new InputStreamReader(System.in))`. O
> `BufferedReader` envolve um `Reader` e acrescenta buffer e o método
> `readLine()`."

---

## 6. Prós e Contras (slide 7) — ~1min

- **Prós**: estende o comportamento sem criar subclasses, e em tempo de
  execução; permite combinar comportamentos empilhando decoradores, cada
  classe com uma única responsabilidade; respeita o Princípio
  Aberto/Fechado.
- **Contras**: é difícil remover um decorador específico do meio da pilha;
  o resultado pode depender da ordem dos decoradores; gera muitos objetos
  pequenos e parecidos, o que dificulta a depuração.

> Dica de fala: "Na nossa cafeteria a ordem não importa, porque somar preço
> é comutativo. Mas imaginem um decorador de 'desconto de 10%' e um de
> 'acréscimo de R$ 2': aplicar um antes ou depois do outro muda o valor
> final."

---

## 7. Encerramento — ~30s

> "Resumindo: o Decorator envolve um objeto em outros objetos que
> implementam a mesma interface, acrescentando comportamento em camadas e
> em tempo de execução, sem explodir o número de subclasses. O código
> completo está disponível no repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Se a turma já viu o Adapter, vale contrastar: o Adapter **muda** a interface de um objeto; o Decorator **mantém** a interface e acrescenta comportamento.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~8min, ajuste o ritmo conforme o tempo definido pelo professor.
