package model;

public class IngredienteReceita {
    private int codReceita;
    private String nomeFantasiaCo;
    private int codIngredientes;
    private double quantidadeIngredientett;
    private String meida;

    // Construtor, getters e setters
    public IngredienteReceita(int codReceita, String nomeFantasiaCo, int codIngredientes, 
                              double quantidadeIngredientett, String meida) {
        this.codReceita = codReceita;
        this.nomeFantasiaCo = nomeFantasiaCo;
        this.codIngredientes = codIngredientes;
        this.quantidadeIngredientett = quantidadeIngredientett;
        this.meida = meida;
    }

    public int getCodReceita() {
        return codReceita;
    }

    public String getNomeFantasiaCo() {
        return nomeFantasiaCo;
    }

    public int getCodIngredientes() {
        return codIngredientes;
    }

    public double getQuantidadeIngredientett() {
        return quantidadeIngredientett;
    }

    public String getMeida() {
        return meida;
    }

    public void setQuantidadeIngredientett(double quantidadeIngredientett) {
        this.quantidadeIngredientett = quantidadeIngredientett;
    }

    public void setMeida(String meida) {
        this.meida = meida;
    }
} 