package Screen;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import model.Cozinheiro;
import dao.CozinheiroDAO;

public class CozinheiroForm extends BaseForm {
    private JTextField nomeField;
    private JTextField nomeFantasiaField;
    private JTextField dataContratoField;
    
    public CozinheiroForm() {
        super("Cadastro de Cozinheiro");
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Nome do Cozinheiro
        addFormComponent(createStyledLabel("Nome do Cozinheiro:"));
        nomeField = createStyledTextField();
        addFormComponent(nomeField);
        
        // Nome Fantasia
        addFormComponent(createStyledLabel("Nome Fantasia:"));
        nomeFantasiaField = createStyledTextField();
        addFormComponent(nomeFantasiaField);
        
        // Data de Contrato
        addFormComponent(createStyledLabel("Data de Contrato:"));
        dataContratoField = createStyledTextField();
        dataContratoField.setText(LocalDate.now().toString()); // Data atual como padrão
        addFormComponent(dataContratoField);
    }
    
    @Override
    protected void handleInsert() {
        try {
            String nome = nomeField.getText();
            String nomeFantasia = nomeFantasiaField.getText();
            Date dtContrato = Date.valueOf(dataContratoField.getText());
            
            Cozinheiro cozinheiro = new Cozinheiro(nome, nomeFantasia, dtContrato);
            
            if (CozinheiroDAO.inserir(cozinheiro)) {
                JOptionPane.showMessageDialog(this, "Cozinheiro inserido com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir cozinheiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleSelect() {
        try {
            String nomeFantasia = nomeFantasiaField.getText();
            Cozinheiro cozinheiro = CozinheiroDAO.buscarPorNomeFantasia(nomeFantasia);
            
            if (cozinheiro != null) {
                nomeField.setText(cozinheiro.getNome());
                dataContratoField.setText(cozinheiro.getDtContrato().toString());
                JOptionPane.showMessageDialog(this, "Cozinheiro encontrado!");
            } else {
                JOptionPane.showMessageDialog(this, "Cozinheiro não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleUpdate() {
        try {
            String nome = nomeField.getText();
            String nomeFantasia = nomeFantasiaField.getText();
            Date dtContrato = Date.valueOf(dataContratoField.getText());
            
            Cozinheiro cozinheiro = new Cozinheiro(nome, nomeFantasia, dtContrato);
            
            if (CozinheiroDAO.atualizar(cozinheiro)) {
                JOptionPane.showMessageDialog(this, "Cozinheiro atualizado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar cozinheiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleDelete() {
        try {
            String nomeFantasia = nomeFantasiaField.getText();
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este cozinheiro?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (CozinheiroDAO.excluirPorNomeFantasia(nomeFantasia)) {
                    JOptionPane.showMessageDialog(this, "Cozinheiro excluído com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir cozinheiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
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
        nomeField.setText("");
        nomeFantasiaField.setText("");
        dataContratoField.setText(LocalDate.now().toString());
    }
} 