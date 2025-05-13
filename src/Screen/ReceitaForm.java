package Screen;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import model.Receita;
import dao.ReceitaDAO;

public class ReceitaForm extends BaseForm {
    private JTextField codReceitaField;
    private JTextField nomeReceitaField;
    private JTextField dataCriacaoField;
    private JTextField nomeChefField;
    private JTextField codCategoriaField;
    
    public ReceitaForm() {
        super("Cadastro de Receita");
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Código da Receita
        codReceitaField = createStyledTextField();
        addLabelAndField("Código da Receita:", codReceitaField);
        
        // Nome da Receita
        nomeReceitaField = createStyledTextField();
        addLabelAndField("Nome da Receita:", nomeReceitaField);
        
        // Data de Criação
        dataCriacaoField = createStyledTextField();
        dataCriacaoField.setText(LocalDate.now().toString()); // Data atual como padrão
        addLabelAndField("Data de Criação:", dataCriacaoField);
        
        // Nome do Chefe
        nomeChefField = createStyledTextField();
        addLabelAndField("Nome do Chefe:", nomeChefField);
        
        // Código da Categoria
        codCategoriaField = createStyledTextField();
        addLabelAndField("Código da Categoria:", codCategoriaField);
    }
    
    @Override
    protected void handleInsert() {
        try {
            int codReceita = Integer.parseInt(codReceitaField.getText());
            String nomeReceita = nomeReceitaField.getText();
            Date dataCriacao = Date.valueOf(dataCriacaoField.getText());
            String nomeChef = nomeChefField.getText();
            int codCategoria = Integer.parseInt(codCategoriaField.getText());
            
            Receita receita = new Receita(codReceita, nomeReceita, dataCriacao, nomeChef, codCategoria);
            
            if (ReceitaDAO.inserir(receita)) {
                JOptionPane.showMessageDialog(this, "Receita inserida com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir receita.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            Receita receita = ReceitaDAO.buscar(codReceita);
            
            if (receita != null) {
                nomeReceitaField.setText(receita.getNomeReceita());
                dataCriacaoField.setText(receita.getDataCriacao().toString());
                nomeChefField.setText(receita.getNomeChefe());
                codCategoriaField.setText(String.valueOf(receita.getCodCategoria()));
                JOptionPane.showMessageDialog(this, "Receita encontrada!");
            } else {
                JOptionPane.showMessageDialog(this, "Receita não encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
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
            int codReceita = Integer.parseInt(codReceitaField.getText());
            String nomeReceita = nomeReceitaField.getText();
            Date dataCriacao = Date.valueOf(dataCriacaoField.getText());
            String nomeChef = nomeChefField.getText();
            int codCategoria = Integer.parseInt(codCategoriaField.getText());
            
            Receita receita = new Receita(codReceita, nomeReceita, dataCriacao, nomeChef, codCategoria);
            
            if (ReceitaDAO.atualizar(receita)) {
                JOptionPane.showMessageDialog(this, "Receita atualizada com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar receita.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir esta receita?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (ReceitaDAO.excluir(codReceita)) {
                    JOptionPane.showMessageDialog(this, "Receita excluída com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir receita.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        codReceitaField.setText("");
        nomeReceitaField.setText("");
        dataCriacaoField.setText(LocalDate.now().toString());
        nomeChefField.setText("");
        codCategoriaField.setText("");
    }
} 