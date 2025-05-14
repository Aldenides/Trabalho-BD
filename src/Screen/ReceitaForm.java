package Screen;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import model.Receita;
import model.Cozinheiro;
import model.Categoria;
import dao.ReceitaDAO;
import dao.CozinheiroDAO;
import dao.CategoriaDAO;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Document;
import javax.swing.text.AbstractDocument;

public class ReceitaForm extends BaseForm {
    private JTextField codReceitaField;
    private JTextField nomeField;
    private JTextField dataField;
    private JTextField tempoPreparoField;
    private JTextField rendimentoField;
    private JTextArea modoPreparoArea;
    private JComboBox<Cozinheiro> cozinheiroComboBox;
    private JComboBox<Categoria> categoriaComboBox;
    private JList<Receita> receitasList;
    private DefaultListModel<Receita> listModel;
    
    public ReceitaForm() {
        super("Cadastro de Receita");
        setSize(1200, 700);
        initializeComponents();
        carregarDados();
    }
    
    private void carregarDados() {
        carregarReceitas();
        carregarCozinheiros();
        carregarCategorias();
    }
    
    private void initializeComponents() {
        // Painel esquerdo - Formulário
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(236, 240, 241));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        
        // Código da Receita
        JPanel codPanel = new JPanel(new GridBagLayout());
        codPanel.setBackground(new Color(236, 240, 241));
        
        GridBagConstraints panelGbc = new GridBagConstraints();
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        panelGbc.anchor = GridBagConstraints.WEST;
        panelGbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel codLabel = createStyledLabel("Código:");
        codLabel.setPreferredSize(new Dimension(180, 25));
        codLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        codPanel.add(codLabel, panelGbc);
        
        panelGbc.gridx = 1;
        codReceitaField = createStyledTextField();
        codReceitaField.setPreferredSize(new Dimension(350, 30));
        codPanel.add(codReceitaField, panelGbc);
        
        leftPanel.add(codPanel, gbc);
        
        // Nome da Receita
        JPanel nomePanel = new JPanel(new GridBagLayout());
        nomePanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel nomeLabel = createStyledLabel("Nome da Receita:");
        nomeLabel.setPreferredSize(new Dimension(180, 25));
        nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nomePanel.add(nomeLabel, panelGbc);
        
        panelGbc.gridx = 1;
        nomeField = createStyledTextField();
        nomeField.setPreferredSize(new Dimension(350, 30));
        nomePanel.add(nomeField, panelGbc);
        
        leftPanel.add(nomePanel, gbc);
        
        // Data de Criação
        JPanel dataPanel = new JPanel(new GridBagLayout());
        dataPanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel dataLabel = createStyledLabel("Data de Criação:");
        dataLabel.setPreferredSize(new Dimension(180, 25));
        dataLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dataPanel.add(dataLabel, panelGbc);
        
        panelGbc.gridx = 1;
        dataField = createStyledTextField();
        dataField.setText(LocalDate.now().toString());
        dataField.setPreferredSize(new Dimension(350, 30));
        dataField.setToolTipText("Formato esperado: AAAA-MM-DD (ex: 2023-11-27)");
        dataPanel.add(dataField, panelGbc);
        
        leftPanel.add(dataPanel, gbc);
        
        // Tempo de Preparo
        JPanel tempoPanel = new JPanel(new GridBagLayout());
        tempoPanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel tempoLabel = createStyledLabel("Tempo de Preparo (min):");
        tempoLabel.setPreferredSize(new Dimension(180, 25));
        tempoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        tempoPanel.add(tempoLabel, panelGbc);
        
