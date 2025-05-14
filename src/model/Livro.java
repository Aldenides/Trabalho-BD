package model;

public class Livro {
    private int isbn;
    private String titulo;
    
    public Livro(int isbn, String titulo) {
        this.isbn = isbn;
        this.titulo = titulo;
    }
    
    public int getIsbn() {
        return isbn;
    }
    
    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    @Override
    public String toString() {
        return String.format("ISBN: %d - %s", isbn, titulo);
    }
} 