package Screen;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import model.Cozinheiro;
import dao.CozinheiroDAO;

public class CozinheiroForm extends BaseForm {
    private JTextField cpfField;
    private JTextField nomeField;
    private JTextField nomeFantasiaField;
    private JTextField dataContratoField;
    private JTextField salarioField;
    private JList<Cozinheiro> cozinheirosList;
    private DefaultListModel<Cozinheiro> listModel;
    
    public CozinheiroForm() {
        super("Cadastro de Cozinheiro");
        setSize(900, 650);  // Tamanho adequado da janela
        initializeComponents();
        carregarCozinheiros();
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
        
        // CPF
        JPanel cpfPanel = new JPanel(new GridBagLayout());
        cpfPanel.setBackground(new Color(236, 240, 241));
        
        GridBagConstraints panelGbc = new GridBagConstraints();
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        panelGbc.anchor = GridBagConstraints.WEST;
        panelGbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel cpfLabel = createStyledLabel("CPF:");
        cpfLabel.setPreferredSize(new Dimension(180, 25));
        cpfLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cpfPanel.add(cpfLabel, panelGbc);
        
        panelGbc.gridx = 1;
        cpfField = createStyledTextField();
        cpfField.setPreferredSize(new Dimension(250, 30));
        cpfPanel.add(cpfField, panelGbc);
        
        leftPanel.add(cpfPanel, gbc);
        
        // Nome do Cozinheiro
        JPanel nomePanel = new JPanel(new GridBagLayout());
        nomePanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel nomeLabel = createStyledLabel("Nome do Cozinheiro:");
        nomeLabel.setPreferredSize(new Dimension(180, 25));
        nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nomePanel.add(nomeLabel, panelGbc);
        
        panelGbc.gridx = 1;
        nomeField = createStyledTextField();
        nomeField.setPreferredSize(new Dimension(250, 30));
        nomePanel.add(nomeField, panelGbc);
        
        leftPanel.add(nomePanel, gbc);
        
        // Nome Fantasia
        JPanel nomeFantasiaPanel = new JPanel(new GridBagLayout());
        nomeFantasiaPanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel nomeFantasiaLabel = createStyledLabel("Nome Fantasia:");
        nomeFantasiaLabel.setPreferredSize(new Dimension(180, 25));
        nomeFantasiaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nomeFantasiaPanel.add(nomeFantasiaLabel, panelGbc);
        
        panelGbc.gridx = 1;
        nomeFantasiaField = createStyledTextField();
        nomeFantasiaField.setPreferredSize(new Dimension(250, 30));
        nomeFantasiaPanel.add(nomeFantasiaField, panelGbc);
        
        leftPanel.add(nomeFantasiaPanel, gbc);
        
        // Data de Contrato
        JPanel dataPanel = new JPanel(new GridBagLayout());
        dataPanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel dataLabel = createStyledLabel("Data de Contrato:");
        dataLabel.setPreferredSize(new Dimension(180, 25));
        dataLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dataPanel.add(dataLabel, panelGbc);
        
        panelGbc.gridx = 1;
        dataContratoField = createStyledTextField();
        dataContratoField.setText(LocalDate.now().toString()); // Data atual como padrão
        dataContratoField.setPreferredSize(new Dimension(250, 30));
        dataPanel.add(dataContratoField, panelGbc);
        
        leftPanel.add(dataPanel, gbc);
        
        // Salário
        JPanel salarioPanel = new JPanel(new GridBagLayout());
        salarioPanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel salarioLabel = createStyledLabel("Salário:");
        salarioLabel.setPreferredSize(new Dimension(180, 25));
        salarioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        salarioPanel.add(salarioLabel, panelGbc);
        
        panelGbc.gridx = 1;
        salarioField = createStyledTextField();
        salarioField.setPreferredSize(new Dimension(250, 30));
        salarioPanel.add(salarioField, panelGbc);
        
        leftPanel.add(salarioPanel, gbc);
        
        // Painel direito - Lista de cozinheiros
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(236, 240, 241));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel listLabel = new JLabel("Cozinheiros Cadastrados:");
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listLabel.setForeground(new Color(44, 62, 80));
        rightPanel.add(listLabel, BorderLayout.NORTH);
        
