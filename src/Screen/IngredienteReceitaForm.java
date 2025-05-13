package Screen;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.IngredienteReceita;
import model.Receita;
import model.Ingrediente;
import dao.IngredienteReceitaDAO;
import dao.ReceitaDAO;
import dao.IngredienteDAO;

public class IngredienteReceitaForm extends BaseForm {
    private JComboBox<Receita> receitaComboBox;
    private JComboBox<Ingrediente> ingredienteComboBox;
    private JTextField quantidadeField;
    private JTextField unidadeMedidaField;
    
    public IngredienteReceitaForm() {
        super("Cadastro de Ingrediente da Receita");
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Receita
        receitaComboBox = new JComboBox<>();
        carregarReceitas();
        addLabelAndField("Receita:", receitaComboBox);
        
        // Ingrediente
        ingredienteComboBox = new JComboBox<>();
        carregarIngredientes();
        addLabelAndField("Ingrediente:", ingredienteComboBox);
        
        // Quantidade
        quantidadeField = createStyledTextField();
        addLabelAndField("Quantidade:", quantidadeField);
        
        // Unidade de Medida
        unidadeMedidaField = createStyledTextField();
        addLabelAndField("Unidade de Medida:", unidadeMedidaField);
    }
    
    private void carregarReceitas() {
        List<Receita> receitas = ReceitaDAO.listarTodas();
        DefaultComboBoxModel<Receita> model = new DefaultComboBoxModel<>();
        for (Receita receita : receitas) {
            model.addElement(receita);
        }
        receitaComboBox.setModel(model);
    }
    
    private void carregarIngredientes() {
        List<Ingrediente> ingredientes = IngredienteDAO.listarTodos();
        DefaultComboBoxModel<Ingrediente> model = new DefaultComboBoxModel<>();
        for (Ingrediente ingrediente : ingredientes) {
            model.addElement(ingrediente);
        }
        ingredienteComboBox.setModel(model);
    }
    
    @Override
    protected void handleInsert() {
        try {
            Receita receita = (Receita) receitaComboBox.getSelectedItem();
            Ingrediente ingrediente = (Ingrediente) ingredienteComboBox.getSelectedItem();
            double quantidade = Double.parseDouble(quantidadeField.getText());
            String unidadeMedida = unidadeMedidaField.getText();
            
            if (receita == null || ingrediente == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione uma receita e um ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            IngredienteReceita ingredienteReceita = new IngredienteReceita(
                receita, ingrediente, quantidade, unidadeMedida);
            
            if (IngredienteReceitaDAO.inserir(ingredienteReceita)) {
                JOptionPane.showMessageDialog(this, "Ingrediente da receita inserido com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir ingrediente da receita.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira uma quantidade válida.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleSelect() {
        try {
            Receita receitaSelecionada = (Receita) receitaComboBox.getSelectedItem();
            Ingrediente ingredienteSelecionado = (Ingrediente) ingredienteComboBox.getSelectedItem();
            
            if (receitaSelecionada == null || ingredienteSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione uma receita e um ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            IngredienteReceita ingredienteReceita = IngredienteReceitaDAO.buscar(
                receitaSelecionada.getCodReceita(), ingredienteSelecionado.getCodIngrediente());
            
            if (ingredienteReceita != null) {
                quantidadeField.setText(String.valueOf(ingredienteReceita.getQuantidade()));
                unidadeMedidaField.setText(ingredienteReceita.getUnidadeMedida());
                
                // Selecionar a receita e o ingrediente nos comboboxes
                for (int i = 0; i < receitaComboBox.getItemCount(); i++) {
                    Receita receita = receitaComboBox.getItemAt(i);
                    if (receita.getCodReceita() == ingredienteReceita.getReceita().getCodReceita()) {
                        receitaComboBox.setSelectedIndex(i);
                        break;
                    }
                }
                
                for (int i = 0; i < ingredienteComboBox.getItemCount(); i++) {
                    Ingrediente ingrediente = ingredienteComboBox.getItemAt(i);
                    if (ingrediente.getCodIngrediente() == ingredienteReceita.getIngrediente().getCodIngrediente()) {
                        ingredienteComboBox.setSelectedIndex(i);
                        break;
                    }
                }
                
                JOptionPane.showMessageDialog(this, "Ingrediente da receita encontrado!");
            } else {
                JOptionPane.showMessageDialog(this, "Ingrediente da receita não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleUpdate() {
        try {
            Receita receita = (Receita) receitaComboBox.getSelectedItem();
            Ingrediente ingrediente = (Ingrediente) ingredienteComboBox.getSelectedItem();
            double quantidade = Double.parseDouble(quantidadeField.getText());
            String unidadeMedida = unidadeMedidaField.getText();
            
            if (receita == null || ingrediente == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione uma receita e um ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            IngredienteReceita ingredienteReceita = new IngredienteReceita(
                receita, ingrediente, quantidade, unidadeMedida);
            
            if (IngredienteReceitaDAO.atualizar(ingredienteReceita)) {
                JOptionPane.showMessageDialog(this, "Ingrediente da receita atualizado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar ingrediente da receita.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira uma quantidade válida.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleDelete() {
        try {
            Receita receita = (Receita) receitaComboBox.getSelectedItem();
            Ingrediente ingrediente = (Ingrediente) ingredienteComboBox.getSelectedItem();
            
            if (receita == null || ingrediente == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione uma receita e um ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este ingrediente da receita?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (IngredienteReceitaDAO.excluir(receita.getCodReceita(), ingrediente.getCodIngrediente())) {
                    JOptionPane.showMessageDialog(this, "Ingrediente da receita excluído com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir ingrediente da receita.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        if (receitaComboBox.getItemCount() > 0) {
            receitaComboBox.setSelectedIndex(0);
        }
        if (ingredienteComboBox.getItemCount() > 0) {
            ingredienteComboBox.setSelectedIndex(0);
        }
        quantidadeField.setText("");
        unidadeMedidaField.setText("");
    }
} 