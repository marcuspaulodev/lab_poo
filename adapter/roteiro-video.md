# Roteiro de Vídeo — Padrão Adapter

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Adapter_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **7 a 9 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Adapter** — o primeiro padrão **estrutural** do
> catálogo GoF que vamos ver, depois de já termos coberto os cinco padrões
> criacionais (Abstract Factory, Factory Method, Prototype, Builder e
> Singleton)."

Frase-resumo (está no slide): *"Adapter permite que classes com interfaces
incompatíveis trabalhem juntas, convertendo a interface de uma classe
existente na interface esperada pelo cliente."*

> Dica de fala: "Os padrões criacionais que vimos até aqui tratam de
> **como criar objetos**. Os padrões estruturais, a partir de agora, tratam
> de **como compor classes e objetos** para formar estruturas maiores — e o
> Adapter é o exemplo mais simples e mais direto dessa categoria."

---

## 1. Problema (slide 2) — ~1min30s

Use o exemplo do slide e do código: um reprodutor de mídia que já sabe tocar
mp3, mas precisa reaproveitar um reprodutor avançado (`ReprodutorAvancado`)
que já sabe tocar vlc e mp4 — só que com uma interface diferente.

Pontos a cobrir, na ordem:

1. **Já existe uma classe pronta e testada** que resolve o problema —
   `ReprodutorAvancado`, com os métodos `reproduzirVlc()` e
   `reproduzirMp4()` — mas sua interface não é a que o cliente espera usar
   (`reproduzir(tipo, nome)`).
2. **Não é possível, ou não é desejável, alterar essa classe existente** —
   pode ser uma biblioteca de terceiros ou código legado usado em várias
   partes do sistema.
3. **O cliente já está escrito contra sua própria interface**
   (`ReprodutorMidia`, com `reproduzir(tipo, nome)`) e não deveria precisar
   conhecer os detalhes internos de cada formato suportado.
4. **É preciso encaixar essa classe incompatível no sistema** sem duplicar
   sua lógica e sem forçar o cliente a se adaptar a ela.

> Dica de fala: "Pensem em situações parecidas no dia a dia: um adaptador de
> tomada, que não muda a energia elétrica em si, só encaixa um plugue de um
> formato na tomada de outro formato. É exatamente essa ideia que o padrão
> traz para código."

---

## 2. Solução (slide 3) — ~1min30s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Definir a **interface Target** que o cliente já espera usar
   (`ReprodutorMidia`, com `reproduzir(tipo, nome)`).
2. Identificar a **classe existente e incompatível** — o **Adaptee**
   (`ReprodutorAvancado`, com `reproduzirVlc()` e `reproduzirMp4()`).
3. Criar uma classe **Adapter** (`AdaptadorMidia`) que implementa a
   interface Target e guarda uma referência ao Adaptee.
4. Cada método do Adapter **traduz a chamada** recebida na interface Target
   para a chamada equivalente na interface do Adaptee.
5. O cliente passa a interagir **só com a interface Target**, sem nunca
   conhecer a classe Adaptee diretamente.

> Dica de fala: "O Adapter não faz o trabalho pesado — ele só traduz a
> chamada e delega para o Adaptee. Toda a lógica de tocar vlc ou mp4
> continua em `ReprodutorVlc`/`ReprodutorMp4`; o Adapter só sabe 'quem
> chamar' para cada formato."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min30s

Aponte para o diagrama enquanto explica:

- **`ReprodutorMp3`** é o cliente: implementa `ReprodutorMidia` e sabe tocar
  mp3 nativamente.
- **`ReprodutorMidia`** é a interface Target esperada pelo cliente: declara
  `reproduzir(tipo, nome)`.
- **`AdaptadorMidia`** implementa `ReprodutorMidia` e guarda uma referência
  a um `ReprodutorAvancado`.
- **`ReprodutorAvancado`** é a interface Adaptee, implementada por
  `ReprodutorVlc` e `ReprodutorMp4`, com métodos incompatíveis com o
  cliente.

