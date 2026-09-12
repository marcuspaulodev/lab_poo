# Roteiro de Vídeo — Padrão Builder

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Builder_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **7 a 9 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Builder**, mais um dos padrões **criacionais** do
> catálogo GoF."

Frase-resumo (está no slide): *"Builder separa a construção de um objeto
complexo, passo a passo, da sua representação final, permitindo montar
variações diferentes com o mesmo processo."*

> Dica de fala: "Já vimos três formas de criar objetos: Abstract Factory
> (famílias de objetos), Factory Method (delegar a criação para
> subclasses) e Prototype (copiar um objeto existente). O Builder resolve
> um problema diferente: como montar **um único objeto complexo**, com
> muitas partes, de forma organizada."

---

## 1. Problema (slide 2) — ~1min30s

Use o exemplo do slide e do código: montar um computador, que tem vários
atributos — alguns obrigatórios (CPU, RAM, armazenamento, fonte) e um
opcional (placa de vídeo dedicada).

Pontos a cobrir, na ordem:

1. **Muitos atributos, vários opcionais** — um construtor com todos os
   parâmetros de uma vez fica difícil de ler e de usar.
2. **O "construtor telescópico"** — a tentação de resolver isso com várias
   sobrecargas de construtor (`Computador(cpu, ram)`,
   `Computador(cpu, ram, armazenamento)`, etc.) rapidamente vira uma
   combinatória incontrolável.
3. **Risco de erro com parâmetros posicionais** — se dois parâmetros têm o
   mesmo tipo (ex.: duas Strings seguidas), é fácil trocar a ordem sem
   perceber.
4. **Estados intermediários inválidos** — se a montagem usa setters soltos
   no objeto, ele pode ficar "pela metade" em algum ponto do código antes
   de estar pronto para uso.

> Dica de fala: "Reparem que aqui o problema não é 'qual classe
> instanciar', como nos padrões anteriores — é 'como organizar a
> construção de UMA classe com muitas partes'."

---

## 2. Solução (slide 3) — ~1min30s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Extrair a lógica de construção para uma **classe Builder** separada,
   com um método para cada parte a configurar.
2. Cada método do Builder configura uma parte e **retorna o próprio
   Builder** (fluent interface), permitindo encadear as chamadas.
3. Um método **`build()`** só ao final produz o objeto já pronto e
   validado — nunca existe um objeto "pela metade" exposto ao cliente.
4. Um **Diretor (Director)** opcional encapsula receitas comuns de
   montagem (ex.: "computador gamer", "computador de escritório"),
   reaproveitando o mesmo Builder para produzir configurações diferentes.
5. O cliente pode usar o Diretor para receitas prontas, **ou** usar o
   Builder diretamente para uma configuração sob medida.

> Dica de fala: "O encadeamento de métodos (`.setCpu(...).setRam(...)`)
> não é obrigatório no Builder clássico do GoF, mas é como a maioria dos
> exemplos modernos em Java implementa — deixa o código de montagem muito
> mais legível."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min30s

Aponte para o diagrama enquanto explica de cima para baixo:

- **DiretorMontagem** conhece apenas a interface `ComputadorBuilder`.
- **ComputadorBuilder** (interface) declara os métodos de configuração
  (`setCpu`, `setRam`, ...) e `build()`.
- **ComputadorBuilderPadrao** implementa a interface e acumula os valores
  internamente até `build()` ser chamado.
- **Computador** é o produto final, só devolvido por `build()`.

> Dica de fala: "Notem a separação de responsabilidades: o Diretor sabe
> **a receita** (a ordem e quais partes configurar para um 'gamer' ou um
> 'escritório'), e o Builder sabe **como** de fato montar cada parte. Isso
> é o que permite trocar o Builder concreto sem mexer no Diretor."

---

## 4. Exemplo de Código (slide 5) — ~2min30s

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`ComputadorBuilder.java`** — a interface com os métodos fluentes e o
   `build()`.
2. **`ComputadorBuilderPadrao.java`** — destaque que cada `setX` retorna
   `this`, e que `build()` valida os campos obrigatórios antes de criar o
   `Computador` (imutável, com construtor de pacote).
3. **`Computador.java`** — o produto final, com campos `final` e sem
   setters — só pode ser criado através do Builder.
4. **`DiretorMontagem.java`** — mostre `montarComputadorGamer(builder)` e
   `montarComputadorEscritorio(builder)`, reaproveitando o mesmo builder
   para receitas diferentes.
5. **`Main.java`** — mostre as três formas de uso: via Diretor (gamer), via
   Diretor (escritório), e diretamente pelo Builder (configuração
   personalizada, sem Diretor).

**Demonstração ao vivo (recomendado):**

```bash
cd builder/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.builder.Main
```

Comente a saída: as três configurações de computador foram construídas
com o mesmo `ComputadorBuilder`, mas produzem objetos `Computador`
diferentes — e note que o computador de escritório aparece com
"placaDeVideo=integrada", porque esse campo nunca foi configurado (ele é
opcional).

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- Muitos parâmetros opcionais → APIs de configuração (ex.: builders de
  requisições HTTP, builders de consultas SQL).
- Mesmo processo, representações diferentes → gerar um relatório em PDF e
  em HTML a partir dos mesmos dados, usando builders diferentes.
- Produto final imutável, montagem em etapas → objetos de domínio
  complexos que não devem mudar depois de criados.
- Isolar a complexidade da construção → manter a classe do produto limpa
  e simples, sem lógica de validação/montagem misturada nela.

---

## 6. Prós e Contras (slide 7) — ~1min

Leia a tabela destacando o trade-off central:

- **Prós**: elimina o construtor telescópico e deixa a montagem legível;
  permite reaproveitar o mesmo processo de construção para gerar
  representações diferentes via Diretor; garante que o produto só é
  exposto ao cliente já completo e validado.
- **Contras**: aumenta o número de classes do projeto; só compensa para
  objetos realmente complexos (para objetos simples, é código a mais sem
  necessidade); produtos com muitos atributos obrigatórios diferentes
  podem exigir validações extras dentro de `build()`.

> Dica de fala: "Essa última observação vale para todos os padrões
> criacionais que vimos: eles resolvem um problema real, mas têm um
> custo em número de classes. Vale a pena perguntar sempre: meu objeto é
> complexo o suficiente para justificar isso?"

---

## 7. Encerramento — ~30s

> "Resumindo: o Builder é ideal quando um objeto tem muitas partes,
> especialmente opcionais, e você quer montar essas partes de forma
> legível e controlada, podendo reaproveitar o mesmo processo para gerar
> variações diferentes do produto final. Com Abstract Factory, Factory
> Method e Prototype, ele completa quatro abordagens diferentes para o
> mesmo problema geral de criação de objetos. O código completo está
> disponível no repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Se sua turma já apresentou os outros padrões criacionais, aproveite para puxar o contraste: criar via fábrica, via cópia, ou via montagem passo a passo — ajuda a fixar todos.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~8min, ajuste o ritmo conforme o tempo definido pelo professor.
