package Screen;

import javax.swing.*;
import java.awt.*;
import model.IngredienteReceita;
import dao.IngredienteReceitaDAO;

public class IngredienteReceitaForm extends BaseForm {
    private JTextField codReceitaField;
    private JTextField nomeFantasiaCoField;
    private JTextField codIngredientesField;
    private JTextField quantidadeIngredientettField;
    private JTextField meidaField;
    
    public IngredienteReceitaForm() {
        super("Cadastro de Ingrediente da Receita");
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Código da Receita
        codReceitaField = createStyledTextField();
        addLabelAndField("Código da Receita:", codReceitaField);
        
        // Nome Fantasia do Cozinheiro
        nomeFantasiaCoField = createStyledTextField();
        addLabelAndField("Nome Fantasia do Cozinheiro:", nomeFantasiaCoField);
        
        // Código do Ingrediente
        codIngredientesField = createStyledTextField();
        addLabelAndField("Código do Ingrediente:", codIngredientesField);
        
        // Quantidade do Ingrediente
        quantidadeIngredientettField = createStyledTextField();
        addLabelAndField("Quantidade do Ingrediente:", quantidadeIngredientettField);
        
        // Medida
        meidaField = createStyledTextField();
        addLabelAndField("Medida:", meidaField);
    }
    
    @Override
    protected void handleInsert() {
        try {
            int codReceita = Integer.parseInt(codReceitaField.getText());
            String nomeFantasiaCo = nomeFantasiaCoField.getText();
            int codIngredientes = Integer.parseInt(codIngredientesField.getText());
            double quantidadeIngredientett = Double.parseDouble(quantidadeIngredientettField.getText());
            String meida = meidaField.getText();
            
            IngredienteReceita ingredienteReceita = new IngredienteReceita(
                codReceita, nomeFantasiaCo, codIngredientes, quantidadeIngredientett, meida);
            
            if (IngredienteReceitaDAO.inserir(ingredienteReceita)) {
                JOptionPane.showMessageDialog(this, "Ingrediente da receita inserido com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir ingrediente da receita.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            int codReceita = Integer.parseInt(codReceitaField.getText());
            String nomeFantasiaCo = nomeFantasiaCoField.getText();
            int codIngredientes = Integer.parseInt(codIngredientesField.getText());
            
            IngredienteReceita ingredienteReceita = IngredienteReceitaDAO.buscar(
                codReceita, nomeFantasiaCo, codIngredientes);
            
            if (ingredienteReceita != null) {
                quantidadeIngredientettField.setText(String.valueOf(ingredienteReceita.getQuantidadeIngredientett()));
                meidaField.setText(ingredienteReceita.getMeida());
                JOptionPane.showMessageDialog(this, "Ingrediente da receita encontrado!");
            } else {
                JOptionPane.showMessageDialog(this, "Ingrediente da receita não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha os campos numéricos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleUpdate() {
        try {
            int codReceita = Integer.parseInt(codReceitaField.getText());
            String nomeFantasiaCo = nomeFantasiaCoField.getText();
            int codIngredientes = Integer.parseInt(codIngredientesField.getText());
            double quantidadeIngredientett = Double.parseDouble(quantidadeIngredientettField.getText());
            String meida = meidaField.getText();
            
            IngredienteReceita ingredienteReceita = new IngredienteReceita(
                codReceita, nomeFantasiaCo, codIngredientes, quantidadeIngredientett, meida);
            
            if (IngredienteReceitaDAO.atualizar(ingredienteReceita)) {
                JOptionPane.showMessageDialog(this, "Ingrediente da receita atualizado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar ingrediente da receita.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            int codReceita = Integer.parseInt(codReceitaField.getText());
            String nomeFantasiaCo = nomeFantasiaCoField.getText();
            int codIngredientes = Integer.parseInt(codIngredientesField.getText());
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este ingrediente da receita?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (IngredienteReceitaDAO.excluir(codReceita, nomeFantasiaCo, codIngredientes)) {
                    JOptionPane.showMessageDialog(this, "Ingrediente da receita excluído com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir ingrediente da receita.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha os campos numéricos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        codReceitaField.setText("");
        nomeFantasiaCoField.setText("");
        codIngredientesField.setText("");
        quantidadeIngredientettField.setText("");
        meidaField.setText("");
    }
} 