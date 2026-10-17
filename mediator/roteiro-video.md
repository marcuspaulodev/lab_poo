# Roteiro de Vídeo — Padrão Mediator

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Mediator_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **6 a 8 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Mediator**, ou Mediador, um padrão
> **comportamental** do catálogo GoF."

Frase-resumo (está no slide): *"Mediator define um objeto que encapsula a
forma como um conjunto de objetos interage, promovendo o acoplamento fraco
ao evitar que os objetos se refiram uns aos outros explicitamente."*

> Dica de fala: "Em vez de todo mundo conversar com todo mundo, todos
> conversam com um intermediário — e é ele quem coordena."

---

## 1. Problema (slide 2) — ~1min15s

Use o exemplo do slide e do código: aeronaves disputando uma única pista.

Pontos a cobrir, na ordem:

1. **Objetos que colaboram** costumam conversar diretamente, cada um
   guardando referências para os outros.
2. **Exemplo**: três aviões querendo pousar na mesma pista. Sem
   coordenação central, cada avião teria que avisar e consultar todos os
   outros antes de pousar.
3. **Com N objetos** surgem até N×(N−1) ligações: uma teia de
   dependências difícil de entender e de testar.
4. **Mudar a regra** (por exemplo, dar prioridade a um pouso de
   emergência) obriga a mexer em todas as classes envolvidas.

> Dica de fala: "Imaginem se os pilotos tivessem que combinar entre si,
> pelo rádio, quem pousa primeiro. Com três aviões já é confuso; com
> trinta, é um desastre. Por isso existe a torre de controle."

---

## 2. Solução (slide 3) — ~1min15s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Criar um objeto **Mediador** que centraliza a comunicação: os objetos
   deixam de conversar diretamente.
2. O **Mediador** (`TorreControle`) define a interface pela qual os
   colegas avisam o que aconteceu.
3. O **Mediador Concreto** (`TorreAeroporto`) conhece os colegas e
   concentra as regras de coordenação: quem está na pista, a fila, a
   ordem de pouso.
4. Cada **Colega** (`Aeronave`) conhece **apenas o mediador** e reage às
   mensagens que recebe dele.
5. A teia N×N de ligações vira uma **estrela**: cada colega fala só com o
   mediador.

> Dica de fala: "O avião não sabe quantos outros aviões existem nem quem
> está na pista. Ele só fala 'quero pousar' para a torre e espera a
> resposta."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min

Aponte para o diagrama enquanto explica:

- **`Main`** (Cliente) cria a torre e as aeronaves e dispara as ações nas
  aeronaves.
- **`TorreControle`** é a interface do mediador, com
  `solicitarPouso(a)` e `liberarPista(a)`.
- **`TorreAeroporto`** implementa a interface (seta tracejada) e guarda o
  estado da coordenação: a `fila` de espera e quem está `naPista`.
- **`Aeronave`** (Colega) guarda uma referência para a **interface**
  `TorreControle` (seta cheia), não para a implementação concreta.
- A torre conhece as aeronaves (losango): é ela quem manda mensagens de
  volta pelo método `receber(msg)`.

> Dica de fala: "Reparem que não existe nenhuma seta de `Aeronave` para
> `Aeronave`. Toda a comunicação passa pela torre."

---

## 4. Exemplo de Código (slide 5) — ~2min

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`TorreControle.java`** — a interface do mediador, com duas
   operações.
2. **`Aeronave.java`** — o colega: recebe a torre no construtor,
   `solicitarPouso()` e `liberarPista()` apenas repassam o pedido para a
   torre, e `receber()` imprime a mensagem recebida.
3. **`TorreAeroporto.java`** — o mediador concreto: autoriza se a pista
   está livre, senão coloca a aeronave na fila; ao liberar a pista,
   autoriza o próximo da fila e avisa quem continua esperando.
4. **`Main.java`** — cria uma torre e três aeronaves, pede três pousos e
   libera a pista três vezes.

**Demonstração ao vivo (recomendado):**

```bash
cd mediator/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.mediator.Main
```

Saída esperada:

```
GOL1234: solicita pouso.
  -> GOL1234 recebe: pouso autorizado
AZU4321: solicita pouso.
  -> AZU4321 recebe: pista ocupada por GOL1234, aguarde (posição 1 na fila)
TAM3030: solicita pouso.
  -> TAM3030 recebe: pista ocupada por GOL1234, aguarde (posição 2 na fila)
GOL1234: pousou e liberou a pista.
  -> AZU4321 recebe: pouso autorizado
  -> TAM3030 recebe: AZU4321 está na pista, mantenha a espera
AZU4321: pousou e liberou a pista.
  -> TAM3030 recebe: pouso autorizado
TAM3030: pousou e liberou a pista.
```

Comente a saída: quando `GOL1234` libera a pista, quem é avisado são
`AZU4321` e `TAM3030` — mas `GOL1234` **não conhece** essas aeronaves. Foi
a torre que decidiu quem pousa a seguir e quem deve continuar esperando.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- Um conjunto de objetos se comunica de forma bem definida, porém
  complexa, gerando dependências confusas.
- Reutilizar um objeto é difícil porque ele referencia e conversa com
  muitos outros.
- Um comportamento distribuído entre várias classes deve ser
  customizável sem criar muitas subclasses: basta trocar o mediador.
- Exemplos: torre de controle, salas de chat, diálogos de formulário
  (um checkbox que habilita um campo, que habilita um botão), o
  Controller no MVC, `java.util.Timer` (coordena as `TimerTask`s).

> Dica de fala: "Num formulário de cadastro, o campo 'CPF' só aparece se o
> tipo for 'Pessoa Física', e o botão 'Salvar' só habilita quando tudo
> está válido. Se cada componente conhecesse os outros, seria um nó. Com
> um mediador, o formulário coordena tudo."

---

## 6. Prós e Contras (slide 7) — ~1min

- **Prós**: reduz o acoplamento (colegas reutilizáveis e testáveis
  isoladamente); centraliza as regras de interação em um só lugar; troca
  ligações muitos-para-muitos por um-para-muitos.
- **Contras**: o mediador tende a concentrar lógica demais e virar um
  "objeto Deus"; um mediador grande fica difícil de manter; toda a
  comunicação passa por um único ponto.

> Dica de fala: "Querem dar prioridade a pousos de emergência? Muda só a
> `TorreAeroporto`. Nenhuma `Aeronave` precisa ser alterada. Mas cuidado:
> se toda regra nova cair na torre, ela vira a classe mais complicada do
> sistema."

---

## 7. Encerramento — ~30s

> "Resumindo: o Mediator tira a comunicação de dentro dos objetos e a
> coloca em um intermediário. Os colegas só conhecem o mediador, e o
> mediador coordena todo mundo. É um padrão **comportamental** parecido
> com o Facade — ambos ficam no meio do caminho —, mas o Facade simplifica
> o acesso a um subsistema em uma única direção, enquanto o Mediator
> coordena a conversa entre objetos que se comunicam nos dois sentidos. O
> código completo está disponível no repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Se a turma já viu o Facade e o Observer, tenha pronta a diferença: Facade é unidirecional; Observer distribui notificações, Mediator coordena.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~7min, ajuste o ritmo conforme o tempo definido pelo professor.
