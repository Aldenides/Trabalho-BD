package model;

import java.sql.Date;

public class Cozinheiro {
    private String nome;
    private String nomeFantasia;
    private Date dtContrato;

    // Construtor, getters e setters
    public Cozinheiro(String nome, String nomeFantasia, Date dtContrato) {
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
        this.dtContrato = dtContrato;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public Date getDtContrato() {
        return dtContrato;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
}