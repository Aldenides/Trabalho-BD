package app;
import Screen.*;
import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Sistema de Receitas");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER,80,50));

        String[] operacoes = {"Inserir", "Consultar", "Atualizar", "Excluir"};
        for (String op : operacoes) {
            JButton btn = new JButton(op);
            btn.setPreferredSize(new Dimension(80, 35));
            btn.setBackground(new Color(70, 130, 180));
            btn.setForeground(Color.WHITE); 
            btn.setOpaque(true);
            btn.setBorderPainted(false); 
            
            btn.setFont(new Font("Arial", Font.BOLD, 10));
            
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(100, 150, 200)); 
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(70, 130, 180));  
                }
    });
            btn.addActionListener(e -> abrirTela(op));
            buttons.add(btn);
        }
        
    add(buttons, BorderLayout.SOUTH);
    
    add(new JPanel(), BorderLayout.CENTER);
    }
    
    private void abrirTela(String operacao) {
        switch(operacao) {
            case "Inserir":
                new InsertCozinheiro().setVisible(true);
                break;
            case "Consultar":
                new Select().setVisible(true);
                break;
            case "Atualizar":
                new Update().setVisible(true);
                break;
            case "Excluir":
                new Delete().setVisible(true);
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