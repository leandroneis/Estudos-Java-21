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
| Record Patterns | `recordpatterns` | ✅ |
| Sequenced Collections | `sequencedcollections` | ✅ |
| String Templates (preview) | `stringtemplates` | ✅ |
| Sealed Classes + switch exaustivo | `sealedclasses` | ✅ |

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

```java
// Ponto de partida — estrutura mínima de um JFrame na EDT
SwingUtilities.invokeLater(() -> {
    JFrame frame = new JFrame("Título");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);

    JPanel painel = new JPanel();
    painel.add(new JLabel("Nome:"));
    painel.add(new JTextField(15));
    painel.add(new JButton("Clique"));
    painel.add(new JCheckBox("Ativo"));

    frame.add(painel);
    frame.setVisible(true);
});
// Dica: adicione um WindowListener para reagir ao fechar a janela
// frame.addWindowListener(new WindowAdapter() { ... });
```

### 2. Calculadora
Layout com `GridLayout` para os botões numéricos e `BorderLayout` para o display.
`ActionListener` compartilhado entre botões via `getActionCommand()`.

```java
// Dica de estrutura do layout
JPanel display = new JPanel(new BorderLayout());
display.add(new JTextField("0", 10), BorderLayout.CENTER);

JPanel botoes = new JPanel(new GridLayout(4, 4, 5, 5));
String[] labels = {"7","8","9","/", "4","5","6","*", "1","2","3","-", "0","C","=","+"};
for (String label : labels) {
    JButton btn = new JButton(label);
    btn.addActionListener(e -> processar(e.getActionCommand())); // ActionCommand == label
    botoes.add(btn);
}
// Dica: use um campo "operacaoAtual" e "valorAcumulado" para guardar estado
```

### 3. Lista de tarefas (To-Do)
`JList` + `DefaultListModel` para adicionar e remover tarefas.
`JScrollPane` para rolagem. `ListSelectionListener` para habilitar botão remover.

```java
// Dica: DefaultListModel é o "ArrayList" do JList
DefaultListModel<String> modelo = new DefaultListModel<>();
JList<String> lista = new JList<>(modelo);

JButton remover = new JButton("Remover");
remover.setEnabled(false); // começa desabilitado

// Habilitado só quando há item selecionado
lista.addListSelectionListener(e -> remover.setEnabled(!lista.isSelectionEmpty()));

// Adicionar tarefa
modelo.addElement("Nova tarefa");

// Remover tarefa selecionada
remover.addActionListener(e -> modelo.remove(lista.getSelectedIndex()));
```

### 4. Tabela de dados com JTable
`AbstractTableModel` customizado com lista de objetos.
Ordenação com `TableRowSorter`. `JScrollPane` + seleção de linha.

```java
// Dica: AbstractTableModel exige implementar 3 métodos obrigatórios
class ProdutoTableModel extends AbstractTableModel {
    private final String[] colunas = {"Nome", "Preço", "Qtd"};
    private final List<Produto> dados = new ArrayList<>();

    @Override public int getRowCount() { return dados.size(); }
    @Override public int getColumnCount() { return colunas.length; }
    @Override public Object getValueAt(int row, int col) {
        Produto p = dados.get(row);
        return switch (col) { case 0 -> p.nome(); case 1 -> p.preco(); default -> p.qtd(); };
    }
    @Override public String getColumnName(int col) { return colunas[col]; }
}

// Ordenação automática por coluna ao clicar no cabeçalho
ProdutoTableModel modelo = new ProdutoTableModel();
JTable tabela = new JTable(modelo);
tabela.setRowSorter(new TableRowSorter<>(modelo));
```

### 5. Formulário com validação
`JTextField`, `JComboBox`, `JSpinner` e `JRadioButton`.
Validação ao submeter: campos obrigatórios, formato de e-mail, feedback visual com `JOptionPane`.

```java
// Dica: valide ao clicar em "Salvar", não campo a campo
JTextField campoEmail = new JTextField(20);
JComboBox<String> combo = new JComboBox<>(new String[]{"Admin", "Usuário"});
JSpinner idade = new JSpinner(new SpinnerNumberModel(18, 1, 120, 1));

JButton salvar = new JButton("Salvar");
salvar.addActionListener(e -> {
    String email = campoEmail.getText().trim();
    if (email.isEmpty() || !email.contains("@")) {
        JOptionPane.showMessageDialog(frame, "E-mail inválido", "Erro", JOptionPane.ERROR_MESSAGE);
        campoEmail.requestFocus(); // foca o campo com erro
        return;
    }
    // prosseguir com os dados válidos
});
// Dica: ButtonGroup agrupa JRadioButtons para seleção exclusiva
```

### 6. Desenho customizado
`JPanel` com `paintComponent` sobrescrito para desenhar formas geométricas.
`MouseListener` e `MouseMotionListener` para arrastar formas na tela.

```java
// Dica: SEMPRE chame super.paintComponent(g) primeiro para limpar o fundo
class PainelDesenho extends JPanel {
    private int x = 50, y = 50;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // obrigatório — limpa o frame anterior
        g.setColor(Color.BLUE);
        g.fillOval(x, y, 60, 60); // desenha na posição atual
    }
}

// Para arrastar: guarde o offset entre o clique e a posição da forma
painel.addMouseMotionListener(new MouseMotionAdapter() {
    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        painel.repaint(); // dispara novo paintComponent
    }
});
```

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
