package Screen;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import model.Editor;
import dao.EditorDAO;

public class EditorForm extends BaseForm {
    private JTextField cpfField;
    private JTextField nomeField;
    private JTextField dataContratoField;
    private JTextField salarioField;
    private JList<Editor> editoresList;
    private DefaultListModel<Editor> listModel;
    
    public EditorForm() {
        super("Cadastro de Editor");
        setSize(900, 650);  // Tamanho adequado da janela
        initializeComponents();
        carregarEditores();
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
        
        // Nome do Editor
        JPanel nomePanel = new JPanel(new GridBagLayout());
        nomePanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel nomeLabel = createStyledLabel("Nome do Editor:");
        nomeLabel.setPreferredSize(new Dimension(180, 25));
        nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nomePanel.add(nomeLabel, panelGbc);
        
        panelGbc.gridx = 1;
        nomeField = createStyledTextField();
        nomeField.setPreferredSize(new Dimension(250, 30));
        nomePanel.add(nomeField, panelGbc);
        
        leftPanel.add(nomePanel, gbc);
        
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
        
        // Painel direito - Lista de editores
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(236, 240, 241));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel listLabel = new JLabel("Editores Cadastrados:");
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listLabel.setForeground(new Color(44, 62, 80));
        rightPanel.add(listLabel, BorderLayout.NORTH);
        
        listModel = new DefaultListModel<>();
        editoresList = new JList<>(listModel);
        editoresList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        editoresList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        editoresList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Editor selectedEditor = editoresList.getSelectedValue();
                if (selectedEditor != null) {
                    exibirEditorSelecionado(selectedEditor);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(editoresList);
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
    
    private void carregarEditores() {
        listModel.clear();
        List<Editor> editores = EditorDAO.listarTodos();
        for (Editor editor : editores) {
            listModel.addElement(editor);
        }
    }
    
    private void exibirEditorSelecionado(Editor editor) {
        cpfField.setText(String.valueOf(editor.getCpf()));
        nomeField.setText(editor.getNome());
        dataContratoField.setText(editor.getDtContrato().toString());
        if (editor.getSalario() != null) {
            salarioField.setText(String.valueOf(editor.getSalario()));
        } else {
            salarioField.setText("");
        }
    }
    
    private void gerarProximoCPF() {
        long proximoCPF = EditorDAO.gerarProximoCPF();
        cpfField.setText(String.valueOf(proximoCPF));
        nomeField.setText("");
        dataContratoField.setText(LocalDate.now().toString());
        salarioField.setText("");
    }
    
    @Override
    protected void handleInsert() {
        try {
            // Verificar se os campos já estão preenchidos
            if (nomeField.getText().isEmpty()) {
                // Se o nome está vazio, apenas gerar um novo CPF e preparar o formulário
                gerarProximoCPF();
                JOptionPane.showMessageDialog(this, "Preencha os dados do editor e pressione 'Salvar' para confirmar.");
                return;
            }
            
            // Se chegou aqui, é porque os campos já estão preenchidos, então podemos tentar inserir
            long cpf = Long.parseLong(cpfField.getText());
            String nome = nomeField.getText();
            Date dtContrato = Date.valueOf(dataContratoField.getText());
            Double salario = salarioField.getText().isEmpty() ? null : Double.parseDouble(salarioField.getText());
            
            // Verificar se o CPF já existe
            Editor existente = EditorDAO.buscar(cpf);
            if (existente != null) {
                JOptionPane.showMessageDialog(this, 
                    "Já existe um editor com o CPF " + cpf + ".\n" +
                    "Um novo CPF será gerado automaticamente.", 
                    "CPF duplicado", JOptionPane.WARNING_MESSAGE);
                gerarProximoCPF();
                return;
            }
            
            Editor editor = new Editor(cpf, nome, dtContrato, salario);
            
            if (EditorDAO.inserir(editor)) {
                JOptionPane.showMessageDialog(this, "Editor inserido com sucesso!");
                limparCampos();
                carregarEditores();
                // Preparar para um novo editor
                gerarProximoCPF();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir editor.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            long cpf = Long.parseLong(cpfField.getText());
            Editor editor = EditorDAO.buscar(cpf);
            
            if (editor != null) {
                nomeField.setText(editor.getNome());
                dataContratoField.setText(editor.getDtContrato().toString());
                if (editor.getSalario() != null) {
                    salarioField.setText(String.valueOf(editor.getSalario()));
                } else {
                    salarioField.setText("");
                }
                
                // Selecionar na lista
                for (int i = 0; i < listModel.getSize(); i++) {
                    if (listModel.getElementAt(i).getCpf() == cpf) {
                        editoresList.setSelectedIndex(i);
                        editoresList.ensureIndexIsVisible(i);
                        break;
                    }
                }
                
                JOptionPane.showMessageDialog(this, "Editor encontrado!");
            } else {
                JOptionPane.showMessageDialog(this, "Editor não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um CPF válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleUpdate() {
        try {
            long cpf = Long.parseLong(cpfField.getText());
            String nome = nomeField.getText();
            Date dtContrato = Date.valueOf(dataContratoField.getText());
            Double salario = salarioField.getText().isEmpty() ? null : Double.parseDouble(salarioField.getText());
            
            // Validações
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha o nome do editor.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se o editor existe
            Editor existente = EditorDAO.buscar(cpf);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, 
                    "Não existe um editor com o CPF " + cpf + ".\n" +
                    "Utilize o botão 'Consultar' para encontrar um editor existente.", 
                    "Registro inexistente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Editor editor = new Editor(cpf, nome, dtContrato, salario);
            
            if (EditorDAO.atualizar(editor)) {
                JOptionPane.showMessageDialog(this, "Editor atualizado com sucesso!");
                limparCampos();
                carregarEditores();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar editor.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            long cpf = Long.parseLong(cpfField.getText());
            
            // Verificar se o editor existe
            Editor existente = EditorDAO.buscar(cpf);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, 
                    "Não existe um editor com o CPF " + cpf + ".", 
                    "Registro inexistente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o editor: " + existente.getNome() + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (EditorDAO.excluir(cpf)) {
                    JOptionPane.showMessageDialog(this, "Editor excluído com sucesso!");
                    limparCampos();
                    carregarEditores();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir editor. Verifique se ele não está sendo usado em outros registros.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um CPF válido.", "Erro", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Por favor, preencha o nome do editor.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            long cpf = Long.parseLong(cpfField.getText());
            Date dtContrato = Date.valueOf(dataContratoField.getText());
            Double salario = salarioField.getText().isEmpty() ? null : Double.parseDouble(salarioField.getText());
            
            // Verificar se o CPF já existe
            Editor existente = EditorDAO.buscar(cpf);
            if (existente != null) {
                // Se estamos tentando atualizar um existente
                int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Já existe um editor com este CPF. Deseja atualizá-lo?",
                    "Confirmar atualização",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirmacao == JOptionPane.YES_OPTION) {
                    handleUpdate();
                    return;
                } else {
                    gerarProximoCPF();
                    return;
                }
            }
            
            Editor editor = new Editor(cpf, nome, dtContrato, salario);
            
            if (EditorDAO.inserir(editor)) {
                JOptionPane.showMessageDialog(this, "Editor salvo com sucesso!");
                limparCampos();
                carregarEditores();
                // Preparar para um novo editor
                gerarProximoCPF();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar editor.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        editoresList.clearSelection();
    }
    
    private void limparCampos() {
        cpfField.setText("");
        nomeField.setText("");
        dataContratoField.setText(LocalDate.now().toString());
        salarioField.setText("");
        editoresList.clearSelection();
    }
} 