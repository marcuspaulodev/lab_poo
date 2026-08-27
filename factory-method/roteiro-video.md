# Roteiro de Vídeo — Padrão Factory Method

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Factory_Method_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **7 a 9 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Factory Method**, outro padrão **criacional** do
> catálogo GoF."

Frase-resumo (está no slide): *"Factory Method define uma interface para
criar um objeto, mas deixa as subclasses decidirem qual classe instanciar,
adiando a instanciação para elas."*

> Dica de fala: "Se vocês já viram o Abstract Factory, pensem no Factory
> Method como o 'irmão mais simples': aqui não existe uma família de
> fábricas trocáveis, existe **um único método de fábrica dentro de uma
> classe**, que as subclasses sobrescrevem."

---

## 1. Problema (slide 2) — ~1min30s

Use o exemplo do slide e do código: uma empresa de logística que precisa
planejar entregas, mas o meio de transporte muda conforme o contexto —
**caminhão** (rodoviário) ou **navio** (marítimo).

Pontos a cobrir, na ordem:

1. **A classe não deve conhecer o produto concreto antecipadamente** — a
   classe `Logistics` sabe que existe "um transporte", mas não deveria
   precisar saber, no seu próprio código, se é um `Truck` ou um `Ship`.
2. **`new` direto = acoplamento e violação do OCP** — se `Logistics`
   decidisse com `if/else` + `new Truck()` / `new Ship()`, toda vez que
   surgisse um novo modal (ex.: `Plane`) seria preciso alterar essa classe.
3. **O algoritmo é o mesmo, só o produto muda** — o passo a passo de
   "planejar entrega" (`planDelivery()`) é idêntico para qualquer modal;
   só a parte de "qual veículo usar" varia.

> Dica de fala: "É um problema parecido com o do Abstract Factory, mas aqui
> só existe **um produto** variando (o transporte), não uma família
> inteira de produtos relacionados."

---

## 2. Solução (slide 3) — ~1min30s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Criar uma **interface de produto** (`Transport`).
2. Na classe **Creator** (`Logistics`), declarar um **método de fábrica
   abstrato** (`createTransport()`) — este é o "Factory Method" que dá
   nome ao padrão.
3. Implementar a **lógica de negócio comum** (`planDelivery()`) uma única
   vez, na classe base, chamando apenas o Factory Method — sem saber qual
   produto concreto será devolvido.
4. Cada **Concrete Creator** (`RoadLogistics`, `SeaLogistics`) sobrescreve
   o Factory Method devolvendo a variante correta (`Truck`, `Ship`).
5. O cliente só enxerga a classe abstrata `Logistics`; a subclasse
   escolhida na hora da criação decide, por trás dos panos, qual produto
   será usado.

> Dica de fala: "Reparem que o polimorfismo faz o trabalho pesado aqui: é
> a sobrescrita do método `createTransport()` que muda o comportamento,
> não um `if` espalhado pelo código."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min30s

Aponte para o diagrama enquanto explica de cima para baixo:

- **Main (cliente)** usa a classe abstrata `Logistics`.
- **Logistics** (abstract) declara `createTransport()` como abstrato e
  implementa `planDelivery()` — o método que **não muda** entre subclasses.
- **RoadLogistics** e **SeaLogistics** **estendem** `Logistics` e
  sobrescrevem só o `createTransport()`.
- **Transport** é a interface de produto (`deliver()`).
- **Truck** e **Ship** são os produtos concretos.

> Dica de fala: "A diferença estrutural chave para o Abstract Factory:
> ali eram *objetos* fábrica separados implementando uma interface; aqui é
> um *método* dentro da própria hierarquia de classes do Creator, usando
> herança em vez de composição de uma factory externa."

---

## 4. Exemplo de Código (slide 5) — ~2min30s

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`Transport.java`** — a interface do produto.
2. **`Logistics.java`** — a classe abstrata Creator. Destaque as duas
   partes: o método abstrato `createTransport()` (o Factory Method em si)
   e o método concreto `planDelivery()` (a lógica de negócio reaproveitada).
3. **`RoadLogistics.java`** (e mencione que `SeaLogistics.java` é análoga)
   — mostre que a subclasse só precisa sobrescrever um método.
4. **`Main.java`** — o único lugar do sistema que decide a subclasse:
   ```java
   Logistics logistics = modal.equals("sea") ? new SeaLogistics() : new RoadLogistics();
   logistics.planDelivery();
   ```

**Demonstração ao vivo (recomendado):**

```bash
cd factory-method/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.factorymethod.Main road
java -cp bin com.labpoo.factorymethod.Main sea
```

Comente a saída: a mesma chamada `logistics.planDelivery()` produz um
comportamento diferente, dependendo apenas de qual subclasse concreta foi
instanciada em `Main`.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- Classe não pode antecipar o que vai criar → frameworks que definem um
  fluxo genérico e esperam que o código cliente "encaixe" o objeto certo.
- Subclasses especificam o objeto criado → hierarquias de UI onde cada
  tela cria seu próprio tipo de diálogo/documento.
- Centralizar a decisão de qual produto usar → drivers/parsers que mudam
  conforme o formato de arquivo.
- Frameworks extensíveis → cite `Collection.iterator()` do Java: cada
  coleção decide qual `Iterator` concreto devolver.

---

## 6. Prós e Contras (slide 7) — ~1min

Leia a tabela destacando o trade-off central:

- **Prós**: baixo acoplamento entre Creator e produtos concretos, OCP
  (novo produto = nova subclasse), lógica de negócio reaproveitada, fácil
  de testar com subclasses "fake".
- **Contras**: pode gerar muitas subclasses só para variar o produto, e
  fica **menos óbvio olhando o código do cliente** qual classe concreta
  será instanciada em tempo de execução — é preciso rastrear a hierarquia.

> Dica de fala: "Vale a reflexão: para poucas variações, às vezes um
> `if/switch` simples é mais legível que criar uma subclasse inteira só
> para trocar um `new`. O padrão compensa quando essa variação tende a
> crescer."

---

## 7. Encerramento — ~30s

> "Resumindo: o Factory Method é ideal quando uma classe precisa delegar
> a criação de um objeto para suas subclasses, mantendo o restante do
> algoritmo intacto. É um dos padrões mais usados na prática, principalmente
> dentro de frameworks e bibliotecas. O código completo está disponível
> no repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`, variantes `road` e `sea`) uma vez, sem gravar.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Se sua turma já apresentou o Abstract Factory antes, aproveite para puxar o contraste (uma fábrica-objeto com família de produtos vs. um método-fábrica sobrescrito por herança) — ajuda a fixar os dois padrões.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~8min, ajuste o ritmo conforme o tempo definido pelo professor.
