package Screen;

import javax.swing.*;
import java.awt.*;
import model.Categoria;
import dao.CategoriaDAO;

public class CategoriaForm extends BaseForm {
    private JTextField codCategoriaField;
    private JTextField descricaoField;
    
    public CategoriaForm() {
        super("Cadastro de Categoria");
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Código da Categoria
        addFormComponent(createStyledLabel("Código da Categoria:"));
        codCategoriaField = createStyledTextField();
        addFormComponent(codCategoriaField);
        
        // Descrição da Categoria
        addFormComponent(createStyledLabel("Descrição:"));
        descricaoField = createStyledTextField();
        addFormComponent(descricaoField);
    }
    
    @Override
    protected void handleInsert() {
        try {
            int codCategoria = Integer.parseInt(codCategoriaField.getText());
            String descricao = descricaoField.getText();
            
            Categoria categoria = new Categoria(codCategoria, descricao);
            
            if (CategoriaDAO.inserir(categoria)) {
                JOptionPane.showMessageDialog(this, "Categoria inserida com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            int codCategoria = Integer.parseInt(codCategoriaField.getText());
            Categoria categoria = CategoriaDAO.buscar(codCategoria);
            
            if (categoria != null) {
                descricaoField.setText(categoria.getDescricaoCategoria());
                JOptionPane.showMessageDialog(this, "Categoria encontrada!");
            } else {
                JOptionPane.showMessageDialog(this, "Categoria não encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
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
            int codCategoria = Integer.parseInt(codCategoriaField.getText());
            String descricao = descricaoField.getText();
            
            Categoria categoria = new Categoria(codCategoria, descricao);
            
            if (CategoriaDAO.atualizar(categoria)) {
                JOptionPane.showMessageDialog(this, "Categoria atualizada com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            int codCategoria = Integer.parseInt(codCategoriaField.getText());
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir esta categoria?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (CategoriaDAO.excluir(codCategoria)) {
                    JOptionPane.showMessageDialog(this, "Categoria excluída com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        codCategoriaField.setText("");
        descricaoField.setText("");
    }
} 