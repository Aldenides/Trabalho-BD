package Screen;
import app.Main;
import javax.swing.*;

public class Update extends JFrame {
    public Update() {
        setTitle("Atualizar Dados");
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