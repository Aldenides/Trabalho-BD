package Screen;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import model.Receita;
import model.Cozinheiro;
import model.Categoria;
import dao.ReceitaDAO;
import dao.CozinheiroDAO;
import dao.CategoriaDAO;

public class ReceitaForm extends BaseForm {
    private JTextField codReceitaField;
    private JTextField nomeField;
    private JTextField dataCriacaoField;
    private JTextField tempoPreparoField;
    private JTextField rendimentoField;
    private JTextArea modoPreparoArea;
    private JComboBox<Cozinheiro> cozinheiroComboBox;
    private JComboBox<Categoria> categoriaComboBox;
    
    public ReceitaForm() {
        super("Cadastro de Receita");
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Código da Receita
        codReceitaField = createStyledTextField();
        addLabelAndField("Código da Receita:", codReceitaField);
        
        // Nome da Receita
        nomeField = createStyledTextField();
        addLabelAndField("Nome da Receita:", nomeField);
        
        // Data de Criação
        dataCriacaoField = createStyledTextField();
        dataCriacaoField.setText(LocalDate.now().toString()); // Data atual como padrão
        addLabelAndField("Data de Criação:", dataCriacaoField);
        
        // Tempo de Preparo
        tempoPreparoField = createStyledTextField();
        addLabelAndField("Tempo de Preparo (min):", tempoPreparoField);
        
        // Rendimento
        rendimentoField = createStyledTextField();
        addLabelAndField("Rendimento (porções):", rendimentoField);
        
        // Modo de Preparo
        modoPreparoArea = new JTextArea(5, 30);
        modoPreparoArea.setLineWrap(true);
        modoPreparoArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(modoPreparoArea);
        JPanel modoPreparoPanel = new JPanel(new BorderLayout());
        modoPreparoPanel.add(new JLabel("Modo de Preparo:"), BorderLayout.NORTH);
        modoPreparoPanel.add(scrollPane, BorderLayout.CENTER);
        addFormComponent(modoPreparoPanel);
        
        // Cozinheiro
        cozinheiroComboBox = new JComboBox<>();
        carregarCozinheiros();
        addLabelAndField("Cozinheiro:", cozinheiroComboBox);
        
        // Categoria
        categoriaComboBox = new JComboBox<>();
        carregarCategorias();
        addLabelAndField("Categoria:", categoriaComboBox);
    }
    
    private void carregarCozinheiros() {
        List<Cozinheiro> cozinheiros = CozinheiroDAO.listarTodos();
        DefaultComboBoxModel<Cozinheiro> model = new DefaultComboBoxModel<>();
        for (Cozinheiro cozinheiro : cozinheiros) {
            model.addElement(cozinheiro);
        }
        cozinheiroComboBox.setModel(model);
    }
    
    private void carregarCategorias() {
        List<Categoria> categorias = CategoriaDAO.listarTodas();
        DefaultComboBoxModel<Categoria> model = new DefaultComboBoxModel<>();
        for (Categoria categoria : categorias) {
            model.addElement(categoria);
        }
        categoriaComboBox.setModel(model);
    }
    
    @Override
    protected void handleInsert() {
        try {
            int codReceita = Integer.parseInt(codReceitaField.getText());
            String nome = nomeField.getText();
            Date dataCriacao = Date.valueOf(dataCriacaoField.getText());
            int tempoPreparo = Integer.parseInt(tempoPreparoField.getText());
            int rendimento = Integer.parseInt(rendimentoField.getText());
            String modoPreparo = modoPreparoArea.getText();
            Cozinheiro cozinheiro = (Cozinheiro) cozinheiroComboBox.getSelectedItem();
            Categoria categoria = (Categoria) categoriaComboBox.getSelectedItem();
            
            if (cozinheiro == null || categoria == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um cozinheiro e uma categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Receita receita = new Receita(codReceita, nome, dataCriacao, tempoPreparo, rendimento, modoPreparo, cozinheiro, categoria);
            
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
                nomeField.setText(receita.getNome());
                dataCriacaoField.setText(receita.getDataCriacao().toString());
                tempoPreparoField.setText(String.valueOf(receita.getTempoPreparo()));
                rendimentoField.setText(String.valueOf(receita.getRendimento()));
                modoPreparoArea.setText(receita.getModoPreparo());
                
                // Selecionar o cozinheiro no combobox
                for (int i = 0; i < cozinheiroComboBox.getItemCount(); i++) {
                    Cozinheiro cozinheiro = cozinheiroComboBox.getItemAt(i);
                    if (cozinheiro.getCpf() == receita.getCozinheiro().getCpf()) {
                        cozinheiroComboBox.setSelectedIndex(i);
                        break;
                    }
                }
                
                // Selecionar a categoria no combobox
                for (int i = 0; i < categoriaComboBox.getItemCount(); i++) {
                    Categoria categoria = categoriaComboBox.getItemAt(i);
                    if (categoria.getNome().equals(receita.getCategoria().getNome())) {
                        categoriaComboBox.setSelectedIndex(i);
                        break;
                    }
                }
                
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
            String nome = nomeField.getText();
            Date dataCriacao = Date.valueOf(dataCriacaoField.getText());
            int tempoPreparo = Integer.parseInt(tempoPreparoField.getText());
            int rendimento = Integer.parseInt(rendimentoField.getText());
            String modoPreparo = modoPreparoArea.getText();
            Cozinheiro cozinheiro = (Cozinheiro) cozinheiroComboBox.getSelectedItem();
            Categoria categoria = (Categoria) categoriaComboBox.getSelectedItem();
            
            if (cozinheiro == null || categoria == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um cozinheiro e uma categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Receita receita = new Receita(codReceita, nome, dataCriacao, tempoPreparo, rendimento, modoPreparo, cozinheiro, categoria);
            
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
        nomeField.setText("");
        dataCriacaoField.setText(LocalDate.now().toString());
        tempoPreparoField.setText("");
        rendimentoField.setText("");
        modoPreparoArea.setText("");
        if (cozinheiroComboBox.getItemCount() > 0) {
            cozinheiroComboBox.setSelectedIndex(0);
        }
        if (categoriaComboBox.getItemCount() > 0) {
            categoriaComboBox.setSelectedIndex(0);
        }
    }
} 