# Roteiro de Vídeo — Padrão Facade

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Facade_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **6 a 8 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Facade**, ou Fachada, mais um padrão **estrutural**
> do catálogo GoF."

Frase-resumo (está no slide): *"Facade fornece uma interface unificada e
simplificada para um conjunto de interfaces de um subsistema, tornando o
subsistema mais fácil de usar."*

> Dica de fala: "Pensem na fachada de um prédio: quem passa na rua vê uma
> frente organizada e uma porta de entrada, sem precisar conhecer a
> tubulação e a fiação lá dentro."

---

## 1. Problema (slide 2) — ~1min15s

Use o exemplo do slide e do código: um home theater com vários
equipamentos.

Pontos a cobrir, na ordem:

1. **Um subsistema pode ter muitas classes** com operações de baixo nível:
   `Luzes`, `Projetor`, `SistemaSom`, `PlayerStreaming`.
2. **Para uma tarefa simples** ("assistir a um filme"), o cliente precisa
   chamar vários métodos, **na ordem certa**, em vários objetos.
3. **O cliente fica fortemente acoplado** aos detalhes internos do
   subsistema.
4. **Qualquer mudança no subsistema** (trocar o projetor, adicionar uma
   pipoqueira) obriga a alterar todos os clientes.

> Dica de fala: "Para assistir a um filme, são 8 chamadas: diminuir as
> luzes, ligar o projetor, colocar em widescreen, ligar o som, ativar o
> surround, ajustar o volume, ligar o player e dar play. E, no fim, tudo de
> novo ao contrário."

---

## 2. Solução (slide 3) — ~1min15s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Criar uma classe **Fachada** (`HomeTheaterFacade`) que conhece as
   classes do subsistema e sabe como coordená-las.
2. A Fachada oferece **poucos métodos de alto nível**, voltados às tarefas
   do cliente: `assistirFilme()`, `encerrarFilme()`.
3. Cada método da Fachada **delega** o trabalho para os objetos do
   subsistema, na ordem correta.
4. O cliente passa a **depender apenas da Fachada**.
5. O subsistema **continua acessível** diretamente para quem precisar de
   controle fino: a Fachada simplifica, mas não esconde.

> Dica de fala: "Esse último ponto é importante: a Fachada não proíbe
> ninguém de usar o `SistemaSom` diretamente. Ela só oferece um caminho
> mais curto para os casos comuns."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min

Aponte para o diagrama enquanto explica de cima para baixo:

- **`Main`** (Cliente) conhece apenas a `HomeTheaterFacade`.
- **`HomeTheaterFacade`** (Fachada) guarda referências aos objetos do
  subsistema e expõe `assistirFilme(filme)` e `encerrarFilme()`.
- **`Luzes`, `Projetor`, `SistemaSom`, `PlayerStreaming`** (Subsistema)
  fazem o trabalho real e **não sabem** que a Fachada existe.

> Dica de fala: "As setas só vão em uma direção: a Fachada conhece o
> subsistema, mas o subsistema não conhece a Fachada. Isso mantém as
> classes do subsistema independentes e reutilizáveis."

---

## 4. Exemplo de Código (slide 5) — ~2min

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`Luzes.java`, `Projetor.java`, `SistemaSom.java`,
   `PlayerStreaming.java`** — classes independentes, cada uma com suas
   operações de baixo nível (mostre rapidamente uma ou duas).
2. **`HomeTheaterFacade.java`** — recebe os quatro objetos no construtor;
   destaque `assistirFilme()`, que faz toda a sequência de chamadas, e
   `encerrarFilme()`, que desliga tudo na ordem inversa.
3. **`Main.java`** — cria a Fachada e assiste a um filme com **uma única
   chamada**.

**Demonstração ao vivo (recomendado):**

```bash
cd facade/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.facade.Main
```

Saída esperada:

```
== Começando a sessão ==
Luzes: diminuindo para 10%
Projetor: ligado
Projetor: modo widescreen (16:9)
Som: ligado
Som: modo surround 5.1
Som: volume em 20
Player: ligado
Player: reproduzindo "O Auto da Compadecida"
== Fim da sessão ==
Player: parado
Player: desligado
Som: desligado
Projetor: desligado
Luzes: acesas
```

Comente a saída: o `Main` fez só duas chamadas (`assistirFilme` e
`encerrarFilme`), mas o subsistema executou 13 operações, na ordem certa.
Se amanhã entrar uma pipoqueira, só a `HomeTheaterFacade` muda — o `Main`
continua igual.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- Oferecer uma interface simples para um subsistema complexo, cobrindo os
  usos mais comuns.
- Muitas dependências entre clientes e classes de implementação → a
  Fachada desacopla os dois lados.
- Organizar o sistema em camadas → cada camada expõe uma Fachada como
  ponto de entrada.
- Exemplos: APIs de bibliotecas complexas (conversão de vídeo, envio de
  e-mails), classes de serviço em aplicações em camadas.

> Dica de fala: "No Spring, o `JdbcTemplate` é uma fachada sobre o JDBC:
> em vez de abrir conexão, criar statement, percorrer o `ResultSet` e
> fechar tudo manualmente, vocês chamam um único método."

---

## 6. Prós e Contras (slide 7) — ~1min

- **Prós**: isola o cliente da complexidade do subsistema; reduz o
  acoplamento, já que mudanças no subsistema afetam só a Fachada; ajuda a
  organizar o sistema em camadas.
- **Contras**: a Fachada pode virar um "objeto deus", acoplado a todo o
  sistema; cobre só os casos de uso mais comuns; é mais uma classe a
  manter, acompanhando as mudanças do subsistema.

> Dica de fala: "Se a Fachada começa a ter dezenas de métodos para
> tudo, é sinal de que ela deveria ser dividida em várias fachadas menores,
> cada uma para um grupo de tarefas."

---

## 7. Encerramento — ~30s

> "Resumindo: o Facade cria um ponto de entrada simples para um subsistema
> complexo, escondendo do cliente a sequência de chamadas de baixo nível.
> Diferente do Adapter, que converte uma interface para outra que o
> cliente espera, o Facade cria uma interface **nova e mais simples**. O
> código completo está disponível no repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Se a turma já viu o Adapter, reforce a diferença no encerramento: Adapter adapta uma interface existente; Facade simplifica um subsistema inteiro.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~7min, ajuste o ritmo conforme o tempo definido pelo professor.
