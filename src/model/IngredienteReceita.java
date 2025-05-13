package model;

public class IngredienteReceita {
    private Receita receita;
    private Ingrediente ingrediente;
    private double quantidade;
    private String unidadeMedida;
    
    public IngredienteReceita(Receita receita, Ingrediente ingrediente, double quantidade, String unidadeMedida) {
        this.receita = receita;
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
    }
    
    public Receita getReceita() {
        return receita;
    }
    
    public void setReceita(Receita receita) {
        this.receita = receita;
    }
    
    public Ingrediente getIngrediente() {
        return ingrediente;
    }
    
    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }
    
    public double getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
    
    public String getUnidadeMedida() {
        return unidadeMedida;
    }
    
    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
    
    @Override
    public String toString() {
        return ingrediente.getNome() + " - " + quantidade + " " + unidadeMedida;
    }
} 