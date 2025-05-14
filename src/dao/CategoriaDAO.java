package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;
import DataBase.Conexao;

public class CategoriaDAO {
    
    public static boolean inserir(Categoria categoria) {
        String sql = "INSERT INTO \"CATEGORIA-RECEITA\" (\"Cod-cat-rec\", \"Desc-cat-rec\") VALUES (?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int codCategoria;
            try {
                codCategoria = Integer.parseInt(categoria.getNome());
            } catch (NumberFormatException e) {
                System.out.println("Erro: O código da categoria deve ser um número inteiro.");
                return false;
            }
            
            stmt.setInt(1, codCategoria);
            stmt.setString(2, categoria.getDescricao());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir categoria: " + e.getMessage());
            return false;
        }
    }
    
    public static Categoria buscarPorNome(String nome) {
        String sql = "SELECT * FROM \"CATEGORIA-RECEITA\" WHERE \"Cod-cat-rec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int codCategoria;
            try {
                codCategoria = Integer.parseInt(nome);
            } catch (NumberFormatException e) {
                System.out.println("Erro: O código da categoria deve ser um número inteiro.");
                return null;
            }
            
            stmt.setInt(1, codCategoria);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Categoria(
                        String.valueOf(rs.getInt("Cod-cat-rec")),
                        rs.getString("Desc-cat-rec")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar categoria: " + e.getMessage());
        }
        
        return null;
    }
    
    public static List<Categoria> listarTodas() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM \"CATEGORIA-RECEITA\"";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Categoria categoria = new Categoria(
                    String.valueOf(rs.getInt("Cod-cat-rec")),
                    rs.getString("Desc-cat-rec")
                );
                
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar categorias: " + e.getMessage());
        }
        
        return categorias;
    }
    
    public static boolean atualizar(Categoria categoria, String nomeAntigo) {
        String sql = "UPDATE \"CATEGORIA-RECEITA\" SET \"Desc-cat-rec\" = ? WHERE \"Cod-cat-rec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int codCategoriaAntigo;
            try {
                codCategoriaAntigo = Integer.parseInt(nomeAntigo);
            } catch (NumberFormatException e) {
                System.out.println("Erro: O código da categoria deve ser um número inteiro.");
                return false;
            }
            
            stmt.setString(1, categoria.getDescricao());
            stmt.setInt(2, codCategoriaAntigo);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar categoria: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(String nome) {
        String sql = "DELETE FROM \"CATEGORIA-RECEITA\" WHERE \"Cod-cat-rec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int codCategoria;
            try {
                codCategoria = Integer.parseInt(nome);
            } catch (NumberFormatException e) {
                System.out.println("Erro: O código da categoria deve ser um número inteiro.");
                return false;
            }
            
            stmt.setInt(1, codCategoria);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir categoria: " + e.getMessage());
            return false;
        }
    }
} 