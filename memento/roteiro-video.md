# Roteiro de Vídeo — Padrão Memento

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Memento_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **6 a 8 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Memento**, ou Lembrança, um padrão
> **comportamental** do catálogo GoF."

Frase-resumo (está no slide): *"Memento, sem violar o encapsulamento,
captura e externaliza o estado interno de um objeto, de modo que ele possa
ser restaurado para esse estado mais tarde."*

> Dica de fala: "Pensem no 'salvar jogo' de um videogame. Antes da fase
> difícil vocês salvam, e se tudo der errado voltam exatamente para aquele
> ponto. O Memento é esse arquivo de save, só que em forma de objeto."

---

## 1. Problema (slide 2) — ~1min15s

Use o exemplo do slide e do código: um personagem de jogo com checkpoints.

Pontos a cobrir, na ordem:

1. **Muitas aplicações precisam voltar a um estado anterior**: desfazer
   uma edição, recarregar um checkpoint, cancelar uma alteração.
2. **Exemplo**: o personagem salva o progresso antes de uma fase difícil e
   volta se for derrotado.
3. **Para copiar o estado, alguém de fora teria que ler todos os
   atributos** do objeto, obrigando a classe a expor seus campos privados
   com getters e setters.
4. **Expor o estado quebra o encapsulamento**: qualquer mudança nos
   atributos passa a afetar todas as classes que fazem as cópias.

> Dica de fala: "Se o sistema de save precisa de um `getVida()`, um
> `setVida()`, um `getFase()`, um `setFase()`... na prática o personagem
> deixou de ter atributos privados. Qualquer um pode mexer neles."

---

## 2. Solução (slide 3) — ~1min15s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. O **próprio objeto** (o **Originator**, aqui o `Personagem`) cria uma
   "foto" do seu estado: o **Memento**.
2. O Memento é **imutável** e guarda uma cópia dos campos (`fase`,
   `nivel`, `vida`) sem expô-los a outras classes.
3. **Só o Originator lê o conteúdo do Memento**; para os demais ele é uma
   caixa fechada.
4. O **Caretaker** (`Checkpoints`) guarda os Mementos em uma pilha e
   decide **quando** salvar e **quando** voltar.
5. Para voltar, o Caretaker devolve um Memento ao Originator, que copia o
   estado salvo de volta para si.

> Dica de fala: "Quem sabe **o que** salvar é o personagem. Quem sabe
> **quando** salvar e quantos saves guardar é o Caretaker. Cada um cuida
> de uma coisa, e ninguém precisa abrir a caixa do outro."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min

Aponte para o diagrama enquanto explica:

- **`Main`** (Cliente) joga com o **`Personagem`** e pede ao
  **`Checkpoints`** para salvar ou voltar.
- **`Personagem`** (Originator) tem o estado (`fase`, `nivel`, `vida`) e os
  métodos `salvar()`, que devolve um `Memento`, e `restaurar(Memento)`. A
  seta tracejada mostra que ele **cria e lê** o Memento.
- **`Checkpoints`** (Caretaker) conversa com o `Personagem` e **guarda os
  Mementos** (o losango): é dono do histórico, mas só enxerga
  `rotulo()`.
- **`Memento`** tem os campos privados e imutáveis.

> Dica de fala: "Reparem que a única seta que entra no conteúdo do Memento
> vem do Personagem. O Checkpoints só segura a caixa."

---

## 4. Exemplo de Código (slide 5) — ~2min

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`Personagem.java`** — o estado (`fase`, `nivel`, `vida`) e os métodos
   do jogo (`avancarFase`, `subirNivel`, `sofrerDano`).
2. **`Personagem.Memento`** — classe aninhada, `final`, com campos
   `private final` e **construtor privado**. Destaque que em Java a classe
   externa enxerga os membros privados da aninhada: por isso só o
   `Personagem` consegue criar e ler um Memento. Para os outros, o único
   método público é `rotulo()`.
3. **`salvar()` e `restaurar()`** — copiam o estado para o Memento e de
   volta.
4. **`Checkpoints.java`** — o Caretaker, com a pilha (`Deque`) de
   Mementos. `salvar()` empilha, `voltar()` desempilha e restaura.
5. **`Main.java`** — salva na Vila e na Floresta, é derrotado no Castelo e
   volta duas vezes.

**Demonstração ao vivo (recomendado):**

```bash
cd memento/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.memento.Main
```

Saída esperada:

```
  Aria | Fase: Vila | Nível 1 | Vida 100
Checkpoint salvo: Vila
  Aria | Fase: Floresta | Nível 2 | Vida 70
Checkpoint salvo: Floresta
  Aria | Fase: Castelo | Nível 2 | Vida 0
Voltou para:      Floresta
  Aria | Fase: Floresta | Nível 2 | Vida 70
Voltou para:      Vila
  Aria | Fase: Vila | Nível 1 | Vida 100
Nenhum checkpoint para voltar
```

Comente a saída: no Castelo a vida chega a 0, e ao voltar o personagem
recupera **fase, nível e vida juntos**, exatamente como estavam no
checkpoint. A segunda volta leva à Vila, porque o histórico é uma pilha. A
última chamada mostra o caso de pilha vazia.

> Dica de fala (opcional): "Tentem escrever `memento.vida` dentro do
> `Main` ou do `Checkpoints`: o compilador não deixa. O encapsulamento é
> garantido pela linguagem, não pela boa vontade de quem usa."

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- Quando é preciso guardar "fotos" do estado de um objeto para
  restaurá-lo depois: desfazer, checkpoints, rollback.
- Quando acessar o estado diretamente exporia detalhes de implementação e
  quebraria o encapsulamento.
- Quando o estado é pequeno o bastante para que copiá-lo a cada
  salvamento seja barato.
- Exemplos: Ctrl+Z em editores (junto com o Command), save games,
  rollback de transações e rascunhos de formulários.

> Dica de fala: "Lembram do Command? Lá o `ApagarTextoComando` guardava o
> trecho apagado para conseguir desfazer. Quando desfazer uma ação é
> complicado, o comando pode simplesmente guardar um Memento do editor
> antes de executar, e restaurá-lo no `desfazer()`."

---

## 6. Prós e Contras (slide 7) — ~1min

- **Prós**: salva e restaura o estado sem violar o encapsulamento;
  simplifica o Originator, já que o histórico fica com o Caretaker;
  facilita implementar desfazer, checkpoints e rollback.
- **Contras**: consome memória se os Mementos forem grandes ou frequentes;
  o Caretaker precisa controlar quantos Mementos guarda e descartar os
  antigos; em algumas linguagens é difícil garantir que só o Originator
  acesse o conteúdo do Memento.

> Dica de fala: "Um editor que salva o documento inteiro a cada tecla
> digitada vai estourar a memória rapidinho. Por isso, na prática, se
> limita o tamanho do histórico ou se guarda só o que mudou."

---

## 7. Encerramento — ~30s

> "Resumindo: o Memento permite tirar uma 'foto' do estado de um objeto e
> voltar para ela depois, sem que ninguém de fora precise conhecer os
> atributos internos. O Originator cria e lê a foto, o Caretaker só a
> guarda. É um padrão **comportamental** que costuma andar junto com o
> Command para implementar o desfazer. O código completo está disponível
> no repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Ter pronta uma explicação curta de por que a classe aninhada garante o encapsulamento (a externa acessa os privados da aninhada).
- [ ] Se a turma já viu o Command, reforce a ligação: Memento + Command é a combinação clássica para desfazer.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~7min, ajuste o ritmo conforme o tempo definido pelo professor.
