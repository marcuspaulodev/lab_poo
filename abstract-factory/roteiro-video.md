# Roteiro de Vídeo — Padrão Abstract Factory

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Abstract_Factory_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **7 a 9 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o padrão
> de projeto **Abstract Factory**, um dos padrões **criacionais** do catálogo
> GoF (Gang of Four)."

- Diga rapidamente o que é um padrão criacional (padrões que tratam de
  **como objetos são criados**, escondendo a lógica de instanciação do
  código cliente).
- Frase-resumo (está no slide): *"Abstract Factory fornece uma interface
  para criar famílias de objetos relacionados sem especificar suas classes
  concretas."*

---

## 1. Problema (slide 2) — ~1min30s

Explique o cenário com um exemplo concreto (o mesmo do slide e do código):
uma aplicação que precisa desenhar componentes de interface gráfica
(**botão** e **checkbox**) em dois sistemas operacionais diferentes,
**Windows** e **Mac**, cada um com sua própria aparência.

Pontos a cobrir, na ordem:

1. **Famílias de objetos relacionados** — botão e checkbox não são
   independentes: se a aplicação está no "modo Windows", ambos precisam ter
   a cara do Windows; nunca pode nascer um botão Windows ao lado de um
   checkbox Mac.
2. **Múltiplas variantes** — o sistema deve suportar mais de uma família
   (Windows, Mac, e no futuro talvez Linux) e trocar de uma para outra sem
   reescrever a aplicação.
3. **O problema de usar `new` diretamente** — se o código cliente decidir
   "na mão" `new WindowsButton()` ou `new MacButton()` espalhado pelo
   sistema, qualquer manutenção ou nova variante obriga a mexer em vários
   lugares — isso fere o **Princípio Aberto-Fechado (OCP)**, que vocês já
   viram na aula de SOLID.

> Dica de fala: "Repara que esse problema é justamente o que o OCP nos
> alertava: a classe cliente não deveria precisar mudar toda vez que surge
> uma nova variante de produto."

---

## 2. Solução (slide 3) — ~1min30s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Criar uma **interface para cada tipo de produto** da família
   (`Button`, `Checkbox`).
2. Criar a interface **Abstract Factory** (`GUIFactory`) com um método de
   criação para cada produto (`createButton()`, `createCheckbox()`).
3. Criar uma **Concrete Factory por variante** (`WindowsFactory`,
   `MacFactory`), cada uma implementando todos os métodos de criação com
   as classes concretas certas.
4. O **cliente só conhece interfaces** — nunca importa `WindowsButton` ou
   `MacButton` diretamente.
5. A variante é escolhida **uma única vez** (configuração, argumento de
   linha de comando, injeção de dependência) e a partir daí toda a família
   de objetos criada é garantidamente compatível.

> Dica de fala: "O truque central é que a decisão de 'qual variante usar'
> acontece em um único ponto do sistema — todo o resto do código trabalha
> só com as interfaces."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min30s

Aponte para o diagrama enquanto explica de cima para baixo:

- **Application (cliente)** usa apenas `GUIFactory`.
- **GUIFactory** é a interface Abstract Factory: declara
  `createButton()` e `createCheckbox()`.
- **WindowsFactory** e **MacFactory** implementam `GUIFactory` — cada uma é
  uma Concrete Factory responsável por UMA família completa.
- **Button** e **Checkbox** são interfaces de produto (Abstract Product).
- **WindowsButton/WindowsCheckbox** e **MacButton/MacCheckbox** são os
  produtos concretos.

> Dica de fala: "Reparem que as setas de 'realiza' (WindowsFactory →
> GUIFactory) mostram herança de interface, e as setas tracejadas mostram
> quem cria quem: a WindowsFactory só cria produtos Windows, nunca um
> produto Mac."

---

## 4. Exemplo de Código (slide 5) — ~2min30s

Aqui é o momento de mostrar o código de verdade. Sugestão: alterne entre o
slide (para o público acompanhar a estrutura) e o editor/terminal com o
projeto em `codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`GUIFactory.java`** — mostre a interface, reforce que ela só declara
   *o que* é criado, não *como*.
2. **`Button.java` / `Checkbox.java`** — as interfaces de produto.
3. **`WindowsFactory.java`** (e mencione que `MacFactory.java` é análoga) —
   mostre como cada método `create...()` devolve a implementação concreta
   certa.
4. **`Application.java`** — o cliente: repare que o construtor recebe uma
   `GUIFactory` (não uma `WindowsFactory` especificamente) e chama só
   `button.render()` / `checkbox.render()`.
5. **`Main.java`** — o único lugar do sistema que decide a variante:
   ```java
   GUIFactory factory = os.contains("mac") ? new MacFactory() : new WindowsFactory();
   Application app = new Application(factory);
   app.render();
   ```

**Demonstração ao vivo (recomendado):** compile e rode o projeto mostrando
as duas variantes, para provar que a troca de família não exige alterar
`Application`:

```bash
cd abstract-factory/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.abstractfactory.Main windows
java -cp bin com.labpoo.abstractfactory.Main mac
```

Comente a saída no terminal: mesma classe `Application`, mesma chamada
`app.render()`, mas comportamento e "aparência" diferentes conforme a
factory injetada.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), dando um
exemplo rápido de mundo real para cada um, por exemplo:

- Independência de criação/composição → bibliotecas de UI multiplataforma.
- Configuração com uma entre várias famílias → escolher entre banco de
  dados MySQL/PostgreSQL, ou entre provedores de nuvem AWS/Azure.
- Expor só interfaces → bibliotecas/SDKs de terceiros.
- Consistência entre produtos → drivers de impressora que precisam ter o
  mesmo fabricante para todos os componentes.

---

## 6. Prós e Contras (slide 7) — ~1min

Leia a tabela destacando o trade-off central:

- **Prós**: consistência garantida entre produtos da mesma família,
  baixo acoplamento do cliente com classes concretas, fácil trocar a
  família inteira, responsabilidade de criação isolada (SRP).
- **Contras**: mais interfaces e classes no projeto, e principalmente —
  **adicionar um novo produto** (ex.: `Slider`) obriga a alterar a
  interface `GUIFactory` e **todas** as factories concretas já existentes.

> Dica de fala: "Isso é justamente o custo que a gente discutiu na aula de
> SOLID: mais flexibilidade para novas *variantes*, ao custo de mais
> esforço para adicionar novos *tipos* de produto."

---

## 7. Encerramento — ~30s

> "Resumindo: o Abstract Factory é ideal quando você precisa garantir que
> objetos relacionados sejam sempre usados como uma família consistente,
> e quer trocar essa família inteira sem tocar no código cliente. O
> código completo está disponível no repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`, variantes `windows` e `mac`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~8min, ajuste o ritmo conforme o tempo definido pelo professor.
