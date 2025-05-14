package Screen;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Categoria;
import dao.CategoriaDAO;

public class CategoriaForm extends BaseForm {
    private JTextField codCategoriaField;
    private JTextField descricaoField;
    private JList<Categoria> categoriasList;
    private DefaultListModel<Categoria> listModel;
    private String codCategoriaAntigo;
    
    public CategoriaForm() {
        super("Cadastro de Categoria");
        setSize(900, 650);  // Tamanho adequado da janela
        initializeComponents();
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
        
        // Código da Categoria
        JPanel codPanel = new JPanel(new GridBagLayout());
        codPanel.setBackground(new Color(236, 240, 241));
        
        GridBagConstraints panelGbc = new GridBagConstraints();
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        panelGbc.anchor = GridBagConstraints.WEST;
        panelGbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel codLabel = createStyledLabel("Código da Categoria:");
        codLabel.setPreferredSize(new Dimension(180, 25));
        codLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        codPanel.add(codLabel, panelGbc);
        
        panelGbc.gridx = 1;
        codCategoriaField = createStyledTextField();
        codCategoriaField.setPreferredSize(new Dimension(250, 30));
        codPanel.add(codCategoriaField, panelGbc);
        
        leftPanel.add(codPanel, gbc);
        
        // Descrição da Categoria
        JPanel descPanel = new JPanel(new GridBagLayout());
        descPanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel descLabel = createStyledLabel("Descrição da Categoria:");
        descLabel.setPreferredSize(new Dimension(180, 25));
        descLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descPanel.add(descLabel, panelGbc);
        
        panelGbc.gridx = 1;
        descricaoField = createStyledTextField();
        descricaoField.setPreferredSize(new Dimension(250, 30));
        descPanel.add(descricaoField, panelGbc);
        
        leftPanel.add(descPanel, gbc);
        
        // Painel direito - Lista de categorias
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(236, 240, 241));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel listLabel = new JLabel("Categorias Cadastradas:");
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listLabel.setForeground(new Color(44, 62, 80));
        rightPanel.add(listLabel, BorderLayout.NORTH);
        
