package app;
import Screen.*;
import DataBase.SQLDebugger;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {
    public Main() {
        setTitle("Sistema de Receitas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de título estilizado
        JLabel titulo = new JLabel("Sistema de Receitas", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titulo.setForeground(new Color(44, 62, 80));
        titulo.setBorder(new EmptyBorder(40, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // Painel central com fundo suave
        JPanel centralPanel = new JPanel();
        centralPanel.setBackground(new Color(236, 240, 241));
        centralPanel.setLayout(new GridBagLayout());
        add(centralPanel, BorderLayout.CENTER);

        // Painel de botões customizado
        JPanel buttons = new JPanel(new GridLayout(3, 3, 20, 20));
        buttons.setOpaque(false);
        
        String[] entidades = {
            "Cozinheiros", "Degustadores", "Editores",
            "Livros", "Receitas", "Restaurantes",
            "Categorias", "Ingredientes", "Ingredientes da Receita"
        };
        
        for (String entidade : entidades) {
            JButton btn = createStyledButton(entidade);
            btn.addActionListener(e -> abrirTela(entidade));
            buttons.add(btn);
        }

        // Centralizar verticalmente
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        centralPanel.add(buttons, gbc);
    }
    
    private JButton createStyledButton(String text) {
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
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };
        
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return btn;
    }
    
    private void abrirTela(String entidade) {
        switch(entidade) {
            case "Cozinheiros":
                new CozinheiroForm().setVisible(true);
                break;
            case "Degustadores":
                new DegustadorForm().setVisible(true);
                break;
            case "Editores":
                new EditorForm().setVisible(true);
                break;
            case "Livros":
                new LivroForm().setVisible(true);
                break;
            case "Receitas":
                new ReceitaForm().setVisible(true);
                break;
            case "Restaurantes":
                new RestauranteForm().setVisible(true);
                break;
            case "Categorias":
                new CategoriaForm().setVisible(true);
                break;
            case "Ingredientes":
                new IngredienteForm().setVisible(true);
                break;
            case "Ingredientes da Receita":
                JOptionPane.showMessageDialog(this, 
                    "Para gerenciar os ingredientes de uma receita, primeiro selecione uma receita no cadastro de receitas.",
                    "Selecione uma Receita",
                    JOptionPane.INFORMATION_MESSAGE);
                new ReceitaForm().setVisible(true);
                break;
        }
        this.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}