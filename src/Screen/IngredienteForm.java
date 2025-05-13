package Screen;

import javax.swing.*;
import java.awt.*;
import model.Ingrediente;
import dao.IngredienteDAO;

public class IngredienteForm extends BaseForm {
    private JTextField codIngredienteField;
    private JTextField nomeField;
    private JTextField descricaoField;
    
    public IngredienteForm() {
        super("Cadastro de Ingrediente");
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Código do Ingrediente
        codIngredienteField = createStyledTextField();
        addLabelAndField("Código do Ingrediente:", codIngredienteField);
        
        // Nome do Ingrediente
        nomeField = createStyledTextField();
        addLabelAndField("Nome do Ingrediente:", nomeField);
        
        // Descrição do Ingrediente
        descricaoField = createStyledTextField();
        addLabelAndField("Descrição do Ingrediente:", descricaoField);
    }
    
    @Override
    protected void handleInsert() {
        try {
            int codIngrediente = Integer.parseInt(codIngredienteField.getText());
            String nome = nomeField.getText();
            String descricao = descricaoField.getText();
            
            Ingrediente ingrediente = new Ingrediente(codIngrediente, nome, descricao);
            
            if (IngredienteDAO.inserir(ingrediente)) {
                JOptionPane.showMessageDialog(this, "Ingrediente inserido com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um código válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleSelect() {
        try {
            int codIngrediente = Integer.parseInt(codIngredienteField.getText());
            Ingrediente ingrediente = IngredienteDAO.buscar(codIngrediente);
            
            if (ingrediente != null) {
                nomeField.setText(ingrediente.getNome());
                descricaoField.setText(ingrediente.getDescricao());
                JOptionPane.showMessageDialog(this, "Ingrediente encontrado!");
            } else {
                JOptionPane.showMessageDialog(this, "Ingrediente não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um código válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleUpdate() {
        try {
            int codIngrediente = Integer.parseInt(codIngredienteField.getText());
            String nome = nomeField.getText();
            String descricao = descricaoField.getText();
            
            Ingrediente ingrediente = new Ingrediente(codIngrediente, nome, descricao);
            
            if (IngredienteDAO.atualizar(ingrediente)) {
                JOptionPane.showMessageDialog(this, "Ingrediente atualizado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um código válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleDelete() {
        try {
            int codIngrediente = Integer.parseInt(codIngredienteField.getText());
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este ingrediente?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (IngredienteDAO.excluir(codIngrediente)) {
                    JOptionPane.showMessageDialog(this, "Ingrediente excluído com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um código válido.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        codIngredienteField.setText("");
        nomeField.setText("");
        descricaoField.setText("");
    }
} 