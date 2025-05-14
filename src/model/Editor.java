package model;

import java.sql.Date;

public class Editor {
    private long cpf;
    private String nome;
    private Date dtContrato;
    private Double salario;

    // Construtor, getters e setters
    public Editor(long cpf, String nome, Date dtContrato, Double salario) {
        this.cpf = cpf;
        this.nome = nome;
        this.dtContrato = dtContrato;
        this.salario = salario;
    }
    
    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDtContrato() {
        return dtContrato;
    }

    public void setDtContrato(Date dtContrato) {
        this.dtContrato = dtContrato;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }
    
    @Override
    public String toString() {
        return String.format("CPF: %d - %s", cpf, nome);
    }
} 