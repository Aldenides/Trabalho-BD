package Screen;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import app.Main;

public class BaseForm extends JFrame {
    protected JPanel mainPanel;
    protected JPanel formPanel;
    protected JPanel buttonPanel;
    protected JPanel crudPanel;
    
    public BaseForm(String title) {
        setTitle(title);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Painel principal
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));
        
        // Título
        JLabel titulo = new JLabel(title, SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(44, 62, 80));
        titulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        mainPanel.add(titulo, BorderLayout.NORTH);
        
        // Painel do formulário - Usando GridBagLayout para centralizar
        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(236, 240, 241));
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        // Painel de botões CRUD
        crudPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        crudPanel.setBackground(new Color(236, 240, 241));
        addCrudButtons();
        
        // Painel de botões de ação
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(236, 240, 241));
        addDefaultButtons();
        
        // Painel para os botões
        JPanel buttonsContainer = new JPanel(new BorderLayout());
        buttonsContainer.setBackground(new Color(236, 240, 241));
        buttonsContainer.add(crudPanel, BorderLayout.NORTH);
        buttonsContainer.add(buttonPanel, BorderLayout.CENTER);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsContainer, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    protected void addCrudButtons() {
        JButton btnInserir = createStyledButton("Inserir");
        JButton btnConsultar = createStyledButton("Consultar");
        JButton btnAtualizar = createStyledButton("Atualizar");
        JButton btnExcluir = createStyledButton("Excluir");
        
        crudPanel.add(btnInserir);
        crudPanel.add(btnConsultar);
        crudPanel.add(btnAtualizar);
        crudPanel.add(btnExcluir);
        
        btnInserir.addActionListener(e -> handleInsert());
        btnConsultar.addActionListener(e -> handleSelect());
        btnAtualizar.addActionListener(e -> handleUpdate());
        btnExcluir.addActionListener(e -> handleDelete());
    }
    
    protected void addDefaultButtons() {
        JButton btnSalvar = createStyledButton("Salvar");
        JButton btnCancelar = createStyledButton("Cancelar");
        JButton btnVoltar = createStyledButton("Voltar");
        
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnVoltar);
        
        btnSalvar.addActionListener(e -> handleSave());
        btnCancelar.addActionListener(e -> handleCancel());
        btnVoltar.addActionListener(e -> {
            new Main().setVisible(true);
            dispose();
        });
    }
    
    // Métodos CRUD que podem ser sobrescritos pelas classes filhas
    protected void handleInsert() {
        JOptionPane.showMessageDialog(this, "Funcionalidade de Inserir a ser implementada");
    }
    
    protected void handleSelect() {
        JOptionPane.showMessageDialog(this, "Funcionalidade de Consultar a ser implementada");
    }
    
    protected void handleUpdate() {
        JOptionPane.showMessageDialog(this, "Funcionalidade de Atualizar a ser implementada");
    }
    
    protected void handleDelete() {
        JOptionPane.showMessageDialog(this, "Funcionalidade de Excluir a ser implementada");
    }
    
    protected void handleSave() {
        JOptionPane.showMessageDialog(this, "Funcionalidade de Salvar a ser implementada");
    }
    
    protected void handleCancel() {
        JOptionPane.showMessageDialog(this, "Funcionalidade de Cancelar a ser implementada");
    }
    
    protected JButton createStyledButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isArmed()) {
                    g.setColor(new Color(52, 152, 219));
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(41, 128, 185));
                } else {
                    g.setColor(new Color(52, 152, 219));
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        
        btn.setPreferredSize(new Dimension(120, 40));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return btn;
    }
    
    protected JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(250, 30));
        field.setMinimumSize(new Dimension(250, 30));
        field.setMaximumSize(new Dimension(250, 30));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(3, 6, 3, 6)
        ));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        return field;
    }
    
    protected JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(44, 62, 80));
        label.setPreferredSize(new Dimension(180, 25));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        return label;
    }
    
    // Método para adicionar componentes ao formulário de forma centralizada
    protected void addFormComponent(JComponent component) {
        // Criamos um painel que agrupa cada componente
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(236, 240, 241));
        panel.add(component);
        
        // Configurações do GridBagConstraints para melhor controle do layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Ocupa a linha inteira
        gbc.fill = GridBagConstraints.HORIZONTAL;     // Preenche horizontalmente
        gbc.anchor = GridBagConstraints.CENTER;       // Centraliza na célula
        gbc.insets = new Insets(5, 5, 5, 5);          // Margens maiores
        gbc.weightx = 1.0;                           // Distribuição horizontal
        
        formPanel.add(panel, gbc);
    }
    
    // Método para adicionar um rótulo e um campo de texto como uma unidade
    protected void addLabelAndField(String labelText, JComponent field) {
        JPanel rowPanel = new JPanel(new GridBagLayout());
        rowPanel.setBackground(new Color(236, 240, 241));
        
        GridBagConstraints panelGbc = new GridBagConstraints();
        
        // Criar e adicionar o rótulo
        JLabel label = createStyledLabel(labelText);
        label.setPreferredSize(new Dimension(180, 25));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        panelGbc.anchor = GridBagConstraints.EAST;
        panelGbc.insets = new Insets(5, 5, 5, 10);
        rowPanel.add(label, panelGbc);
        
        // Adicionar o campo
        panelGbc.gridx = 1;
        panelGbc.anchor = GridBagConstraints.WEST;
        panelGbc.insets = new Insets(5, 0, 5, 5);
        
        // Se o campo não tiver um tamanho definido, defina-o
        if (field.getPreferredSize().width < 100) {
            field.setPreferredSize(new Dimension(250, 30));
        }
        
        rowPanel.add(field, panelGbc);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        
        formPanel.add(rowPanel, gbc);
    }
} 