        panelGbc.gridx = 1;
        tempoPreparoField = createStyledTextField();
        tempoPreparoField.setPreferredSize(new Dimension(350, 30));
        ((AbstractDocument) tempoPreparoField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        tempoPanel.add(tempoPreparoField, panelGbc);
        
        leftPanel.add(tempoPanel, gbc);
        
        // Rendimento
        JPanel rendimentoPanel = new JPanel(new GridBagLayout());
        rendimentoPanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel rendimentoLabel = createStyledLabel("Rendimento (porções):");
        rendimentoLabel.setPreferredSize(new Dimension(180, 25));
        rendimentoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        rendimentoPanel.add(rendimentoLabel, panelGbc);
        
        panelGbc.gridx = 1;
        rendimentoField = createStyledTextField();
        rendimentoField.setPreferredSize(new Dimension(350, 30));
        ((AbstractDocument) rendimentoField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        rendimentoPanel.add(rendimentoField, panelGbc);
        
        leftPanel.add(rendimentoPanel, gbc);
        
        // Cozinheiro
        JPanel cozinheiroPanel = new JPanel(new GridBagLayout());
        cozinheiroPanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel cozinheiroLabel = createStyledLabel("Cozinheiro:");
        cozinheiroLabel.setPreferredSize(new Dimension(180, 25));
        cozinheiroLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cozinheiroPanel.add(cozinheiroLabel, panelGbc);
        
        panelGbc.gridx = 1;
        cozinheiroComboBox = new JComboBox<>();
        cozinheiroComboBox.setPreferredSize(new Dimension(350, 30));
        cozinheiroComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cozinheiroPanel.add(cozinheiroComboBox, panelGbc);
        
        leftPanel.add(cozinheiroPanel, gbc);
        
        // Categoria
        JPanel categoriaPanel = new JPanel(new GridBagLayout());
        categoriaPanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel categoriaLabel = createStyledLabel("Categoria:");
        categoriaLabel.setPreferredSize(new Dimension(180, 25));
        categoriaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        categoriaPanel.add(categoriaLabel, panelGbc);
        
        panelGbc.gridx = 1;
        categoriaComboBox = new JComboBox<>();
        categoriaComboBox.setPreferredSize(new Dimension(350, 30));
        categoriaComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        categoriaPanel.add(categoriaComboBox, panelGbc);
        
        leftPanel.add(categoriaPanel, gbc);
        
        // Modo de Preparo
        JPanel modoPreparoPanel = new JPanel(new GridBagLayout());
        modoPreparoPanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel modoPreparoLabel = createStyledLabel("Modo de Preparo:");
        modoPreparoLabel.setPreferredSize(new Dimension(180, 25));
        modoPreparoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        modoPreparoPanel.add(modoPreparoLabel, panelGbc);
        
        panelGbc.gridx = 1;
        modoPreparoArea = new JTextArea();
        modoPreparoArea.setLineWrap(true);
        modoPreparoArea.setWrapStyleWord(true);
        modoPreparoArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane modoPreparoScroll = new JScrollPane(modoPreparoArea);
        modoPreparoScroll.setPreferredSize(new Dimension(350, 150));
        modoPreparoPanel.add(modoPreparoScroll, panelGbc);
        
        leftPanel.add(modoPreparoPanel, gbc);
        
        // Painel direito - Lista de receitas
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(236, 240, 241));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel listLabel = new JLabel("Receitas Cadastradas:");
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listLabel.setForeground(new Color(44, 62, 80));
        rightPanel.add(listLabel, BorderLayout.NORTH);
        
        listModel = new DefaultListModel<>();
        receitasList = new JList<>(listModel);
        receitasList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        receitasList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        receitasList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Receita selectedReceita = receitasList.getSelectedValue();
                if (selectedReceita != null) {
                    exibirReceitaSelecionada(selectedReceita);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(receitasList);
        scrollPane.setPreferredSize(new Dimension(400, 450));
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Adicionar painéis ao painel principal
        JPanel mainContentPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        mainContentPanel.setBackground(new Color(236, 240, 241));
        mainContentPanel.add(leftPanel);
        mainContentPanel.add(rightPanel);
        
        formPanel.setLayout(new BorderLayout());
        formPanel.add(mainContentPanel, BorderLayout.CENTER);
        
        // Adicionar botão para Ingredientes
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(236, 240, 241));
        
        JButton ingredientesButton = new JButton("Gerenciar Ingredientes");
        ingredientesButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ingredientesButton.addActionListener(e -> abrirGerenciadorIngredientes());
        
        bottomPanel.add(ingredientesButton);
        formPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void carregarReceitas() {
        listModel.clear();
        List<Receita> receitas = ReceitaDAO.listarTodas();
        for (Receita receita : receitas) {
            listModel.addElement(receita);
        }
    }
    
    private void carregarCozinheiros() {
        cozinheiroComboBox.removeAllItems();
        List<Cozinheiro> cozinheiros = CozinheiroDAO.listarTodos();
        for (Cozinheiro cozinheiro : cozinheiros) {
            cozinheiroComboBox.addItem(cozinheiro);
        }
    }
    
    private void carregarCategorias() {
        categoriaComboBox.removeAllItems();
        List<Categoria> categorias = CategoriaDAO.listarTodas();
        for (Categoria categoria : categorias) {
            categoriaComboBox.addItem(categoria);
        }
    }
    
    private void exibirReceitaSelecionada(Receita receita) {
        codReceitaField.setText(String.valueOf(receita.getCodReceita()));
        nomeField.setText(receita.getNome());
        dataField.setText(receita.getDataCriacao().toString());
        tempoPreparoField.setText(String.valueOf(receita.getTempoPreparo()));
        rendimentoField.setText(String.valueOf(receita.getRendimento()));
        modoPreparoArea.setText(receita.getModoPreparo());
        
        // Selecionar o cozinheiro no ComboBox
        for (int i = 0; i < cozinheiroComboBox.getItemCount(); i++) {
            Cozinheiro cozinheiro = cozinheiroComboBox.getItemAt(i);
            if (cozinheiro.getCpf() == receita.getCozinheiro().getCpf()) {
                cozinheiroComboBox.setSelectedIndex(i);
                break;
            }
        }
        
        // Selecionar a categoria no ComboBox
        for (int i = 0; i < categoriaComboBox.getItemCount(); i++) {
            Categoria categoria = categoriaComboBox.getItemAt(i);
            if (categoria.getNome().equals(receita.getCategoria().getNome())) {
                categoriaComboBox.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void gerarProximoCodigo() {
        int proximoCodigo = 1;
        
        if (!listModel.isEmpty()) {
            int maxCod = 0;
            for (int i = 0; i < listModel.getSize(); i++) {
                int cod = listModel.getElementAt(i).getCodReceita();
                if (cod > maxCod) maxCod = cod;
            }
            proximoCodigo = maxCod + 1;
        }
        
        codReceitaField.setText(String.valueOf(proximoCodigo));
        nomeField.setText("");
        dataField.setText(LocalDate.now().toString());
        tempoPreparoField.setText("");
        rendimentoField.setText("");
        modoPreparoArea.setText("");
    }
    
    private void abrirGerenciadorIngredientes() {
        if (codReceitaField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione uma receita antes de gerenciar seus ingredientes.", 
                                          "Receita não selecionada", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int codReceita = Integer.parseInt(codReceitaField.getText());
            
            // Verificar se a receita existe
            Receita receita = ReceitaDAO.buscar(codReceita);
            if (receita == null) {
                JOptionPane.showMessageDialog(this, "Receita não encontrada. Salve a receita antes de gerenciar seus ingredientes.", 
                                              "Receita não encontrada", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Abrir o formulário de ingredientes da receita
            IngredienteReceitaForm form = new IngredienteReceitaForm(receita);
            form.setVisible(true);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código de receita inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleInsert() {
        try {
            // Verificar se os campos já estão preenchidos
            if (nomeField.getText().isEmpty()) {
                // Se o nome está vazio, apenas gerar um novo código e preparar o formulário
                gerarProximoCodigo();
                JOptionPane.showMessageDialog(this, "Preencha os dados da receita e pressione 'Salvar' para confirmar.");
                return;
            }
            
            // Se chegou aqui, é porque os campos já estão preenchidos, então podemos tentar inserir
            int codReceita = Integer.parseInt(codReceitaField.getText());
            String nome = nomeField.getText();
            Date dataCriacao = Date.valueOf(dataField.getText());
            int tempoPreparo = tempoPreparoField.getText().isEmpty() ? 0 : Integer.parseInt(tempoPreparoField.getText());
            int rendimento = rendimentoField.getText().isEmpty() ? 0 : Integer.parseInt(rendimentoField.getText());
            String modoPreparo = modoPreparoArea.getText();
            
            // Verificar se o código já existe
            Receita existente = ReceitaDAO.buscar(codReceita);
            if (existente != null) {
                JOptionPane.showMessageDialog(this, 
                    "Já existe uma receita com o código " + codReceita + ".\n" +
                    "Um novo código será gerado automaticamente.", 
                    "Código duplicado", JOptionPane.WARNING_MESSAGE);
                gerarProximoCodigo();
                return;
            }
            
            // Verificar se os ComboBox estão selecionados
            if (cozinheiroComboBox.getSelectedIndex() == -1 || categoriaComboBox.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um cozinheiro e uma categoria.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Cozinheiro cozinheiro = (Cozinheiro) cozinheiroComboBox.getSelectedItem();
            Categoria categoria = (Categoria) categoriaComboBox.getSelectedItem();
            
            Receita receita = new Receita(codReceita, nome, dataCriacao, tempoPreparo, rendimento, modoPreparo, cozinheiro, categoria);
            
            if (ReceitaDAO.inserir(receita)) {
                JOptionPane.showMessageDialog(this, "Receita inserida com sucesso!");
                limparCampos();
                carregarReceitas();
                // Preparar para uma nova receita
                gerarProximoCodigo();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir receita.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha os campos numéricos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleSelect() {
        try {
            int codReceita = Integer.parseInt(codReceitaField.getText());
            Receita receita = ReceitaDAO.buscar(codReceita);
            
            if (receita != null) {
                exibirReceitaSelecionada(receita);
                
                // Selecionar na lista
                for (int i = 0; i < listModel.getSize(); i++) {
                    if (listModel.getElementAt(i).getCodReceita() == codReceita) {
                        receitasList.setSelectedIndex(i);
                        receitasList.ensureIndexIsVisible(i);
                        break;
                    }
                }
                
                JOptionPane.showMessageDialog(this, "Receita encontrada!");
            } else {
                JOptionPane.showMessageDialog(this, "Receita não encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um código válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleUpdate() {
        try {
            int codReceita = Integer.parseInt(codReceitaField.getText());
            String nome = nomeField.getText();
            
            // Validar formato da data
            Date dataCriacao;
            try {
                dataCriacao = Date.valueOf(dataField.getText().trim());
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, 
                    "Formato de data inválido. Use o formato AAAA-MM-DD (ex: 2023-11-27).", 
                    "Erro de formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validação melhorada para campos numéricos
            int tempoPreparo = 0;
            if (!tempoPreparoField.getText().isEmpty()) {
                try {
                    tempoPreparo = Integer.parseInt(tempoPreparoField.getText().trim());
                    if (tempoPreparo < 0) {
                        JOptionPane.showMessageDialog(this, "O tempo de preparo não pode ser negativo.", "Valor inválido", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Por favor, insira um valor inteiro válido para o tempo de preparo.", "Formato inválido", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            int rendimento = 0;
            if (!rendimentoField.getText().isEmpty()) {
                try {
                    rendimento = Integer.parseInt(rendimentoField.getText().trim());
                    if (rendimento <= 0) {
                        JOptionPane.showMessageDialog(this, "O rendimento deve ser maior que zero.", "Valor inválido", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Por favor, insira um valor inteiro válido para o rendimento.", "Formato inválido", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, preencha o campo de rendimento.", "Campo obrigatório", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String modoPreparo = modoPreparoArea.getText();
            
            // Validações
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha o nome da receita.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se os ComboBox estão selecionados
            if (cozinheiroComboBox.getSelectedIndex() == -1 || categoriaComboBox.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um cozinheiro e uma categoria.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Verificar se a receita existe
            Receita existente = ReceitaDAO.buscar(codReceita);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, 
                    "Não existe uma receita com o código " + codReceita + ".\n" +
                    "Utilize o botão 'Consultar' para encontrar uma receita existente.", 
                    "Registro inexistente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Cozinheiro cozinheiro = (Cozinheiro) cozinheiroComboBox.getSelectedItem();
            Categoria categoria = (Categoria) categoriaComboBox.getSelectedItem();
            
            Receita receita = new Receita(codReceita, nome, dataCriacao, tempoPreparo, rendimento, modoPreparo, cozinheiro, categoria);
            
            if (ReceitaDAO.atualizar(receita)) {
                JOptionPane.showMessageDialog(this, "Receita atualizada com sucesso!");
                limparCampos();
                carregarReceitas();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar receita.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha os campos numéricos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleDelete() {
        try {
            int codReceita = Integer.parseInt(codReceitaField.getText());
            
            // Verificar se a receita existe
            Receita existente = ReceitaDAO.buscar(codReceita);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, 
                    "Não existe uma receita com o código " + codReceita + ".", 
                    "Registro inexistente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir a receita: " + existente.getNome() + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (ReceitaDAO.excluir(codReceita)) {
                    JOptionPane.showMessageDialog(this, "Receita excluída com sucesso!");
                    limparCampos();
                    carregarReceitas();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir receita.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um código válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleSave() {
        try {
            String nome = nomeField.getText();
            
            // Validações
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha o nome da receita.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se os ComboBox estão selecionados
            if (cozinheiroComboBox.getSelectedIndex() == -1 || categoriaComboBox.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um cozinheiro e uma categoria.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int codReceita = Integer.parseInt(codReceitaField.getText());
            
            // Validar formato da data
            Date dataCriacao;
            try {
                dataCriacao = Date.valueOf(dataField.getText().trim());
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, 
                    "Formato de data inválido. Use o formato AAAA-MM-DD (ex: 2023-11-27).", 
                    "Erro de formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validação melhorada para campos numéricos
            int tempoPreparo = 0;
            if (!tempoPreparoField.getText().isEmpty()) {
                try {
                    tempoPreparo = Integer.parseInt(tempoPreparoField.getText().trim());
                    if (tempoPreparo < 0) {
                        JOptionPane.showMessageDialog(this, "O tempo de preparo não pode ser negativo.", "Valor inválido", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Por favor, insira um valor inteiro válido para o tempo de preparo.", "Formato inválido", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            int rendimento = 0;
            if (!rendimentoField.getText().isEmpty()) {
                try {
                    rendimento = Integer.parseInt(rendimentoField.getText().trim());
                    if (rendimento <= 0) {
                        JOptionPane.showMessageDialog(this, "O rendimento deve ser maior que zero.", "Valor inválido", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Por favor, insira um valor inteiro válido para o rendimento.", "Formato inválido", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, preencha o campo de rendimento.", "Campo obrigatório", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String modoPreparo = modoPreparoArea.getText();
            
            Cozinheiro cozinheiro = (Cozinheiro) cozinheiroComboBox.getSelectedItem();
            Categoria categoria = (Categoria) categoriaComboBox.getSelectedItem();
            
            // Verificar se o código já existe
            Receita existente = ReceitaDAO.buscar(codReceita);
            if (existente != null) {
                // Se estamos tentando atualizar um existente
                int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Já existe uma receita com este código. Deseja atualizá-la?",
                    "Confirmar atualização",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirmacao == JOptionPane.YES_OPTION) {
                    handleUpdate();
                    return;
                } else {
                    gerarProximoCodigo();
                    return;
                }
            }
            
            Receita receita = new Receita(codReceita, nome, dataCriacao, tempoPreparo, rendimento, modoPreparo, cozinheiro, categoria);
            
            if (ReceitaDAO.inserir(receita)) {
                JOptionPane.showMessageDialog(this, "Receita salva com sucesso!");
                limparCampos();
                carregarReceitas();
                // Preparar para uma nova receita
                gerarProximoCodigo();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar receita.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha os campos numéricos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleCancel() {
        limparCampos();
        receitasList.clearSelection();
    }
    
    private void limparCampos() {
        codReceitaField.setText("");
        nomeField.setText("");
        dataField.setText(LocalDate.now().toString());
        tempoPreparoField.setText("");
        rendimentoField.setText("");
        modoPreparoArea.setText("");
        cozinheiroComboBox.setSelectedIndex(-1);
        categoriaComboBox.setSelectedIndex(-1);
        receitasList.clearSelection();
    }
} 