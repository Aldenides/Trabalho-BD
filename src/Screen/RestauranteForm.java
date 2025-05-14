package Screen;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Restaurante;
import dao.RestauranteDAO;

public class RestauranteForm extends BaseForm {
    private JTextField codRestauranteField;
    private JTextField nomeField;
    private JTextField enderecoField;
    private JTextField telefoneField;
    private JTextField capacidadeField;
    private JTextField especialidadeField;
    private JList<Restaurante> restaurantesList;
    private DefaultListModel<Restaurante> listModel;
    
    public RestauranteForm() {
        super("Cadastro de Restaurante");
        setSize(900, 650);
        initializeComponents();
        carregarRestaurantes();
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
        
        // Código do Restaurante
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
        codRestauranteField = createStyledTextField();
        codRestauranteField.setPreferredSize(new Dimension(250, 30));
        codPanel.add(codRestauranteField, panelGbc);
        
        leftPanel.add(codPanel, gbc);
        
        // Nome do Restaurante
        JPanel nomePanel = new JPanel(new GridBagLayout());
        nomePanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel nomeLabel = createStyledLabel("Nome do Restaurante:");
        nomeLabel.setPreferredSize(new Dimension(180, 25));
        nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nomePanel.add(nomeLabel, panelGbc);
        
        panelGbc.gridx = 1;
        nomeField = createStyledTextField();
        nomeField.setPreferredSize(new Dimension(250, 30));
        nomePanel.add(nomeField, panelGbc);
        
        leftPanel.add(nomePanel, gbc);
        
        // Endereço
        JPanel enderecoPanel = new JPanel(new GridBagLayout());
        enderecoPanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel enderecoLabel = createStyledLabel("Endereço:");
        enderecoLabel.setPreferredSize(new Dimension(180, 25));
        enderecoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        enderecoPanel.add(enderecoLabel, panelGbc);
        
        panelGbc.gridx = 1;
        enderecoField = createStyledTextField();
        enderecoField.setPreferredSize(new Dimension(250, 30));
        enderecoPanel.add(enderecoField, panelGbc);
        
        leftPanel.add(enderecoPanel, gbc);
        
        // Telefone
        JPanel telefonePanel = new JPanel(new GridBagLayout());
        telefonePanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel telefoneLabel = createStyledLabel("Telefone:");
        telefoneLabel.setPreferredSize(new Dimension(180, 25));
        telefoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        telefonePanel.add(telefoneLabel, panelGbc);
        
        panelGbc.gridx = 1;
        telefoneField = createStyledTextField();
        telefoneField.setPreferredSize(new Dimension(250, 30));
        telefonePanel.add(telefoneField, panelGbc);
        
        leftPanel.add(telefonePanel, gbc);
        
        // Capacidade
        JPanel capacidadePanel = new JPanel(new GridBagLayout());
        capacidadePanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel capacidadeLabel = createStyledLabel("Capacidade:");
        capacidadeLabel.setPreferredSize(new Dimension(180, 25));
        capacidadeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        capacidadePanel.add(capacidadeLabel, panelGbc);
        
        panelGbc.gridx = 1;
        capacidadeField = createStyledTextField();
        capacidadeField.setPreferredSize(new Dimension(250, 30));
        capacidadePanel.add(capacidadeField, panelGbc);
        
        leftPanel.add(capacidadePanel, gbc);
        
        // Especialidade
        JPanel especialidadePanel = new JPanel(new GridBagLayout());
        especialidadePanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel especialidadeLabel = createStyledLabel("Especialidade:");
        especialidadeLabel.setPreferredSize(new Dimension(180, 25));
        especialidadeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        especialidadePanel.add(especialidadeLabel, panelGbc);
        
        panelGbc.gridx = 1;
        especialidadeField = createStyledTextField();
        especialidadeField.setPreferredSize(new Dimension(250, 30));
        especialidadePanel.add(especialidadeField, panelGbc);
        
        leftPanel.add(especialidadePanel, gbc);
        
        // Painel direito - Lista de restaurantes
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(236, 240, 241));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel listLabel = new JLabel("Restaurantes Cadastrados:");
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listLabel.setForeground(new Color(44, 62, 80));
        rightPanel.add(listLabel, BorderLayout.NORTH);
        