        listModel = new DefaultListModel<>();
        cozinheirosList = new JList<>(listModel);
        cozinheirosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cozinheirosList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cozinheirosList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Cozinheiro selectedCozinheiro = cozinheirosList.getSelectedValue();
                if (selectedCozinheiro != null) {
                    exibirCozinheiroSelecionado(selectedCozinheiro);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(cozinheirosList);
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
    
    private void carregarCozinheiros() {
        listModel.clear();
        List<Cozinheiro> cozinheiros = CozinheiroDAO.listarTodos();
        for (Cozinheiro cozinheiro : cozinheiros) {
            listModel.addElement(cozinheiro);
        }
    }
    
    private void exibirCozinheiroSelecionado(Cozinheiro cozinheiro) {
        cpfField.setText(String.valueOf(cozinheiro.getCpf()));
        nomeField.setText(cozinheiro.getNome());
        nomeFantasiaField.setText(cozinheiro.getNomeFantasia());
        dataContratoField.setText(cozinheiro.getDtContrato().toString());
        if (cozinheiro.getSalario() != null) {
            salarioField.setText(String.valueOf(cozinheiro.getSalario()));
        } else {
            salarioField.setText("");
        }
    }
    
    private void gerarProximoCPF() {
        int proximoCPF = CozinheiroDAO.listarTodos().stream()
                .mapToInt(Cozinheiro::getCpf)
                .max()
                .orElse(100000000) + 1;
        
        cpfField.setText(String.valueOf(proximoCPF));
        nomeField.setText("");
        nomeFantasiaField.setText("");
        dataContratoField.setText(LocalDate.now().toString());
        salarioField.setText("");
    }
    
    @Override
    protected void handleInsert() {
        try {
            // Verificar se os campos já estão preenchidos
            if (nomeField.getText().isEmpty() || nomeFantasiaField.getText().isEmpty()) {
                // Se o nome está vazio, apenas gerar um novo CPF e preparar o formulário
                gerarProximoCPF();
                JOptionPane.showMessageDialog(this, "Preencha os dados do cozinheiro e pressione 'Salvar' para confirmar.");
                return;
            }
            
            // Se chegou aqui, é porque os campos já estão preenchidos, então podemos tentar inserir
            int cpf = Integer.parseInt(cpfField.getText());
            String nome = nomeField.getText();
            String nomeFantasia = nomeFantasiaField.getText();
            Date dtContrato = Date.valueOf(dataContratoField.getText());
            Double salario = salarioField.getText().isEmpty() ? null : Double.parseDouble(salarioField.getText());
            
            // Verificar se o CPF já existe
            Cozinheiro existente = CozinheiroDAO.buscar(cpf);
            if (existente != null) {
                JOptionPane.showMessageDialog(this, 
                    "Já existe um cozinheiro com o CPF " + cpf + ".\n" +
                    "Um novo CPF será gerado automaticamente.", 
                    "CPF duplicado", JOptionPane.WARNING_MESSAGE);
                gerarProximoCPF();
                return;
            }
            
            Cozinheiro cozinheiro = new Cozinheiro(cpf, nome, nomeFantasia, dtContrato, salario);
            
            if (CozinheiroDAO.inserir(cozinheiro)) {
                JOptionPane.showMessageDialog(this, "Cozinheiro inserido com sucesso!");
                limparCampos();
                carregarCozinheiros();
                // Preparar para um novo cozinheiro
                gerarProximoCPF();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir cozinheiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha os campos numéricos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleSelect() {
        Cozinheiro selectedCozinheiro = cozinheirosList.getSelectedValue();
        if (selectedCozinheiro == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um cozinheiro da lista.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        try {
            int cpf = selectedCozinheiro.getCpf();
            Cozinheiro cozinheiro = CozinheiroDAO.buscar(cpf);
            
            if (cozinheiro != null) {
                cpfField.setText(String.valueOf(cozinheiro.getCpf()));
                nomeField.setText(cozinheiro.getNome());
                nomeFantasiaField.setText(cozinheiro.getNomeFantasia());
                dataContratoField.setText(cozinheiro.getDtContrato().toString());
                if (cozinheiro.getSalario() != null) {
                    salarioField.setText(String.valueOf(cozinheiro.getSalario()));
                } else {
                    salarioField.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cozinheiro não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao selecionar cozinheiro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleUpdate() {
        try {
            // Verificar se um cozinheiro está selecionado
            Cozinheiro selectedCozinheiro = cozinheirosList.getSelectedValue();
            if (selectedCozinheiro == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um cozinheiro para atualizar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            int cpf = Integer.parseInt(cpfField.getText());
            String nome = nomeField.getText();
            String nomeFantasia = nomeFantasiaField.getText();
            Date dtContrato = Date.valueOf(dataContratoField.getText());
            Double salario = salarioField.getText().isEmpty() ? null : Double.parseDouble(salarioField.getText());
            
            // Verificar se o CPF existe
            Cozinheiro existente = CozinheiroDAO.buscar(cpf);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, "Cozinheiro com CPF " + cpf + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Cozinheiro cozinheiro = new Cozinheiro(cpf, nome, nomeFantasia, dtContrato, salario);
            
            if (CozinheiroDAO.atualizar(cozinheiro)) {
                JOptionPane.showMessageDialog(this, "Cozinheiro atualizado com sucesso!");
                limparCampos();
                carregarCozinheiros();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar cozinheiro.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            // Verificar se um cozinheiro está selecionado
            Cozinheiro selectedCozinheiro = cozinheirosList.getSelectedValue();
            if (selectedCozinheiro == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um cozinheiro para excluir.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            int cpf = Integer.parseInt(cpfField.getText());
            
            // Verificar se o CPF existe
            Cozinheiro existente = CozinheiroDAO.buscar(cpf);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, "Cozinheiro com CPF " + cpf + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int confirmacao = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja excluir o cozinheiro " + existente.getNome() + "?", 
                "Confirmação de Exclusão", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (CozinheiroDAO.excluir(cpf)) {
                    JOptionPane.showMessageDialog(this, "Cozinheiro excluído com sucesso!");
                    limparCampos();
                    carregarCozinheiros();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir cozinheiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o CPF corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleSave() {
        try {
            // Verificar se os campos obrigatórios estão preenchidos
            if (cpfField.getText().isEmpty() || nomeField.getText().isEmpty() || nomeFantasiaField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int cpf = Integer.parseInt(cpfField.getText());
            String nome = nomeField.getText();
            String nomeFantasia = nomeFantasiaField.getText();
            Date dtContrato = Date.valueOf(dataContratoField.getText());
            Double salario = salarioField.getText().isEmpty() ? null : Double.parseDouble(salarioField.getText());
            
            // Verificar se o CPF já existe
            Cozinheiro existente = CozinheiroDAO.buscar(cpf);
            
            boolean sucesso;
            if (existente != null) {
                // Atualizar
                Cozinheiro cozinheiro = new Cozinheiro(cpf, nome, nomeFantasia, dtContrato, salario);
                sucesso = CozinheiroDAO.atualizar(cozinheiro);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Cozinheiro atualizado com sucesso!");
                }
            } else {
                // Inserir
                Cozinheiro cozinheiro = new Cozinheiro(cpf, nome, nomeFantasia, dtContrato, salario);
                sucesso = CozinheiroDAO.inserir(cozinheiro);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Cozinheiro inserido com sucesso!");
                }
            }
            
            if (sucesso) {
                limparCampos();
                carregarCozinheiros();
                gerarProximoCPF();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar cozinheiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha os campos numéricos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use o formato YYYY-MM-DD.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleCancel() {
        limparCampos();
        cozinheirosList.clearSelection();
    }
    
    private void limparCampos() {
        cpfField.setText("");
        nomeField.setText("");
        nomeFantasiaField.setText("");
        dataContratoField.setText(LocalDate.now().toString());
        salarioField.setText("");
        cozinheirosList.clearSelection();
    }
} 