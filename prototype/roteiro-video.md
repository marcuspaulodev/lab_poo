# Roteiro de Vídeo — Padrão Prototype

Roteiro para gravação da apresentação em vídeo, acompanhando os slides de
`apresentacao/Prototype_-_Apresentacao.pptx`. Fale com suas próprias
palavras — isto é um guia de conteúdo e ordem, não um texto para decorar.
Tempo total estimado: **7 a 9 minutos**.

---

## 0. Abertura (slide 1 — Capa) — ~30s

> "Olá, meu nome é [seu nome] e nesta apresentação eu vou falar sobre o
> padrão de projeto **Prototype**, mais um padrão **criacional** do
> catálogo GoF."

Frase-resumo (está no slide): *"Prototype cria novos objetos copiando uma
instância existente e pré-configurada, em vez de construí-los do zero."*

> Dica de fala: "Diferente do Abstract Factory e do Factory Method, que
> criam objetos **novos** chamando `new` por trás de uma fábrica, o
> Prototype cria objetos **copiando** um objeto que já existe e já está
> configurado."

---

## 1. Problema (slide 2) — ~1min30s

Use o exemplo do slide e do código: um jogo (RPG) que precisa criar
monstros com atributos custosos de configurar (HP, ataque, lista de loot).

Pontos a cobrir, na ordem:

1. **Criar um objeto do zero pode ser caro** — em sistemas reais isso pode
   significar cálculo pesado, leitura de arquivo/rede, ou muitos passos de
   configuração; no nosso exemplo simplificado, seria redefinir HP, ataque
   e loot toda vez que um monstro nasce.
2. **A classe concreta pode só ser conhecida em tempo de execução** — por
   exemplo, um tipo de monstro carregado dinamicamente de um arquivo de
   configuração ou de um plugin.
3. **Instanciar direto com `new` acopla o cliente à classe concreta** — e
   obriga repetir toda a configuração a cada nova instância, mesmo quando
   já existe um "molde" pronto e testado daquele tipo de objeto.

> Dica de fala: "A pergunta que o Prototype responde é: já que eu tenho um
> Goblin configurado e funcionando, por que recriar tudo do zero para
> gerar outro Goblin, em vez de simplesmente copiar o que já existe?"

---

## 2. Solução (slide 3) — ~1min30s

Apresente a receita do padrão, passo a passo (os 5 itens do slide):

1. Declarar uma **interface Prototype** com um método de clonagem
   (`clonar()`).
2. Cada **classe concreta** implementa a interface e sabe copiar seus
   próprios dados — inclusive fazendo **cópia profunda (deep copy)** de
   campos mutáveis, como listas.
3. O cliente mantém instâncias já configuradas (**protótipos**) e cria
   novos objetos chamando `clonar()`, em vez de usar `new` e reconfigurar
   tudo.
4. Um **Registro de Protótipos** (catálogo) pode guardar protótipos por
   chave e devolver clones sob demanda — permite adicionar novos "tipos"
   em tempo de execução, só registrando um novo protótipo.
5. O cliente nunca precisa conhecer a classe concreta: só conhece a
   interface `Prototype`.

> Dica de fala: "Reparem no destaque para o *deep copy*: se a cópia fosse
> rasa (shallow), o clone e o original compartilhariam a mesma lista de
> loot na memória — mudar o loot de um clone mudaria o do outro sem
> querer. Isso é o cuidado técnico central do padrão."

---

## 3. Estrutura / Diagrama UML (slide 4) — ~1min30s

Aponte para o diagrama enquanto explica de cima para baixo:

- **Main / MonstroRegistro (cliente)** usa apenas a interface `Prototype`.
- **Prototype** é a interface: declara `clonar()`.
- **Goblin** e **Dragao** implementam `Prototype` e sabem copiar a si
  mesmos, inclusive o loot (deep copy).
- O `MonstroRegistro` guarda protótipos por chave e devolve
  `prototipo.clonar()` sob demanda.

> Dica de fala: "Reparem que não existe uma 'fábrica' separada criando o
> objeto do zero, como nos padrões anteriores — quem cria o novo objeto é
> o próprio objeto original, através do método `clonar()`. É por isso que
> o Prototype é às vezes descrito como uma alternativa à herança para
> configurar variações de um objeto."

---

## 4. Exemplo de Código (slide 5) — ~2min30s

Alterne entre o slide (estrutura) e o editor/terminal com o projeto em
`codigo/` (para mostrar rodando).

