package model;

public class Restaurante {
    private int codRestaurante;
    private String nome;
    private String endereco;
    private String telefone;
    private int capacidade;
    private String especialidade;
    
    // Construtor
    public Restaurante(int codRestaurante, String nome, String endereco, String telefone, int capacidade, String especialidade) {
        this.codRestaurante = codRestaurante;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.capacidade = capacidade;
        this.especialidade = especialidade;
    }
    
    // Getters e Setters
    public int getCodRestaurante() {
        return codRestaurante;
    }
    
    public void setCodRestaurante(int codRestaurante) {
        this.codRestaurante = codRestaurante;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public int getCapacidade() {
        return capacidade;
    }
    
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
    
    public String getEspecialidade() {
        return especialidade;
    }
    
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    
    @Override
    public String toString() {
        return String.format("Código: %d - %s", codRestaurante, nome);
    }
} 