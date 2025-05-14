package model;

public class Ingrediente {
    private int codIngrediente;
    private String nome;
    private String descricao;

    // Construtor, getters e setters
    public Ingrediente(int codIngrediente, String nome, String descricao) {
        this.codIngrediente = codIngrediente;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getCodIngrediente() {
        return codIngrediente;
    }

    public void setCodIngrediente(int codIngrediente) {
        this.codIngrediente = codIngrediente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        // Format: "Código: X - Nome do Ingrediente"
        return String.format("Código: %d - %s", codIngrediente, nome);
    }
} 