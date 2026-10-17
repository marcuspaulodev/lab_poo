# Roteiro de Vídeo — Padrão Observer

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Observer_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **6 a 8 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Observer**, ou Observador, um padrão
> **comportamental** do catálogo GoF."

Frase-resumo (está no slide): *"Observer define uma dependência
um-para-muitos entre objetos, de modo que, quando um objeto muda de
estado, todos os seus dependentes são notificados e atualizados
automaticamente."*

> Dica de fala: "Pensem em se inscrever num canal do YouTube: vocês não
> ficam entrando no canal toda hora para ver se saiu vídeo novo. O canal
> avisa todos os inscritos quando publica. O Observer é exatamente isso."

---

## 1. Problema (slide 2) — ~1min15s

Use o exemplo do slide e do código: uma estação meteorológica.

Pontos a cobrir, na ordem:

1. **Vários objetos precisam reagir** quando o estado de outro objeto
   muda.
2. **Exemplo**: a estação mede uma nova temperatura e o painel, o alerta
   de calor e o histórico precisam ser atualizados.
3. **Chamar cada um diretamente** acopla a estação a todas as classes
   interessadas na medição.
4. **Adicionar ou remover um interessado** obriga a alterar a estação; a
   alternativa de cada um ficar consultando o estado o tempo todo
   (*polling*) desperdiça processamento.

> Dica de fala: "Se amanhã alguém quiser mandar a temperatura por e-mail,
> eu teria que abrir a classe da estação e acrescentar mais uma chamada.
> A estação não deveria precisar saber disso."

---

## 2. Solução (slide 3) — ~1min15s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. O objeto observado (**Sujeito**) mantém uma **lista de Observadores**.
2. Observadores se **inscrevem e cancelam a inscrição** em tempo de
   execução: `adicionar()` e `remover()`.
3. Quando o estado muda, o Sujeito chama **`notificar()`**, que invoca
   **`atualizar()`** em cada Observador da lista.
4. O Sujeito conhece **apenas a interface `Observador`**, nunca as classes
   concretas.
5. **Cada Observador decide** o que fazer com a notificação: exibir,
   alertar, registrar...

> Dica de fala: "Também é chamado de *publish/subscribe*: o Sujeito
> publica, os Observadores assinam."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min

Aponte para o diagrama enquanto explica:

- **`Sujeito`** e **`Observador`** são as duas interfaces do padrão, no
  topo.
- **`EstacaoMeteorologica`** é o Sujeito concreto (seta tracejada até
  `Sujeito`) e guarda uma **lista de `Observador`**: é o losango ligando a
  estação à interface `Observador`.
- **`PainelTemperatura`, `AlertaCalor` e `HistoricoTemperaturas`** são os
  observadores concretos e implementam `Observador` (seta tracejada).
- **`Main`** (Cliente) cria a estação e os observadores e faz as
  inscrições.

> Dica de fala: "Reparem que não existe nenhuma seta da estação para o
> painel ou para o alerta. A estação só aponta para a interface. É isso
> que deixa ela livre para ganhar novos observadores sem mudar."

---

## 4. Exemplo de Código (slide 5) — ~2min

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`Sujeito.java`** e **`Observador.java`** — as duas interfaces.
2. **`EstacaoMeteorologica.java`** — a lista de observadores,
   `adicionar()`/`remover()`, e `medir()`, que guarda a temperatura e
   chama `notificar()`.
3. **`PainelTemperatura.java`**, **`AlertaCalor.java`** e
   **`HistoricoTemperaturas.java`** — três reações diferentes à mesma
   notificação: exibir, alertar só acima de um limite, e guardar.
4. **`Main.java`** — inscreve os três, faz duas medições, remove o painel
   e faz mais uma.

**Demonstração ao vivo (recomendado):**

```bash
cd observer/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.observer.Main
```

Saída esperada:

```
Estação mediu 28 °C
  [Painel]    Temperatura atual: 28 °C
  [Histórico] 1 medição(ões) registrada(s)
Estação mediu 36 °C
  [Painel]    Temperatura atual: 36 °C
  [Alerta]    Calor extremo! 36 °C (limite 35 °C)
  [Histórico] 2 medição(ões) registrada(s)
Painel removido da lista de observadores
Estação mediu 31 °C
  [Histórico] 3 medição(ões) registrada(s)
Histórico completo: [28, 36, 31]
```

Comente a saída: em 28 °C o alerta foi notificado, mas decidiu não fazer
nada (abaixo do limite); em 36 °C ele dispara. Depois de
`remover(painel)`, a medição de 31 °C não chega mais ao painel — a
inscrição mudou **em tempo de execução**, sem tocar em nenhuma classe.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- A mudança de estado de um objeto exige atualizar outros, e não se sabe
  de antemão quantos ou quais são.
- Um objeto deve avisar outros sem depender de suas classes concretas.
- Os interessados precisam entrar e sair em tempo de execução.
- Exemplos: listeners de eventos em Swing/JavaFX,
  `PropertyChangeListener`, sistemas publish/subscribe, MVC.

> Dica de fala: "Todo `addActionListener` que vocês já escreveram num botão
> é um Observer: o botão é o Sujeito e o listener é o Observador."

---

## 6. Prós e Contras (slide 7) — ~1min

- **Prós**: baixo acoplamento (o Sujeito só conhece a interface); novos
  observadores sem alterar o Sujeito (princípio Aberto/Fechado);
  inscrições feitas e desfeitas em tempo de execução.
- **Contras**: a ordem de notificação não é garantida pelo padrão;
  atualizações em cascata são difíceis de rastrear; observadores
  esquecidos na lista continuam sendo avisados e não são coletados pelo
  *garbage collector* (vazamento de memória).

> Dica de fala: "Querem mandar a temperatura por e-mail? É criar uma classe
> `NotificadorEmail implements Observador` e uma linha
> `estacao.adicionar(...)` no `Main`. A estação não muda."

---

## 7. Encerramento — ~30s

> "Resumindo: no Observer, um Sujeito mantém uma lista de Observadores e
> avisa todos eles quando seu estado muda, conhecendo apenas uma
> interface. É um padrão **comportamental** que troca chamadas diretas por
> inscrições, e está por trás de praticamente todo sistema de eventos que
> a gente usa. O código completo está disponível no repositório do
> projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Ter pronta a diferença entre modelo *push* (a temperatura vai no `atualizar()`, como aqui) e *pull* (o observador consulta o Sujeito), caso alguém pergunte.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~7min, ajuste o ritmo conforme o tempo definido pelo professor.
