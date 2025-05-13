package Screen;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import model.Cozinheiro;
import dao.CozinheiroDAO;

public class CozinheiroForm extends BaseForm {
    private JTextField cpfField;
    private JTextField nomeField;
    private JTextField nomeFantasiaField;
    private JTextField dataContratoField;
    private JTextField salarioField;
    
    public CozinheiroForm() {
        super("Cadastro de Cozinheiro");
        initializeComponents();
    }
    
    private void initializeComponents() {
        // CPF
        cpfField = createStyledTextField();
        addLabelAndField("CPF:", cpfField);
        
        // Nome do Cozinheiro
        nomeField = createStyledTextField();
        addLabelAndField("Nome do Cozinheiro:", nomeField);
        
        // Nome Fantasia
        nomeFantasiaField = createStyledTextField();
        addLabelAndField("Nome Fantasia:", nomeFantasiaField);
        
        // Data de Contrato
        dataContratoField = createStyledTextField();
        dataContratoField.setText(LocalDate.now().toString()); // Data atual como padrão
        addLabelAndField("Data de Contrato:", dataContratoField);
        
        // Salário
        salarioField = createStyledTextField();
        addLabelAndField("Salário:", salarioField);
    }
    
    @Override
    protected void handleInsert() {
        try {
            long cpf = Long.parseLong(cpfField.getText());
            String nome = nomeField.getText();
            String nomeFantasia = nomeFantasiaField.getText();
            Date dtContrato = Date.valueOf(dataContratoField.getText());
            Double salario = salarioField.getText().isEmpty() ? null : Double.parseDouble(salarioField.getText());
            
            Cozinheiro cozinheiro = new Cozinheiro(cpf, nome, nomeFantasia, dtContrato, salario);
            
            if (CozinheiroDAO.inserir(cozinheiro)) {
                JOptionPane.showMessageDialog(this, "Cozinheiro inserido com sucesso!");
                limparCampos();
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
        try {
            long cpf = Long.parseLong(cpfField.getText());
            Cozinheiro cozinheiro = CozinheiroDAO.buscar(cpf);
            
            if (cozinheiro != null) {
                nomeField.setText(cozinheiro.getNome());
                nomeFantasiaField.setText(cozinheiro.getNomeFantasia());
                dataContratoField.setText(cozinheiro.getDtContrato().toString());
                if (cozinheiro.getSalario() != null) {
                    salarioField.setText(String.valueOf(cozinheiro.getSalario()));
                } else {
                    salarioField.setText("");
                }
                JOptionPane.showMessageDialog(this, "Cozinheiro encontrado!");
            } else {
                JOptionPane.showMessageDialog(this, "Cozinheiro não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
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
            String nomeFantasia = nomeFantasiaField.getText();
            Date dtContrato = Date.valueOf(dataContratoField.getText());
            Double salario = salarioField.getText().isEmpty() ? null : Double.parseDouble(salarioField.getText());
            
            Cozinheiro cozinheiro = new Cozinheiro(cpf, nome, nomeFantasia, dtContrato, salario);
            
            if (CozinheiroDAO.atualizar(cozinheiro)) {
                JOptionPane.showMessageDialog(this, "Cozinheiro atualizado com sucesso!");
                limparCampos();
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
            long cpf = Long.parseLong(cpfField.getText());
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este cozinheiro?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (CozinheiroDAO.excluir(cpf)) {
                    JOptionPane.showMessageDialog(this, "Cozinheiro excluído com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir cozinheiro.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        handleInsert();
    }
    
    @Override
    protected void handleCancel() {
        limparCampos();
    }
    
    private void limparCampos() {
        cpfField.setText("");
        nomeField.setText("");
        nomeFantasiaField.setText("");
        dataContratoField.setText(LocalDate.now().toString());
        salarioField.setText("");
    }
} 