> Dica de fala: "Reparem que tanto o cliente (`ReprodutorMp3`) quanto o
> Adapter (`AdaptadorMidia`) implementam a mesma interface Target. É isso
> que permite ao `Main` tratar os dois de forma idêntica, sem saber qual
> deles está tocando o arquivo por trás dos panos."

---

## 4. Exemplo de Código (slide 5) — ~2min30s

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`ReprodutorMidia.java`** — a interface Target, com um único método
   `reproduzir(tipoArquivo, nomeArquivo)`.
2. **`ReprodutorAvancado.java` / `ReprodutorVlc.java` / `ReprodutorMp4.java`**
   — a interface Adaptee e suas implementações concretas, cada uma só
   sabendo tocar o próprio formato.
3. **`AdaptadorMidia.java`** — destaque o construtor, que decide qual
   `ReprodutorAvancado` instanciar de acordo com o `tipoArquivo`, e o método
   `reproduzir()`, que traduz a chamada para `reproduzirVlc()` ou
   `reproduzirMp4()`.
4. **`ReprodutorMp3.java`** — mostre que ele toca "mp3" direto, mas cria um
   `AdaptadorMidia` para delegar "vlc"/"mp4".
5. **`Main.java`** — chama `reproduzir()` sempre pela mesma interface
   (`ReprodutorMidia`), independente do formato real do arquivo.

**Demonstração ao vivo (recomendado):**

```bash
cd adapter/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.adapter.Main
```

Comente a saída: os quatro formatos (mp3, mp4, vlc, e um formato inválido
como "avi") são tocados através da mesma chamada `reproduzir()` no cliente;
o `AdaptadorMidia` é criado e usado de forma transparente para mp4/vlc, sem
que o `Main` precise saber disso; e o formato não suportado ("avi") é
tratado sem quebrar a aplicação.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- Usar uma biblioteca de terceiros ou código legado com interface
  incompatível → conversores de formato, integrações com APIs antigas.
- Impossibilidade de alterar o código-fonte da classe existente → classe
  fechada, de terceiros, ou compartilhada por muitas outras partes do
  sistema.
- Reaproveitar subclasses existentes que não têm alguma funcionalidade em
  comum → sem precisar herdar de cada uma delas.
- Integrar sistemas diferentes → converter dados de um formato para outro,
  ou uma API antiga para a interface esperada pela aplicação atual.

---

## 6. Prós e Contras (slide 7) — ~1min

Leia a tabela destacando o trade-off central:

- **Prós**: reaproveita código existente e testado, mesmo quando sua
  interface não é compatível com o que o cliente espera; concentra a lógica
  de conversão em uma única classe, em vez de espalhar adaptações pelo
  código cliente; desacopla cliente e classe adaptada, já que nenhum dos
  dois precisa conhecer o outro diretamente.
- **Contras**: aumenta a complexidade geral do código, já que é preciso
  introduzir um conjunto de novas interfaces e classes só para
  compatibilizar interfaces; às vezes é mais simples mudar a classe de
  serviço para que ela já fique de acordo com o resto do código, quando
  isso é possível; usado em excesso, pode encobrir problemas de design que
  deveriam ser resolvidos de outra forma.

> Dica de fala: "O Adapter é uma solução de compatibilidade, não uma
> solução de design ideal. Se você está no controle de todas as classes
> envolvidas, geralmente é melhor ajustar as interfaces desde o início do
> que empilhar adaptadores."

---

## 7. Encerramento — ~30s

> "Resumindo: o Adapter permite reaproveitar uma classe já existente, mesmo
> quando sua interface não é compatível com o que o cliente espera,
> traduzindo as chamadas através de uma classe intermediária. Diferente dos
> cinco padrões criacionais que vimos antes — que tratam de como criar
> objetos —, o Adapter abre a categoria dos padrões estruturais, que tratam
> de como compor classes e objetos em estruturas maiores. O código completo
> está disponível no repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Se sua turma já apresentou os padrões criacionais antes, aproveite para marcar a virada de categoria: criação de objetos (criacionais) vs. composição de classes e objetos (estruturais) — ajuda a situar o Adapter no catálogo geral.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~8min, ajuste o ritmo conforme o tempo definido pelo professor.