Ordem sugerida de explicação:

1. **`Prototype.java`** — a interface genérica com `T clonar()`.
2. **`Monstro.java`** — destaque o **construtor de cópia** (`protected
   Monstro(Monstro original)`), que copia nome, HP, ataque, e recria a
   lista de loot com `new ArrayList<>(original.loot)` — esse é o ponto
   exato do deep copy.
3. **`Goblin.java` / `Dragao.java`** — mostre que essas subclasses só
   reaproveitam o construtor de cópia da superclasse e implementam
   `clonar()` chamando esse construtor.
4. **`MonstroRegistro.java`** — o registro de protótipos:
   ```java
   Monstro criarMonstro(String tipo) {
       return prototipos.get(tipo).clonar();
   }
   ```
5. **`Main.java`** — registra os protótipos e cria clones a partir deles.

**Demonstração ao vivo (recomendado):**

```bash
cd prototype/codigo
javac -d bin $(find src -name "*.java")
java -cp bin com.labpoo.prototype.Main
```

Comente a saída: `goblin1` e `goblin2` são clones independentes do mesmo
protótipo (`goblin1 == goblin2` é `false`); ao modificar o nome e o loot
de `goblin2`, `goblin1` permanece intacto; e
`goblin1.getLoot() == goblin2.getLoot()` é `false`, provando que a lista de
loot foi copiada de verdade (deep copy), não apenas referenciada.

---

## 5. Aplicabilidade (slide 6) — ~1min

Explique quando vale a pena usar o padrão (os 4 itens do slide), com
exemplos rápidos:

- Classes definidas em tempo de execução → plugins, carregamento
  dinâmico de tipos.
- Evitar hierarquia paralela de fábricas → basta clonar em vez de criar
  uma fábrica para cada variação.
- Poucas combinações de estado → mais simples manter protótipos prontos
  do que reconfigurar cada objeto novo do zero.
- Criação cara → jogos com muitas entidades, editores gráficos, geração
  de documentos a partir de templates.

---

## 6. Prós e Contras (slide 7) — ~1min

Leia a tabela destacando o trade-off central:

- **Prós**: adicionar/remover "tipos" de produto em tempo de execução só
  registrando um novo protótipo; reduz a necessidade de subclasses de
  fábrica comparado ao Factory Method; evita repetir configuração cara,
  já que o objeto complexo é montado uma vez e depois só copiado.
- **Contras**: clonar objetos complexos com referências circulares pode
  ser difícil; exige atenção à diferença entre cópia rasa e cópia
  profunda — um erro aqui gera bugs sutis de estado compartilhado; toda
  subclasse concreta precisa implementar sua própria lógica correta de
  clonagem.

> Dica de fala: "Vale destacar de novo o risco do shallow copy: é o erro
> mais comum ao implementar Prototype, e o tipo de bug que só aparece
> depois, quando dois objetos que deveriam ser independentes começam a
> 'vazar' mudanças um para o outro."

---

## 7. Encerramento — ~30s

> "Resumindo: o Prototype é ideal quando criar um objeto do zero é caro
> ou quando a classe concreta só é conhecida em tempo de execução — nesses
> casos, copiar um protótipo já configurado é mais simples e mais barato
> do que reconstruir tudo. Junto com o Abstract Factory e o Factory
> Method, ele completa três formas diferentes de resolver o mesmo
> problema geral: como criar objetos sem acoplar o cliente às classes
> concretas. O código completo está disponível no repositório do
> projeto."

Encerre agradecendo e, se for pedido pela disciplina, cite as fontes
(GoF — Padrões de Projeto, e o material da disciplina Laboratório de POO).

---

## Checklist antes de gravar

- [ ] Testar a execução do projeto (`javac` + `java`) uma vez, sem gravar, para não travar durante o vídeo.
- [ ] Deixar o PPTX aberto em modo apresentação, e o terminal/editor já abertos em outra janela para alternar rápido.
- [ ] Ajustar o nome no rodapé dos slides (`Marcus Paulo — marcuspaulodev@gmail.com`) se necessário.
- [ ] Se sua turma já apresentou Abstract Factory e/ou Factory Method antes, aproveite para puxar o contraste: criar via fábrica (objeto novo) vs. criar via cópia (clonar um objeto existente) — ajuda a fixar os três padrões criacionais.
- [ ] Cronometrar um ensaio — o roteiro acima soma ~8min, ajuste o ritmo conforme o tempo definido pelo professor.
