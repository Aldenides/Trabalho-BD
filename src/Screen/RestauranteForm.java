package Screen;

import javax.swing.*;
import java.awt.*;
import model.Restaurante;

public class RestauranteForm extends BaseForm {
    
    public RestauranteForm() {
        super("Cadastro de Restaurante");
        initializeComponents();
    }
    
    private void initializeComponents() {
        addFormComponent(createStyledLabel("Campos vazios"));
    }
    
    @Override
    protected void handleInsert() {
        JOptionPane.showMessageDialog(this, "Função não implementada.");
    }
    
    @Override
    protected void handleSelect() {
        JOptionPane.showMessageDialog(this, "Função não implementada.");
    }
    
    @Override
    protected void handleUpdate() {
        JOptionPane.showMessageDialog(this, "Função não implementada.");
    }
    
    @Override
    protected void handleDelete() {
        JOptionPane.showMessageDialog(this, "Função não implementada.");
    }
    
    @Override
    protected void handleSave() {
        JOptionPane.showMessageDialog(this, "Função não implementada.");
    }
    
    @Override
    protected void handleCancel() {
        JOptionPane.showMessageDialog(this, "Função não implementada.");
    }
} 