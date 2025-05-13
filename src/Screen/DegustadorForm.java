package Screen;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class DegustadorForm extends BaseForm {
    private JTextField nomeField;
    private JTextField dataContratoField;
    
    public DegustadorForm() {
        super("Cadastro de Degustador");
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Nome do Degustador
        addFormComponent(createStyledLabel("Nome do Degustador:"));
        nomeField = createStyledTextField();
        addFormComponent(nomeField);
        
        // Data de Contrato
        addFormComponent(createStyledLabel("Data de Contrato:"));
        dataContratoField = createStyledTextField();
        dataContratoField.setText(LocalDate.now().toString()); // Data atual como padrão
        addFormComponent(dataContratoField);
    }
} 