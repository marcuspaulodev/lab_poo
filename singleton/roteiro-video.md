# Roteiro de Vídeo — Padrão Singleton

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Singleton_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **7 a 9 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Singleton**, também um padrão **criacional** do
> catálogo GoF — e provavelmente o mais conhecido de todos."

Frase-resumo (está no slide): *"Singleton garante que uma classe tenha
apenas uma única instância e fornece um ponto de acesso global a ela."*

> Dica de fala: "Diferente dos padrões anteriores, que tratam de **como**
> criar objetos (por fábrica, por cópia, por montagem), o Singleton trata
> de **quantas** instâncias uma classe pode ter — e a resposta é: no
> máximo uma."

---

## 1. Problema (slide 2) — ~1min30s

Use o exemplo do slide e do código: um `Logger` compartilhado, que deve
escrever sempre no mesmo lugar, usado por diferentes serviços da
aplicação (`ServicoPedidos`, `ServicoPagamentos`).

Pontos a cobrir, na ordem:

1. **Algumas classes devem ter exatamente uma instância** — se cada
   serviço criasse seu próprio `Logger`, cada um teria seu próprio
   histórico isolado, quebrando a ideia de um log centralizado.
2. **`new Logger()` livre não impede múltiplas instâncias** — se o
   construtor é público, nada garante que só existirá uma.
3. **Uma variável global exposta também não é uma boa solução** — não há
   controle sobre quando o objeto é criado (inicialização preguiçosa) nem
   encapsulamento da lógica de acesso.
4. **É preciso conciliar duas coisas**: garantir a instância única E
   oferecer um ponto de acesso conhecido para qualquer parte do código.

> Dica de fala: "Pensem em outros exemplos do dia a dia: um gerenciador de
> configuração da aplicação, um pool de conexões com banco de dados — em
> todos esses casos, ter duas instâncias 'brigando' pelo mesmo recurso
> seria um bug."

---

## 2. Solução (slide 3) — ~1min30s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Tornar o **construtor privado** — nenhuma outra classe consegue criar
   uma instância diretamente com `new`.
2. Guardar a única instância em um **campo estático privado**, pertencente
   à própria classe.
3. Expor um **método estático público** (`getInstance()`) que cria a
   instância na primeira chamada (inicialização preguiçosa) e devolve
   sempre a mesma instância depois.
4. Em ambientes com múltiplas threads, **proteger a criação da instância**
   (ex.: double-checked locking com uma variável `volatile`) para evitar
   que duas threads criem instâncias diferentes ao mesmo tempo.
5. Qualquer parte do código acessa a mesma instância chamando
   `Logger.getInstance()` — sem precisar de uma variável global exposta.

> Dica de fala: "O double-checked locking parece complicado à primeira
> vista, mas a ideia é simples: só entra no bloco `synchronized` (mais
> lento) na primeira vez; depois disso, a checagem rápida de fora já
> resolve. Vale mencionar que existem alternativas mais simples em Java,
> como inicialização estática eager ou um enum — mas o double-checked
> locking é o exemplo clássico do GoF adaptado para múltiplas threads."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min30s

Aponte para o diagrama enquanto explica:

- **ServicoPedidos** e **ServicoPagamentos** são clientes independentes
  que usam o mesmo `Logger`.
- **Logger** guarda sua própria instância em um campo estático privado
  (`- instancia: Logger {static}`).
- O **construtor de `Logger` é privado** (`- Logger() {private}`) — só a
  própria classe pode criar uma instância.
- **`getInstance()`** é o único ponto de acesso: cria a instância na
  primeira chamada e devolve sempre a mesma depois.

> Dica de fala: "Reparem que não existe uma interface nem uma hierarquia
> de classes aqui, ao contrário dos outros padrões criacionais que vimos.
> O Singleton é sobre controlar o **ciclo de vida de uma única classe**,
> não sobre variar entre implementações diferentes."

---

## 4. Exemplo de Código (slide 5) — ~2min30s

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`Logger.java`** — mostre o campo `private static volatile Logger
   instancia`, o construtor privado, e o método `getInstance()` com o
   double-checked locking.
2. **`ServicoPedidos.java` / `ServicoPagamentos.java`** — mostre que ambos
   só chamam `Logger.getInstance().log(...)`, sem conhecer nenhum detalhe
   de como a instância é criada ou protegida.
3. **`Main.java`** — destaque o teste `logger1 == logger2`, que prova que
   as duas variáveis apontam para o mesmo objeto na memória.

**Demonstração ao vivo (recomendado):**

```bash
cd singleton/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.singleton.Main
```

Comente a saída: a mensagem "Inicializando instância única" aparece **só
uma vez**, mesmo com duas chamadas a `getInstance()`;
`logger1 == logger2` é `true`; e o histórico visto a partir de `logger1`
contém os logs escritos por `ServicoPedidos` e `ServicoPagamentos` —
provando que os três (`logger1`, `logger2`, e o logger usado internamente
pelos serviços) são, na verdade, o mesmo objeto.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- Exatamente uma instância acessível globalmente → registro de log,
  gerenciador de configuração.
- Logging centralizado → todas as partes do sistema escrevendo no mesmo
  destino.
- Cache compartilhado ou pool de conexões → recursos caros que devem ser
  reaproveitados, não recriados.
- Inicialização preguiçosa → a instância só é criada quando realmente
  necessária, economizando recursos até lá.

---

## 6. Prós e Contras (slide 7) — ~1min

Leia a tabela destacando o trade-off central:

- **Prós**: garante instância única controlada pela própria classe;
  permite inicialização preguiçosa; fornece ponto de acesso global sem
  variáveis globais espalhadas pelo código.
- **Contras**: introduz estado global, que pode esconder dependências
  entre classes; dificulta testes automatizados (difícil "mockar" um
  Singleton isoladamente); exige cuidado extra em ambientes com múltiplas
  threads.

> Dica de fala: "O Singleton é, ao mesmo tempo, um dos padrões mais usados
> e um dos mais criticados na indústria — justamente por causa desses
> contras. Muita gente considera que ele introduz uma forma de estado
> global 'disfarçada de boa prática'. Vale a reflexão: será que aquele
> objeto realmente precisa ser único, ou daria para passar ele como
> dependência explícita (injeção de dependência) em vez de um acesso
> global?"

---

## 7. Encerramento — ~30s

> "Resumindo: o Singleton garante uma única instância de uma classe com
> um ponto de acesso global, e é útil quando existe um recurso
> genuinamente compartilhado no sistema, como um log ou uma configuração.
> Mas é importante usá-lo com moderação, por causa do estado global e da
> dificuldade em testar. Junto com Abstract Factory, Factory Method,
> Prototype e Builder, ele completa os cinco padrões criacionais mais
> conhecidos do catálogo GoF. O código completo está disponível no
> repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Se sua turma já apresentou os outros padrões criacionais, aproveite para fechar o grupo: fábrica, cópia, montagem passo a passo, e instância única — ajuda a fixar todos.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~8min, ajuste o ritmo conforme o tempo definido pelo professor.
