package model;

public class Categoria {
    private int codCategoria;
    private String descricaoCategoria;

    // Construtor, getters e setters
    public Categoria(int codCategoria, String descricaoCategoria) {
        this.codCategoria = codCategoria;
        this.descricaoCategoria = descricaoCategoria;
    }

    public int getCodCategoria() {
        return codCategoria;
    }

    public String getDescricaoCategoria() {
        return descricaoCategoria;
    }

    public void setDescricaoCategoria(String descricaoCategoria) {
        this.descricaoCategoria = descricaoCategoria;
    }
} 