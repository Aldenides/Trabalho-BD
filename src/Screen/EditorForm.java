package Screen;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class EditorForm extends BaseForm {
    private JTextField nomeField;
    private JTextField dataContratoField;
    
    public EditorForm() {
        super("Cadastro de Editor");
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Nome do Editor
        addFormComponent(createStyledLabel("Nome do Editor:"));
        nomeField = createStyledTextField();
        addFormComponent(nomeField);
        
        // Data de Contrato
        addFormComponent(createStyledLabel("Data de Contrato:"));
        dataContratoField = createStyledTextField();
        dataContratoField.setText(LocalDate.now().toString()); // Data atual como padrão
        addFormComponent(dataContratoField);
    }
} 