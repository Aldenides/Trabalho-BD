package Screen;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Livro;
import dao.LivroDAO;

public class LivroForm extends BaseForm {
    private JTextField isbnField;
    private JTextField tituloField;
    private JList<Livro> livrosList;
    private DefaultListModel<Livro> listModel;
    
    public LivroForm() {
        super("Cadastro de Livro");
        setSize(900, 650);  // Tamanho adequado da janela
        initializeComponents();
        carregarLivros();
    }
    
    private void initializeComponents() {
        // Painel esquerdo - Formulário
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(236, 240, 241));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        
        // ISBN
        JPanel isbnPanel = new JPanel(new GridBagLayout());
        isbnPanel.setBackground(new Color(236, 240, 241));
        
        GridBagConstraints panelGbc = new GridBagConstraints();
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        panelGbc.anchor = GridBagConstraints.WEST;
        panelGbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel isbnLabel = createStyledLabel("ISBN:");
        isbnLabel.setPreferredSize(new Dimension(180, 25));
        isbnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        isbnPanel.add(isbnLabel, panelGbc);
        
        panelGbc.gridx = 1;
        isbnField = createStyledTextField();
        isbnField.setPreferredSize(new Dimension(250, 30));
        isbnPanel.add(isbnField, panelGbc);
        
        leftPanel.add(isbnPanel, gbc);
        
        // Título do Livro
        JPanel tituloPanel = new JPanel(new GridBagLayout());
        tituloPanel.setBackground(new Color(236, 240, 241));
        
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        JLabel tituloLabel = createStyledLabel("Título do Livro:");
        tituloLabel.setPreferredSize(new Dimension(180, 25));
        tituloLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        tituloPanel.add(tituloLabel, panelGbc);
        
        panelGbc.gridx = 1;
        tituloField = createStyledTextField();
        tituloField.setPreferredSize(new Dimension(250, 30));
        tituloPanel.add(tituloField, panelGbc);
        
        leftPanel.add(tituloPanel, gbc);
        
