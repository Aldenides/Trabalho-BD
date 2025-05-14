package model;

import java.sql.Date;

public class Receita {
    private int codReceita;
    private String nome;
    private Date dataCriacao;
    private int tempoPreparo;
    private int rendimento;
    private String modoPreparo;
    private Cozinheiro cozinheiro;
    private Categoria categoria;

    // Construtor, getters e setters
    public Receita(int codReceita, String nome, Date dataCriacao, int tempoPreparo, int rendimento, 
                  String modoPreparo, Cozinheiro cozinheiro, Categoria categoria) {
        this.codReceita = codReceita;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.tempoPreparo = tempoPreparo;
        this.rendimento = rendimento;
        this.modoPreparo = modoPreparo;
        this.cozinheiro = cozinheiro;
        this.categoria = categoria;
    }

    public int getCodReceita() {
        return codReceita;
    }

    public void setCodReceita(int codReceita) {
        this.codReceita = codReceita;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(int tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public int getRendimento() {
        return rendimento;
    }

    public void setRendimento(int rendimento) {
        this.rendimento = rendimento;
    }

    public String getModoPreparo() {
        return modoPreparo;
    }

    public void setModoPreparo(String modoPreparo) {
        this.modoPreparo = modoPreparo;
    }

    public Cozinheiro getCozinheiro() {
        return cozinheiro;
    }

    public void setCozinheiro(Cozinheiro cozinheiro) {
        this.cozinheiro = cozinheiro;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        // Format: "Código: X - Nome da Receita"
        return String.format("Código: %d - %s", codReceita, nome);
    }
} 