        listModel = new DefaultListModel<>();
        categoriasList = new JList<>(listModel);
        categoriasList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoriasList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        categoriasList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Categoria selectedCategoria = categoriasList.getSelectedValue();
                if (selectedCategoria != null) {
                    exibirCategoriaSelecionada(selectedCategoria);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(categoriasList);
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
    
    private void carregarCategorias() {
        listModel.clear();
        List<Categoria> categorias = CategoriaDAO.listarTodas();
        for (Categoria categoria : categorias) {
            listModel.addElement(categoria);
        }
    }
    
    private void exibirCategoriaSelecionada(Categoria categoria) {
        codCategoriaField.setText(categoria.getNome());
        descricaoField.setText(categoria.getDescricao());
        codCategoriaAntigo = categoria.getNome();
    }
    
    private void gerarProximoCodigo() {
        // Lista todas as categorias
        List<Categoria> categorias = CategoriaDAO.listarTodas();
        int maxCodigo = 0;
        
        // Encontra o maior código
        for (Categoria categoria : categorias) {
            try {
                int codigo = Integer.parseInt(categoria.getNome());
                if (codigo > maxCodigo) {
                    maxCodigo = codigo;
                }
            } catch (NumberFormatException e) {
                // Ignora valores não numéricos
            }
        }
        
        // Gera o próximo código
        codCategoriaField.setText(String.valueOf(maxCodigo + 1));
        descricaoField.setText("");
        categoriasList.clearSelection();
    }
    
    @Override
    protected void handleInsert() {
        try {
            // Verificar se os campos já estão preenchidos
            if (descricaoField.getText().isEmpty()) {
                // Se a descrição está vazia, apenas gerar um novo código e preparar o formulário
                gerarProximoCodigo();
                JOptionPane.showMessageDialog(this, "Digite a descrição da categoria e pressione 'Salvar' para confirmar.");
                return;
            }
            
            // Se chegou aqui, é porque os campos já estão preenchidos, então podemos tentar inserir
            String codigo = codCategoriaField.getText();
            String descricao = descricaoField.getText();
            
            // Validações
            if (!codigo.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um código numérico para a categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se o código já existe
            Categoria existente = CategoriaDAO.buscarPorNome(codigo);
            if (existente != null) {
                JOptionPane.showMessageDialog(this, 
                    "Já existe uma categoria com o código " + codigo + ".\n" +
                    "Um novo código será gerado automaticamente.", 
                    "Código duplicado", JOptionPane.WARNING_MESSAGE);
                gerarProximoCodigo();
                return;
            }
            
            Categoria categoria = new Categoria(codigo, descricao);
            
            if (CategoriaDAO.inserir(categoria)) {
                JOptionPane.showMessageDialog(this, "Categoria inserida com sucesso!");
                limparCampos();
                carregarCategorias();
                // Preparar para uma nova categoria
                gerarProximoCodigo();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleSelect() {
        try {
            String codigo = codCategoriaField.getText();
            
            // Validações
            if (!codigo.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um código numérico para a categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Categoria categoria = CategoriaDAO.buscarPorNome(codigo);
            
            if (categoria != null) {
                descricaoField.setText(categoria.getDescricao());
                codCategoriaAntigo = categoria.getNome();
                
                // Selecionar na lista
                for (int i = 0; i < listModel.getSize(); i++) {
                    if (listModel.getElementAt(i).getNome().equals(codigo)) {
                        categoriasList.setSelectedIndex(i);
                        categoriasList.ensureIndexIsVisible(i);
                        break;
                    }
                }
                
                JOptionPane.showMessageDialog(this, "Categoria encontrada!");
            } else {
                JOptionPane.showMessageDialog(this, "Categoria não encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleUpdate() {
        try {
            String codigo = codCategoriaField.getText();
            String descricao = descricaoField.getText();
            
            // Validações
            if (!codigo.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um código numérico para a categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, insira uma descrição para a categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se a categoria existe
            if (codCategoriaAntigo == null) {
                JOptionPane.showMessageDialog(this, 
                    "Nenhuma categoria selecionada para atualização.\n" +
                    "Utilize o botão 'Consultar' para encontrar uma categoria existente.", 
                    "Registro inexistente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Categoria categoria = new Categoria(codigo, descricao);
            
            if (CategoriaDAO.atualizar(categoria, codCategoriaAntigo)) {
                JOptionPane.showMessageDialog(this, "Categoria atualizada com sucesso!");
                limparCampos();
                carregarCategorias();
                codCategoriaAntigo = null;
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleDelete() {
        try {
            String codigo = codCategoriaField.getText();
            
            // Validações
            if (!codigo.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um código numérico para a categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se a categoria existe
            Categoria existente = CategoriaDAO.buscarPorNome(codigo);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, 
                    "Não existe uma categoria com o código " + codigo + ".", 
                    "Registro inexistente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir a categoria: " + existente.getDescricao() + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (CategoriaDAO.excluir(codigo)) {
                    JOptionPane.showMessageDialog(this, "Categoria excluída com sucesso!");
                    limparCampos();
                    carregarCategorias();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir categoria. Verifique se ela não está sendo usada em receitas.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleSave() {
        try {
            String codigo = codCategoriaField.getText();
            String descricao = descricaoField.getText();
            
            // Validações
            if (!codigo.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um código numérico para a categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, insira uma descrição para a categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se o código já existe
            Categoria existente = CategoriaDAO.buscarPorNome(codigo);
            if (existente != null) {
                // Se estamos tentando atualizar um existente
                int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Já existe uma categoria com este código. Deseja atualizá-la?",
                    "Confirmar atualização",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirmacao == JOptionPane.YES_OPTION) {
                    codCategoriaAntigo = codigo;
                    handleUpdate();
                    return;
                } else {
                    gerarProximoCodigo();
                    return;
                }
            }
            
            Categoria categoria = new Categoria(codigo, descricao);
            
            if (CategoriaDAO.inserir(categoria)) {
                JOptionPane.showMessageDialog(this, "Categoria salva com sucesso!");
                limparCampos();
                carregarCategorias();
                // Preparar para uma nova categoria
                gerarProximoCodigo();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleCancel() {
        limparCampos();
        categoriasList.clearSelection();
    }
    
    private void limparCampos() {
        codCategoriaField.setText("");
        descricaoField.setText("");
        codCategoriaAntigo = null;
        categoriasList.clearSelection();
    }
} 