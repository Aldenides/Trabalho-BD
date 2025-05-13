package Screen;

import model.Cozinheiro;
import dao.CozinheiroDAO;
import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;

public class InsertCozinheiro extends JFrame {
    private JTextField cpfField, nomeField, nomeFantasiaField;
    private JFormattedTextField salarioField;
    private JButton btnSalvar;
    
    public InsertCozinheiro() {
        // Configuração da interface
        setTitle("Cadastrar Cozinheiro");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        // Componentes do formulário
        cpfField = new JTextField(11);
        nomeField = new JTextField(30);
        nomeFantasiaField = new JTextField(30);
        salarioField = new JFormattedTextField();
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarCozinheiro());
        
        // Adiciona componentes
        add(new JLabel("CPF:"));
        add(cpfField);
        add(new JLabel("Nome:"));
        add(nomeField);
        add(new JLabel("Nome Fantasia:"));
        add(nomeFantasiaField);
        add(new JLabel("Salário:"));
        add(salarioField);
        add(btnSalvar);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void salvarCozinheiro() {
        try {
            Cozinheiro cozinheiro = new Cozinheiro(
                Long.parseLong(cpfField.getText()),
                nomeField.getText(),
                nomeFantasiaField.getText(),
                Date.valueOf(LocalDate.now()), // Data atual como contrato
                salarioField.getText().isEmpty() ? null : Double.parseDouble(salarioField.getText())
            );
            
            if (CozinheiroDAO.inserir(cozinheiro)) {
                JOptionPane.showMessageDialog(this, "Cozinheiro cadastrado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar cozinheiro", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dados inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        cpfField.setText("");
        nomeField.setText("");
        nomeFantasiaField.setText("");
        salarioField.setText("");
    }
}