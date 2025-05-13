package Screen;

import javax.swing.*;
import java.awt.*;
import model.Categoria;
import dao.CategoriaDAO;

public class CategoriaForm extends BaseForm {
    private JTextField nomeField;
    private JTextField descricaoField;
    private String nomeAntigoParaAtualizacao;
    
    public CategoriaForm() {
        super("Cadastro de Categoria");
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Nome da Categoria
        nomeField = createStyledTextField();
        addLabelAndField("Nome da Categoria:", nomeField);
        
        // Descrição da Categoria
        descricaoField = createStyledTextField();
        addLabelAndField("Descrição:", descricaoField);
    }
    
    @Override
    protected void handleInsert() {
        try {
            String nome = nomeField.getText();
            String descricao = descricaoField.getText();
            
            Categoria categoria = new Categoria(nome, descricao);
            
            if (CategoriaDAO.inserir(categoria)) {
                JOptionPane.showMessageDialog(this, "Categoria inserida com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleSelect() {
        try {
            String nome = nomeField.getText();
            Categoria categoria = CategoriaDAO.buscarPorNome(nome);
            
            if (categoria != null) {
                nomeField.setText(categoria.getNome());
                descricaoField.setText(categoria.getDescricao());
                nomeAntigoParaAtualizacao = categoria.getNome();
                JOptionPane.showMessageDialog(this, "Categoria encontrada!");
            } else {
                JOptionPane.showMessageDialog(this, "Categoria não encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleUpdate() {
        try {
            String nome = nomeField.getText();
            String descricao = descricaoField.getText();
            
            Categoria categoria = new Categoria(nome, descricao);
            
            if (nomeAntigoParaAtualizacao != null && CategoriaDAO.atualizar(categoria, nomeAntigoParaAtualizacao)) {
                JOptionPane.showMessageDialog(this, "Categoria atualizada com sucesso!");
                limparCampos();
                nomeAntigoParaAtualizacao = null;
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar categoria. Primeiro selecione uma categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleDelete() {
        try {
            String nome = nomeField.getText();
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir esta categoria?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (CategoriaDAO.excluir(nome)) {
                    JOptionPane.showMessageDialog(this, "Categoria excluída com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        descricaoField.setText("");
        nomeAntigoParaAtualizacao = null;
    }
} 