package model;

public class Ingrediente {
    private int codIngrediente;
    private String nomeIngrediente;

    // Construtor, getters e setters
    public Ingrediente(int codIngrediente, String nomeIngrediente) {
        this.codIngrediente = codIngrediente;
        this.nomeIngrediente = nomeIngrediente;
    }

    public int getCodIngrediente() {
        return codIngrediente;
    }

    public String getNomeIngrediente() {
        return nomeIngrediente;
    }

    public void setNomeIngrediente(String nomeIngrediente) {
        this.nomeIngrediente = nomeIngrediente;
    }
} 