        listModel = new DefaultListModel<>();
        restaurantesList = new JList<>(listModel);
        restaurantesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        restaurantesList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        restaurantesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Restaurante selectedRestaurante = restaurantesList.getSelectedValue();
                if (selectedRestaurante != null) {
                    exibirRestauranteSelecionado(selectedRestaurante);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(restaurantesList);
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
    
    private void carregarRestaurantes() {
        listModel.clear();
        List<Restaurante> restaurantes = RestauranteDAO.listarTodos();
        for (Restaurante restaurante : restaurantes) {
            listModel.addElement(restaurante);
        }
    }
    
    private void exibirRestauranteSelecionado(Restaurante restaurante) {
        codRestauranteField.setText(String.valueOf(restaurante.getCodRestaurante()));
        nomeField.setText(restaurante.getNome());
        enderecoField.setText(restaurante.getEndereco());
        telefoneField.setText(restaurante.getTelefone());
        capacidadeField.setText(String.valueOf(restaurante.getCapacidade()));
        especialidadeField.setText(restaurante.getEspecialidade());
    }
    
    private void gerarProximoCodigo() {
        int proximoCodigo = RestauranteDAO.gerarProximoCodigo();
        codRestauranteField.setText(String.valueOf(proximoCodigo));
        nomeField.setText("");
        enderecoField.setText("");
        telefoneField.setText("");
        capacidadeField.setText("");
        especialidadeField.setText("");
    }
    
    @Override
    protected void handleInsert() {
        try {
            // Verificar se os campos já estão preenchidos
            if (nomeField.getText().isEmpty()) {
                // Se o nome está vazio, apenas gerar um novo código e preparar o formulário
                gerarProximoCodigo();
                JOptionPane.showMessageDialog(this, "Preencha os dados do restaurante e pressione 'Salvar' para confirmar.");
                return;
            }
            
            // Se chegou aqui, é porque os campos já estão preenchidos, então podemos tentar inserir
            int codRestaurante = Integer.parseInt(codRestauranteField.getText());
            String nome = nomeField.getText();
            String endereco = enderecoField.getText();
            String telefone = telefoneField.getText();
            int capacidade = capacidadeField.getText().isEmpty() ? 0 : Integer.parseInt(capacidadeField.getText());
            String especialidade = especialidadeField.getText();
            
            // Verificar se o código já existe
            Restaurante existente = RestauranteDAO.buscar(codRestaurante);
            if (existente != null) {
                JOptionPane.showMessageDialog(this, 
                    "Já existe um restaurante com o código " + codRestaurante + ".\n" +
                    "Um novo código será gerado automaticamente.", 
                    "Código duplicado", JOptionPane.WARNING_MESSAGE);
                gerarProximoCodigo();
                return;
            }
            
            Restaurante restaurante = new Restaurante(codRestaurante, nome, endereco, telefone, capacidade, especialidade);
            
            if (RestauranteDAO.inserir(restaurante)) {
                JOptionPane.showMessageDialog(this, "Restaurante inserido com sucesso!");
                limparCampos();
                carregarRestaurantes();
                // Preparar para um novo restaurante
                gerarProximoCodigo();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir restaurante.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            int codRestaurante = Integer.parseInt(codRestauranteField.getText());
            Restaurante restaurante = RestauranteDAO.buscar(codRestaurante);
            
            if (restaurante != null) {
                nomeField.setText(restaurante.getNome());
                enderecoField.setText(restaurante.getEndereco());
                telefoneField.setText(restaurante.getTelefone());
                capacidadeField.setText(String.valueOf(restaurante.getCapacidade()));
                especialidadeField.setText(restaurante.getEspecialidade());
                
                // Selecionar na lista
                for (int i = 0; i < listModel.getSize(); i++) {
                    if (listModel.getElementAt(i).getCodRestaurante() == codRestaurante) {
                        restaurantesList.setSelectedIndex(i);
                        restaurantesList.ensureIndexIsVisible(i);
                        break;
                    }
                }
                
                JOptionPane.showMessageDialog(this, "Restaurante encontrado!");
            } else {
                JOptionPane.showMessageDialog(this, "Restaurante não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
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
            int codRestaurante = Integer.parseInt(codRestauranteField.getText());
            String nome = nomeField.getText();
            String endereco = enderecoField.getText();
            String telefone = telefoneField.getText();
            int capacidade = capacidadeField.getText().isEmpty() ? 0 : Integer.parseInt(capacidadeField.getText());
            String especialidade = especialidadeField.getText();
            
            // Validações
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha o nome do restaurante.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se o restaurante existe
            Restaurante existente = RestauranteDAO.buscar(codRestaurante);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, 
                    "Não existe um restaurante com o código " + codRestaurante + ".\n" +
                    "Utilize o botão 'Consultar' para encontrar um restaurante existente.", 
                    "Registro inexistente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Restaurante restaurante = new Restaurante(codRestaurante, nome, endereco, telefone, capacidade, especialidade);
            
            if (RestauranteDAO.atualizar(restaurante)) {
                JOptionPane.showMessageDialog(this, "Restaurante atualizado com sucesso!");
                limparCampos();
                carregarRestaurantes();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar restaurante.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            int codRestaurante = Integer.parseInt(codRestauranteField.getText());
            
            // Verificar se o restaurante existe
            Restaurante existente = RestauranteDAO.buscar(codRestaurante);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, 
                    "Não existe um restaurante com o código " + codRestaurante + ".", 
                    "Registro inexistente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o restaurante: " + existente.getNome() + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (RestauranteDAO.excluir(codRestaurante)) {
                    JOptionPane.showMessageDialog(this, "Restaurante excluído com sucesso!");
                    limparCampos();
                    carregarRestaurantes();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir restaurante. Verifique se ele não está sendo usado em outros registros.", "Erro", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Por favor, preencha o nome do restaurante.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int codRestaurante = Integer.parseInt(codRestauranteField.getText());
            String endereco = enderecoField.getText();
            String telefone = telefoneField.getText();
            int capacidade = capacidadeField.getText().isEmpty() ? 0 : Integer.parseInt(capacidadeField.getText());
            String especialidade = especialidadeField.getText();
            
            // Verificar se o código já existe
            Restaurante existente = RestauranteDAO.buscar(codRestaurante);
            if (existente != null) {
                // Se estamos tentando atualizar um existente
                int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Já existe um restaurante com este código. Deseja atualizá-lo?",
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
            
            Restaurante restaurante = new Restaurante(codRestaurante, nome, endereco, telefone, capacidade, especialidade);
            
            if (RestauranteDAO.inserir(restaurante)) {
                JOptionPane.showMessageDialog(this, "Restaurante salvo com sucesso!");
                limparCampos();
                carregarRestaurantes();
                // Preparar para um novo restaurante
                gerarProximoCodigo();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar restaurante.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        restaurantesList.clearSelection();
    }
    
    private void limparCampos() {
        codRestauranteField.setText("");
        nomeField.setText("");
        enderecoField.setText("");
        telefoneField.setText("");
        capacidadeField.setText("");
        especialidadeField.setText("");
        restaurantesList.clearSelection();
    }
} 