# java21-estudos

Projeto de estudo prático de Java 21 e Java Swing.
Cada feature/tema tem seu próprio pacote com exemplos e exercícios implementados.

## Contexto do desenvolvedor

- QA Engineer e Java Developer com +10 anos de experiência
- Background em Spring Batch, Kafka, Arquitetura Hexagonal
- Aprofundando em Java 21 e Swing para entrevistas e uso real

## Objetivo

Implementar os exercícios práticos das features do Java 21 e de interfaces gráficas
com Java Swing, consolidando o entendimento de cada tema.

## Features Java 21 cobertas

| Feature | Pacote | Status |
|---|---|---|
| Virtual Threads (Project Loom) | `virtualthreads` | ✅ |
| Pattern Matching para switch | `patternmatching` | ✅ |
| Record Patterns | `recordpatterns` | 🔲 |
| Sequenced Collections | `sequencedcollections` | 🔲 |
| String Templates (preview) | `stringtemplates` | 🔲 |
| Sealed Classes + switch exaustivo | `sealedclasses` | 🔲 |

## Exercícios Swing cobertos

| Exercício | Pacote | Status |
|---|---|---|
| Janela básica e componentes | `swing/helloworld` | 🔲 |
| Calculadora | `swing/calculadora` | 🔲 |
| Lista de tarefas (To-Do) | `swing/todolist` | 🔲 |
| Tabela de dados com JTable | `swing/tabela` | 🔲 |
| Formulário com validação | `swing/formulario` | 🔲 |
| Desenho customizado (paintComponent) | `swing/pintura` | 🔲 |

## Estrutura de pacotes

```
src/main/java/br/leandro/java21/
├── virtualthreads/
├── patternmatching/
├── recordpatterns/
├── sequencedcollections/
├── stringtemplates/
├── sealedclasses/
└── swing/
    ├── helloworld/
    ├── calculadora/
    ├── todolist/
    ├── tabela/
    ├── formulario/
    └── pintura/

src/test/java/br/leandro/java21/
└── (mesma estrutura com testes JUnit 5 — apenas para Java 21 features)
```

## Convenções de código

- Java 21 com preview features habilitadas (`--enable-preview`)
- Cada feature tem uma classe `Main` com `public static void main` para rodar isolada
- Testes com JUnit 5; nomenclatura: `deve_[comportamento]_quando_[cenario]`
- Comentários em português explicando o "porquê" da feature, não só o "como"
- Componentes Swing sempre instanciados na Event Dispatch Thread (EDT) via `SwingUtilities.invokeLater`

## Dependências principais

- Java 21
- JUnit 5 (testes)
- Maven (build)
- Preview features habilitadas no compiler plugin
- Maven Toolchains configurado para usar JDK 21 (`~/.m2/toolchains.xml`)

## Resumo dos exercícios Java 21

### 1. Virtual Threads
Disparar 1000 tarefas com `Thread.sleep(100)` usando virtual threads.
Comparar throughput com `Executors.newFixedThreadPool(10)`.

### 2. Pattern Matching para switch
Modelar `sealed interface Pagamento` (Pix, Cartao, Boleto) e calcular taxas
com switch exaustivo + guarded patterns (`when`).

### 3. Record Patterns
Criar `Endereco` e `Cliente` aninhados, desestruturar com record patterns
em switch para imprimir dados sem chamar accessors manualmente.

### 4. Sequenced Collections
Implementar histórico de navegação (máx 5 páginas) usando `LinkedList` com
a nova API: `addLast`, `removeLast`, `getFirst`, `getLast`, `reversed()`.

### 5. String Templates (preview)
Montar mensagens de log com dados dinâmicos usando String Templates.

### 6. Sealed Classes + switch exaustivo
Modelar `Notificacao` (Email, SMS, Push), switch sem `default`, e observar
erro de compilação ao adicionar um 4º subtipo sem atualizar o switch.

## Resumo dos exercícios Swing

### 1. Janela básica e componentes
Criar `JFrame` com `JLabel`, `JButton`, `JTextField` e `JCheckBox`.
Entender ciclo de vida da janela e `WindowListener`.

### 2. Calculadora
Layout com `GridLayout` para os botões numéricos e `BorderLayout` para o display.
`ActionListener` compartilhado entre botões via `getActionCommand()`.

### 3. Lista de tarefas (To-Do)
`JList` + `DefaultListModel` para adicionar e remover tarefas.
`JScrollPane` para rolagem. `ListSelectionListener` para habilitar botão remover.

### 4. Tabela de dados com JTable
`AbstractTableModel` customizado com lista de objetos.
Ordenação com `TableRowSorter`. `JScrollPane` + seleção de linha.

### 5. Formulário com validação
`JTextField`, `JComboBox`, `JSpinner` e `JRadioButton`.
Validação ao submeter: campos obrigatórios, formato de e-mail, feedback visual com `JOptionPane`.

### 6. Desenho customizado
`JPanel` com `paintComponent` sobrescrito para desenhar formas geométricas.
`MouseListener` e `MouseMotionListener` para arrastar formas na tela.

## Comandos úteis

```bash
# Compilar
mvn compile

# Rodar testes
mvn test

# Rodar uma classe específica
mvn exec:java -Dexec.mainClass="br.leandro.java21.virtualthreads.Main"
mvn exec:java -Dexec.mainClass="br.leandro.java21.swing.calculadora.Main"
```
