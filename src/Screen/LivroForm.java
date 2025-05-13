package Screen;

import javax.swing.*;
import java.awt.*;

public class LivroForm extends BaseForm {
    private JTextField tituloField;
    private JTextField isbnField;
    
    public LivroForm() {
        super("Cadastro de Livro");
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Título
        tituloField = createStyledTextField();
        addLabelAndField("Título do Livro:", tituloField);
        
        // ISBN
        isbnField = createStyledTextField();
        addLabelAndField("ISBN:", isbnField);
    }
} 