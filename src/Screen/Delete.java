package Screen;
import app.Main;
import javax.swing.*;

public class Delete extends JFrame {
    public Delete() {
        setTitle("Excluir Dados");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Botão de exemplo
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            new Main().setVisible(true);
            dispose();
        });
        
        add(btnVoltar);
    }
}