package model;

import java.sql.Date;

public class Cozinheiro {
    private long cpf;
    private String nome;
    private String nomeFantasia;
    private Date dtContrato;
    private Double salario;

    // Construtor, getters e setters
    public Cozinheiro(long cpf, String nome, String nomeFantasia, Date dtContrato, Double salario) {
        this.cpf = cpf;
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
        this.dtContrato = dtContrato;
        this.salario = salario;
    }
    
    public long getCpf() {
        return cpf;
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

    public Double getSalario() {
        return salario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

}