        // Painel direito - Lista de livros
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(236, 240, 241));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel listLabel = new JLabel("Livros Cadastrados:");
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listLabel.setForeground(new Color(44, 62, 80));
        rightPanel.add(listLabel, BorderLayout.NORTH);
        
        listModel = new DefaultListModel<>();
        livrosList = new JList<>(listModel);
        livrosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        livrosList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        livrosList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Livro selectedLivro = livrosList.getSelectedValue();
                if (selectedLivro != null) {
                    exibirLivroSelecionado(selectedLivro);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(livrosList);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Adicionar painéis ao painel principal
        JPanel mainContentPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        mainContentPanel.setBackground(new Color(236, 240, 241));
        mainContentPanel.add(leftPanel);
        mainContentPanel.add(rightPanel);
        
        formPanel.setLayout(new BorderLayout());
        formPanel.add(mainContentPanel, BorderLayout.CENTER);
    }
    
    private void carregarLivros() {
        listModel.clear();
        List<Livro> livros = LivroDAO.listarTodos();
        for (Livro livro : livros) {
            listModel.addElement(livro);
        }
    }
    
    private void exibirLivroSelecionado(Livro livro) {
        isbnField.setText(String.valueOf(livro.getIsbn()));
        tituloField.setText(livro.getTitulo());
    }
    
    private void gerarProximoISBN() {
        int proximoISBN = LivroDAO.gerarProximoISBN();
        isbnField.setText(String.valueOf(proximoISBN));
        tituloField.setText("");
        livrosList.clearSelection();
    }
    
    @Override
    protected void handleInsert() {
        try {
            // Verificar se os campos já estão preenchidos
            if (tituloField.getText().isEmpty()) {
                // Se o título está vazio, apenas gerar um novo ISBN e preparar o formulário
                gerarProximoISBN();
                JOptionPane.showMessageDialog(this, "Digite o título do livro e pressione 'Salvar' para confirmar.");
                return;
            }
            
            // Se chegou aqui, é porque os campos já estão preenchidos, então podemos tentar inserir
            int isbn = Integer.parseInt(isbnField.getText());
            String titulo = tituloField.getText();
            
            // Verificar se o ISBN já existe
            Livro existente = LivroDAO.buscar(isbn);
            if (existente != null) {
                JOptionPane.showMessageDialog(this, 
                    "Já existe um livro com o ISBN " + isbn + ".\n" +
                    "Um novo ISBN será gerado automaticamente.", 
                    "ISBN duplicado", JOptionPane.WARNING_MESSAGE);
                gerarProximoISBN();
                return;
            }
            
            Livro livro = new Livro(isbn, titulo);
            
            if (LivroDAO.inserir(livro)) {
                JOptionPane.showMessageDialog(this, "Livro inserido com sucesso!");
                limparCampos();
                carregarLivros();
                // Preparar para um novo livro
                gerarProximoISBN();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir livro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ISBN válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleSelect() {
        try {
            int isbn = Integer.parseInt(isbnField.getText());
            Livro livro = LivroDAO.buscar(isbn);
            
            if (livro != null) {
                tituloField.setText(livro.getTitulo());
                
                // Selecionar na lista
                for (int i = 0; i < listModel.getSize(); i++) {
                    if (listModel.getElementAt(i).getIsbn() == isbn) {
                        livrosList.setSelectedIndex(i);
                        livrosList.ensureIndexIsVisible(i);
                        break;
                    }
                }
                
                JOptionPane.showMessageDialog(this, "Livro encontrado!");
            } else {
                JOptionPane.showMessageDialog(this, "Livro não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ISBN válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleUpdate() {
        try {
            int isbn = Integer.parseInt(isbnField.getText());
            String titulo = tituloField.getText();
            
            // Validações
            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um título para o livro.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se o livro existe
            Livro existente = LivroDAO.buscar(isbn);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, 
                    "Não existe um livro com o ISBN " + isbn + ".\n" +
                    "Utilize o botão 'Consultar' para encontrar um livro existente.", 
                    "Registro inexistente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Livro livro = new Livro(isbn, titulo);
            
            if (LivroDAO.atualizar(livro)) {
                JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!");
                limparCampos();
                carregarLivros();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar livro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ISBN válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleDelete() {
        try {
            int isbn = Integer.parseInt(isbnField.getText());
            
            // Verificar se o livro existe
            Livro existente = LivroDAO.buscar(isbn);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, 
                    "Não existe um livro com o ISBN " + isbn + ".", 
                    "Registro inexistente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o livro: " + existente.getTitulo() + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (LivroDAO.excluir(isbn)) {
                    JOptionPane.showMessageDialog(this, "Livro excluído com sucesso!");
                    limparCampos();
                    carregarLivros();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir livro. Verifique se ele não está sendo usado em outras partes do sistema.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ISBN válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleSave() {
        try {
            String titulo = tituloField.getText();
            int isbn = Integer.parseInt(isbnField.getText());
            
            // Validações
            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um título para o livro.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se o ISBN já existe
            Livro existente = LivroDAO.buscar(isbn);
            if (existente != null) {
                // Se estamos tentando atualizar um existente
                int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Já existe um livro com este ISBN. Deseja atualizá-lo?",
                    "Confirmar atualização",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirmacao == JOptionPane.YES_OPTION) {
                    handleUpdate();
                    return;
                } else {
                    gerarProximoISBN();
                    return;
                }
            }
            
            Livro livro = new Livro(isbn, titulo);
            
            if (LivroDAO.inserir(livro)) {
                JOptionPane.showMessageDialog(this, "Livro salvo com sucesso!");
                limparCampos();
                carregarLivros();
                // Preparar para um novo livro
                gerarProximoISBN();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar livro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ISBN válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void handleCancel() {
        limparCampos();
        livrosList.clearSelection();
    }
    
    private void limparCampos() {
        isbnField.setText("");
        tituloField.setText("");
        livrosList.clearSelection();
    }
} 