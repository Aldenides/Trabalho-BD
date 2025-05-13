package Screen;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Ingrediente;
import dao.IngredienteDAO;

public class IngredienteForm extends BaseForm {
    private JTextField codIngredienteField;
    private JTextField nomeField;
    private JTextField descricaoField;
    private JList<Ingrediente> ingredientesList;
    private DefaultListModel<Ingrediente> listModel;
    
    public IngredienteForm() {
        super("Cadastro de Ingrediente");
        initializeComponents();
        carregarIngredientes();
    }
    
    private void initializeComponents() {
        // Painel esquerdo - Formulário
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(236, 240, 241));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Código do Ingrediente
        JPanel codPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        codPanel.setBackground(new Color(236, 240, 241));
        JLabel codLabel = createStyledLabel("Código do Ingrediente:");
        codPanel.add(codLabel);
        codIngredienteField = createStyledTextField();
        codPanel.add(codIngredienteField);
        leftPanel.add(codPanel, gbc);
        
        // Nome do Ingrediente
        JPanel nomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nomePanel.setBackground(new Color(236, 240, 241));
        JLabel nomeLabel = createStyledLabel("Nome do Ingrediente:");
        nomePanel.add(nomeLabel);
        nomeField = createStyledTextField();
        nomePanel.add(nomeField);
        leftPanel.add(nomePanel, gbc);
        
        // Descrição do Ingrediente
        JPanel descPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        descPanel.setBackground(new Color(236, 240, 241));
        JLabel descLabel = createStyledLabel("Descrição (opcional):");
        descPanel.add(descLabel);
        descricaoField = createStyledTextField();
        descPanel.add(descricaoField);
        leftPanel.add(descPanel, gbc);
        
        // Botões adicionais
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setBackground(new Color(236, 240, 241));
        
        JButton btnNovo = createStyledButton("Novo");
        btnNovo.addActionListener(e -> gerarProximoCodigo());
        buttonsPanel.add(btnNovo);
        
        leftPanel.add(buttonsPanel, gbc);
        
        // Painel direito - Lista de ingredientes
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(236, 240, 241));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel listLabel = new JLabel("Ingredientes Cadastrados:");
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listLabel.setForeground(new Color(44, 62, 80));
        rightPanel.add(listLabel, BorderLayout.NORTH);
        
        listModel = new DefaultListModel<>();
        ingredientesList = new JList<>(listModel);
        ingredientesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ingredientesList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ingredientesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Ingrediente selectedIngrediente = ingredientesList.getSelectedValue();
                if (selectedIngrediente != null) {
                    exibirIngredienteSelecionado(selectedIngrediente);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(ingredientesList);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Adicionar painéis ao painel principal
        JPanel mainContentPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        mainContentPanel.setBackground(new Color(236, 240, 241));
        mainContentPanel.add(leftPanel);
        mainContentPanel.add(rightPanel);
        
        formPanel.setLayout(new BorderLayout());
        formPanel.add(mainContentPanel, BorderLayout.CENTER);
    }
    
    private void carregarIngredientes() {
        listModel.clear();
        List<Ingrediente> ingredientes = IngredienteDAO.listarTodos();
        for (Ingrediente ingrediente : ingredientes) {
            listModel.addElement(ingrediente);
        }
    }
    
    private void exibirIngredienteSelecionado(Ingrediente ingrediente) {
        codIngredienteField.setText(String.valueOf(ingrediente.getCodIngrediente()));
        nomeField.setText(ingrediente.getNome());
        descricaoField.setText(ingrediente.getDescricao());
    }
    
    private void gerarProximoCodigo() {
        List<Ingrediente> ingredientes = IngredienteDAO.listarTodos();
        int maxCodigo = 0;
        
        for (Ingrediente ingrediente : ingredientes) {
            if (ingrediente.getCodIngrediente() > maxCodigo) {
                maxCodigo = ingrediente.getCodIngrediente();
            }
        }
        
        codIngredienteField.setText(String.valueOf(maxCodigo + 1));
        nomeField.setText("");
        descricaoField.setText("");
        ingredientesList.clearSelection();
    }
    
    @Override
    protected void handleInsert() {
        try {
            int codIngrediente = Integer.parseInt(codIngredienteField.getText());
            String nome = nomeField.getText();
            String descricao = descricaoField.getText();
            
            // Validações
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um nome para o ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se o código já existe
            Ingrediente existente = IngredienteDAO.buscar(codIngrediente);
            if (existente != null) {
                JOptionPane.showMessageDialog(this, 
                    "Já existe um ingrediente com o código " + codIngrediente + ".\n" +
                    "Clique em 'Novo' para gerar um código disponível.", 
                    "Código duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Ingrediente ingrediente = new Ingrediente(codIngrediente, nome, descricao);
            
            if (IngredienteDAO.inserir(ingrediente)) {
                JOptionPane.showMessageDialog(this, "Ingrediente inserido com sucesso!");
                limparCampos();
                carregarIngredientes();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um código válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleSelect() {
        try {
            int codIngrediente = Integer.parseInt(codIngredienteField.getText());
            Ingrediente ingrediente = IngredienteDAO.buscar(codIngrediente);
            
            if (ingrediente != null) {
                nomeField.setText(ingrediente.getNome());
                descricaoField.setText(ingrediente.getDescricao());
                
                // Selecionar na lista
                for (int i = 0; i < listModel.getSize(); i++) {
                    if (listModel.getElementAt(i).getCodIngrediente() == codIngrediente) {
                        ingredientesList.setSelectedIndex(i);
                        ingredientesList.ensureIndexIsVisible(i);
                        break;
                    }
                }
                
                JOptionPane.showMessageDialog(this, "Ingrediente encontrado!");
            } else {
                JOptionPane.showMessageDialog(this, "Ingrediente não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
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
            int codIngrediente = Integer.parseInt(codIngredienteField.getText());
            String nome = nomeField.getText();
            String descricao = descricaoField.getText();
            
            // Validações
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um nome para o ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se o ingrediente existe
            Ingrediente existente = IngredienteDAO.buscar(codIngrediente);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, 
                    "Não existe um ingrediente com o código " + codIngrediente + ".\n" +
                    "Utilize o botão 'Consultar' para encontrar um ingrediente existente.", 
                    "Registro inexistente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Ingrediente ingrediente = new Ingrediente(codIngrediente, nome, descricao);
            
            if (IngredienteDAO.atualizar(ingrediente)) {
                JOptionPane.showMessageDialog(this, "Ingrediente atualizado com sucesso!");
                limparCampos();
                carregarIngredientes();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um código válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleDelete() {
        try {
            int codIngrediente = Integer.parseInt(codIngredienteField.getText());
            
            // Verificar se o ingrediente existe
            Ingrediente existente = IngredienteDAO.buscar(codIngrediente);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, 
                    "Não existe um ingrediente com o código " + codIngrediente + ".", 
                    "Registro inexistente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o ingrediente: " + existente.getNome() + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (IngredienteDAO.excluir(codIngrediente)) {
                    JOptionPane.showMessageDialog(this, "Ingrediente excluído com sucesso!");
                    limparCampos();
                    carregarIngredientes();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir ingrediente. Verifique se ele não está sendo usado em receitas.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        handleInsert();
    }
    
    @Override
    protected void handleCancel() {
        limparCampos();
        ingredientesList.clearSelection();
    }
    
    private void limparCampos() {
        codIngredienteField.setText("");
        nomeField.setText("");
        descricaoField.setText("");
        ingredientesList.clearSelection();
    }
} 