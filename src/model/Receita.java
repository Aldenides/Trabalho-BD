package model;

import java.sql.Date;

public class Receita {
    private int codReceita;
    private String nomeReceita;
    private Date dataCriacao;
    private String nomeChefe;
    private int codCategoria;

    // Construtor, getters e setters
    public Receita(int codReceita, String nomeReceita, Date dataCriacao, String nomeChefe, int codCategoria) {
        this.codReceita = codReceita;
        this.nomeReceita = nomeReceita;
        this.dataCriacao = dataCriacao;
        this.nomeChefe = nomeChefe;
        this.codCategoria = codCategoria;
    }

    public int getCodReceita() {
        return codReceita;
    }

    public String getNomeReceita() {
        return nomeReceita;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public String getNomeChefe() {
        return nomeChefe;
    }

    public int getCodCategoria() {
        return codCategoria;
    }

    public void setNomeReceita(String nomeReceita) {
        this.nomeReceita = nomeReceita;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setNomeChefe(String nomeChefe) {
        this.nomeChefe = nomeChefe;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }
} 