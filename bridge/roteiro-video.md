# Roteiro de Vídeo — Padrão Bridge

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Bridge_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **7 a 9 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Bridge**, mais um padrão **estrutural** do catálogo
> GoF — depois do Adapter, que já vimos antes."

Frase-resumo (está no slide): *"Bridge desacopla uma abstração de sua
implementação, permitindo que as duas variem de forma independente."*

> Dica de fala: "O Adapter conecta duas interfaces que já existem e são
> incompatíveis. O Bridge é diferente: ele é pensado desde o início do
> projeto, para separar de propósito uma hierarquia de 'o que fazer'
> (abstração) de uma hierarquia de 'como fazer' (implementação)."

---

## 1. Problema (slide 2) — ~1min30s

Use o exemplo do slide e do código: um controle remoto que precisa
funcionar com vários dispositivos diferentes (TV, Rádio), e que também tem
variações de controle (básico, avançado).

Pontos a cobrir, na ordem:

1. **Uma classe pode ter várias variações de abstração** (controle básico,
   controle avançado) e, ao mesmo tempo, **precisa funcionar com vários
   dispositivos diferentes** (TV, Rádio).
2. **Se a abstração herdar diretamente de cada dispositivo concreto**, o
   número de subclasses explode: uma classe para cada combinação de tipo
   de controle × tipo de dispositivo.
3. **Herança fixa essas duas dimensões de variação** juntas em tempo de
   compilação, dificultando adicionar um novo dispositivo ou um novo tipo
   de controle sem tocar em várias classes.
4. **É preciso separar essas duas dimensões** — a abstração de alto nível
   (o controle) e a implementação de baixo nível (o dispositivo) — para que
   cada uma possa evoluir de forma independente.

> Dica de fala: "Imaginem ter que criar `ControleBasicoTv`,
> `ControleAvancadoTv`, `ControleBasicoRadio`, `ControleAvancadoRadio`... A
> cada novo dispositivo ou tipo de controle, o número de classes cresce
> multiplicando, não somando."

---

## 2. Solução (slide 3) — ~1min30s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Separar a hierarquia em duas: a **Abstração** (`ControleRemoto`) e o
   **Implementor** (`Dispositivo`).
2. A Abstração guarda uma **referência ao Implementor** (composição, não
   herança) — essa referência é a "ponte" entre as duas hierarquias.
3. A Abstração **delega** as operações de baixo nível para o Implementor,
   em vez de implementá-las ela mesma.
4. Cada hierarquia pode **crescer separadamente**: novas Abstrações
   Refinadas (`ControleRemotoAvancado`) ou novos Implementadores Concretos
   (`TV`, `Radio`) sem afetar a outra hierarquia.
5. O cliente trabalha com a Abstração, que por sua vez delega para
   qualquer Implementor concreto configurado.

> Dica de fala: "O nome 'Bridge' vem exatamente dessa referência: ela é a
> ponte que liga a hierarquia de abstração à hierarquia de implementação,
> sem que uma dependa diretamente da outra."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min30s

Aponte para o diagrama enquanto explica de cima para baixo:

- **`ControleRemotoAvancado`** (Abstração Refinada) estende `ControleRemoto`
  e adiciona comportamento extra (`mudo()`).
- **`ControleRemoto`** (Abstração) guarda uma referência a um `Dispositivo`
  e delega as operações básicas (`ligar()`, `desligar()`, volume).
- **`Dispositivo`** é o Implementor: a interface com as operações de baixo
  nível (`ligar`, `desligar`, `ajustarVolume`).
- **`TV`** e **`Radio`** são Implementadores Concretos, cada um controlando
  um tipo de hardware diferente.

> Dica de fala: "Reparem que a seta entre `ControleRemoto` e `Dispositivo`
> é uma composição, não uma herança — é isso que permite trocar o
> `Dispositivo` por trás de um `ControleRemoto` sem precisar criar uma
> nova subclasse de controle para cada dispositivo."

---

## 4. Exemplo de Código (slide 5) — ~2min30s

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`Dispositivo.java`** — a interface Implementor, com `ligar()`,
   `desligar()`, `ajustarVolume(variacao)` e `status()`.
2. **`TV.java` / `Radio.java`** — implementações concretas, cada uma com
   seu próprio estado interno (ligada/desligada, volume).
3. **`ControleRemoto.java`** — destaque o campo `protected final
   Dispositivo dispositivo` (a ponte) e como `aumentarVolume()` /
   `diminuirVolume()` só delegam para `dispositivo.ajustarVolume(...)`.
4. **`ControleRemotoAvancado.java`** — estende `ControleRemoto` e adiciona
   `mudo()`, sem precisar saber qual `Dispositivo` está por trás.
5. **`Main.java`** — monta um `ControleRemoto` com uma `TV` e um
   `ControleRemotoAvancado` com um `Radio`, mostrando as duas hierarquias
   combinadas livremente.

**Demonstração ao vivo (recomendado):**

```bash
cd bridge/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.bridge.Main
```

Comente a saída: a `TV` liga e tem o volume aumentado duas vezes (20 → 40);
o `Radio`, controlado por um `ControleRemotoAvancado`, liga, aumenta o
volume e depois é mudado — mostrando que a mesma Abstração (`ControleRemoto`
ou sua versão avançada) funciona com qualquer `Dispositivo`, sem precisar de
uma classe específica para cada combinação.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- Evitar uma ligação permanente entre abstração e implementação → trocar a
  implementação em tempo de execução.
- Abstração e implementação devem poder ser estendidas por herança de
  forma independente → drivers de dispositivos, GUIs multiplataforma.
- Mudanças na implementação não devem exigir recompilar o código cliente →
  bibliotecas com implementações plugáveis.
- Evitar a explosão de subclasses ao combinar variações de abstração e de
  implementação por herança direta.

---

## 6. Prós e Contras (slide 7) — ~1min

Leia a tabela destacando o trade-off central:

- **Prós**: desacopla abstração e implementação, permitindo que cada uma
  evolua de forma independente; evita a explosão de subclasses que
  ocorreria combinando variações por herança direta; permite trocar a
  implementação em tempo de execução, já que a ligação é por composição.
- **Contras**: aumenta a complexidade geral do código, introduzindo mais
  uma camada de indireção; fica mais difícil acompanhar o fluxo do
  programa com abstração e implementação em classes separadas; só compensa
  quando realmente existem (ou vão existir) múltiplas variações
  independentes dos dois lados.

> Dica de fala: "Se você só tem um tipo de controle e um tipo de
> dispositivo, e nenhuma perspectiva de ter mais, o Bridge é complexidade
> desnecessária. Ele compensa quando as duas dimensões de variação são
> reais, não hipotéticas."

---

## 7. Encerramento — ~30s

> "Resumindo: o Bridge separa uma abstração da sua implementação através de
> composição, permitindo que as duas hierarquias evoluam de forma
> independente. Junto com o Adapter, que já vimos, ele mostra duas formas
> diferentes de lidar com interfaces: o Adapter conecta interfaces que já
> existem e são incompatíveis; o Bridge é planejado desde o início para
> manter duas hierarquias desacopladas. O código completo está disponível
> no repositório do projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Se sua turma já apresentou o Adapter antes, aproveite para contrastar: conectar interfaces incompatíveis existentes (Adapter) vs. separar abstração de implementação por design (Bridge) — ajuda a diferenciar os dois padrões estruturais.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~8min, ajuste o ritmo conforme o tempo definido pelo professor.
