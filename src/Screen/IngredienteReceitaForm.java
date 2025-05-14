package Screen;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.List;
import model.Receita;
import model.Ingrediente;
import model.IngredienteReceita;
import dao.IngredienteDAO;
import dao.IngredienteReceitaDAO;

public class IngredienteReceitaForm extends JFrame {
    private Receita receita;
    private JPanel mainPanel;
    private JList<IngredienteReceita> ingredientesList;
    private DefaultListModel<IngredienteReceita> listModel;
    private JComboBox<Ingrediente> ingredienteComboBox;
    private JTextField quantidadeField;
    private JTextField unidadeMedidaField;
    
    public IngredienteReceitaForm(Receita receita) {
        this.receita = receita;
        
        setTitle("Ingredientes da Receita: " + receita.getNome());
        setSize(900, 600);  // Increased from default size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initializeComponents();
        carregarDados();
    }
    
    private void initializeComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Painel do título
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(236, 240, 241));
        
        JLabel titleLabel = new JLabel("Gerenciamento de Ingredientes para: " + receita.getNome());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 62, 80));
        titlePanel.add(titleLabel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Painel central (conteúdo)
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentPanel.setBackground(new Color(236, 240, 241));
        
        // Painel esquerdo (formulário)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(236, 240, 241));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        
        // Ingrediente
        JPanel ingredientePanel = new JPanel(new GridBagLayout());
        ingredientePanel.setBackground(new Color(236, 240, 241));
        
        GridBagConstraints panelGbc = new GridBagConstraints();
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        panelGbc.anchor = GridBagConstraints.WEST;
        panelGbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel ingredienteLabel = new JLabel("Ingrediente:");
        ingredienteLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ingredienteLabel.setPreferredSize(new Dimension(150, 25));
        ingredientePanel.add(ingredienteLabel, panelGbc);
        
        panelGbc.gridx = 1;
        ingredienteComboBox = new JComboBox<>();
        ingredienteComboBox.setPreferredSize(new Dimension(300, 30)); // Increased width
        ingredienteComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ingredientePanel.add(ingredienteComboBox, panelGbc);
        
        leftPanel.add(ingredientePanel, gbc);
        
        // Quantidade
        JPanel quantidadePanel = new JPanel(new GridBagLayout());
        quantidadePanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel quantidadeLabel = new JLabel("Quantidade:");
        quantidadeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        quantidadeLabel.setPreferredSize(new Dimension(150, 25));
        quantidadePanel.add(quantidadeLabel, panelGbc);
        
        panelGbc.gridx = 1;
        quantidadeField = new JTextField();
        quantidadeField.setPreferredSize(new Dimension(300, 30)); // Increased width
        quantidadeField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Adicionar filtro para aceitar apenas números e um ponto decimal
        ((AbstractDocument) quantidadeField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (isValidNumericInput(fb.getDocument().getText(0, fb.getDocument().getLength()) + string)) {
                    super.insertString(fb, offset, string, attr);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String resultText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                
                if (isValidNumericInput(resultText)) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
            
            private boolean isValidNumericInput(String text) {
                // Aceitar campo vazio
                if (text.isEmpty()) {
                    return true;
                }
                
                // Verificar formato (dígitos e um único ponto decimal)
                if (text.equals(".")) {
                    return true; // Permitir começar com ponto
                }
                
                // Verifica se o texto contém apenas dígitos e no máximo um ponto
                if (!text.matches("^\\d*\\.?\\d*$")) {
                    return false;
                }
                
                // Verificar se não excede o tamanho máximo (precisão 4,2)
                if (text.contains(".")) {
                    String[] parts = text.split("\\.");
                    // Verificar parte inteira (máximo 2 dígitos)
                    if (parts[0].length() > 2) {
                        return false;
                    }
                    // Verificar parte decimal (máximo 2 dígitos)
                    if (parts.length > 1 && parts[1].length() > 2) {
                        return false;
                    }
                } else if (text.length() > 2) {
                    // Se não tiver ponto, limitar a 2 dígitos (parte inteira)
                    return false;
                }
                
                // Verificar se não excede o valor máximo (99.99)
                try {
                    double value = Double.parseDouble(text);
                    return value < 100;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
        
        quantidadePanel.add(quantidadeField, panelGbc);
        
        leftPanel.add(quantidadePanel, gbc);
        
        // Unidade de Medida
        JPanel unidadePanel = new JPanel(new GridBagLayout());
        unidadePanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel unidadeLabel = new JLabel("Unidade de Medida:");
        unidadeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        unidadeLabel.setPreferredSize(new Dimension(150, 25));
        unidadePanel.add(unidadeLabel, panelGbc);
        
        panelGbc.gridx = 1;
        unidadeMedidaField = new JTextField();
        unidadeMedidaField.setPreferredSize(new Dimension(300, 30)); // Increased width
        unidadeMedidaField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        unidadePanel.add(unidadeMedidaField, panelGbc);
        
        leftPanel.add(unidadePanel, gbc);
        
        // Botões do formulário
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        JButton adicionarButton = criarBotao("Adicionar");
        JButton atualizarButton = criarBotao("Atualizar");
        JButton removerButton = criarBotao("Remover");
        JButton limparButton = criarBotao("Limpar");
        
        adicionarButton.addActionListener(e -> adicionarIngrediente());
        atualizarButton.addActionListener(e -> atualizarIngrediente());
        removerButton.addActionListener(e -> removerIngrediente());
        limparButton.addActionListener(e -> limparCampos());
        
        buttonPanel.add(adicionarButton);
        buttonPanel.add(atualizarButton);
        buttonPanel.add(removerButton);
        buttonPanel.add(limparButton);
        
        leftPanel.add(buttonPanel, gbc);
        
        // Painel direito (lista de ingredientes)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(236, 240, 241));
        
        JLabel listLabel = new JLabel("Ingredientes da Receita");
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        listLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(listLabel, BorderLayout.NORTH);
        
        listModel = new DefaultListModel<>();
        ingredientesList = new JList<>(listModel);
        ingredientesList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ingredientesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                IngredienteReceita ingredienteReceita = ingredientesList.getSelectedValue();
                if (ingredienteReceita != null) {
                    exibirIngredienteSelecionado(ingredienteReceita);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(ingredientesList);
        scrollPane.setPreferredSize(new Dimension(350, 400)); // Increased width
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Adicionar os painéis ao painel de conteúdo
        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Botão de fechar no rodapé
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(new Color(236, 240, 241));
        
        JButton fecharButton = criarBotao("Fechar");
        fecharButton.addActionListener(e -> dispose());
        footerPanel.add(fecharButton);
        
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(52, 152, 219));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }
    
    private void carregarDados() {
        // Carregar ingredientes disponíveis
        carregarIngredientes();
        
        // Carregar ingredientes da receita
        carregarIngredientesReceita();
    }
    
    private void carregarIngredientes() {
        ingredienteComboBox.removeAllItems();
        List<Ingrediente> ingredientes = IngredienteDAO.listarTodos();
        for (Ingrediente ingrediente : ingredientes) {
            ingredienteComboBox.addItem(ingrediente);
        }
    }
    
    private void carregarIngredientesReceita() {
        listModel.clear();
        List<IngredienteReceita> ingredientes = IngredienteReceitaDAO.listarPorReceita(receita.getCodReceita());
        for (IngredienteReceita ingrediente : ingredientes) {
            listModel.addElement(ingrediente);
        }
    }
    
    private void exibirIngredienteSelecionado(IngredienteReceita ingredienteReceita) {
        // Selecionar o ingrediente no ComboBox
        for (int i = 0; i < ingredienteComboBox.getItemCount(); i++) {
            Ingrediente ingrediente = ingredienteComboBox.getItemAt(i);
            if (ingrediente.getCodIngrediente() == ingredienteReceita.getIngrediente().getCodIngrediente()) {
                ingredienteComboBox.setSelectedIndex(i);
                break;
            }
        }
        
        quantidadeField.setText(String.valueOf(ingredienteReceita.getQuantidade()));
        unidadeMedidaField.setText(ingredienteReceita.getUnidadeMedida());
    }
    
    private void adicionarIngrediente() {
        try {
            if (ingredienteComboBox.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um ingrediente.", "Campo incompleto", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (quantidadeField.getText().isEmpty() || unidadeMedidaField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha a quantidade e a unidade de medida.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Ingrediente ingrediente = (Ingrediente) ingredienteComboBox.getSelectedItem();
            
            // Melhorada a validação de entrada numérica
            String quantidadeText = quantidadeField.getText().trim().replace(",", ".");
            if (!isValidNumber(quantidadeText)) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, insira um valor numérico válido para a quantidade.\n" +
                    "Use apenas números e ponto (.) ou vírgula (,) como separador decimal.", 
                    "Formato inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double quantidade = Double.parseDouble(quantidadeText);
            String unidadeMedida = unidadeMedidaField.getText();
            
            // Verificar se o ingrediente já existe na receita
            for (int i = 0; i < listModel.getSize(); i++) {
                IngredienteReceita ir = listModel.getElementAt(i);
                if (ir.getIngrediente().getCodIngrediente() == ingrediente.getCodIngrediente()) {
                    JOptionPane.showMessageDialog(this, 
                        "Este ingrediente já está na receita.\n" +
                        "Use o botão 'Atualizar' para modificar a quantidade.", 
                        "Ingrediente duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            IngredienteReceita ingredienteReceita = new IngredienteReceita(receita, ingrediente, quantidade, unidadeMedida);
            
            if (IngredienteReceitaDAO.inserir(ingredienteReceita)) {
                JOptionPane.showMessageDialog(this, "Ingrediente adicionado com sucesso!");
                limparCampos();
                carregarIngredientesReceita();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, insira uma quantidade válida.\n" +
                "Certifique-se de que o valor está no formato correto (ex: 2.5).", 
                "Formato inválido", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarIngrediente() {
        try {
            IngredienteReceita selecionado = ingredientesList.getSelectedValue();
            if (selecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um ingrediente na lista para atualizar.", "Seleção necessária", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (ingredienteComboBox.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um ingrediente.", "Campo incompleto", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (quantidadeField.getText().isEmpty() || unidadeMedidaField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha a quantidade e a unidade de medida.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Ingrediente ingrediente = (Ingrediente) ingredienteComboBox.getSelectedItem();
            
            // Melhorada a validação de entrada numérica
            String quantidadeText = quantidadeField.getText().trim().replace(",", ".");
            if (!isValidNumber(quantidadeText)) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, insira um valor numérico válido para a quantidade.\n" +
                    "Use apenas números e ponto (.) ou vírgula (,) como separador decimal.", 
                    "Formato inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double quantidade = Double.parseDouble(quantidadeText);
            String unidadeMedida = unidadeMedidaField.getText();
            
            // Verificar se o ingrediente está sendo alterado
            if (ingrediente.getCodIngrediente() != selecionado.getIngrediente().getCodIngrediente()) {
                // Se estiver alterando para outro ingrediente, verificar se já existe na receita
                for (int i = 0; i < listModel.getSize(); i++) {
                    IngredienteReceita ir = listModel.getElementAt(i);
                    if (ir.getIngrediente().getCodIngrediente() == ingrediente.getCodIngrediente()) {
                        JOptionPane.showMessageDialog(this, 
                            "Este ingrediente já está na receita.\n" +
                            "Selecione o ingrediente na lista e modifique sua quantidade.", 
                            "Ingrediente duplicado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                
                // Excluir o ingrediente selecionado
                IngredienteReceitaDAO.excluir(selecionado.getReceita().getCodReceita(), selecionado.getIngrediente().getCodIngrediente());
                
                // Inserir o novo ingrediente
                IngredienteReceita novoIngrediente = new IngredienteReceita(receita, ingrediente, quantidade, unidadeMedida);
                
                if (IngredienteReceitaDAO.inserir(novoIngrediente)) {
                    JOptionPane.showMessageDialog(this, "Ingrediente atualizado com sucesso!");
                    limparCampos();
                    carregarIngredientesReceita();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Se estiver mantendo o mesmo ingrediente, apenas atualizar
                IngredienteReceita ingredienteReceita = new IngredienteReceita(receita, ingrediente, quantidade, unidadeMedida);
                
                if (IngredienteReceitaDAO.atualizar(ingredienteReceita)) {
                    JOptionPane.showMessageDialog(this, "Ingrediente atualizado com sucesso!");
                    limparCampos();
                    carregarIngredientesReceita();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, insira uma quantidade válida.\n" +
                "Certifique-se de que o valor está no formato correto (ex: 2.5).", 
                "Formato inválido", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removerIngrediente() {
        try {
            IngredienteReceita selecionado = ingredientesList.getSelectedValue();
            if (selecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um ingrediente na lista para remover.", "Seleção necessária", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja remover o ingrediente: " + selecionado.getIngrediente().getNome() + "?",
                "Confirmar remoção",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (IngredienteReceitaDAO.excluir(selecionado.getReceita().getCodReceita(), selecionado.getIngrediente().getCodIngrediente())) {
                    JOptionPane.showMessageDialog(this, "Ingrediente removido com sucesso!");
                    limparCampos();
                    carregarIngredientesReceita();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao remover ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        ingredienteComboBox.setSelectedIndex(-1);
        quantidadeField.setText("");
        unidadeMedidaField.setText("");
        ingredientesList.clearSelection();
    }

    // Método auxiliar para validar números
    private boolean isValidNumber(String text) {
        // Remove espaços em branco e substitui vírgulas por pontos
        String cleanText = text.trim().replace(",", ".");
        try {
            double value = Double.parseDouble(cleanText);
            
            // Validar tamanho para numeric(4,2)
            // Isso significa no máximo 2 dígitos para a parte inteira e 2 dígitos para a parte decimal
            if (value < 0) {
                JOptionPane.showMessageDialog(null, 
                    "A quantidade não pode ser negativa.",
                    "Valor inválido", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (value >= 100) {
                JOptionPane.showMessageDialog(null, 
                    "A quantidade deve ser menor que 100 (limitação do banco de dados).",
                    "Valor muito grande", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Verificar quantidade de casas decimais (máximo 2)
            String[] parts = cleanText.split("\\.");
            if (parts.length > 1 && parts[1].length() > 2) {
                JOptionPane.showMessageDialog(null, 
                    "A quantidade deve ter no máximo 2 casas decimais.",
                    "Formato inválido", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
} 